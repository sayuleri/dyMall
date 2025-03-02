import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import ProductDetail from '../views/ProductDetail.vue';
import Cart from '../views/Cart.vue';
import Profile from '../views/Profile.vue';
import Order from '../views/Order.vue';

const routes = [
  { path: '/', component: Home },
  { path: '/home', component: Home },  
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/product/:id', component: ProductDetail }, // 商品详情页
  { path: '/cart', component: Cart }, // 购物车
  { path: '/profile', component: Profile }, // 用户个人中心
  { path: '/order', component: Order },  // 订单页面
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
