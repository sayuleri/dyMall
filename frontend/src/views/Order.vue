<template>
    <div>
      <h2>订单列表</h2>
      <el-table :data="orders">
        <el-table-column prop="id" label="订单ID" />
        <el-table-column prop="totalPrice" label="总价" />
        <el-table-column prop="status" label="状态" />
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button @click="cancelOrder(row.id)">取消订单</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import api from '@/api/api.js';
  
  const orders = ref([]);
  
  const fetchOrders = async () => {
    try {
      const res = await api.get('/order/list');
      orders.value = res.data;
    } catch (error) {
      console.error('获取订单失败', error);
    }
  };
  
  const cancelOrder = async (orderId) => {
    await api.delete('/order/cancel', { data: { orderId } });
    fetchOrders();
  };
  
  onMounted(fetchOrders);
  </script>
  