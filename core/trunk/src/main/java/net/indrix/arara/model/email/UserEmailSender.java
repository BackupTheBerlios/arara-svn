package net.indrix.arara.model.email;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.model.UserNotFoundException;
import net.indrix.arara.model.email.exceptions.SendEmailException;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.User;

/**
 * This class is responsible for sending a message from one user to another. 
 * 
 * The sender is the website, and the sender's email is not visible to the user.
 * 
 * @author Jeff
 *
 */
public class UserEmailSender extends AbstractEmailSender {

    private String login;
    private String subject;
    private String message;
    private User userFrom;
    private User userTo;
    
    public UserEmailSender(String login, String subject, String message, User userFrom){
        this.login = login;
        this.subject = subject;
        this.message = message;
        this.userFrom = userFrom;
    }
    
    /**
     * This method sends an email from the logged user to the selected user
     */
    public void sendEmailNow() throws SendEmailException{
        UserModel userModel = new UserModel();
        try {
            logger.debug("UserEmailSender.sendEmailNow : Retrieving email for user " + login);
            userTo = userModel.retrieve(login);
            if (userTo != null) {
                sendEmailToUser();
            }
        } catch (DatabaseDownException e1) {
            logger.fatal("DatabaseDownException ", e1);
            throw new SendEmailException();
        } catch (SQLException e1) {
            logger.fatal("SQLException ", e1);
            throw new SendEmailException();
        } catch (UserNotFoundException e1) {
            logger.fatal("UserNotFoundException ", e1);
            throw new SendEmailException();
        }
    }

    /**
     * This method sends an email from the logged user to the selected user
     */
    private void sendEmailToUser() throws SendEmailException{
        Locale l = new Locale(userTo.getLanguage());
        EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle.getInstance();
        String body = bundle.getString("email.fromUser.body", l);
        
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        try {

            // send email to user
            MailClass sender = new MailClass(server);
            logger.debug("Setting to...");
            sender.setBCCAddress(userTo.getEmail());
            logger.debug("Setting subject...");
            sender.setSubject(subject);
            logger.debug("Setting message...");
            String msg = getMessage(body, message, userTo, userFrom);
            sender.setMessageTextBody(msg);
            logger.debug("Setting from...");
            sender.setFromAddress(fromAdd, "Aves do Brasil");
            logger.debug("Sending message..." + msg);
            sender.sendMessage(true);
            // true indicates to emailObject to send the message right now
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail " + e);
            throw new SendEmailException();
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
            throw new SendEmailException();
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
            throw new SendEmailException();
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
            throw new SendEmailException();
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
            throw new SendEmailException();
        }
    }

    /**
     * This method creates the message to be sent to user about a new Sound
     * 
     * @param body The body text template
     * @param message The message from the user
     * @param userTo The user sending the message
     * @param userSender THe user to send the message to
     * 
     * @return A string message with the content of the email
     */
    private String getMessage(String body, String message, User userTo, User userSender) {
        String bodyFormatted = "";
        ArrayList <String>list = new ArrayList<String>();
        list.add(userTo.getName());
        list.add(userSender.getLogin() + " - " + userSender.getName());

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
            bodyFormatted += "\n\n" + message; 
        } catch (WrongNumberOfValuesException e) {
            logger.error("UserEmailSender.getMessage : Exception", e);
            bodyFormatted = message;
        }
        return bodyFormatted;
    }

    @Override
    public void run() {
        // do nothing. This class sends the email right way
    }
}
