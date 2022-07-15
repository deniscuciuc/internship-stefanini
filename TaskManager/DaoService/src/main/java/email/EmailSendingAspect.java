package email;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class EmailSendingAspect {

    @After("@annotation(SendMail) && args(firstName, lastName, userName)")
    public void createMessage(String firstName, String lastName, String userName) {
        String message = "User { " + firstName + " " + lastName + " } identified by {" + userName + "} has been created";
        new EmailSender().sendEmail(message);
    }

    @After("@annotation(SendMail) && args(id, title, description, userName)")
    public void createMessage(int id, String title, String description, String userName) {
        String message = "Task { " + title + " } { " + description + " } has been assigned to {" + userName + "}";
        new EmailSender().sendEmail(message);
    }
}
