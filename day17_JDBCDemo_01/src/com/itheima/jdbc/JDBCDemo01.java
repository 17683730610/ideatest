package com.itheima.jdbc;
import java.sql.*;
/**
 * 包名:com.itheima.jdbc
 * 作者:Leevi
 * 日期2018-10-15  09:34
 */
public class JDBCDemo01 {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //使用JDBC操作SQL语句
        //1.注册驱动,使用DriverManager类调用registerDriver()的方法
        //要传入一个java.sql.Driver接口的对象
        //在Driver类中有一个静态代码块，静态代码块在类加载的时候执行。在这个静态代码块中已经注册了一次驱动
        //1.1.所以如果用DriverManager.registerDriver(new Driver());注册驱动就重复注册驱动了
        //1.2.因为new Driver()所使用到的Driver类是mysql驱动jar包中的类，这里就依赖了mysql驱动jar包，耦合性就高了
        /*DriverManager.registerDriver(new Driver());*/

        //其实在Driver类的静态代码块中已经自动注册了驱动，我们只需要让Driver类的静态代码块执行就OK。
        //怎么让它的静态代码块执行，那么也就是只需要让其Driver类加载就行了，Class.forName("类的全限定名")获取某个类的字节码对象
        Class.forName("com.mysql.jdbc.Driver");


        //2.获取连接对象:其实就是与mysql服务器建立连接
        //调用DriverManager类的getConnection(url,user,password)方法
        //1.url表示要连接到的数据库的路径。 2.user表示要连接到的数据库的用户名  3.password表示密码
        //url = "jdbc:mysql://localhost:3306/数据库名"  jdbc:mysql://表示协议名称
        //localhost表示数据库服务器所在的电脑的ip地址，localhost表示在本机
        //3306表示mysql服务器的端口号
        /*String url = "jdbc:mysql://localhost:3306/day17";*/
        //简写：如果是本机的数据库，而且端口号是3306，那么可以简写成jdbc:mysql:///day17
        String url = "jdbc:mysql:///day17";

        String user = "root";
        String password = "root";
        //获取到了一个连接对象，也就是与mysql服务器建立起的连接
        //"="左边相当于是对象的引用，"="右边相当于对象本身。
        //多态:父类的引用指向子类的对象。为了低耦合，咱们要使用多态
        Connection conn = DriverManager.getConnection(url, user, password);


        //3.使用conn调用createStatement()方法创建一个Statement对象，该对象就能将SQL语句发送到mysql服务器进行执行
        Statement stm = conn.createStatement();

        //可以调用statement对象的方法，来执行SQL语句
        //executeUpdate()方法，负责执行增删改的SQL语句。executeQuery()方法是负责执行查询的SQL
        //executeUpdate()方法的返回值是一个int类型的值表示受到影响的行数
        //executeQuery()方法的返回值是一个ResultSet对象，里面就包含了查询到的结果

        //4.执行SQL语句，查询id大于4的用户的数据
        String sql = "select * from user where id>4";
        ResultSet rst = stm.executeQuery(sql);

        //5.遍历结果集获取结果
        while(rst.next()){
            //如果返回true，就取出游标 对应的数据
            int id = rst.getInt("id");
            String username = rst.getString("username");
            String pwd = rst.getString("password");
            String nickname = rst.getString("nickname");

            System.out.println(id + ":" + username + ":" + pwd + ":" + nickname);
            System.out.println("---------------");
        }

        //6.关闭资源:后创建的资源先关闭
        rst.close();
        stm.close();
        conn.close();
    }
}
