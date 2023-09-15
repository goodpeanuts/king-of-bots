<template>  
    <div class="playground-blue" v-if="$store.state.pk.status === 'playing' && parseInt($store.state.user.id) === parseInt($store.state.pk.a_id)">
        <GameMap />
    </div>
    <div class="playground-red" v-if="$store.state.pk.status === 'playing' && parseInt($store.state.user.id) === parseInt($store.state.pk.b_id)">
        <GameMap />
    </div> 
</template>

<script>
import  GameMap  from './GameMap.vue';

export default {
    components: {
        GameMap,
    }
}
</script>

<style scoped>
div.playground-blue {
    height: 73vh;
    background: rgb(60, 130, 235);
    margin: 70px auto;
    aspect-ratio: 14 / 13; /* 设置容器的宽高比为1:1，使其保持正方形 */
}
div.playground-red {
    height: 73vh;
    background: rgb(226, 75, 64);
    margin: 70px auto;
    aspect-ratio: 14 / 13; /* 设置容器的宽高比为1:1，使其保持正方形 */
}
</style> 