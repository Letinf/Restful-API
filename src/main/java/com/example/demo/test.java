package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class test {
	public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载sql驱动");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("找不到sql驱动");
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false";   
        Connection conn;
        try {
            conn = (Connection) DriverManager.getConnection(url,"root","123456");
            //创建一个Statement对象
            Statement stmt = (Statement) conn.createStatement(); //创建Statement对象
            System.out.print("成功连接到数据库！");
            
            ResultSet rs = stmt.executeQuery("select * from test");
            //test 为表的名称
            System.out.println(rs.toString());
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }

            stmt.close();
            conn.close();
        } catch (SQLException e){
            System.out.println("fail to connect the database!");
            e.printStackTrace();
        }
  }
}