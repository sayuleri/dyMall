<template>
    <div>
      <h2>购物车</h2>
      <el-table :data="cartItems">
        <el-table-column prop="productId" label="商品ID" />
        <el-table-column prop="quantity" label="数量" />
        <el-table-column prop="price" label="单价" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="danger" @click="removeItem(row.productId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-button type="primary" @click="checkout">结算订单</el-button>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import api from '@/api/api.js';
  
  const cartItems = ref([]);
  
  const fetchCart = async () => {
    try {
      const res = await api.get('/cart/items');
      cartItems.value = res.data;
    } catch (error) {
      console.error('获取购物车失败', error);
    }
  };
  
  const removeItem = async (productId) => {
    await api.post('/cart/remove', { productId });
    fetchCart();
  };
  
  const checkout = async () => {
    await api.post('/order/create');
    alert('订单已创建！');
    fetchCart();
  };
  
  onMounted(fetchCart);
  </script>
  