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
                    <span style="font-size: 130%;">我的Bot</span>
                    <button type="button" class="btn btn-success float-end" data-bs-toggle="modal" data-bs-target="#add-bot-btn">
                        创建Bot
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="add-bot-btn" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-xl">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="exampleModalLabel">创建Bot</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                            <div class="modal-body">
                                <form>
                                    <div class="mb-3">
                                        <label for="add-bot-btn" class="form-label">名称</label>
                                        <input type="text" class="form-control" id="add-bot-btn" placeholder="请输入Bot名称">
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-description" class="form-label">简介</label>
                                        <textarea class="form-control" id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-code" class="form-label">代码</label>
                                        <textarea class="form-control" id="add-bot-code" rows="10" placeholder="请编写Bot代码"></textarea>
                                    </div>
                                    </form>
                            </div>
                                <div class="modal-footer">
                                    <div class="error-message">标题不能为空</div>
                                    <button type="button" class="btn btn-primary">提交</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>名称</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="bot in bots" :key="bot.id">
                                <td>{{ bot.title }}</td>
                                <td>{{ bot.createtime }}</td>
                                <td>
                                    <button type="button" class="btn btn-secondary" style="margin-right: 12px;">修改</button>
                                    <button type="button" class="btn btn-danger">删除</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


</template>

<script>
import { ref } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'


export default {
    setup() {
        const store = useStore();
        let bots = ref([]);

        const refresh_bots = () => {
                $.ajax({
                    url: "http://127.0.0.1:3000/user/bot/getlist/",
                    type: "get",
                    headers: {
                        Authorization: "Bearer " + store.state.user.token,
                    },
                    success(resp) {
                        bots.value = resp;
                    }
                })
            }


        refresh_bots();
        return {
            bots    //这里要返回
        }
    }
}
</script>

<style scoped>
</style>