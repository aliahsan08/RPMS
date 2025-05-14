package oopproject.rpmsfinal;

import java.io.Serial;
import java.io.Serializable;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Handles email notifications using JavaMail API with singleton pattern
 */
public class EmailNotification implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private static EmailNotification instance;

    // SMTP server configuration
    private String host = "smtp.gmail.com";
    private int port = 587;
    private String username = "realtimepatientmanagement@gmail.com"; // Will be set through UI
    private String password = "nwxc lnnk zigi jpki"; // Will be set through UI

    // Private constructor for singleton to prevent multiple instances
    private EmailNotification() {}

    // Singleton pattern to ensure only one instance of EmailNotification
    public static synchronized EmailNotification getInstance() {
        if (instance == null) {
            instance = new EmailNotification();
        }
        return instance;
    }

    // Sends an email notification to a specified address with a subject and message
    public void sendNotification(String email, String subject, String message) {
        try {
            // Set up properties for the SMTP connection
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.ssl.trust", host);

            // Create a session with authentication details
            javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Create the email message
            Message emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress(username)); // Set the sender
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // Set the recipient(s)
            emailMessage.setSubject(subject); // Set the subject of the email
            emailMessage.setText(message); // Set the message content

            // Send the email
            Transport.send(emailMessage);
            System.out.println("Email sent to user");

        } catch (MessagingException e) {
            // Handle failure in sending email
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
