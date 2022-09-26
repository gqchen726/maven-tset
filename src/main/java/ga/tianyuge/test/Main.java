/*
 * @说明：演示statement的SQL注入问题
 * */
package ga.tianyuge.test;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名");
        String admin_name = scanner.next();
        System.out.println("请输入密码");
        String admin_pwd = scanner.next();
        // 1、注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        /* 2、得到连接:
         *   */
        String url = "jdbc:mysql://localhost:3306/bjpowernode";
        String username = "root";
        String password = "123456";
        Connection connection = DriverManager.getConnection(url, username, password);
        // 3、执行sql
        String sql = "select name,pwd from admin where name = \'" + admin_name + "\' and pwd = \'" + admin_pwd + "\'";
        // 接收返回的结果对象
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(sql);
        ResultSetMetaData rsmd = result.getMetaData();
        if (result.next()) {
            // 查询到用户
            System.out.println("登录成功");
            while (result.next()) {
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = result.getString(i);
                    System.out.print("[列名:" + rsmd.getColumnName(i) + "](值:" + columnValue + ")");
                }
                System.out.println("");
            }
        } else {
            System.out.println("登录失败");
        }
        // 4、关闭连接
        statement.close();
        connection.close();
        result.close();
    }
}