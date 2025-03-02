<template>
    <div>
      <h2>个人中心</h2>
      <p>用户名: {{ user.username }}</p>
      <p>邮箱: {{ user.email }}</p>
      <button @click="logout">退出登录</button>
    </div>
  </template>
  
  <script>
  import api from '@/api/api.js';
  
  export default {
    data() {
      return {
        user: {},
      };
    },
    async mounted() {
      try {
        const response = await api.get('/user'); // 获取用户信息
        this.user = response.data;
      } catch (error) {
        console.error('获取用户信息失败', error);
      }
    },
    methods: {
      logout() {
        localStorage.removeItem('token'); // 删除 Token
        this.$router.push('/login'); // 跳转到登录页面
      },
    },
  };
  </script>
  