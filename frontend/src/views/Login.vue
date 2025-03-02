<template>
    <div>
      <h2>用户登录</h2>
      <input v-model="email" placeholder="邮箱">
      <input v-model="password" type="password" placeholder="密码">
      <button @click="login">登录</button>
    </div>
</template>
  
<script>
    import { loginUser } from '@/api/api.js'; // 改成绝对路径
  
    export default {
        data() {
        return {
            email: '',
            password: '',
        };
        },
        methods: {
        async login() {
            try {
            await loginUser(this.email, this.password);
            alert('登录成功');
            this.$router.push('/home');
            } catch (error) {
            alert('登录失败，请检查用户名和密码');
            }
        }
        }
    };
</script>
  