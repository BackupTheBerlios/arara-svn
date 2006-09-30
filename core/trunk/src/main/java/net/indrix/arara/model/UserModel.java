/*
 * Created on 12/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.UserDAO;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UserModel extends AbstractModel {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * The object used to retrieve database information
	 */
	UserDAO userDAO = new UserDAO();

	/**
	 * This method performs the login for a user
	 * 
	 * @param login
	 *            The user login
	 * @param password
	 *            The user password
	 * 
	 * @return A valid user object, if the login is success, null otherwise
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 * @throws UserNotFoundException
	 *             If the user is not in database
	 */
	public User login(String login, String password)
			throws DatabaseDownException, SQLException, UserNotFoundException {
		User dbUser = userDAO.retrieve(login);
		if (dbUser == null) {
			throw new UserNotFoundException();
		} else {
			String encryptedPassword = Cryptography.cryptPassword(password);
			if (!encryptedPassword.equals(dbUser.getPassword())) {
				dbUser = null;
			}
		}
		return dbUser;
	}

	/**
	 * This method checks if a given password is correct for a given user
	 * 
	 * @param user
	 *            The user object
	 * @param password
	 *            The input password
	 * 
	 * @return true if the password is correct, false otherwise
	 */
	public boolean validatePassword(User user, String password) {
		boolean passwordOk = false;

		if (isPasswordValid(password)) {
			String encryptedPassword = Cryptography.cryptPassword(password);
			if (encryptedPassword.equals(user.getPassword())) {
				passwordOk = true;
			}
		} else {
			passwordOk = false;
		}
		return passwordOk;
	}

	/**
	 * This method performs the login for a user
	 * 
	 * @param login
	 *            The user login
	 * @param password
	 *            The user password
	 * 
	 * @return A valid user object, if the login is success, null otherwise
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 * @throws UserNotFoundException
	 *             If the user is not in database
	 */
	public void changePassword(User user, String password)
			throws DatabaseDownException, SQLException {
		logger.debug("Changing password for " + user.getLogin() + " to "
				+ password);
		// encrypt new password
		String encryptedPassword = Cryptography.cryptPassword(password);
		user.setPassword(encryptedPassword);

		// update the user object into database
		userDAO.update(user);
	}

	/**
	 * This method verifies if the given login exists in the database
	 * 
	 * @param login
	 *            The login to be verified in the database
	 * 
	 * @return true if the login exists in the database, false otherwise
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public boolean userExists(String login) throws DatabaseDownException,
			SQLException {
		boolean exists = true;
		try {
			User u = retrieve(login);
            exists = (u != null);
		} catch (DatabaseDownException e) {
			throw e;
		} catch (SQLException e) {
			throw e;
		} catch (UserNotFoundException e) {
			exists = false;
		}
		return exists;
	}

	/**
	 * This method retrieves an user from database, given the login
	 * 
	 * @param login
	 *            The user login to be retrieved
	 * 
	 * @return A user object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 * @throws UserNotFoundException
	 *             If the user is not in database
	 */
	public User retrieve(String login) throws DatabaseDownException,
			SQLException, UserNotFoundException {
		User user = null;
		user = userDAO.retrieve(login);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return user;
	}

	/**
	 * This method retrieves all users from database that want to receive email
	 * 
	 * @return A list of user objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 * @throws UserNotFoundException
	 *             If the user is not in database
	 */
	public List retrieveForEmailOnNewPhoto() throws DatabaseDownException, SQLException,
			UserNotFoundException {
		logger
				.debug("UserModel.retrieveForEmailOnNewPhoto : retrieving users to send email to");
		List list = userDAO.retrieveForEmailOnNewPhoto();
		if ((list == null) || (list.isEmpty())) {
			logger.debug("List with users is EMPTY !");
		}
		return list;
	}

    /**
     * This method retrieves all users from database that want to receive email on new sound
     * 
     * @return A list of user objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     * @throws UserNotFoundException
     *             If the user is not in database
     */
    public List retrieveForEmailOnNewSound() throws DatabaseDownException, SQLException,
            UserNotFoundException {
        logger
                .debug("UserModel.retrieveForEmailOnNewSound : retrieving users to send email to");
        List list = userDAO.retrieveForEmailOnNewSound();
        if ((list == null) || (list.isEmpty())) {
            logger.debug("List with users is EMPTY !");
        }
        return list;
    }
    
	/**
	 * This method retrieves all users from database that want to receive email
	 * 
	 * @return A list of user objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 * @throws UserNotFoundException
	 *             If the user is not in database
	 */
	public List retrieveForPhotoIdentificationEmail()
			throws DatabaseDownException, SQLException, UserNotFoundException {
		logger
				.debug("UserModel.retrieveForPhotoIdentificationEmail : retrieving users to send email to");
		List list = userDAO.retrieveForPhotoIdentificationEmail();
		if ((list == null) || (list.isEmpty())) {
			logger.debug("List with users is EMPTY !");
		}
		return list;
	}

	/**
	 * This method inserts a new User to the database
	 * 
	 * @param user
	 *            The user object to be inserted into the database
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public void insert(User user) throws DatabaseDownException, SQLException {
		encryptUserPassword(user);
		userDAO.insert(user);

		sendEmailToWebmaster(user);
	}

	/**
	 * This method updates a User to the database
	 * 
	 * @param user
	 *            The user object to be updated into the database
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public void update(User user) throws DatabaseDownException, SQLException {
		userDAO.update(user);
	}

	/**
	 * This method retrieves an user from database, given the login
	 * 
	 * @param login
	 *            The user login to be retrieved
	 * 
	 * @return A user object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 * @throws UserNotFoundException
	 *             If the user is not in database
	 */
	public User sendPassword(String login) throws DatabaseDownException,
			SQLException, UserNotFoundException {
		User user = null;
		user = userDAO.retrieve(login);
		if (user == null) {
			throw new UserNotFoundException();
		} else {
			String password = Cryptography.decryptPassword(user.getPassword());
			user.setPassword(password);
			sendPassword(user);
		}
		return user;
	}

	/**
	 * This method notifies the webmaster about a new user
	 * 
	 * @param user
	 *            The new user
	 */
	private void sendEmailToWebmaster(User user) {
		String server = PropertiesManager.getProperty("email.server");
		String fromAdd = PropertiesManager.getProperty("email.fromAdd");
		String subject = PropertiesManager.getProperty("email.newUser.subject");
		String body = PropertiesManager.getProperty("email.newUser.body");
		String toAdd = PropertiesManager.getProperty("email.newUser.toAdd");
		String fromText = PropertiesManager
				.getProperty("email.newUser.fromText");
		try {

			// send password to user
			logger.debug("enviando email com os dados:");
			logger.debug(server);
			logger.debug(subject);
			logger.debug(getMessage(body, user));
			logger.debug(fromAdd);
			logger.debug(toAdd);
			logger.debug(fromText);

			MailClass sender = new MailClass(server);
			logger.debug("Setting to...");
			sender.setToAddress(toAdd);
			logger.debug("Setting subject...");
			sender.setSubject(subject);
			logger.debug("Setting message...");
			sender.setMessageTextBody(getMessage(body, user));
			logger.debug("Setting from...");
			sender.setFromAddress(fromAdd, fromText);
			logger.debug("Sending message...");
			sender.sendMessage(true);
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
	 * This method sends the password to the given user
	 * 
	 * @param user
	 *            The user that requested his/her password
	 */
	private void sendPassword(User user) {
		Locale l = new Locale(user.getLanguage());
		EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
				.getInstance();

		String server = PropertiesManager.getProperty("email.server");
		String fromAdd = PropertiesManager.getProperty("email.from");
		String subject = bundle.getString("email.forgotPassword.subject", l);
		String body = bundle.getString("email.forgotPassword.body", l);
		String fromText = bundle.getString("email.forgotPassword.fromText", l);
		try {

			// send password to user
			logger.debug("enviando email com os dados:");
			logger.debug(server);
			logger.debug(subject);
			logger.debug(getPasswordMessage(body, user));
			logger.debug(fromAdd);
			logger.debug(user.getEmail());
			logger.debug(fromText);

			MailClass sender = new MailClass(server);
			logger.debug("Setting to...");
			sender.setToAddress(user.getEmail());
			logger.debug("Setting subject...");
			sender.setSubject(subject);
			logger.debug("Setting message...");
			sender.setMessageTextBody(getPasswordMessage(body, user));
			logger.debug("Setting from...");
			sender.setFromAddress(fromAdd, fromText);
			logger.debug("Sending message...");
			sender.sendMessage(true);
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
	 * @param body
	 * @return
	 */
	private String getMessage(String body, User user) {
		String bodyFormatted = "";
		ArrayList <String>list = new ArrayList<String>();
		String name = user.getName();
		list.add(name);
		String email = user.getEmail();
		list.add(email);

		logger.debug(name + " | " + email);

		try {
			bodyFormatted = MessageComposer.formatMessage(body, list);
		} catch (WrongNumberOfValuesException e) {
			logger.error("UserModel.getMessage : Exception", e);
		}
		return bodyFormatted;
	}

	/**
	 * @param body
	 * @return
	 */
	private String getPasswordMessage(String body, User user) {
		String bodyFormatted = "";
		ArrayList <String>list = new ArrayList<String>();
		String name = user.getName();
		list.add(name);
		String password = user.getPassword();
		list.add(password);

		try {
			bodyFormatted = MessageComposer.formatMessage(body, list);
		} catch (WrongNumberOfValuesException e) {
			logger.error("UserModel.getPasswordMessage : Exception", e);
		}
		return bodyFormatted;
	}

	/**
	 * This method encrypts the user password
	 * 
	 * @param user
	 *            The user object that will have the password encrypted
	 */
	private void encryptUserPassword(User user) {
		String pass = user.getPassword();
		String newPass = Cryptography.cryptPassword(pass);
		user.setPassword(newPass);
	}

	/**
	 * This method verifies if the password contains a character different than
	 * digit or letter
	 * 
	 * @param p
	 *            The password string to be verified
	 * 
	 * @return true if the pass is valid, false otherwise
	 */
	private boolean isPasswordValid(String p) {
		boolean valid = true;
		for (int i = 0; i < p.length() && valid; i++) {
			if (!Character.isLetterOrDigit(p.charAt(i))) {
				valid = false;
			}
		}

		return valid;
	}
}
