import { AcGameObject  } from "./AcGameObject"; 
import { Wall } from "./Wall";

// export 的对象 import时需要用大括号将其括起 
// export default 的对象则不需要，但是一个文件只能有一个default

export class GameMap extends AcGameObject {
    constructor(ctx, parent) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13;
        this.cols = 14;

        this.inner_walls_count = 20;
        this.walls = [];
    }

    //确保连通性
    check_coonnectivity(g, sx, sy, tx, ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = true;

        let dx = [-1, 0, 1, 0], dy = [0, 1, 0, -1];
        for (let i = 0; i < 4; i++) {
            let x = sx + dx[i], y = sy + dy[i];
            if (!g[x][y] && this.check_coonnectivity(g, x, y, tx, ty))
            return true;
        }
        return false;
    }
    
    create_walls() {
        const g = [];
        for (let r = 0; r < this.rows; r++) {
            g[r] = [];
            for (let c = 0; c < this.cols; c++) {
                g[r][c] = false;
            }
        }

        // 四周加上围墙
        for (let r = 0; r < this.rows; r++) {
            g[r][0] = g[r][this.cols - 1] =true;
        }

        for (let c = 0; c < this.cols; c++) {
            g[0][c] = g[this.rows - 1][c] =true;
        }

        // 创建随机障碍物
        for (let i = 0; i < this.inner_walls_count; i++) {
            for (let j = 0; j < 1000; j++) {
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);

                if (g[r][c] || g[c][r]) continue;
                if (r == this.row - 2 && c == 1 || r == 1 && c == this.cols - 2 ) 
                    continue;

                g[r][c] = g[c][r] = true;
                break;
            }
        }

        const copy_g = JSON.parse(JSON.stringify(g));
        if (!this.check_coonnectivity(copy_g, this.rows - 2, 1, 1, this.cols - 2 )) return false;


        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }

    start() {
        for (let i = 0; i < 1000; i++){
            if (this.create_walls())
                break;
        }
    }
 
    update_size() {
        //this.L = Math.min(this.parent.clientwidth / this.cols, this.parent.clientwidth / this.rows);
        //上面一行代码只有这样改能够实现加载绿色
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    update() {
        this.update_size();  
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