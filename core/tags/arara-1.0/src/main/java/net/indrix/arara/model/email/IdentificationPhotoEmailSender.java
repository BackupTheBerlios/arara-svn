/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.email;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.model.UserNotFoundException;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class IdentificationPhotoEmailSender extends AbstractPhotoEmailSender {

    /**
     * Saves the given photo in the class attribute
     * 
     * @param photo The photo just added to the database
     */
	public IdentificationPhotoEmailSender(Photo photo) {
		super(photo);
	}

	/**
	 * @see net.indrix.arara.model.email.AbstractPhotoEmailSender#run()
	 */
	public void run() {
        UserModel userModel = new UserModel();
        try {
            logger.debug("UploadPhoto.sendEmailForIdentification : Retrieving list of users to sent email to");
            List list = userModel.retrieveForPhotoIdentificationEmail();
            if (!list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    User user = (User) it.next();
                    if (!user.equals(photo.getUser())){
                        logger.debug("UploadPhoto.sendEmailForIdentification : sending email to user " + user.getLogin());
                        sendEmailForIdentificationToUser(user, photo);
                    }
                }
            }
        } catch (DatabaseDownException e1) {
            logger.fatal("DatabaseDownException ", e1);
        } catch (SQLException e1) {
            logger.fatal("SQL exception ", e1);
        } catch (UserNotFoundException e1) {
            logger.fatal("No user has been found in database");
        }
    }

    /**
     * This method sends an email about a new photo to the given user
     * 
     * @param user The user to be notified
     * @param photo The new photo just added
     */
    private void sendEmailForIdentificationToUser(User user, Photo photo) {
        Locale l = new Locale(user.getLanguage());
        EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle.getInstance();
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.fromAdd");
        String subject = bundle.getString("email.newPhotoForIdentification.subject", l);
        String body = bundle.getString("email.newPhotoForIdentification.body", l);
        String fromText = bundle.getString("email.newPhotoForIdentification.fromText", l);
        try {

            // send password to user
            logger.debug("enviando email com os dados:");
            logger.debug(server);
            logger.debug(user.getEmail());
            logger.debug(subject);
            logger.debug(getMessageForIdentification(body, user, photo));
            logger.debug(fromAdd);
            logger.debug(fromText);

            MailClass sender = new MailClass(server);
            logger.debug("Setting to...");
            sender.setToAddress(user.getEmail());
            logger.debug("Setting subject...");
            sender.setSubject(subject);
            logger.debug("Setting message...");
            sender.setMessageTextBody(getMessageForIdentification(body, user, photo));
            logger.debug("Setting from...");
            sender.setFromAddress(fromAdd, fromText);
            logger.debug("Sending message...");
            sender.sendMessage(true);
            // true indicates to emailObject to send the message right now
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
     * @param body The body text template
     * @param photo The new photo just added into database
     * 
     * @return A string message with the content of the email
     */
    private String getMessageForIdentification(String body, User user, Photo photo) {
        String bodyFormatted = "";
        ArrayList list = new ArrayList();
        list.add(user.getName());
        list.add(photo.getUser().getLogin() + " | " + photo.getUser().getName());
        String url = "http://www.aves.brasil.nom.br/servlet/initIdentification?photoId=" + photo.getId();
        list.add(url);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("UploadPhoto.getMessage : Exception", e);
        }
        return bodyFormatted;
    }


}
