import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; // 引入 router
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'; //  CSS引入

const app = createApp(App);
app.use(router); // 挂载 Vue Router
app.use(ElementPlus); // 注册 Element Plus 组件库
app.mount('#app');
