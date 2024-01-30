//используется smtp-сервер google. В качестве пароля отправителя необходимо использовать app passwords
//не полечается отправить сообщение, если в списке невалидная почта (sendpartial не помогает).
package controller;

//import model.Admin;
import model.UserBase;
import model.User;

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
    private String username;
    private String password;
    private Properties props;
    private static String smtp = "smtp.gmail.com";
    EmailSender(){
        this.username = "...";
        this.password = "...";//использовать app passwords google
        props = new Properties();
    }
    private void setProps(){
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.sendpartial", "true");//не работает
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", "465");
    }
    public void send(String header, String text, UserBase to){
        Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        Message message = new MimeMessage(session);
        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
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
            System.out.println("Ошибка отпраки email");
        }


    }
}