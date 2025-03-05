<!-- <template>
    <div class="cart-container">
      <h2>购物车</h2>
      <el-table :data="cartItems" border style="width: 100%">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="image" label="商品图片" width="150">
          <template #default="{ row }">
            <img :src="row.image" alt="商品图片" class="product-image" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称" width="200"></el-table-column>
        <el-table-column prop="price" label="单价" width="100"></el-table-column>
        <el-table-column label="数量" width="150">
          <template #default="{ row }">
            <el-input-number v-model="row.quantity" :min="1" @change="updateCart(row)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="总价" width="100">
          <template #default="{ row }">
            ¥{{ (row.price * row.quantity).toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button type="danger" @click="removeFromCart(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
  
      <div class="cart-summary">
        <p>总计: ¥{{ totalPrice.toFixed(2) }}</p>
        <el-button type="primary" @click="checkout">结算</el-button>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, computed, onMounted } from "vue";
  import { getCartItems, updateCartItem, removeCartItem, checkoutCart } from "@/api/api.js";
  import { ElTable, ElTableColumn, ElButton, ElInputNumber } from "element-plus";
  
  export default {
    components: {
      ElTable,
      ElTableColumn,
      ElButton,
      ElInputNumber
    },
    setup() {
      const cartItems = ref([]);
  
      const fetchCart = async () => {
        try {
          const response = await getCartItems();
          cartItems.value = response;
        } catch (error) {
          console.error("❌ 加载购物车失败:", error);
        }
      };
  
      const updateCart = async (item) => {
        await updateCartItem(item.id, item.quantity);
      };
  
      const removeFromCart = async (id) => {
        await removeCartItem(id);
        cartItems.value = cartItems.value.filter(item => item.id !== id);
      };
  
      const totalPrice = computed(() => {
        return cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0);
      });
  
      const checkout = async () => {
        await checkoutCart();
        alert("结算成功，订单已生成！");
        cartItems.value = [];
      };
  
      onMounted(fetchCart);
  
      return { cartItems, updateCart, removeFromCart, totalPrice, checkout };
    },
  };
  </script>
  
  <style>
  .cart-container {
    max-width: 900px;
    margin: auto;
  }
  
  .product-image {
    width: 100px;
    height: 100px;
    object-fit: cover;
  }
  
  .cart-summary {
    margin-top: 20px;
    text-align: right;
  }
  </style> -->

  <template>
    <div>
      <h2>购物车</h2>
      <div v-if="cartItems.length === 0">
      <p>购物车暂无商品</p>
    </div>
      <el-table :data="cartItems" border>
        <el-table-column type="selection"></el-table-column>
        <el-table-column prop="image" label="商品图片">
          <template v-slot="{ row }">
            <img :src="row.image" alt="商品图片" width="50" />
          </template>
        </el-table-column>
        <el-table-column prop="name" label="商品名称"></el-table-column>
        <el-table-column prop="price" label="单价"></el-table-column>
        <el-table-column prop="quantity" label="数量">
          <template v-slot="{ row }">
            <el-input-number v-model="row.quantity" :min="1" @change="updateCart(row)"></el-input-number>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template v-slot="{ row }">
            <el-button type="danger" @click="removeFromCart(row.id)">移除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <p>总价: ¥{{ totalPrice }}</p>
      <el-button type="primary" @click="checkout">结算</el-button>
    </div>
  </template>
  
  <script>
  import axios from "@/api/api.js";
  export default {
    data() {
      return {
        cartItems: []
      };
    },
    computed: {
      totalPrice() {
        return this.cartItems.reduce((total, item) => total + item.price * item.quantity, 0);
      }
    },
    methods: {
        async fetchCart() {
    try {
        const token = localStorage.getItem('token');
        console.log("当前 Token:", token);  // ✅ 确保 Token 正确

        if (!token) {
            console.warn("❌ Token 不存在，无法获取购物车");
            return;
        }

        const response = await axios.get("http://localhost:8080/api/cart/items", {
            headers: {
                Authorization: `Bearer ${token}`,  // ✅ 确保 "Bearer " 前缀
                "Content-Type": "application/json"
            }
        });

        console.log("✅ 请求成功, 返回数据:", response.data);
        this.cartItems = response.data || [];
            } catch (error) {
                console.error("❌ 购物车请求失败:", error.response?.data || error);
                this.cartItems = [];
            }
        }, 
        async addToCart(item) {
    try {
        const token = localStorage.getItem('token');
        if (!token) {
            console.warn("❌ Token 不存在，无法添加到购物车");
            return;
        }

        const response = await axios.post(`http://localhost:8080/api/cart/add?productId=${item.id}&quantity=1`, 
            {}, // `POST` 需要一个空的 Body
            { headers: { Authorization: `Bearer ${token}` } }
        );

        console.log("✅ 商品添加成功:", response.data);
        this.fetchCart();  // 重新获取购物车数据
            } catch (error) {
                console.error("❌ 添加购物车失败:", error.response?.data || error);
            }
        },
        async updateCart(item) {
        try {
            const token = localStorage.getItem('token');
            await axios.post("http://localhost:8080/api/cart/update", 
                { productId: item.id, quantity: item.quantity }, 
                { headers: { Authorization: `Bearer ${token}` } }
            );
            this.fetchCart();
        } catch (error) {
            console.error("❌ 更新购物车失败:", error.response?.data || error);
        }
    },
        async removeFromCart(itemId) {
        try {
            const token = localStorage.getItem('token');
            await axios.post("http://localhost:8080/api/cart/remove", 
                { productId: itemId },  // ✅ 这里可能需要用 `POST` 传参数
                { headers: { Authorization: `Bearer ${token}` } }
            );
            this.fetchCart();
        } catch (error) {
            console.error("❌ 删除商品失败:", error.response?.data || error);
        }
    },
      async checkout() {
        try {
          await axios.post("/checkout");
          this.cartItems = [];
          alert("订单提交成功！");
        } catch (error) {
          console.error("结算失败:", error);
        }
      }
    },
    mounted() {
      this.fetchCart();
    }
  };
  </script>
  