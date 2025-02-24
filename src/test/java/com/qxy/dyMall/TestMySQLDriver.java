package com.qxy.dyMall;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestMySQLDriver {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 手动加载 MySQL 驱动
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dy_mall?serverTimezone=Asia/Shanghai&useSSL=false",
                "root", "123456"
            );
            System.out.println("✅ 数据库连接成功: " + conn);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
