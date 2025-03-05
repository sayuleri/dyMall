<!-- <template>
    <div class="order-container">
      <h2>订单列表</h2>
      <el-table :data="orders" border style="width: 100%">
        <el-table-column prop="id" label="订单号" width="180"></el-table-column>
        <el-table-column prop="total" label="总金额" width="100"></el-table-column>
        <el-table-column prop="status" label="订单状态" width="150"></el-table-column>
        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button type="info" @click="viewOrder(row)">查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <el-dialog v-model="showOrderDetail" title="订单详情">
        <el-table :data="selectedOrder.items" border style="width: 100%">
          <el-table-column prop="name" label="商品名称"></el-table-column>
          <el-table-column prop="price" label="单价"></el-table-column>
          <el-table-column prop="quantity" label="数量"></el-table-column>
        </el-table>
        <p>总金额: ¥{{ selectedOrder.total }}</p>
        <el-button @click="showOrderDetail = false">关闭</el-button>
      </el-dialog>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from "vue";
  import { getOrders } from "@/api/api.js";
  import { ElTable, ElTableColumn, ElButton, ElDialog } from "element-plus";
  
  export default {
    components: {
      ElTable,
      ElTableColumn,
      ElButton,
      ElDialog
    },
    setup() {
      const orders = ref([]);
      const selectedOrder = ref({});
      const showOrderDetail = ref(false);
  
      const fetchOrders = async () => {
        try {
          const response = await getOrders();
          orders.value = response;
        } catch (error) {
          console.error("❌ 获取订单失败:", error);
        }
      };
  
      const viewOrder = (order) => {
        selectedOrder.value = order;
        showOrderDetail.value = true;
      };
  
      onMounted(fetchOrders);
  
      return { orders, selectedOrder, showOrderDetail, viewOrder };
    },
  };
  </script>
  
  <style>
  .order-container {
    max-width: 900px;
    margin: auto;
  }
  </style> -->

  <template>
    <div>
      <h2>订单列表</h2>
      <el-table :data="orders" border>
        <el-table-column prop="id" label="订单ID"></el-table-column>
        <el-table-column prop="total" label="总金额"></el-table-column>
        <el-table-column prop="status" label="订单状态"></el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  import axios from "@/api/api.js";
  export default {
    data() {
      return {
        orders: []
      };
    },
    methods: {
        async fetchOrders() {
  try {
    const token = localStorage.getItem('token');
    onsole.log("当前 Token:", token);
    if (!token) {
      console.warn("用户未登录，无法获取订单");
      return;
    }

    const response = await axios.get('http://localhost:8080/api/order/list', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    console.log(`Bearer ${localStorage.getItem("token")}`);
    this.orders = response.data;
    } catch (error) {
        console.error('订单加载失败:', error.response?.data || error);
    }
    }
    },
    mounted() {
      this.fetchOrders();
    }
  };
  </script>
  