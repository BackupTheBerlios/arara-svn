/*
 * Created on 07/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.indrix.arara.vo.LightUser;
import net.indrix.arara.vo.User;

public class UserDAO extends AbstractDAO {
	public static final String ID_COLUMN = "id";
	public static final String NAME_COLUMN = "name";
	public static final String LOGIN_COLUMN = "login";
	public static final String PASSWORD_COLUMN = "password";
	public static final String EMAIL_COLUMN = "email";
	public static final String LANGUAGE_COLUMN = "language";
	public static final String EMAIL_ON_NEW_PHOTO_COLUMN = "emailOnNewPhoto";
	public static final String EMAIL_ON_NEW_ID_PHOTO_COLUMN = "emailOnNewIdPhoto";
	public static final String EMAIL_ON_NEW_SOUND_COLUMN = "emailOnNewSound";
	public static final String ADD_PHOTO_COLUMN = "addPhoto";
	public static final String ADD_SOUND_COLUMN = "addSound";
	public static final String ADMIN_COLUMN = "admin";
    public static final String ACTIVE_COLUMN = "active";
    public static final String REGISTERED_ON_COLUMN = "registeredOn";
    
	public static final String INSERT = "INSERT INTO user (name, login, password, email, language, emailOnNewPhoto, emailOnNewIdPhoto, emailOnNewSound, addPhoto, addSound, registeredOn) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE = "UPDATE user "
			+ "set name = ?, password = ?, email = ?, language = ?, emailOnNewPhoto = ?, emailOnNewIdPhoto = ?, emailOnNewSound = ? "
			+ "WHERE id = ?";

    public static final String UPDATE_USER_CANCEL_EMAIL = "UPDATE user "
        + "set emailOnNewPhoto = ?, emailOnNewIdPhoto = ?, emailOnNewSound = ? "
        + "WHERE id = ?";

    public static final String UPDATE_USER_ACTIVE = "UPDATE user set active = ?, registeredOn = ? WHERE id = ?";

	private static final String SELECT_ALL_FOR_PHOTOS = "SELECT * FROM user where addPhoto = 1 order by name";

    private static final String SELECT_ALL_FOR_SOUNDS = "SELECT * FROM user where addSound = 1 order by name";

	private static final String SELECT_BY_ID = "SELECT * FROM user WHERE id = ?";

	private static final String SELECT_BY_LOGIN = "SELECT * FROM user where login = ?";

	private static final String SELECT_FOR_EMAIL_ON_NEW_PHOTO = "SELECT * FROM user where emailOnNewPhoto = 1 and active = 1";

	private static final String SELECT_FOR_EMAIL_ON_NEW_ID_PHOTO = "SELECT * FROM user where emailOnNewIdPhoto = 1 and active = 1";

    private static final String SELECT_FOR_EMAIL_ON_NEW_SOUND = "SELECT * FROM user where emailOnNewSound = 1 and active = 1";

    /**
     * Temp code
     */
    private static final String SELECT_INACTIVE_USERS = "SELECT * from user WHERE active = false";
    
	/**
	 * This method retrieves all users from database
	 * 
	 * @return an ArrayList object with User objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieve() throws DatabaseDownException, SQLException {
		List list = super.retrieveObjects(SELECT_ALL_FOR_PHOTOS);
		return list;
	}

    /**
     * This method retrieves all users from database
     * 
     * @return an ArrayList object with User objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveUsersForSound() throws DatabaseDownException, SQLException {
        List list = super.retrieveObjects(SELECT_ALL_FOR_SOUNDS);
        return list;
    }    
	/**
	 * This method retrieves all users from database that want to receive email
	 * 
	 * @return an ArrayList object with User objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieveForEmailOnNewPhoto() throws DatabaseDownException, SQLException {
		logger.debug("UserDAO.retrieveForEmailOnNewPhoto: entering method...");
		List list = super.retrieveObjects(SELECT_FOR_EMAIL_ON_NEW_PHOTO, true);
		return list;
	}

    /**
     * This method retrieves all users from database that want to receive email
     * 
     * @return an ArrayList object with User objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveForEmailOnNewSound() throws DatabaseDownException, SQLException {
        logger.debug("UserDAO.retrieveForEmailOnNewSound: entering method...");
        List list = super.retrieveObjects(SELECT_FOR_EMAIL_ON_NEW_SOUND, true);
        return list;
    }

	/**
	 * This method retrieves all users from database that want to receive email
	 * 
	 * @return an ArrayList object with User objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieveForPhotoIdentificationEmail()
			throws DatabaseDownException, SQLException {
		logger.debug("UserDAO.retrieveForPhotoIdentificationEmail: entering method...");
		List list = super.retrieveObjects(SELECT_FOR_EMAIL_ON_NEW_ID_PHOTO, true);
		return list;
	}

    /**
     * Temp code!!!
     */
    public List<User> retrieveInactiveUsers() throws DatabaseDownException, SQLException {
        logger.debug("UserDAO.retrieveInactiveUsers: entering method...");
        List<User> list = super.retrieveObjects(SELECT_INACTIVE_USERS, false);
        return list;
    }

	/**
	 * This method retrieves a <code>User</code> object based on its id
	 * 
	 * @param id
	 *            The id of the <code>User</code>
	 * 
	 * @return a <code>User</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public User retrieve(int id) throws DatabaseDownException, SQLException {
		User user = (User) super.retrieveObject(id, SELECT_BY_ID);
		return user;
	}

	/**
	 * This method retrieves a <code>User</code> object based on its login
	 * 
	 * @param login
	 *            The login of the <code>User</code>
	 * 
	 * @return a <code>User</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public User retrieve(String login) throws DatabaseDownException,
			SQLException {
		User user = (User) super.retrieveObject(login, SELECT_BY_LOGIN);
		return user;
	}

    /**
     * This method updates a user so that he/she will not receive emails any more.
     * 
     * @param id The user id
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void cancelEmail(int id)
            throws DatabaseDownException, SQLException {

        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(UPDATE_USER_CANCEL_EMAIL);
            stmt.setInt(1, 0);
            stmt.setInt(2, 0);
            stmt.setInt(3, 0);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("USERDAO.cancelEmail : could not update data");
            logger.error("Error in SQL : " + UPDATE_USER_CANCEL_EMAIL, e);
            logger.error(stmt.toString());
            throw e;
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            closeConnection(conn);
        }
    }

    /**
     * This method updates a user so that he/she will not receive emails any more.
     * 
     * @param id The user id
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void updateUserAccountStatus(int id, boolean status)
            throws DatabaseDownException, SQLException {

        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        
        try {
            Timestamp stamp = getTimestamp(getSQLDate());

            stmt = conn.prepareStatement(UPDATE_USER_ACTIVE);
            stmt.setBoolean(1, status);
            stmt.setTimestamp(2, stamp);
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("USERDAO.updateUserAccountStatus : could not update data");
            logger.error("Error in SQL : " + UPDATE_USER_ACTIVE, e);
            logger.error(stmt.toString());
            throw e;
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            closeConnection(conn);
        }
    }
    
	/**
	 * This method creates a <code>User</code> object with the data from
	 * database
	 * 
	 * @param rs
	 *            The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>User</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		User user;
		user = new User();
		user.setId(rs.getInt(ID_COLUMN));
		user.setName(rs.getString(NAME_COLUMN));
		user.setLogin(rs.getString(LOGIN_COLUMN));
		user.setPassword(rs.getString(PASSWORD_COLUMN));
		user.setEmail(rs.getString(EMAIL_COLUMN));
		user.setLanguage(rs.getString(LANGUAGE_COLUMN));
		user.setEmailOnNewPhoto(rs.getInt(EMAIL_ON_NEW_PHOTO_COLUMN) == 1 ? true : false);
		user.setEmailOnNewIdPhoto(rs.getInt(EMAIL_ON_NEW_ID_PHOTO_COLUMN) == 1 ? true : false);
		user.setEmailOnNewSound(rs.getInt(EMAIL_ON_NEW_SOUND_COLUMN) == 1 ? true : false);
		user.setAddPhoto(rs.getInt(ADD_PHOTO_COLUMN) == 1 ? true : false);
		user.setAddSound(rs.getInt(ADD_SOUND_COLUMN) == 1 ? true : false);
		user.setAdmin(rs.getBoolean(ADMIN_COLUMN));
        user.setActive(rs.getBoolean(ACTIVE_COLUMN));
        user.setRegisteredOn(getDate(rs, REGISTERED_ON_COLUMN));
		return user;
	}

    /**
     * This method creates a <code>User</code> object with the data from
     * database
     * 
     * @param rs
     *            The <code>ResultSet<code> object to retrieve the data
     * 
     * @return A new <code>User</code> object 
     * 
     * @throws SQLException If an error occur while retrieving data from result set
     */
    protected Object createLightObject(ResultSet rs) throws SQLException {
        LightUser user;
        user = new LightUser();
        user.setId(rs.getInt(ID_COLUMN));
        user.setName(rs.getString(NAME_COLUMN));
        user.setEmail(rs.getString(EMAIL_COLUMN));
        user.setLanguage(rs.getString(LANGUAGE_COLUMN));
        return user;
    }
    
	/**
	 * This method set the values into statement, before running the SQL in
	 * insert method
	 * 
	 * @param stmt
	 *            The statement to insert the values to sql
	 * @param object
	 *            The object to retrieve the values from
	 * 
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object)
			throws SQLException {
		User user = (User) object;
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getLogin());
		stmt.setString(3, user.getPassword());
		stmt.setString(4, user.getEmail());
		stmt.setString(5, user.getLanguage());
		stmt.setInt(6, user.isEmailOnNewPhoto() ? 1 : 0);
		stmt.setInt(7, user.isEmailOnNewIdPhoto() ? 1 : 0);
		stmt.setInt(8, user.isEmailOnNewSound() ? 1 : 0);
		stmt.setInt(9, 0);
		stmt.setInt(10, 0);
        
        Timestamp stamp = getTimestamp(getSQLDate());
        stmt.setTimestamp(11, stamp);        
        
        Date d = new Date(stamp.getTime());
        user.setRegisteredOn(d);
	}

	/**
	 * This method sets the id into the object
	 * 
	 * @param id
	 *            The id value
	 * @param object
	 *            The object to set the id
	 */
	protected void setObjectId(int id, Object object) throws SQLException {
		User user = (User) object;
		user.setId(id);
	}

	/**
	 * This method set the values into statement, before running the SQL in
	 * update method
	 * 
	 * @param stmt
	 *            The statement to update the values to sql
	 * @param object
	 *            The object to retrieve the values from
	 * 
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	protected void setStatementValuesForUpdate(PreparedStatement stmt,
			Object object) throws SQLException {
		logger.debug("UserDAO.setStatementValuesForUpdate: Entering method");
		User user = (User) object;
		logger.debug("User = " + user);
		stmt.setString(1, user.getName());
		stmt.setString(2, user.getPassword());
		stmt.setString(3, user.getEmail());
		stmt.setString(4, user.getLanguage());
		stmt.setInt(5, user.isEmailOnNewPhoto() ? 1 : 0);
		stmt.setInt(6, user.isEmailOnNewIdPhoto() ? 1 : 0);
		stmt.setInt(7, user.isEmailOnNewSound() ? 1 : 0);
		stmt.setInt(8, user.getId());
		logger.debug("UserDAO.setStatementValuesForUpdate: Exiting method");
	}

	/**
	 * This method returns the SQL statement to insert a new object into
	 * database
	 * 
	 * @return The insert SQL statement
	 */
	protected String getInsertSQL() {
		return INSERT;
	}

	/**
	 * This method returns the SQL statement to update an object in the database
	 * 
	 * @return The update SQL statement
	 */
	protected String getUpdateSQL() {
		return UPDATE;
	}
}
