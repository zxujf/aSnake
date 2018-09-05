package a;
import java.sql.*;  

import javax.swing.JOptionPane;  
/* 
 * 与数据库相关的操作，单独封装成类 
 */  
  
class SQLserver {  
  
    Connection ct;  
    PreparedStatement ps;  
    ResultSet rs;  
    String user,pwd;  
      
    //将连接数据库的方法封装为一个方法  
    public void ConnectSQL() throws SQLException  
    {  
    	/* Connection conn = null;
         String sql;
         // MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
         // 避免中文乱码要指定useUnicode和characterEncoding
         // 下面语句之前就要先创建javademo数据库
         String url = "jdbc:mysql://localhost:3306/javademo?"
                 + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
  
         try {
             // 之所以要使用下面这条语句，是因为要使用MySQL的驱动，所以我们要把它驱动起来，
             // 可以通过Class.forName把它加载进去，也可以通过初始化来驱动起来，下面三种形式都可以
             Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
             // or:
             // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
             // or：
             // new com.mysql.jdbc.Driver();
  
             System.out.println("成功加载MySQL驱动程序");
             // 一个Connection代表一个数据库连接
             conn = DriverManager.getConnection(url);
             // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
             Statement stmt = conn.createStatement();
             sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
             int result = stmt.executeUpdate(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
             if (result != -1) {
                 System.out.println("创建数据表成功");
                 sql = "insert into student(NO,name) values('2012001','陶伟基')";
                 result = stmt.executeUpdate(sql);
                 sql = "insert into student(NO,name) values('2012002','周小俊')";
                 result = stmt.executeUpdate(sql);
                 sql = "select * from student";
                 ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
                 System.out.println("学号\t姓名");
                 while (rs.next()) {
                     System.out
                             .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
                 }
             }
         } catch (SQLException e) {
             System.out.println("MySQL操作错误");
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             conn.close();
         }*/
        try {  
        	
        	 String url = "jdbc:mysql://localhost:3306/snake?"
                     + "user=root&password=1111&useUnicode=true&characterEncoding=UTF8";
        	
            Class.forName("com.mysql.jdbc.Driver"); //加载驱动  
              //"jdbc:odbc:ywq"
            ct=DriverManager.getConnection(url); //得到连接  
            /*Statement stmt = null;
            stmt = ct.createStatement();
        String sql = "CREATE TABLE login_user " +
                       "(ID INT PRIMARY KEY     NOT NULL," +
                       " NAME           TEXT    NOT NULL, " + 
                       " PASS           TEXT     NOT NULL, "+
                       " RealName           TEXT    "+
                       " Number           TEXT      ";
         stmt.executeUpdate(sql);*/
               
            System.out.println("已成功连接数据库...");  
              
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  finally {
        	ct.close();
        }
    }  
      
    //注册用户的方法  
    public void UserRegis(String a,String b,String c,String d)  
    {  
        //创建火箭车  
        try {  
            ps=ct.prepareStatement("insert into users values(?,?,?,?)");  
            ps.setString(1,a);  
            ps.setString(2,b);  
            ps.setString(3,c);  
            ps.setString(4,d);  
              
            //执行  
            int i=ps.executeUpdate();  
            if(i==1)  
            {  
                JOptionPane.showMessageDialog(null, "注册成功","提示消息",JOptionPane.WARNING_MESSAGE);  
                  
            }else  
            {  
                JOptionPane.showMessageDialog(null, "注册失败","提示消息",JOptionPane.ERROR_MESSAGE);  
            }  
              
              
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
      
//  登录验证方法  
    public void SQLverify(String a,String b)  
    {  
        try {  
            ps=ct.prepareStatement("select * from users where 用户名=? and 密码=? ");  
            ps.setString(1, a);  
            ps.setString(2, b);  
              
            // ResultSet结果集返回一张表行的结果集  
            rs = ps.executeQuery();  
              
            if(rs.next())  
            {  
                user = rs.getString(1);  
                pwd = rs.getString(2);  
                JOptionPane.showMessageDialog(null, "登录成功！！！", "提示消息", JOptionPane.WARNING_MESSAGE);  
                System.out.println("成功获取到密码和用户名from数据库");  
                System.out.println(user + "\t" + pwd + "\t");  
            }else  
            {  
                JOptionPane.showMessageDialog(null, "用户名或者密码错误，请重新输入！", "提示消息", JOptionPane.ERROR_MESSAGE);  
                  
            }  
              
        } catch (SQLException e) {  
              
            e.printStackTrace();  
        }  
    }  
      
    //注册验证方法，判断用户名是否已经存在  
    public void ZhuceVerify(String a)  
    {  
        try {  
            ps=ct.prepareStatement("select * from users where 用户名=?");  
//          System.out.println(ps);  
            ps.setString(1, a);  
              
            rs=ps.executeQuery();  
            if(rs.next())  
            {  
                JOptionPane.showMessageDialog(null, "该用户名已经存在", "提示信息", JOptionPane.WARNING_MESSAGE);  
            }else  
            {  
//              进行注册  
                UI ui=new UI();  
                this.UserRegis(ui.jtf1.getText(),ui.jtf2.getText(),ui.jtf3.getText(),ui.jtf4.getText());  
            }  
              
        } catch (SQLException e) {  
              
            e.printStackTrace();  
        }  
    }  
      
      
      
      
      
} 
