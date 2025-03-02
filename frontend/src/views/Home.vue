<template>
    <div>
      <h1>商品列表</h1>
      <ul>
        <li v-for="product in products" :key="product.id">
          <router-link :to="`/product/${product.id}`">
            <strong>{{ product.name }}</strong>
          </router-link>
          <p>价格: ¥{{ product.price }}</p>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
import { fetchProducts } from '@/api/api.js'; // 绝对路径
  
  export default {
    data() {
      return {
        products: [],
      };
    },
    async mounted() {
      this.products = await fetchProducts();
    }
  };
  </script>
  