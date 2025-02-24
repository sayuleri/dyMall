package com.qxy.dyMall.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    
    // 使用了lombok后，不需要手动写getter和setter方法
    // public Long getId(){return id;}
    // public void setId(Long id){this.id = id;}

    // public String getUserName(){return username;}
    // public void setUserName(String name){username = name;}

    // public String getPassword(){return password;}
    // public void setPassword(String password){this.password = password;}

    // public String getEmail(){return email;}
    // public void setEmail(String email){this.email = email;}
}