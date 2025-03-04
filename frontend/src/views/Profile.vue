<template>
    <div>
      <h2>个人中心</h2>
      <div v-if="user">
        <p>用户名: {{ user.username }}</p>
        <button @click="logout">退出登录</button>
      </div>
      <p v-else>请先登录...</p>
    </div>
  </template>
  
  <script>
  import { fetchUserProfile } from '@/api/api.js';
  
  export default {
    data() {
      return {
        user: null,
      };
    },
    async created() {
      const token = localStorage.getItem('token');
      if (!token) {
        console.warn("❌ 用户未登录，跳转到登录页");
        this.$router.push('/login'); // ✅ 重新跳转到登录页
        return;
      }
  
      try {
        console.log("📥 正在获取用户信息...");
        this.user = await fetchUserProfile();
        console.log("✅ 用户信息获取成功:", this.user);
      } catch (error) {
        console.error('❌ 获取用户信息失败:', error);
        this.logout();
      }
    },
    methods: {
      logout() {
        localStorage.removeItem('token');
        this.$router.push('/login');
      },
    },
  };
  </script>
  