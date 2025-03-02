import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器，自动携带 token
api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
}, error => {
  return Promise.reject(error);
});

// 登录请求
export const loginUser = async (email, password) => {
  const response = await api.post('/users/login', { email, password });
  localStorage.setItem('token', response.data.token);
  return response.data;
};

// 获取商品列表
export const fetchProducts = async () => {
  const response = await api.get('/products/list');
  return response.data;
};

// 获取商品详情
export const fetchProductDetail = async (id) => {
  const response = await api.get(`/products/details/${id}`); // 确保后端支持
  return response.data;
};

// 获取用户信息
export const fetchUserProfile = async (id) => {
  const response = await api.get(`/users/profile/${id}`); // 确保路径正确
  return response.data;
};

export default api;
