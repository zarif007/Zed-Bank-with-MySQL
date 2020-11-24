package Project;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;



public class SendEmail {
    public void AuthenticatorPinGen(String To,int pin,String name) {

//--------------------------Auto mail Sending----------------------

        //Setting up properties
        String to = To;
        String from = "Your mailing Id";
        Properties properties= new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);

        //write your email id and password here(from where you want to send mails to your clients)
        Session session=Session.getDefaultInstance(properties,new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication("Your Email Id", "Your Password");
            }
        });
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            //Subject
            message.setSubject("Authentication Pin Number");
            //Body
            message.setText("Hello MR."+name+",\nYour pin number is "+pin);
            Transport.send(message);
            System.out.println("sending");
        }
        catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}