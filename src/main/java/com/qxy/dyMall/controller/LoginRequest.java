package com.qxy.dyMall.controller;

public class LoginRequest {
    private String username;
    // private static String password;
    private String password; // 移除 static
    
        // 无参构造函数
        public LoginRequest() {}
    
        // 带参构造函数
        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    
        // Getter 和 Setter 方法
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
        public String getPassword() {
            return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
