<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 25px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                    </div>
                </div>
            </div>
            <div class="col-9">
                <div class="card" style="margin-top: 25px;">
                    <div class="card-header" >
                        <span style="font-size: 130%;">服务器状态</span>
                        <button type="button" class="btn btn-success float-end" @click="refresh_status">
                            检查
                        </button>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>服务器</th>
                                    <th>状态</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="server in server_list" :key="server.id" >
                                <td :class="{ offline: server.status === '离线', online: server.status === '在线' }">{{ server.name }}</td>
                                <td :class="{ offline: server.status === '离线', online: server.status === '在线' }">{{ server.status }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>  
            </div>
        </div>
        <div class="col-9">
                <div class="card" style="margin-top: 25px;">
                    <div class="card-header" >
                        <span style="font-size: 130%;">服务器    </span>
                        <input type="text" v-model="cmd.content" placeholder="请输入ip">
                        <button type="button" class="btn btn-success float-end" @click="return_cmd">
                            检查
                        </button>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>执行结果</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>{{ result }}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>  
            </div>
    </div>
    
    
    </template>
    
    <script>
    import { ref, reactive } from 'vue'
    import $ from 'jquery'
    import { useStore } from 'vuex'
    
    export default {
    
        setup() {
            const store = useStore();
            const result = ref("");

            const cmd = reactive({
                content: ""
            });

            let server_list = ref([
                { id: 1, name: "游戏服务", status: "" },
                { id: 2, name: "匹配服务", status: "" },
                { id: 3, name: "Bots运行服务", status: "" },
            ]);
    
    
            const refresh_status = () => {
                    $.ajax({
                        url: "http://localhost:3000/api/user/server/refresh/",
                        type: "get",
                        headers: {
                            Authorization: "Bearer " + store.state.user.token,
                        },
                        success(resp) {
                            server_list.value.forEach((server, index) => {
                                server.status = resp[index];
                            });
                        },
                        error() {
                            server_list.value.forEach(server => {
                            server.status = "离线";
                            });
                        }
                    })
                }
            refresh_status();

            const return_cmd = () => {
                    $.ajax({
                        url: "http://localhost:3000/api/user/server/cmd/",
                        type: "post",
                        data: {
                            content: cmd.content
                        },
                        success(resp) {
                            result.value = resp;
                        },
                        error(resp) {
                            result.value = resp;
                        }
                    })
                }

            return {    
                refresh_status,
                return_cmd,
                server_list,
                result,
                cmd
            }
        }
    }
    </script>
    
    <style scoped>
    div.error-message {
        color: red;
    }
    .offline {
        background-color: rgb(206, 147, 99);
        font-family: Arial, sans-serif;
        font-size: 16px;
        font-weight: bold;
    }
    .online {
        background-color: rgb(165, 226, 181);
        font-family: Arial, sans-serif;
        font-size: 16px;
        font-weight: bold;
    }
    </style>