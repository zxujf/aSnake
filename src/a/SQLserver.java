package a;
import java.sql.*;  

import javax.swing.JOptionPane;  
/* 
 * �����ݿ���صĲ�����������װ���� 
 */  
  
class SQLserver {  
  
    Connection ct;  
    PreparedStatement ps;  
    ResultSet rs;  
    String user,pwd;  
      
    //���������ݿ�ķ�����װΪһ������  
    public void ConnectSQL() throws SQLException  
    {  
    	/* Connection conn = null;
         String sql;
         // MySQL��JDBC URL��д��ʽ��jdbc:mysql://�������ƣ����Ӷ˿�/���ݿ������?����=ֵ
         // ������������Ҫָ��useUnicode��characterEncoding
         // �������֮ǰ��Ҫ�ȴ���javademo���ݿ�
         String url = "jdbc:mysql://localhost:3306/javademo?"
                 + "user=root&password=root&useUnicode=true&characterEncoding=UTF8";
  
         try {
             // ֮����Ҫʹ������������䣬����ΪҪʹ��MySQL����������������Ҫ��������������
             // ����ͨ��Class.forName�������ؽ�ȥ��Ҳ����ͨ����ʼ������������������������ʽ������
             Class.forName("com.mysql.jdbc.Driver");// ��̬����mysql����
             // or:
             // com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
             // or��
             // new com.mysql.jdbc.Driver();
  
             System.out.println("�ɹ�����MySQL��������");
             // һ��Connection����һ�����ݿ�����
             conn = DriverManager.getConnection(url);
             // Statement������кܶ෽��������executeUpdate����ʵ�ֲ��룬���º�ɾ����
             Statement stmt = conn.createStatement();
             sql = "create table student(NO char(20),name varchar(20),primary key(NO))";
             int result = stmt.executeUpdate(sql);// executeUpdate���᷵��һ����Ӱ����������������-1��û�гɹ�
             if (result != -1) {
                 System.out.println("�������ݱ�ɹ�");
                 sql = "insert into student(NO,name) values('2012001','��ΰ��')";
                 result = stmt.executeUpdate(sql);
                 sql = "insert into student(NO,name) values('2012002','��С��')";
                 result = stmt.executeUpdate(sql);
                 sql = "select * from student";
                 ResultSet rs = stmt.executeQuery(sql);// executeQuery�᷵�ؽ���ļ��ϣ����򷵻ؿ�ֵ
                 System.out.println("ѧ��\t����");
                 while (rs.next()) {
                     System.out
                             .println(rs.getString(1) + "\t" + rs.getString(2));// ��������ص���int���Ϳ�����getInt()
                 }
             }
         } catch (SQLException e) {
             System.out.println("MySQL��������");
             e.printStackTrace();
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             conn.close();
         }*/
        try {  
        	
        	 String url = "jdbc:mysql://localhost:3306/snake?"
                     + "user=root&password=1111&useUnicode=true&characterEncoding=UTF8";
        	
            Class.forName("com.mysql.jdbc.Driver"); //��������  
              //"jdbc:odbc:ywq"
            ct=DriverManager.getConnection(url); //�õ�����  
            /*Statement stmt = null;
            stmt = ct.createStatement();
        String sql = "CREATE TABLE login_user " +
                       "(ID INT PRIMARY KEY     NOT NULL," +
                       " NAME           TEXT    NOT NULL, " + 
                       " PASS           TEXT     NOT NULL, "+
                       " RealName           TEXT    "+
                       " Number           TEXT      ";
         stmt.executeUpdate(sql);*/
               
            System.out.println("�ѳɹ��������ݿ�...");  
              
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  finally {
        	ct.close();
        }
    }  
      
    //ע���û��ķ���  
    public void UserRegis(String a,String b,String c,String d)  
    {  
        //���������  
        try {  
            ps=ct.prepareStatement("insert into users values(?,?,?,?)");  
            ps.setString(1,a);  
            ps.setString(2,b);  
            ps.setString(3,c);  
            ps.setString(4,d);  
              
            //ִ��  
            int i=ps.executeUpdate();  
            if(i==1)  
            {  
                JOptionPane.showMessageDialog(null, "ע��ɹ�","��ʾ��Ϣ",JOptionPane.WARNING_MESSAGE);  
                  
            }else  
            {  
                JOptionPane.showMessageDialog(null, "ע��ʧ��","��ʾ��Ϣ",JOptionPane.ERROR_MESSAGE);  
            }  
              
              
        } catch (SQLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
      
//  ��¼��֤����  
    public void SQLverify(String a,String b)  
    {  
        try {  
            ps=ct.prepareStatement("select * from users where �û���=? and ����=? ");  
            ps.setString(1, a);  
            ps.setString(2, b);  
              
            // ResultSet���������һ�ű��еĽ����  
            rs = ps.executeQuery();  
              
            if(rs.next())  
            {  
                user = rs.getString(1);  
                pwd = rs.getString(2);  
                JOptionPane.showMessageDialog(null, "��¼�ɹ�������", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);  
                System.out.println("�ɹ���ȡ��������û���from���ݿ�");  
                System.out.println(user + "\t" + pwd + "\t");  
            }else  
            {  
                JOptionPane.showMessageDialog(null, "�û�����������������������룡", "��ʾ��Ϣ", JOptionPane.ERROR_MESSAGE);  
                  
            }  
              
        } catch (SQLException e) {  
              
            e.printStackTrace();  
        }  
    }  
      
    //ע����֤�������ж��û����Ƿ��Ѿ�����  
    public void ZhuceVerify(String a)  
    {  
        try {  
            ps=ct.prepareStatement("select * from users where �û���=?");  
//          System.out.println(ps);  
            ps.setString(1, a);  
              
            rs=ps.executeQuery();  
            if(rs.next())  
            {  
                JOptionPane.showMessageDialog(null, "���û����Ѿ�����", "��ʾ��Ϣ", JOptionPane.WARNING_MESSAGE);  
            }else  
            {  
//              ����ע��  
                UI ui=new UI();  
                this.UserRegis(ui.jtf1.getText(),ui.jtf2.getText(),ui.jtf3.getText(),ui.jtf4.getText());  
            }  
              
        } catch (SQLException e) {  
              
            e.printStackTrace();  
        }  
    }  
      
      
      
      
      
} 
