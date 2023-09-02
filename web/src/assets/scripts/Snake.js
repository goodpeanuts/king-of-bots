import { AcGameObject } from "./AcGameObject";
import { Cell } from "./Cell";

export class Snake extends AcGameObject {
    constructor(info, gamemap) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.gamemap = gamemap;

        this.cells = [new Cell(info.r, info.c)]; //存放蛇的身体，Cell[0]存放蛇头
        this.next_cell = null; // 下一步的目标位置

        this.speed = 5; // 每秒走5个格子
        this.direction = -1;  // -1 表示没有指定，0、1、2、3表示上右下左
        this.status = "idle"; // idle 表示静止， move表示正在移动， die表示死亡

        this.dr = [-1, 0, 1, 0];    // 4个方向行偏移量
        this.dc = [0, 1, 0, -1];    // 4个方向列偏移量

        this.step = 0; // 当前回合数
        this.eps = 1e-2; // 允许的误差用于判断两个点是否重合

    }

    start() {

    } 
    
    set_direction(d) { // 接受方向的接口
        this.direction = d;
    }

    check_tail_increasing() { // 检测当前回合蛇长度是否增加
        if (this.step <= 10) return true;
        if (this.step % 3 === 1) return true;
        return false;
    }

    next_step() { // 将蛇的状态变为走下一步
        const d = this.direction;
        this.next_cell = new Cell(this.cells[0].r + this.dr[d], this.cells[0].c + this.dc[d]);
        this.direction = -1; // 走完一步后清空方向
        this.status = "move";
        this.step ++;

        const k = this.cells.length;
        for (let i = k; i > 0; i--) {
            this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1])); // JSON字符串化实现深层复制, 直接复制是引用
        }

        if (!this.gamemap.check_valid(this.next_cell)) { // 下一步撞墙，直接去世
            this.status = "die";
        }
    }

    update_move() { 
        const dx = this.next_cell.x - this.cells[0].x;
        const dy = this.next_cell.y - this.cells[0].y;
        const distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < this.eps) {
            this.cells[0] = this.next_cell;
            this.next_cell = null;
            this.status = "idle"; // 走完了停下来

            if (!this.check_tail_increasing()) { // 蛇不变长的情况把尾巴丢了
                this.cells.pop();
            }
        } else {
            const move_distance = this.speed * this.timedelta / 1000;
            this.cells[0].x += move_distance * dx / distance;
            this.cells[0].y += move_distance * dy / distance;

            if (!this.check_tail_increasing()) {    //蛇长度增加的情况尾巴不动
                const k = this.cells.length;
                const tail = this.cells[k - 1], tail_target = this.cells[k - 2]; 
                const tail_dx = tail_target - tail.x;
                const tail_dy = tail_target - tail.y;
                tail.x += move_distance * tail_dx / distance;   
                tail.y += move_distance * tail_dy / distance;
            }
        }
    }

    update() { // 每一帧执行一次
        if (this.status === 'move') {
            this.update_move(); 
        } 
        this.render();
    }

    render() {
        const L = this.gamemap.L;
        const ctx = this.gamemap.ctx;

        ctx.fillStyle = this.color;
        if (this.status === "die") {
            ctx.fillStyle = "white";
        }

        for (const cell of this.cells) {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L / 2 * 0.8, 0, Math.PI * 2);
            ctx.fill();
        }

        for (let i = 1; i < this.cells.length; i++) {
            const a = this.cells[i - 1], b = this.cells[i];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps)
                continue;
            if (Math.abs(a.x - b.x) < this.eps) {
                ctx.fillRect((a.x - 0.4) * L, Math.min(a.y, b.y) * L, L * 0.8, Math.abs(a.y - b.y) * L);
            } else {
                ctx.fillRect(Math.min(a.x, b.x) * L, (a.y - 0.4) * L, Math.abs(a.x - b.x) * L, L * 0.8); 
            }
        }
    }
}