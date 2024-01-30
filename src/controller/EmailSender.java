//используется smtp-сервер google. В качестве пароля отправителя необходимо использовать app passwords
//не полечается отправить сообщение, если в списке невалидная почта (sendpartial не помогает).
package controller;

//import model.Admin;
import model.UserBase;
import model.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {
    private Properties sessionProps;
    private Properties librarySenderProps;
    private static String smtp = "smtp.gmail.com";
    EmailSender(){
        sessionProps = new Properties();
        librarySenderProps = new Properties();
    }
    public void setProps(){
        try {
            sessionProps.load(new FileInputStream("src/files/session.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            librarySenderProps.load(new FileInputStream("src/files/librarySender.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

/*        sessionProps.put("mail.smtp.auth", "true");
        sessionProps.put("mail.smtp.socketFactory.port", "465");
        sessionProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sessionProps.put("mail.smtp.sendpartial", "true");//не работает
        sessionProps.put("mail.smtp.host", smtp);
        sessionProps.put("mail.smtp.port", "465");*/
    }
    public void send(String header, String text, UserBase to){
        Session session = Session.getInstance(sessionProps, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(librarySenderProps.getProperty("email"),
                                librarySenderProps.getProperty("password"));
                    }
                });
        Message message = null;
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(librarySenderProps.getProperty("email")));
            message.setSubject(header);
            message.setText(text);

            StringBuilder emails = new StringBuilder();
            for (User user : to.getUserList()) {
                    emails.append(user.getEmail());
                    emails.append(", ");
            }
            String emailsToSend = emails.toString();
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailsToSend));
            Transport.send(message);
            System.out.println("Сообщение отправлено!");

        } catch (MessagingException e) {
            System.out.println(e);
            System.out.println("Ошибка отправки email");
        }


    }
}