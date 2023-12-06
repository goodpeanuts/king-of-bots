<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
                    <div class="mb-3">
                        <label for="username" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="oldPassword" class="form-label">旧密码</label>
                        <input v-model="oldPassword" type="password" class="form-control" id="oldPassword" placeholder="请输入旧密码">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">新密码</label>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="请新输入密码">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">确认密码</label>
                        <input v-model="confirmPassword" type="password" class="form-control" id="confirmPassword" placeholder="请再次输入密码">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">提交</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue';
import { ref } from 'vue'
import router from '@/router';
import $ from 'jquery'

export default {
    components: {
        ContentField
    },

    setup() {
        let username = ref('');
        let oldPassword = ref('');
        let password = ref('');
        let confirmPassword = ref('');
        let error_message = ref('');

        const register = () => {
            $.ajax({
                url: "http://localhost:3000/api/user/account/reset/",
                type: "post",
                data: {
                    username: username.value,
                    oldPassword: oldPassword.value,
                    password: password.value,
                    confirmPassword: confirmPassword.value,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        router.push({name: "user_account_login"});
                    } else {
                        error_message.value = resp.error_message;
                    }
                }
            });
        }

        return {
            username,
            oldPassword,
            password,
            confirmPassword,
            error_message,
            register
        }
    }
}
</script>

<style scoped>
button {
    width: 100%;
}
div.error-message {
    color: red;
    justify-content: center;
}
</style>