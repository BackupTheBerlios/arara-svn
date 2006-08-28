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
import net.indrix.arara.vo.Sound;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundEmailSender extends AbstractSoundEmailSender {

    /**
     * @param photo
     */
    public SoundEmailSender(Sound sound) {
        super(sound);
    }

    /**
     * {@inheritDoc}
     */
    public void run() {
        UserModel userModel = new UserModel();
        try {
            logger
                    .debug("SoundEmailSender.run : Retrieving list of users to sent email to");
            List list = userModel.retrieveForEmailOnNewPhoto();
            if (!list.isEmpty()) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    User user = (User) it.next();
                    if (!user.equals(sound.getUser())) {
                        logger
                                .debug("SoundEmailSender.run : sending email to user "
                                        + user.getLogin());
                        sendEmailToUser(user, sound);
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
     * This method sends an email about a new sound to the given user
     * 
     * @param user
     *            The user to be notified
     * @param sound
     *            The new sound just added
     */
    private void sendEmailToUser(User user, Sound sound) {
        Locale l = new Locale(user.getLanguage());
        EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
                .getInstance();
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = bundle.getString("email.newSound.subject", l);
        String body = bundle.getString("email.newSound.body", l);
        String fromText = bundle.getString("email.newSound.fromText", l);
        try {

            // send password to user
            logger.debug("enviando email com os dados:");
            logger.debug(server);
            logger.debug(user.getEmail());
            logger.debug(subject);
            logger.debug(getMessage(body, user, sound));
            logger.debug(fromAdd);
            logger.debug(fromText);

            MailClass sender = new MailClass(server);
            logger.debug("Setting to...");
            sender.setBCCAddress(user.getEmail());
            logger.debug("Setting subject...");
            sender.setSubject(subject);
            logger.debug("Setting message...");
            sender.setMessageTextBody(getMessage(body, user, sound));
            logger.debug("Setting from...");
            sender.setFromAddress(fromAdd, fromText);
            logger.debug("Sending message...");
            sender.sendMessage(false);
            // true indicates to emailObject to send the message right now
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail "
                    + e);
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
     * This method creates the message to be sent to user about a new Sound
     * 
     * @param body
     *            The body text template
     * @param sound
     *            The new sound just added into database
     * 
     * @return A string message with the content of the email
     */
    private String getMessage(String body, User user, Sound sound) {
        String bodyFormatted = "";
        ArrayList <String>list = new ArrayList<String>();
        list.add(user.getName());
        list.add(sound.getUser().getLogin() + " | " + sound.getUser().getName());
        String family = sound.getSpecie().getFamily().getName();
        list.add(family);
        String specie = sound.getSpecie().getName();
        list.add(specie);

        logger.debug(family + " | " + specie + " | ");

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("SoundEmailSender.getMessage : Exception", e);
        }
        return bodyFormatted;
    }

}
