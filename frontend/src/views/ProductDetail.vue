<!-- <template>
    <div v-if="product">
      <h2>{{ product.name }}</h2>
      <p>{{ product.description }}</p>
      <p>价格: ¥{{ product.price }}</p>
      <el-input-number v-model="quantity" :min="1"></el-input-number>
      <el-button type="primary" @click="addToCart">加入购物车</el-button>
    </div>
    <div v-else>
      <p>加载中...</p>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRoute } from 'vue-router';
  import { fetchProductDetail } from '@/api/api.js';
  import { ElTable, ElTableColumn, ElButton, ElInput } from 'element-plus';
  
  export default {
    components: {
    ElTable,
    ElTableColumn,
    ElButton,
    ElInput
   },
   setup() {
    const route = useRoute();
    const product = ref({});
    const quantity = ref(1);

    onMounted(async () => {
      const response = await fetchProduct(route.params.id);
      product.value = response.data;
    });

    const addToCart = async () => {
      await addToCart(product.value.id, quantity.value);
      alert('已加入购物车！');
    };

    return { product, quantity, addToCart };
  }
  };
  </script>
   -->

   <template>
    <div>
      <h1>{{ product.name }}</h1>
      <p>{{ product.description }}</p>
      <p>价格: ¥{{ product.price }}</p>
      <el-button type="primary" @click="addToCart">加入购物车</el-button>
    </div>
  </template>
  
  <script>
  import axios from "@/api/api.js";
//   export default {
//     data() {
//       return {
//         product: {}
//       };
//     },
//     methods: {
//       async fetchProduct() {
//         try {
//           const response = await axios.get(`/products/${this.$route.params.id}`);
//           this.product = response.data;
//         } catch (error) {
//           console.error("加载商品失败:", error);
//         }
//       },
//       async addToCart() {
//         try {
//           await axios.post("/cart", { productId: this.product.id, quantity: 1 }, {
//             headers: { Authorization: `Bearer ${localStorage.getItem("token")}` }
//           });
//           alert("成功加入购物车！");
//         } catch (error) {
//           console.error("加入购物车失败:", error);
//         }
//       }
//     },
//     mounted() {
//       this.fetchProduct();
//     }
//   };
    export default {
    data() {
        return {
        product: null
        };
    },
    methods: {
        async fetchProductDetails(productId) {
        try {
            const response = await axios.get(`http://localhost:8080/api/products/${productId}`);
            this.product = response.data;
        } catch (error) {
            console.error("加载商品详情失败:", error);
        }
        }
    },
    mounted() {
        const productId = this.$route.params.id;
        this.fetchProductDetails(productId);
    }
    };
  </script>
  