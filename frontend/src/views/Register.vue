<template>
    <div>
      <h2>用户注册</h2>
      <el-form>
        <el-form-item label="用户名">
          <el-input v-model="username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="password" type="password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-button type="primary" @click="register">注册</el-button>
      </el-form>
    </div>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import api from '@/api/api.js';
  
  const username = ref('');
  const password = ref('');
  const email = ref('');
  
  const register = async () => {
    try {
      await api.post('/users/register', { username: username.value, password: password.value, email: email.value });
      alert('注册成功！');
    } catch (error) {
      console.error('注册失败', error);
    }
  };
  </script>
  