package com.itheima.jdbc.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 包名:com.itheima.jdbc.utils
 * 作者:Leevi
 * 日期2018-10-15  10:59
 *
 * 1.硬编码的问题，涉及到一些配置信息的时候，我们最好是将这些配置信息写到一个配置文件中
 * 2.你在哪个类需要用到配置文件中的数据，就使用代码读取过来就可以了
 * 3.我们所接触的配置文件:1.xml文件  2.properties文件(今天使用)
 * 4.在java项目中，配置文件一般放在resource的目录中,而且必须将resource目录变成"Resource Root"
 */
public class JDBCUtil {
    private static String url;
    private static String user;
    private static String password;
    private static String driverClassName;
    static {
        //读取配置文件中的数据是一个io流操作，所以只需要在静态代码块中类加载的时候读取一次就够了
        //针对properties文件，我们jdk提供了一个特殊的工具类"Properties"类，来读取properties中的信息
        Properties properties = new Properties();
        //让properties对象和"jdbcinfo.properties"文件发生关系
        //将"jdbcinfo.properties"文件转换成一个InputStream
        try {
            /*FileInputStream in = new FileInputStream("C:\\JavaEE_Relation\\JavaEE51\\itheima51\\out\\production\\day17_JDBCDemo_01\\jdbcinfo.properties");*/
            //使用类加载器的getResourceAsStream()方法，将文件转换成流
            //1.获取类加载器:当前类名.class.getClassLoader()
            ClassLoader classLoader = JDBCUtil.class.getClassLoader();
            InputStream in = classLoader.getResourceAsStream("jdbcinfo.properties");//前提是:这个配置文件放在"resource"
            properties.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //properties对象读取到配置文件中的数据之后，我们可以通过该对象的getProperty("key")方法获取value
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        password = properties.getProperty("password");
        driverClassName = properties.getProperty("driverClassName");

        //注册驱动
        try {
            Class.forName(driverClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接的方法
     * @return
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 关闭资源的方法
     * @param conn
     * @param stm
     * @throws SQLException
     */
    public static void close(Connection conn, Statement stm) throws SQLException {
        stm.close();
        conn.close();
    }

    /**
     * 关闭资源的方法
     * @param conn
     * @param stm
     * @param rst
     * @throws SQLException
     */
    public static void close(Connection conn, Statement stm, ResultSet rst) throws SQLException {
        close(conn,stm);
        rst.close();
    }
}
