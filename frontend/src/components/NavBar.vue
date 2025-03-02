<template>
    <el-menu mode="horizontal" router>
      <el-menu-item index="/">首页</el-menu-item>
      <el-menu-item index="/cart">购物车</el-menu-item>
      <el-menu-item index="/orders">订单</el-menu-item>
      <el-menu-item v-if="!isLoggedIn" index="/login">登录</el-menu-item>
      <el-menu-item v-if="!isLoggedIn" index="/register">注册</el-menu-item>
      <el-menu-item v-if="isLoggedIn" index="/profile">个人中心</el-menu-item>
      <el-menu-item v-if="isLoggedIn" @click="logout">退出</el-menu-item>
    </el-menu>
  </template>
  
  <script setup>
  import { ref } from 'vue';
  import { useRouter } from 'vue-router';
  
  const router = useRouter();
  const isLoggedIn = ref(!!localStorage.getItem("token"));
  
  const logout = () => {
    localStorage.removeItem("token");
    router.push("/login");
  };
  </script>
  