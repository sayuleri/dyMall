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
  try {
    const response = await api.post('/users/login', { email, password });
    localStorage.setItem('token', response.data.token);
    return response.data;
  } catch (error) {
    console.error('登录失败', error);
    throw error;
  }
};

// 注册用户
export const registerUser = async (userData) => {
  try {
    const response = await api.post('/users/register', userData);
    return response.data;
  } catch (error) {
    console.error('注册失败', error);
    throw error;
  }
};

// 获取商品列表
export const fetchProducts = async () => {
  const response = await api.get('/products/list');
  return response.data;
};

// 获取商品详情
export const fetchProductDetail = async (id) => {
  try {
    const response = await api.get(`/products/${id}`); // 确保路径和后端一致
    return response.data;
  } catch (error) {
    console.error('获取商品详情失败', error);
    throw error;
  }
};

// 获取用户信息
export const fetchUserProfile = async (id) => {
  try {
    const response = await api.get(`/users/${id}`);
    return response.data;
  } catch (error) {
    console.error('获取用户信息失败', error);
    throw error;
  }
};

export default api;
