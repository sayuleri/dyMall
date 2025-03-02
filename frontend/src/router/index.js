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
  { path: '/product/:id', component: ProductDetail },
  { path: '/cart', component: Cart },
  { path: '/profile', component: Profile },
  { path: '/order', component: Order },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
