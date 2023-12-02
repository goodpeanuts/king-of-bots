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
                        <span style="font-size: 130%;">我的反馈</span>
                        <button type="button" class="btn btn-success float-end" data-bs-toggle="modal" data-bs-target="#add-feedback-btn">
                            创建反馈
                        </button>
    
                        <!-- Modal -->
                        <div class="modal fade" id="add-feedback-btn" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5" >创建反馈</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                <div class="modal-body">
                                    <form>
                                        <div class="mb-3">
                                            <label for="add-feedback-btn" class="form-label">标题</label>
                                            <input v-model="feedbackadd.title" type="text" class="form-control" id="add-feedback-btn" placeholder="请输入反馈标题">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-feedback-description" class="form-label">内容</label>
                                            <textarea v-model="feedbackadd.description" class="form-control" id="add-feedback-description" rows="3" placeholder="请输入反馈内容"></textarea>
                                        </div>
                                        <p>附件上传</p>
                                            <div>
                                            文件：
                                            <input id="file-upload" type="file" name="file">
                                            </div>
                                    </form>
                                </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ feedbackadd.error_message }}</div>
                                        <button type="button" class="btn btn-primary" @click="add_feedback">创建</button>
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
                                    <th>标题</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="feedback in feedbacks" :key="feedback.id">
                                    <td>{{ feedback.title }}</td>
                                    <td>{{ feedback.createtime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 12px;" data-bs-toggle="modal" :data-bs-target="'#updata-feedback-' + feedback.id">修改</button>
                                        <button type="button" class="btn btn-danger" @click="remove_feedback(feedback)">删除</button>
    
                                        <!-- Modal -->
                                        <div class="modal fade" :id="'updata-feedback-' + feedback.id" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-xl">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h1 class="modal-title fs-5">修改反馈</h1>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                    </div>
                                                <div class="modal-body">
                                                    <form>
                                                        <div class="mb-3">
                                                            <label for="add-feedback-btn" class="form-label">名称</label>
                                                            <input v-model="feedback.title" type="text" class="form-control" id="add-feedback-btn" placeholder="请输入feedback名称">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-feedback-description" class="form-label">简介</label>
                                                            <textarea v-model="feedback.description" class="form-control" id="add-feedback-description" rows="3" placeholder="请输入feedback简介"></textarea>
                                                        </div>
                                                        </form>
                                                </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{ feedback.error_message }}</div>
                                                        <button type="button" class="btn btn-primary" @click="update_feedback(feedback)">保存修改</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
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
    import { ref, reactive } from 'vue'
    import $ from 'jquery'
    import { useStore } from 'vuex'
    import { Modal } from 'bootstrap/dist/js/bootstrap' // 用于提交完信息后自动关闭对话框，路径要写全
    import ace from 'ace-builds';
    
    export default {
    
        setup() {
            ace.config.set(
                "basePath",
                "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")
    
            const store = useStore();
            let feedbacks = ref([]);
    
            const feedbackadd = reactive({
                title: "",
                description: "",
                content: "",
                error_message: "",
            });
    
            const refresh_feedbacks = () => {
                    $.ajax({
                        url: "http://localhost:3000/api/user/feedback/getlist/",
                        type: "get",
                        headers: {
                            Authorization: "Bearer " + store.state.user.token,
                        },
                        success(resp) {
                            feedbacks.value = resp;
                        }
                    })
                }
    
    
            refresh_feedbacks();
    
            const add_feedback = () => {
                let formData = new FormData();
                let file = document.getElementById('file-upload').files[0];
                formData.append('file', file);
                formData.append('title', feedbackadd.title);
                formData.append('description', feedbackadd.description);
                formData.append('content', feedbackadd.content);

                feedbackadd.error_message = "",
                $.ajax({
                        url: "http://localhost:3000/api/user/feedback/add/",
                        type: "post",
                        headers: {
                            Authorization: "Bearer " + store.state.user.token,
                        },
                        data: formData,
                        processData: false,
                        contentType: false, 
                        success(resp) {
                            if (resp.error_message === "success") {
                                feedbackadd.title ="",
                                feedbackadd.description = "",
                                feedbackadd.content = "",
                                Modal.getInstance("#add-feedback-btn").hide();
                                refresh_feedbacks();
                            } else {
                                feedbackadd.error_message = resp.error_message;
                            }
                        }
                    })
            }
    
            const update_feedback = (feedback) => {
                feedback.error_message = "",
                $.ajax({
                        url: "http://localhost:3000/api/user/feedback/update/",
                        type: "post",
                        headers: {
                            Authorization: "Bearer " + store.state.user.token,
                        },
                        data: {
                            feedback_id: feedback.id,
                            title: feedback.title,
                            description: feedback.description,
                            content: feedback.content,
                        },
                        success(resp) {
                            if (resp.error_message === "success") {
                                Modal.getInstance('#updata-feedback-' + feedback.id).hide();
                                refresh_feedbacks();
                            } else {
                                feedback.error_message = resp.error_message;
                            }
                        }
                    })
            }
    
            const remove_feedback = (feedback) => {
                $.ajax({
                        url: "http://localhost:3000/api/user/feedback/remove/",
                        type: "post",
                        headers: {
                            Authorization: "Bearer " + store.state.user.token,
                        },
                        data: {
                            feedback_id: feedback.id,
                        },
                        success(resp) {
                            if (resp.error_message === "success") {
                                refresh_feedbacks();
                            } else {
                                feedbackadd.error_message = resp.error_message;
                            }
                        }
                    })
            }
    
            return {    
                feedbacks,    //这里要返回,否则列表加载不出feedback
                feedbackadd,
                add_feedback,
                update_feedback,
                remove_feedback
            }
        }
    }
    </script>
    
    <style scoped>
    div.error-message {
        color: red;
    }
    </style>