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
import net.indrix.arara.model.file.AbstractFileManager;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.utils.jsp.Thumbnail;
import net.indrix.arara.vo.LightUser;
import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoEmailSender extends AbstractPhotoEmailSender {

	private static final int PHOTO_WIDTH = 240;

    /**
	 * @param photo
	 */
	public PhotoEmailSender(Photo photo) {
		super(photo);
	}

	/**
	 * @see net.indrix.arara.model.email.AbstractPhotoEmailSender#run()
	 */
	public void run() {
		UserModel userModel = new UserModel();
		try {
			logger.debug("PhotoEmailSender.sendEmail : Retrieving list of users to sent email to");
			List list = userModel.retrieveForEmailOnNewPhoto();
			if (!list.isEmpty()) {
				Iterator it = list.iterator();
                logger.debug("PhotoEmailSender.sendEmail : sending email to users...");
				while (it.hasNext()) {
					LightUser user = (LightUser) it.next();
					if (user.getId() != photo.getUser().getId()) {
						sendEmailToUser(user, photo);
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
	 * @param user
	 *            The user to be notified
	 * @param photo
	 *            The new photo just added
	 */
	private void sendEmailToUser(LightUser user, Photo photo) {
		Locale l = new Locale(user.getLanguage());
		EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
				.getInstance();
		String server = PropertiesManager.getProperty("email.server");
		String fromAdd = PropertiesManager.getProperty("email.from");
		String subject = bundle.getString("email.newPhoto.subject", l);
		String body = bundle.getString("email.newPhoto.body", l);
		String fromText = bundle.getString("email.newPhoto.fromText", l);
		try {

			MailClass sender = new MailClass(server);
			sender.setBCCAddress(user.getEmail());
			sender.setSubject(subject);
            sender.setMessageTextHTML(getMessage(body, user, photo));
			sender.setFromAddress(fromAdd, fromText);
			sender.sendMessage(false);
			// false indicates to emailObject to not send the message right now
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
	 * This method creates the message to be sent to user about a new photo
	 * 
	 * @param body
	 *            The body text template
	 * @param photo
	 *            The new photo just added into database
	 * 
	 * @return A string message with the content of the email
	 */
	private String getMessage(String body, LightUser user, Photo photo) {
        logger.debug("Body: " + body);

        String link = AbstractFileManager.getRootLink();        
        link += photo.getThumbnailRelativePathAsLink();

        int w = Thumbnail.getWidth(PHOTO_WIDTH, photo.getSmallImage().getWidth(), photo.getSmallImage().getHeight());
        int h = Thumbnail.getHeight(PHOTO_WIDTH, photo.getSmallImage().getWidth(), photo.getSmallImage().getHeight());

		String bodyFormatted = "";
		ArrayList<String> list = new ArrayList<String>();
		list.add(user.getName());
		list.add(photo.getUser().getLogin() + " | " + photo.getUser().getName());
		String family = photo.getSpecie().getFamily().getName();
		list.add(family);
		String specie = photo.getSpecie().getName();
		list.add(specie);
        String popularName = photo.getSpecie().getCommonNamesString();
        list.add(popularName);
		String url = "http://www.aves.brasil.nom.br/servlet/showOnePhoto?photoId=" + photo.getId();
		list.add(url);
		list.add(link);
		list.add(""+w);
        list.add(""+h);

		try {
			bodyFormatted = MessageComposer.formatMessage(body, list);
            logger.debug("Message: " + bodyFormatted);
		} catch (WrongNumberOfValuesException e) {
			logger.error("PhotoEmailSender.getMessage : Exception", e);
		}
		return bodyFormatted;
	}

}
