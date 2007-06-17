package net.indrix.arara.model.email;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.StatisticsModel;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Statistics;
import net.indrix.arara.vo.User;

/**
 * This class sends an email to the photo author, when the photo is to a specie without photo.
 *  
 * @author Jeff
 */
public class NewSpecieEmailSender extends AbstractPhotoEmailSender {
    public NewSpecieEmailSender(Photo photo) {
        super(photo);
    }

    /**
     * @see net.indrix.arara.model.email.AbstractPhotoEmailSender#run()
     */
    public void run() {
        logger.debug("NewSpecieEmailSender.sendEmail : Retrieving list of users to sent email to");
        User user = photo.getUser();
        sendEmailToUser(user, photo);
    }

    public void sendNewSpecieEmail(Photo photo) throws DatabaseDownException, SQLException {
        Statistics s = StatisticsModel.retrieveStatistics();
        String msg = "A new photo has been added to database\n"
                + "Specie: " + photo.getSpecie() + "\n" + "Author: "
                + photo.getUser().getLogin() + "\n"
                + "New number of Species: " + s.getNumberOfSpeciesWithPhoto();
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        MailClass sender = new MailClass(server);
        try {
            sender.setToAddress(fromAdd);
            sender.setSubject("[Aves do Brasil] Nova espécie com foto");
            sender.setMessageTextBody(msg);
            sender.setFromAddress(fromAdd, "Webmaster");
            sender.sendMessage(false);
        } catch (Exception e) {
            logger.error("Could not send new specie email", e);
        } finally {
            // log message
            logger.info(msg);           
        }
    }


    /**
     * This method sends an email about a new photo to the given user
     * 
     * @param user
     *            The user to be notified
     * @param photo
     *            The new photo just added
     */
    private void sendEmailToUser(User user, Photo photo) {
        Locale l = new Locale(user.getLanguage());
        EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle.getInstance();
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = bundle.getString("email.newSpecie.subject", l);
        String body = bundle.getString("email.newSpecie.body", l);
        String fromText = bundle.getString("email.newSpecie.fromText", l);
        try {

            MailClass sender = new MailClass(server);
            sender.setBCCAddress(user.getEmail());
            sender.setSubject(subject);
            sender.setMessageTextBody(getMessage(body, user, photo));
            sender.setFromAddress(fromAdd, fromText);
            sender.sendMessage(false);
            // false indicates to emailObject to send the message right later
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail " + e);
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
        }
    }

    /**
     * This method creates the message to be sent to user about a new photo
     * 
     * @param body
     *            The body text template
     * @param photo
     *            The new photo just added into database
     * 
     * @return A string message with the content of the email
     */
    private String getMessage(String body, User user, Photo photo) {
        String bodyFormatted = "";
        ArrayList<String> list = new ArrayList<String>();
        list.add(user.getName());
        String specie = photo.getSpecie().getName();
        list.add(specie);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("NewSpecieEmailSender.getMessage : Exception", e);
        }
        return bodyFormatted;
    }
}
