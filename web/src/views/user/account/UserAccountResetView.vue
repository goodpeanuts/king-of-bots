<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
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
import { useStore } from 'vuex'
import router from '@/router';
import $ from 'jquery'

export default {
    components: {
        ContentField
    },

    setup() {
        let password = ref('');
        let confirmPassword = ref('');
        let error_message = ref('');
        const store = useStore();

        const register = () => {
            $.ajax({
                url: "http://localhost:3000/api/user/account/reset/",
                type: "post",
                headers: {
                        Authorization: "Bearer " + store.state.user.token,
                },
                data: {
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