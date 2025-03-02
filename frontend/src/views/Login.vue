<template>
    <div>
      <h2>用户登录</h2>
      <input v-model="email" placeholder="邮箱">
      <input v-model="password" type="password" placeholder="密码">
      <button @click="login">登录</button>
    </div>
</template>
  
<script>
    import { loginUser } from '@/api/api.js'; // 改成绝对路径
  
    export default {
        data() {
        return {
            email: '',
            password: '',
        };
        },
        methods: {
        async login() {
            try {
                const response = await axios.post("http://localhost:8080/api/users/login", {
                    username: email,  // 确保这里和后端参数一致
                    password: password
                });

                const token = response.data.token;
                localStorage.setItem("token", token);  // 存储 JWT
                alert("登录成功");
                router.push("/profile");  // 登录后跳转页面
            } catch (error) {
                alert("登录失败，请检查用户名和密码");
            }
        }
        }
    };
</script>
  