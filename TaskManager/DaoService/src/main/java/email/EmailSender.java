package email;

import dao.impl.helper.PropertiesHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailSender {
    private static final Logger logger = LogManager.getLogger(EmailSender.class);
    private PropertiesHelper propertiesHelper = new PropertiesHelper("mail.properties");

    public void sendEmail(String text) {
        Session session = Session.getInstance(propertiesHelper.getProperties());
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(propertiesHelper.readProperty("mail.smtp.user")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(propertiesHelper.readProperty("to")));
            message.setSubject("Task Manager update");
            message.setText(text);
            Transport.send(message, propertiesHelper.readProperty("mail.smtp.user"), propertiesHelper.readProperty("mail.smtp.password"));
            logger.info("Email sent");
        } catch (MessagingException e){
            logger.error("Error occurred while sending email" + e.getMessage());
            e.printStackTrace();
        }
    }
}
