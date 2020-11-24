package Project;

import static Project.Hashing.getCryptoHash;
import java.sql.*;

public class Admin {

    //      Connecting to MySql DB
    String Mysql_url = "Your Database Url";
    String Mysql_username = "Your Database Name";
    String Mysql_password = "Your Database Password";
    public void DisplayAll(int count) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from zed.cred");
            for(int i=0;i<count;i++){
                rs.next();
                String sh = rs.getString("Name") + " " + rs.getString("Email") + " " + rs.getInt("Age") + " " + rs.getString("Password");
                System.out.println(sh);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Unable yo Display");
        } catch (SQLException e) {
            System.out.println("Unable yo Display");
        }
    }

    public void InsertUser(String name, String mail, int age, String password, int noc) {
        password = getCryptoHash(password, "SHA-256", noc);
        String Inserting = "insert into cred values('"+name+"', '"+mail+"',"+age+", '"+password+"',"+ noc+" )";
        String Inserting2 = "insert into users_acc values("+noc+","+10000+",'"+mail+"')";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            st.executeUpdate(Inserting);
            st.executeUpdate(Inserting2);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable yo Create");
        } catch (SQLException e) {
            System.out.println("Unable yo Create");
        }
    }

    public void DeleteUser(int id) {
        String delete = "delete from cred where id = "+id;
        String delete2 = "delete from users_acc where id = "+id;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            st.executeUpdate(delete);
            st.executeUpdate(delete2);
        }
        catch (ClassNotFoundException e) {
            System.out.println("Unable yo Delete");
        } catch (SQLException e) {
            System.out.println("Unable yo Delete");
        }
    }
}
