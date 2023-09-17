<template>
        <PlayGround v-if= "$store.state.pk.status === 'playing'"/>
        <MatchGround v-if="$store.state.pk.status === 'matching'"/>
        <ResultBoard v-if="$store.state.pk.loser != 'none'" />  
        <div class="user-color-blue" v-if="$store.state.pk.status === 'playing' && parseInt($store.state.user.id) === parseInt($store.state.pk.a_id)">
            蓝方
        </div>
        <div class="user-color-red" v-if="$store.state.pk.status === 'playing' && parseInt($store.state.user.id) === parseInt($store.state.pk.b_id)">
            红方
        </div> 

</template>

<script>
import PlayGround from  '@/components/PlayGround.vue'
import MatchGround from '@/components/MatchGround.vue'
import ResultBoard from '@/components/ResultBoard.vue';
import { onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex'

export default {
    components: {
        PlayGround,
        MatchGround,
        ResultBoard,
    },
    setup() {
        const store =  useStore();
        const socketUrl = `wss://app5749.acapp.acwing.com.cn/websocket/${store.state.user.token}/`;
        store.commit("updateLoser", "none");    // 比赛结束后切换页面切回pk页面是， result页面自动关闭
        store.commit("updateIsRecord", false);
        
        let socket = null;
        onMounted(() => {

            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })


            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("connected!");
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                if (data.event === "start-matching") {  // 匹配成功
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");
                    }, 200);
                    store.commit("updateGame", data.game);
                } else if (data.event === "move") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);
                } else if (data.event === "result") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;

                    if (data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if (data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                    store.commit("updateLoser", data.loser);
                }
            }

            socket.onclose = () => {
                console.log("disconnect!");
            }
        });

        onUnmounted(() => {
            socket.close();
            store.commit("updateStatus", "matching");
        })
    }
}
</script>
 
<style scoped>
div.user-color-red {
    text-align: center;
    color: lightcoral;
    font-size: 30px;
    font-weight: 600;
}
div.user-color-blue {
    text-align: center;
    color: lightskyblue;
    font-size: 30px;
    font-weight: 600;
}

</style>