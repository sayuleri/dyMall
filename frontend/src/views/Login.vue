<template>
    <div>
      <h2>用户登录</h2>
      <form @submit.prevent="handleLogin">
        <input v-model="username" type="text" placeholder="请输入用户名" required />
        <input v-model="password" type="password" placeholder="请输入密码" required />
        <button type="submit">登录</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </form>
    </div>
  </template>
  
  <script>
  import { loginUser } from '@/api/api'; // ✅ 确保路径正确
  import { ref } from 'vue';
import { ElTable, ElTableColumn, ElButton, ElInput } from 'element-plus';

  export default {
    components: {
    ElTable,
    ElTableColumn,
    ElButton,
    ElInput
    },
    data() {
      return {
        username: '',
        password: '',
        errorMessage: '',
      };
    },
    mounted() {
      console.log("✅ Login.vue 已加载");
    },
    methods: {
      async handleLogin() {
        try {
          console.log("📤 发送登录请求:", { username: this.username, password: this.password });
  
          const response = await loginUser(this.username, this.password);
          
          console.log("✅ 登录成功，Token:", response.token);
  
          localStorage.setItem('token', response.token);
  
          console.log("🚀 即将跳转到 /profile...");
          this.$router.push('/profile'); // ✅ 确保 Vue Router 正确跳转
  
        } catch (error) {
          this.errorMessage = '登录失败，请检查用户名和密码';
          console.error("❌ 登录错误:", error);
        }
      },
    },
  };
  </script>
  