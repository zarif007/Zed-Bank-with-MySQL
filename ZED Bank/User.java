package Project;
import java.sql.*;

public class User {

    //      Connecting to MySql DB
    String Mysql_url = "jdbc:mysql://localhost:3306/zed";
    String Mysql_username = "Your DB username";
    String Mysql_password = "Your DB password";

//----------------------DataBase for storing Users money----------

    public static int DisplayMoney(String mail){
        int amount = 0;
        try{
            String query = "select Amount from zed.users_acc where Email = '"+mail+"'";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            amount = rs.getInt("Amount");
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to Display, Try again");
        } catch (SQLException e) {
            System.out.println("Unable to Display, Try again");
        }
        return amount;
    }

    //Withdraw Function
    public void Withdraw(int req, String mail, int total) {
        if(req>total-500)
            System.out.println("Insufficient balance");
        else{
            try{
                String query = "update users_acc set Amount = "+(total-req)+" where Email = '"+mail+"'";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
                Statement st = con.createStatement();
                st.executeUpdate(query);
                st.close();
                con.close();
            } catch (ClassNotFoundException e) {
                System.out.println("Unable to Withdraw, Try again");
            } catch (SQLException e) {
                System.out.println("Unable to Display, Try again");
            }
        }
    }

    //Deposit function
    public void Deposit(int req,String mail, int total){
        String query = "update users_acc set Amount = "+(total+req)+" where Email = '"+mail+"'";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to Deposit, Try again");
        } catch (SQLException e) {
            System.out.println("Unable to Deposit, Try again");
        }
    }

    //Money Checking function
    public boolean check(int req, String mail, int total){
        if(req>total-500)
            return false;
        return true;
    }

    //Transfer Function
    public void Transfer(int acc,int amt){
        String query = "update users_acc set Amount = "+(CheckTotal(acc) + amt)+" where Id = "+acc;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            st.executeUpdate(query);
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to Transfer, Try again");
        } catch (SQLException e) {
            System.out.println("Unable to Transfer, Try again");
        }
    }

    //fetching total amount of money from DB
    public int CheckTotal(int acc){
        String query = "select Amount from zed.users_acc where Id = "+acc;
        int amount = 0;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            amount = rs.getInt("Amount");
            st.close();
            con.close();
        }
        catch (ClassNotFoundException e) {
            System.out.println("Unable to Check, Try again");
        } catch (SQLException e) {
            System.out.println("Unable to Check, Try again");
        }
        return amount;
    }

    //Fetching receivers name
    public String TransferTo(int acc){
        String query = "select Name from zed.cred where Id = "+acc, name="";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            name = rs.getString("Name");
            st.close();
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to Show, Try again");
        } catch (SQLException e) {
            System.out.println("Unable to Show, Try again");
        }
        return name;
    }

}
