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
        this.cols = 13;

        this.walls = [];
    }

    create_walls() {
        const g = [];
        for (let r = 0; r < this.rows; r++) {
            g[r] = [];
            for (let c = 0; c < this.cols; c++) {
                g[r][c] = false;
            }
        }

        //四周加上围墙
        for (let r = 0; r < this.rows; r++) {
            g[r][0] = g[r][this.cols - 1] =true;
        }

        for (let c = 0; c < this.cols; c++) {
            g[0][c] = g[this.rows - 1][c] =true;
        }


        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    start() {
        this.create_walls();
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