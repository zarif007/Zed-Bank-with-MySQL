package Project;

import static Project.Hashing.getCryptoHash;
import java.sql.*;

public class Database  {
    static String name = "";
    static int age = 0;
    static String email = "";
    static String Encrypted_password = "";
    static int num_of_acc = 0;

    //      Connecting to MySql DB
    String Mysql_url = "jdbc:mysql://localhost:3306/zed";
    String Mysql_username = "Your DataBase name";
    String Mysql_password = "Your DataBase password";

    //Loading on DataBase
    public void Create(String name, String email, int age, int num_of_acc){
        Database.name = name;
        Database.email = email;
        Database.age = age;
        Database.num_of_acc = num_of_acc;

        String Inserting = "insert into cred values('"+name+"', '"+email+"',"+age+", '"+Encrypted_password+"',"+ num_of_acc+" )";
        String Inserting2 = "insert into users_acc values("+num_of_acc+","+10000+",'"+email+"')";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            st.executeUpdate(Inserting);
            st.executeUpdate(Inserting2);
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to create Account");
        } catch (SQLException e) {
            System.out.println("Unable to create Account");
        }
    }


    //Encrypting with SHA-256 algorithm before storing
    public void encrypt(String p){
        Encrypted_password = getCryptoHash(p, "SHA-256", num_of_acc);
    }

    public static String encrypt1(String p){
        return getCryptoHash(p, "SHA-256", num_of_acc);
    }


    //Function for authenticating Log-in credentials
    public boolean Login(String m,String p){
        ResultSet rs;
        String pass = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            rs = st.executeQuery("select Password from zed.cred where Email = '"+m+"'");
            rs.next();
            pass = rs.getString("Password");
            st.close();
            con.close();

        } catch (ClassNotFoundException e) {
            return false;
        } catch (SQLException e) {
            return false;
        }
        p = encrypt1(p);
        return p.equals(pass);
    }

    //Fetching users account number from DB
    public void Account_number(String mail_input){
        try{
            String query1 = "select Id from zed.cred where Email = '"+mail_input+"'";;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query1);
            rs.next();
            String Id = rs.getString("Id");
            System.out.println("Your Account number is "+Id);
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("You will be notified by your Acc number later");
        } catch (SQLException e) {
            System.out.println("You will be notified by your Acc number later");
        }
    }
}
