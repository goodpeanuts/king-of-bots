import { AcGameObject  } from "./AcGameObject"; 
import { Wall } from "./Wall";
import { Snake } from "./Snake";

// export 的对象 import时需要用大括号将其括起 
// export default 的对象则不需要，但是一个文件只能有一个default

export class GameMap extends AcGameObject {
    constructor(ctx, parent, store) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;

        this.rows = 13;
        this.cols = 14;

        this.inner_walls_count = 20;
        this.walls = [];

        this.snakes = [
            new Snake({id: 0, color: "#4876EC", r: this.rows - 2, c: 1}, this),
            new Snake({id: 1, color: "#F94848", r: 1, c: this.cols - 2}, this),
        ];
    }
    
    create_walls() {
        const g = this.store.state.pk.gamemap;

        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    add_listening_events() {
        console.log(this.store.state.record);

        if (this.store.state.record.is_record) {
            let k = 0;

            const a_steps = this.store.state.record.a_steps;
            const b_steps = this.store.state.record.b_steps;
            const loser = this.store.state.record.record_loser;
            const [snake0, snake1] = this.snakes;
            const interval_id = setInterval(() => { // 每300毫秒执行一次方向设定与移动
                if (k >= a_steps.length - 1) {      // 移动到下一次的第一刻前，下一个位置可能会死亡
                    if (loser === "all" || loser === "A") {
                        snake0.status = "die";
                    }
                    if (loser === "all" || loser === "B") {
                        snake1.status = "die";
                    }
                    clearInterval(interval_id);
                } else {                            // 判定为死亡前移动
                    snake0.set_direction(parseInt(a_steps[k]));
                    snake1.set_direction(parseInt(b_steps[k]));
                }
                k ++ ;
            }, 300);
        } else {
            this.ctx.canvas.focus();
            
            this.ctx.canvas.addEventListener("keydown", e => {
                let d = -1;
                if (e.key === 'w') d = 0;
                else if (e.key === 'd') d = 1;
                else if (e.key === 's') d = 2;
                else if (e.key === 'a') d = 3;

                if (d >= 0) {
                    this.store.state.pk.socket.send(JSON.stringify({
                        event: "move",
                        direction: d,
                    }));
                }
            });
        }
    }

    start() {
        this.create_walls();
        this.add_listening_events();
    }
 
    update_size() {
        //this.L = Math.min(this.parent.clientwidth / this.cols, this.parent.clientwidth / this.rows);
        //上面一行代码只有这样改能够实现加载绿色
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    check_ready() { // 判断两条蛇是否都准备好
        for (const snake of this.snakes) { 
            if (snake.status !== "idle") return false; // 当两条蛇都不静止也就是该回合还没走完
            if (snake.direction === -1) return false;  // 蛇还没有收到本回合指令
        }
        return true;
    }

    next_step() {
        for (const snake of this.snakes) {
            snake.next_step();
        }
    }

    check_valid(cell) { // 检测碰撞
        for (const wall of this.walls) {
            if (wall.r === cell.r && wall.c === cell.c)
                return false;
        }

        for (const snake of this.snakes) {
            let k = snake.cells.length;
            if (!snake.check_tail_increasing()) { // 当蛇尾会前进时，不判断
                k --;
            }
            for (let i = 0; i < k; i ++) {
                if (snake.cells[i].r === cell.r && snake.cells[i].c === cell.c)
                    return false;
            }
        }
        
        return true;
    }

    update() {
        this.update_size();  
        if (this.check_ready()) {
            this.next_step();
        }
        this.render();
    }

    render() {     
        const coler_even = "#AAD751", coler_odd = "#A2D149";
        for (let r = 0; r< this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if ((r + c) % 2 == 0) {
                    this.ctx.fillStyle = coler_even;
                } else { 
                    this.ctx.fillStyle = coler_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }

}