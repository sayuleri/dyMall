<template>
  <NavBar />
  <div>
    <h1>欢迎来到 dyMall</h1>
    <router-view></router-view>
  </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue';
</script>

<style>
h1 {
  text-align: center;
  margin-top: 50px;
}
</style>
