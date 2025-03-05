<!-- <template>
    <div>
      <h1>商品列表</h1>
      <ul>
        <li v-for="product in products" :key="product.id">
          <router-link :to="`/product/${product.id}`">
            <strong>{{ product.name }}</strong>
          </router-link>
          <p>价格: ¥{{ product.price }}</p>
          <el-button type="primary" @click="addToCart(product.id)">加入购物车</el-button>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
import { fetchProducts } from '@/api/api.js'; // 绝对路径
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
        products: [],
      };
    },
    async mounted() {
      this.products = await fetchProducts();
    }
  };
  </script> -->

  <template>
    <div>
      <h1>商品列表</h1>
      <el-row :gutter="20">
        <el-col v-for="product in products" :key="product.id" :span="6">
          <el-card shadow="hover">
            <img :src="product.image" alt="商品图片" style="width: 100%" />
            <h3>{{ product.name }}</h3>
            <p>价格: ¥{{ product.price }}</p>
            <el-button type="primary" @click="addToCart(product)">加入购物车</el-button>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </template>
  
  <script>
  import axios from "@/api/api.js";
  
  export default {
    data() {
      return {
        products: []
      };
    },
    methods: {
      async fetchProducts() {
        try {
          const response = await axios.get("http://localhost:8080/api/products/list");
          this.products = response.data;
        } catch (error) {
          console.error("加载商品失败:", error);
        }
      },
      async addToCart(product) {
        try {
            const token = localStorage.getItem('token'); // 获取 token
        if (!token) {
          alert("请先登录");
          return;
        }
          await axios.post("http://localhost:8080/api/cart/items", { productId: product.id, quantity: 1 }, {
            // headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
            headers: { Authorization: `Bearer ${token}`}
          });
          alert("成功加入购物车！");
        } catch (error) {
          console.error("加入购物车失败:", error);
        }
      }
    },
    mounted() {
      this.fetchProducts();
    }
  };
  </script>
  