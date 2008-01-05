/*
 * Created on 09/10/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.indrix.arara.vo.Comment;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommentsDAO extends AbstractDAO {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	private static final String ID_COLUMN = "id";

	private static final String USER_ID_COLUMN = "user_id";

	private static final String DATE_COLUMN = "date";

	private static final String COMMENT_COLUMN = "comment";

	/**
	 * This sql statement inserts a new comment into database
	 */
	private static final String INSERT = "INSERT INTO user_comments_photo (user_id, photo_id, comment, date) values (?, ?, ?, ?)";

    /**
     * SQL for deleting comments of a given photo 
     */
    private static final String DELETE_BY_PHOTO_ID = "DELETE FROM user_comments_photo WHERE photo_id = ?";

	/**
	 * This sql statement selects all comments for a given photo
	 */
	private static final String SELECT_COMMENTS = "SELECT * FROM user_comments_photo where photo_id = ?";

	/**
	 * This sql statement selects all users that had commented an specific
	 * photo.
	 */
	private static final String SELECT_USERS_FOR_PHOTO = "select distinct u.id, u.login, u.name, u.email, u.language from user_comments_photo c, user u "
			+ " where u.emailOnNewComment = 1 and c.user_id = u.id and photo_id = ? and user_id != ?";

	private Photo photo = null;

	/**
	 * This method inserts a comment to a photo
	 * 
	 * @param comment
	 *            The comment object.
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public void insertComment(Comment comment) throws DatabaseDownException,
			SQLException {
		super.insertObject(comment, INSERT);
	}

	/**
	 * This method retrieves a <code>List</code> object with
	 * <code>Comment</code> objects, based on the id of the photo
	 * 
	 * @param id
	 *            The id of the <code>List</code>
	 * 
	 * @return a <code>List</code> object with <code>Photo</code> objects,
	 *         based on the id of the specie
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieveComments(Photo photo) throws DatabaseDownException,
			SQLException {
		this.photo = photo;
		List list = super.retrieveObjects(photo.getId(), SELECT_COMMENTS);
		return list;
	}

	/**
	 * This method retrieves all users that had commented an specific photo. The
	 * user given by userId is not included in the list
	 * 
	 * @param photoId
	 *            The id of the photo with comments
	 * 
	 * @return a list with users objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 */
	public List<User> retrieveUsersWithCommentsForPhoto(int photoId, int userId)
			throws DatabaseDownException {
		logger.debug("Entering method...");
		List <User>list = new ArrayList<User>();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			logger.debug("Running SQL " + SELECT_USERS_FOR_PHOTO);
			logger.debug("Running SQL with " + photoId + "," + userId);
			stmt = conn.prepareStatement(SELECT_USERS_FOR_PHOTO);
			stmt.setInt(1, photoId);
			stmt.setInt(2, userId);

			rs = stmt.executeQuery();

			logger.debug("Adding emails to list...");
			while (rs.next()) {
				User u = (User) createUserObject(rs);
				list.add(u);
				logger.debug("Adding user: " + u);
			}
		} catch (SQLException e) {
			logger.debug("SQLException !", e);
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			try {
				conn.close();
			} catch (SQLException e1) {
				throw new DatabaseDownException();
			}
		}
		logger.debug("Finishing method...");
		return list;
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
		Comment c = new Comment();
		c.setId(rs.getInt(ID_COLUMN));
		c.setComment(rs.getString(COMMENT_COLUMN));
		c.setDate(getDate(rs, DATE_COLUMN));
		c.setPhoto(photo);
		UserDAO userDao = new UserDAO();
		User user;
		try {
			logger.debug("Retrieving user for comment with id = " + c.getId());
			user = userDao.retrieve(rs.getInt(USER_ID_COLUMN));
			c.setUser(user);
		} catch (DatabaseDownException e) {
			logger.fatal("CommentsDAO.createObject : DatabaseDownException", e);
		}
		return c;
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
	protected Object createUserObject(ResultSet rs) throws SQLException {
		User user;
		user = new User();
		user.setId(rs.getInt(ID_COLUMN));
		user.setName(rs.getString(UserDAO.NAME_COLUMN));
		user.setLogin(rs.getString(UserDAO.LOGIN_COLUMN));
		user.setEmail(rs.getString(UserDAO.EMAIL_COLUMN));
		user.setLanguage(rs.getString(UserDAO.LANGUAGE_COLUMN));
		return user;
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
		Comment comment = (Comment) object;
		comment.setId(id);
	}

	/**
	 * This method set the values into statement, before running the SQL in
	 * insert method
	 * 
	 * @param stmt
	 *            The statement to insert the values to sql
	 * @param object
	 *            The object to retrieve the values from
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object)
			throws SQLException {
		Comment comment = (Comment) object;
		stmt.setInt(1, comment.getUser().getId());
		stmt.setInt(2, comment.getPhoto().getId());
		stmt.setString(3, comment.getComment());
		stmt.setTimestamp(4, getTimestamp(comment.getDate()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.indrix.dao.AbstractDAO#setStatementValuesForUpdate(java.sql.PreparedStatement,
	 *      java.lang.Object)
	 */
	protected void setStatementValuesForUpdate(PreparedStatement stmt,
			Object object) throws SQLException {
		// no implementation for this class is needed
	}

    @Override
    protected String getDeleteSQL() {
        return DELETE_BY_PHOTO_ID;
    }    
}
