/*
 * Created on 12/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.UserDAO;
import net.indrix.arara.model.exceptions.UserNotValidatedException;
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
     * Email da locaweb, que deve receber uma cópia do email de confirmação a ser enviado para o
     * usuário.
     * 
     * Code commented because it was needed only for one message. Keeping code commented for history
     */
	//private static final String ABUSE_LOCAWEB_EMAIL = "abuse@locaweb.com.br";

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
	 * @throws UserNotValidatedException 
	 */
	public User login(String login, String password)
			throws DatabaseDownException, SQLException, UserNotFoundException, UserNotValidatedException {
		User dbUser = userDAO.retrieve(login);
		if (dbUser == null) {
			throw new UserNotFoundException();
		} else {
			String encryptedPassword = Cryptography.cryptPassword(password);
			if (!encryptedPassword.equals(dbUser.getPassword())) {
				dbUser = null;
			} else {
                if (!dbUser.isActive()){
                    throw new UserNotValidatedException();
                }
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
     * This method updates a user so that he/she will not receive emails any more.
     * 
     * @param id The user id
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void cancelEmail(int id) throws DatabaseDownException, SQLException{
        userDAO.cancelEmail(id);
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
		logger.debug("UserModel.retrieveForPhotoIdentificationEmail : retrieving users to send email to");
		List list = userDAO.retrieveForPhotoIdentificationEmail();
		if ((list == null) || (list.isEmpty())) {
			logger.debug("List with users is EMPTY !");
		}
		return list;
	}

    /**
     * Temp code!!!
     */
    public List<User> retrieveInactiveUsers() throws DatabaseDownException, SQLException {
        List<User> list = userDAO.retrieveInactiveUsers();
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
		
        sendConfirmationEmailToUser(user);
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
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 * @throws UserNotFoundException If the user is not in database
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
     * This method verifies whether a user confirmation is valid or not.
     *  
     * @param key The key sent from user, from the link he clicked
     * 
     * @return The user object, if the confirmation is valid.
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     * @throws UserNotFoundException If the user is not in database
	 */
    public User confirmRegistration(String key) throws DatabaseDownException, SQLException, UserNotFoundException {
        String login = getLoginFromKey(key);
        User user = null;
        user = retrieve(login);
        if (user != null){
            String userKey = generateKey(user);
            
            if (key.equals(userKey)){
                logger.debug("UserModel.confirmRegistration: user VALIDATED...");
                
                userDAO.updateUserAccountStatus(user.getId(), true);
                
                sendEmailToWebmaster(user);
            } else {
                logger.debug("UserModel.confirmRegistration: user is not valid...");
                throw new UserNotFoundException();
            }
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
		String fromAdd = PropertiesManager.getProperty("email.from");
		String subject = PropertiesManager.getProperty("email.newUser.subject");
		String body = PropertiesManager.getProperty("email.newUser.body");
		String toAdd = PropertiesManager.getProperty("email.newUser.toAdd");
		String fromText = PropertiesManager.getProperty("email.newUser.fromText");
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

    private void sendConfirmationEmailToUser(User user) {
        logger.debug("UserModel.sendConfirmationEmailToUser: enviando email para usuário...");
        
        String link = "http://www.aves.brasil.nom.br/servlet/confirmRegistration?id=" + generateKey(user);
        
        EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle.getInstance();
        Locale l = new Locale(user.getLanguage());
        
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String fromText = bundle.getString("email.general.fromText", l);

        String subject = bundle.getString("email.newUserConfirmation.subject", l);
        String hello = bundle.getString("email.newUserConfirmation.hello", l);
        String msg = bundle.getString("email.newUserConfirmation.message", l);
        String linkText = bundle.getString("email.newUserConfirmation.link", l);
        String thanks = bundle.getString("email.newUserConfirmation.thanks", l);
        try {
            String body = hello + msg + linkText + thanks;
            // send password to user
            logger.debug("Enviando email de confirmação para usuário, com os dados:");
            logger.debug(server + " | " + fromAdd + " | " + subject);
            logger.debug(getConfirmationMessage(body, user, link));

            MailClass sender = new MailClass(server);
            logger.debug("Setting to...");
            sender.setToAddress(user.getEmail());
            sender.setBCCAddress(fromAdd);

            // Code commented because it was needed only for one message. Keeping code commented for history
            //sender.setBCCAddress(ABUSE_LOCAWEB_EMAIL);
            logger.debug("Setting subject...");
            sender.setSubject(subject);
            logger.debug("Setting message...");
            sender.setMessageTextBody(getConfirmationMessage(body, user, link));
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
    private String getConfirmationMessage(String body, User user, String link) {
        /*
        email.newUserConfirmation.hello=Olá _$$$_!
        email.newUserConfirmation.message=Foi efetuado o cadastro no site Aves do Brasil com seu email. 
        email.newUserConfirmation.link=Para confirmar o registro, clique <a href="http://www.aves.brasil.nom.br/servlets/confirmUser?id=_$$$_">aqui</a>.
        email.newUserConfirmation.thanks=\n\nObrigado pelo seu interesse no site Aves do Brasil.
        */
        
        String bodyFormatted = "";
        ArrayList <String>list = new ArrayList<String>();
        String name = user.getName();
        list.add(name);
        list.add(link);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("UserModel.getMessage : Exception", e);
        }
        return bodyFormatted;
    }

    /**
     * This method generates the key to be appended to the email sent to user
     * Algorithm is:
     * (33 + user.id + year)!(month+day)$-(77+user.id)$=user.login
     * @param user The user data
     * 
     * @return a key that will be sent do user inside the email body, as link
     */
	private String generateKey(User user) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(user.getRegisteredOn());
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        String key = (33 + user.getId() + year) + "!" + (month + day) + "$-" + (77+user.getId()) + "$=" + user.getLogin();
        logger.debug("UserModel.generateKey: key generated: " + key);
        return key;
    }

    /**
     * This method retrieves the user login from the key 
     * Algorithm for key is:
     * (33 + user.id + year)!(month+day)$-(77+user.id)$=user.login
     * @param user The user data
     * 
     * @return a key that will be sent do user inside the email body, as link
     */
    private String getLoginFromKey(String key) {
        String login = "";
        int index = key.indexOf("$=");
        if (index > 0){
            index += 2;
            
            login = key.substring(index);
        } 
        logger.debug("UserModel.getIdFromKey: login retrieved: " + login);
        return login;
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
