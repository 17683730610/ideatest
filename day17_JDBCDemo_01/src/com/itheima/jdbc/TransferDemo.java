package com.itheima.jdbc;

import com.itheima.jdbc.utils.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 包名:com.itheima.jdbc
 * 作者:Leevi
 * 日期2018-10-15  11:40
 *
 * 什么时候开启事务:逻辑操作开始之前
 * 什么时候提交事务:逻辑操作执行完了，没有出现异常，就提交说
 * 什么时候回滚事务，出现了异常，则在catch里面回滚事务
 */
public class TransferDemo {
    public static void main(String[] args) {
        //1.注册驱动
        //2.获得连接
        Statement stm = null;
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            //开启事务
            conn.setAutoCommit(false);//设置不自动提交，也就是开启事务了

            stm = conn.createStatement();

            //1.张三扣款
            String sql1 = "update account set balance=balance-520 where id=1";
            stm.executeUpdate(sql1);

            int num = 10/0;

            //2.李四收款
            String sql2 = "update account set balance=balance+520 where id=2";
            stm.executeUpdate(sql2);

            //提交事务
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();

            //回滚事务
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                stm.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
