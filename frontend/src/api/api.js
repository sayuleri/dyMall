import axios from 'axios';

// const api = axios.create({
//   baseURL: 'http://localhost:8080/api',
//   headers: {
//     'Content-Type': 'application/json',
//   },
// });

// // 请求拦截器，自动携带 token
// api.interceptors.request.use(config => {
//   const token = localStorage.getItem('token');
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// }, error => {
//   return Promise.reject(error);
// });

// // 登录
// export const loginUser = async (username, password) => {  // 传递 username
//     try {
//       console.log("发送的登录信息:", { username, password }); // 确保前端数据正确
  
//       const response = await api.post('/users/login', { username, password }); // 传递 username 而不是 email
  
//       console.log("后端返回的数据:", response.data); 
  
//       if (response.data.token) {
//         localStorage.setItem('token', response.data.token);
//         return response.data;
//       } else {
//         throw new Error('登录失败：服务器未返回 token');
//       }
//     } catch (error) {
//       console.error('登录失败', error);
//       throw error;
//     }
//   };

// 获取用户信息
// export const fetchUserProfile = async () => {
//     try {
//       const response = await api.get('/users/profile'); // 确保后端提供了该接口
//       return response.data;
//     } catch (error) {
//       console.error('获取用户信息失败', error);
//       throw error;
//     }
//   };

const api = axios.create({
    baseURL: 'http://localhost:8080/api',
    headers: { 'Content-Type': 'application/json' },
  });
  
  // ✅ 请求拦截器，自动携带 token
  api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  }, error => Promise.reject(error));
  
  // ✅ 处理 401 未授权错误
  api.interceptors.response.use(response => response, error => {
    if (error.response && error.response.status === 401) {
      console.error("❌ 未授权，跳转到登录页面");
      localStorage.removeItem('token'); 
      window.location.href = '/login'; // 强制跳转到登录页
    }
    return Promise.reject(error);
  });
  
// ✅ 确保 `loginUser` 函数正确导出
export const loginUser = async (username, password) => {
    try {
      console.log("📤 发送登录请求:", { username, password });
  
      const response = await api.post('/users/login', { username, password });
  
      console.log("✅ 后端返回的数据:", response.data);
  
      if (response.data.token) {
        localStorage.setItem('token', response.data.token);
        return response.data;
      } else {
        throw new Error('登录失败：服务器未返回 token');
      }
    } catch (error) {
      console.error('❌ 登录失败:', error);
      throw error;
    }
  };
  
    // ✅ 修正 `fetchUserProfile()` 以确保请求带 `Authorization` 头
    export const fetchUserProfile = async () => {
        try {
        console.log("📤 发送获取用户信息请求到 `/users/profile`...");
        
        const token = localStorage.getItem('token');
        if (!token) throw new Error("❌ 没有 `token`，无法获取用户信息");
    
        const response = await api.get('/users/profile', {
            headers: { Authorization: `Bearer ${token}` } // ✅ 强制传递 `Authorization` 头
        });
    
        console.log("✅ 获取用户信息成功:", response.data);
        return response.data;
        } catch (error) {
        console.error('❌ 获取用户信息失败:', error);
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
    const response = await api.get(`/products/${id}`);
    return response.data;
  } catch (error) {
    console.error('获取商品详情失败', error);
    throw error;
  }
};

export const getCartItems = async () => {
    const response = await api.get("/cart");
    return response.data;
  };
  
  export const updateCartItem = async (id, quantity) => {
    await api.put(`/cart/${id}`, { quantity });
  };
  
  export const removeCartItem = async (id) => {
    await api.delete(`/cart/${id}`);
  };
  
  export const checkoutCart = async () => {
    await api.post("/cart/checkout");
  };
  
  export const getOrders = async () => {
    const response = await api.get("/orders");
    return response.data;
  };
  
export default api;
