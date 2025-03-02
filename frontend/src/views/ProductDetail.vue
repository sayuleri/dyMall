<template>
    <div v-if="product">
      <h2>{{ product.name }}</h2>
      <p>{{ product.description }}</p>
      <p>价格: ¥{{ product.price }}</p>
    </div>
    <div v-else>
      <p>加载中...</p>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import { fetchProductDetail } from '@/api/api.js';
  
  export default {
    setup() {
      const product = ref(null);
      const route = useRoute();
  
      onMounted(async () => {
        product.value = await fetchProductDetail(route.params.id);
      });
  
      return { product };
    }
  };
  </script>
  