package com.itheima.jdbc;

import com.itheima.jdbc.utils.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 包名:com.itheima.jdbc
 * 作者:Leevi
 * 日期2018-10-15  10:44
 * junit单元测试的方法的要求:
 * 1.必须是public 修饰，没有static
 * 2.方法没有参数
 * 3.方法上要添加@Test注解
 * 4.方法必须没有返回值
 */
public class JDBCDemo02 {
    @Test
    public void testAdd() throws ClassNotFoundException, SQLException {
        //执行添加数据的SQL语句，往user表中添加一条数据"null,eson,456789,陈奕迅"
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接对象
        String url = "jdbc:mysql:///day17";
        String user = "root";
        String password = "123";
        Connection conn = DriverManager.getConnection(url, user, password);
        //3.创建Statement对象
        Statement stm = conn.createStatement();
        //4.执行SQL语句
        String sql = "insert into user values(null,'eson','456789','陈奕迅')";
        //执行增删改语句，调用stm的executeUpdate()方法
        int i = stm.executeUpdate(sql);//返回值表示受到影响的行数
        System.out.println(i);
        //5.关闭资源
        stm.close();
        conn.close();
    }

    @Test
    public void testDelete() throws ClassNotFoundException, SQLException {
        //测试删除数据的SQL语句，删除id为9 的数据
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.获得连接对象
        String url = "jdbc:mysql:///day17";
        String user = "root";
        String password = "123";
        Connection conn = DriverManager.getConnection(url, user, password);
        //3.创建Statement对象
        Statement stm = conn.createStatement();
        //4.执行SQL语句
        String sql = "delete from user where id=9";
        int i = stm.executeUpdate(sql);
        System.out.println(i);
        //5.关闭资源
        stm.close();
        conn.close();
    }

    @Test
    public void testUpdate() throws ClassNotFoundException, SQLException {
        //修改id为12的数据的username="zs",nickname修改成"张三疯"
        //1.注册驱动
        //2.获得连接对象
        Connection conn = JDBCUtil.getConnection();
        //3.创建Statement对象
        Statement stm = conn.createStatement();
        //4.执行SQL语句
        String sql = "update user set username='zs',nickname='张三疯' where id=12";
        int i = stm.executeUpdate(sql);
        System.out.println(i);
        //5.关闭资源
        JDBCUtil.close(conn,stm);
    }
}
