//используется smtp-сервер google. В качестве пароля отправителя необходимо использовать app passwords
package controller;

import model.Admin;
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
    EmailSender(String username, String password){
        this.username = "alenakuzmina629@gmail.com";
        this.password = "msqcmbcoomtfonyx";
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.host", smtp);
        props.put("mail.smtp.port", "465");
    }
    public void send(String header, String text, String from, String to){
        Session session = Session.getInstance(props, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));//??????????? или from?
            //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("kuzya295@mail.ru"));
            message.setSubject(header);
            message.setText(text);

            Transport.send(message);
            System.out.println("Сообщение отправлено!");

        } catch (MessagingException e) {
            System.out.println("Ошибка отпраки email");
            throw new RuntimeException(e);
        }
    }
    public void sendToAdmins(String header, String text, UserBase userBase) {
        StringBuilder admins = new StringBuilder();
        for (User user : userBase.getUserList()) {
            if (user instanceof Admin) {
                admins.append(user.getEmail());
                admins.append(", ");
            }
        }
        send(header, text, username, admins.toString());
    }

    public void sendToAll(String header, String text, UserBase userBase) {
        StringBuilder users = new StringBuilder();
        for (User user : userBase.getUserList()) {
            users.append(user.getEmail());
            users.append(", ");
        }
        send(header, text, username, users.toString());
    }
}