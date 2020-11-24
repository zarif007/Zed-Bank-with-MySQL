package Project;

import java.util.Random;
import java.util.Scanner;
import java.sql.*;

public class MAin{
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

//      Connecting to MySql DB
        String Mysql_url = "jdbc:mysql://localhost:3306/zed";
        String Mysql_username = "Your Database username";
        String Mysql_password = "Your Database password";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(Mysql_url, Mysql_username, Mysql_password);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from zed.cred");

//----------------------------Main Dashboard---------------------------------
        Scanner sc=new Scanner(System.in);
        int counter_of_accounts=0;
        while(rs.next())
            counter_of_accounts++;

        String name="";
        String Mail="";
        int age= 0;
        String Password="";

        /**
         * Creating Objects of Database, SendEmail and User classes
         * */
        Database DB=new Database();
        SendEmail SM=new SendEmail();
        User US=new User();

        System.out.println("Welcome to ZedBank\n1.Create an Account(1)\n2.Log in to your Account(2)");
        int first_input=sc.nextInt();

        //Taking Submission Credentials
        for(;true;){
            if(first_input==1){
                sc = new Scanner(System.in);
                System.out.println("Enter your Full name");
                name = sc.nextLine();

                sc = new Scanner(System.in);
                System.out.println("Enter your age");

                age = sc.nextInt();
                sc = new Scanner(System.in);
                System.out.println("Enter your MailID");

                Mail = sc.nextLine();
                System.out.println("Enter your Password");
                Password = sc.nextLine();

//              Random number generations and sending mail function

                Random num = new Random();
                int RanG=num.nextInt(10000);
                SM.AuthenticatorPinGen(Mail, RanG, name);
                System.out.println("We have sent you an Authentication Pin number, write here");
                int Pin = sc.nextInt();

                //Verifying and loading credentials on Database
                if(true){
                    DB.encrypt(Password);
                    DB.Create(name, Mail, age, counter_of_accounts);
                    counter_of_accounts++;
                    System.out.println("Mr. " + name + " Your Account has been created Successfully");
                }
                else{
                    System.out.println("Authorization UnSuccessful\nRetry(1) or Quit creating Account(2)");
                    int ans=sc.nextInt();
                    if(ans==1)
                        continue;
                    else
                        break;
                }
            }
            sc = new Scanner(System.in);
            System.out.println("Want to Create another account?(y/n)");
            char yes = sc.next().charAt(0);
            if(yes != 'y')
                break;
        }

        //Taking Log-in credentials
        for(;true;){
            System.out.println("Want to log in(y/n)");
            char yes2 = sc.next().charAt(0);
            if(yes2 == 'n')
                break;
            else if(yes2=='y'){
                sc = new Scanner(System.in);
                System.out.println("Enter mail");
                String mail_input = sc.nextLine();
                System.out.println("Enter password");
                String pass_input = sc.nextLine();
                boolean authenticator = DB.Login(mail_input, pass_input);

                if(authenticator){
                    System.out.println("Authentication Successful");
                    System.out.println("Welcome to Zed bank");
                    DB.Account_number(mail_input);
                    for(;true;){

                        //Only Admin can avail these functions
                        if(mail_input.equals("admin")){
                            for(;true;){
                                System.out.println("Admin Interface");
                                System.out.println("*Display Database(A1)\n*Inserting a new User(A2)\n*Deleting a User(A3)");
                                Admin ad = new Admin();
                                String ans = sc.nextLine();
                                if(ans.equals("A1"))
                                    ad.DisplayAll(counter_of_accounts);
                                else if(ans.equals("A2")){
                                    System.out.println("Name?");
                                    String n = sc.nextLine();
                                    System.out.println("Email Id?");
                                    String m = sc.nextLine();
                                    sc = new Scanner(System.in);
                                    System.out.println("Age?");
                                    int a= sc.nextInt();
                                    sc = new Scanner(System.in);
                                    System.out.println("Password");
                                    String p = sc.nextLine();
                                    ad.InsertUser(n, m, a, p, counter_of_accounts);
                                }
                                else if(ans.equals("A3")){
                                    System.out.println("Users Id");
                                    int id = sc.nextInt();
                                    ad.DeleteUser(id);
                                }
                                else
                                    System.out.println("-------");
                            }
                        }

                        //Showing banks's Functions on UI
                        int amount = US.DisplayMoney(mail_input);
                        System.out.println("Current amount is "+amount+" TK");
                        System.out.println("1.Withdraw Money(1)\n2.Deposit Money(2)\n3.Transfer Money(3) ");
                        int ui = sc.nextInt();

                        //Function for Withdrawing money
                        if(ui==1){
                            System.out.println("Please input the amount");
                            int wi=sc.nextInt();
                            US.Withdraw(wi, mail_input, amount);
                            System.out.println("Current amount "+US.DisplayMoney(mail_input));
                        }

                        //Function for Depositing money
                        else if(ui==2){
                            System.out.println("Input the amount");
                            int De=sc.nextInt();
                            US.Deposit(De, mail_input, US.DisplayMoney(mail_input));
                            System.out.println("Current amount "+US.DisplayMoney(mail_input));
                        }

                        //Function for Transfer money to another account
                        else{
                            amount = US.DisplayMoney(mail_input);
                            System.out.println("Input Account number");
                            int acc = sc.nextInt();
                            System.out.println("Enter amount");
                            int amt = sc.nextInt();
                            sc = new Scanner(System.in);
                            System.out.println("Enter your password");
                            String pass = sc.nextLine();
                            if(DB.Login(mail_input, pass)){
                                if(US.check(amt, mail_input, amount)){
                                    if(US.TransferTo(acc).equals(""))
                                        System.out.println("Transaction incomplete");
                                    else {
                                        System.out.println("Money Transfer to " + US.TransferTo(acc));
                                        US.Withdraw(amt, mail_input, amount);
                                        US.Transfer(acc, amt);
                                    }
                                }
                                else
                                    System.out.println("Insufficient balance");
                            }
                            else
                                System.out.println("Transfer not Granted , check your credentials and try again");

                            System.out.println("Current amount "+US.DisplayMoney(mail_input));
                        }
                        System.out.println("Stay logged in (1) or logout(2)");
                        int DM = sc.nextInt();
                        if(DM != 1)
                            break;
                    }
                }
                else
                    System.out.println("Authentication Unsuccessful");
            }
        }
    }
}
