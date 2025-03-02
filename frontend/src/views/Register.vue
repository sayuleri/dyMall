<template>
    <div>
      <h1>用户注册</h1>
      <form @submit.prevent="register">
        <label>用户名:</label>
        <input v-model="username" type="text" required />
        <br />
  
        <label>邮箱:</label>
        <input v-model="email" type="email" required />
        <br />
  
        <label>密码:</label>
        <input v-model="password" type="password" required />
        <br />
  
        <button type="submit">注册</button>
      </form>
    </div>
  </template>
  
  <script>
  import api from '@/api/api.js';
  
  export default {
    data() {
      return {
        username: '',
        email: '',
        password: '',
      };
    },
    methods: {
      async register() {
        try {
          const response = await api.post('/users/register', {
            username: this.username,
            email: this.email,
            password: this.password,
          });
  
          alert('注册成功，请登录');
          this.$router.push('/login'); // 注册成功后跳转到登录页
        } catch (error) {
          alert('注册失败，请检查输入信息');
        }
      },
    },
  };
  </script>
  