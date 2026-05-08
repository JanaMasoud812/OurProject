package services;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailService implements NotificationService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    private final String username;
    private final String password;

    public EmailService(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void sendNotification(String username, String message) { 
        sendEmail(username, "Appointment Reminder", message);
    }

    public void sendEmail(String to, String subject, String body) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        EmailService.this.username,
                        EmailService.this.password
                );
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

        } catch (MessagingException e) {

            // ❌ بدل printStackTrace
            LOGGER.log(Level.SEVERE, "Failed to send email", e);

            throw new RuntimeException("Failed to send email");
        }
    }

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("EMAIL_USERNAME");
        String password = dotenv.get("EMAIL_PASSWORD");

        EmailService emailService = new EmailService(username, password);
        emailService.sendEmail(
                "recipient@example.com",
                "Test Email",
                "Hello! This is a test email from Java."
        );
    }
}