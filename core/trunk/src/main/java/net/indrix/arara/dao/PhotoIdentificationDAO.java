/*
 * Created on 14/01/2006
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

import net.indrix.arara.dao.constants.PhotoConstants;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.PhotoIdentification;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoIdentificationDAO extends AbstractDAO implements PhotoConstants {
	private static final String INSERT_FOR_IDENTIFICATION =
		"INSERT INTO photo"
			+ "(specie_id, specie_family_id, age_id, sex_id, user_id, date, place, camera, lens, film, "
			+ "image, w, h, smallImage, sW, sH, post_date, "
			+ "comment, imageSize, smallImageSize, city_id) "
			+ "values (-1, -1, -1, -1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String INSERT_IDENTIFICATION_FOR_PHOTO =
		"INSERT INTO user_identifies_photo"
			+ "(user_id, photo_id, specie_id, sex_id, age_id, comment, date) "
			+ "values (?, ?, ?, ?, ?, ?, ?)";

	/**
	 * SQL to select id of all photos  
	 */
	private static final String SELECT_IDS_FOR_ALL =
		"SELECT p.id " + "from photo p " + "where p.specie_id = -1";

	/**
	 * This sql statement selects all comments for a given photo
	 */
	private static final String SELECT_IDENTIFICATIONS =
		"SELECT id, specie_id s_id, user_id, photo_id, sex_id, age_id, comment, date "
			+ "FROM user_identifies_photo where photo_id = ? order by date desc";

	/**
	 * This sql statement selects all users that had identified a given photo
	 */
	private static final String SELECT_USERS_FOR_IDENTIFICATION =
		"SELECT distinct ui.user_id id, u.name, u.email, u.language "
			+ "FROM user u, user_identifies_photo ui where ui.user_id = u.id and u.emailOnNewIdPhoto = 1 and ui.photo_id = ?";

	/**
	 * Object to retrieve user data from database
	 */
	UserDAO userDao = new UserDAO();

	/**
	 * Object to retrieve specie data from database
	 */
	SpecieDAO specieDao = new SpecieDAO();

	/**
	 * Object to retrieve photo data from database
	 */
	PhotoDAO photoDao = new PhotoDAO();

	/**
	 * The photo object that will have its identifications retrieved
	 */
	private Photo photo = null;

	/**
	 * This method retrives the identifications for the given photo
	 * 
	 * @param photo The photo to have its identifications retrieved
	 * 
	 * @return A list of <code>PhotoIdentification</code> objects
	 */
	public List retrieveIdentifications(Photo photo) throws DatabaseDownException, SQLException {
		this.photo = photo;
		List list = super.retrieveObjects(photo.getId(), SELECT_IDENTIFICATIONS);
		return list;
	}

	/**
	 * This method verifies wheter an Id exists
	 * 
	 * @param id The id of the photo
	 * 
	 * @return a VO object
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public boolean isPhotoForIdentification(int id) throws DatabaseDownException, SQLException {
		return photoDao.isPhotoForIdentification(id);
	}

	/**
	 * This method retrieves all users that had identified the given photo.
	 *  
	 * @param photoId The id of the photo with identifications
	 * 
	 * @return A list of <code>User</code> objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrieveUsersForIdentification(int photoId)
		throws DatabaseDownException, SQLException {
		logger.info("PhotoIdentificationDAO.retrieveUsersForIdentification: entering method...");
		List list = new ArrayList();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			logger.info("Running sql " + SELECT_USERS_FOR_IDENTIFICATION);
			stmt = conn.prepareStatement(SELECT_USERS_FOR_IDENTIFICATION);
			stmt.setInt(1, photoId);
			rs = stmt.executeQuery();
			User user = null;

			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt(UserDAO.ID_COLUMN));
				user.setName(rs.getString(UserDAO.NAME_COLUMN));
				user.setEmail(rs.getString(UserDAO.EMAIL_COLUMN));
                user.setLanguage(rs.getString(UserDAO.LANGUAGE_COLUMN));
				list.add(user);
				logger.debug("Retrieved user " + user);
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + SELECT_USERS_FOR_IDENTIFICATION, e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			conn.close();
		}
		return list;
	}

	/**
	 * This method creates a <code>Photo</code> object with the data from database
	 * 
	 * @param rs The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>Photo</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		PhotoIdentification photoIdentification = new PhotoIdentification();

		Specie specie = getSpecieObject(rs.getInt(SPECIE_ID_COLUMN));
		User user = getUserObject(rs.getInt(USER_ID_COLUMN), photo);

		photoIdentification.setSpecie(specie);
		photoIdentification.setUser(user);
		photoIdentification.setPhoto(photo);
		photoIdentification.setComment(rs.getString(COMMENT_COLUMN));
		photoIdentification.setDate(getDate(rs, DATE_COLUMN));

		// retrieve age from model
		int id = rs.getInt(AGE_ID_COLUMN);
		photoIdentification.setAge(AgeModel.getAge(id));

		// retrieve sex from model
		id = rs.getInt(SEX_ID_COLUMN);
		photoIdentification.setSex(SexModel.getSex(id));

		return photoIdentification;
	}

	/**
	 * This method sets the id into the object
	 * 
	 * @param id The id value
	 * @param object The object to set the id
	 */
	protected void setObjectId(int id, Object object) throws SQLException {
		PhotoIdentification identification = (PhotoIdentification) object;
		identification.setId(id);
	}

	/**
	 * This method set the values into statement, before running the SQL in insert method
	 * 
	 * @param stmt   The statement to insert the values to sql
	 * @param object The object to retrieve the values from
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object) throws SQLException {
		PhotoIdentification identification = (PhotoIdentification) object;

		stmt.setInt(1, identification.getUser().getId());
		stmt.setInt(2, identification.getPhoto().getId());
		stmt.setInt(3, identification.getSpecie().getId());
		stmt.setInt(4, identification.getSex().getId());
		stmt.setInt(5, identification.getAge().getId());
		stmt.setString(6, identification.getComment());

		logger.debug("Comment from identification:" + identification.getComment());
		stmt.setTimestamp(7, getTimestamp(getSQLDate()));
	}

	/**
	 * @see net.indrix.dao.AbstractDAO#setStatementValuesForUpdate(java.sql.PreparedStatement, java.lang.Object)
	 */
	protected void setStatementValuesForUpdate(PreparedStatement stmt, Object object)
		throws SQLException {
	}

	/**
	 * @param i
	 * @return
	 */
	private Specie getSpecieObject(int id) throws SQLException {
		Specie specie = null;
		try {
			specie = specieDao.retrieve(id);
		} catch (DatabaseDownException e) {
			logger.error(
				"PhotoIdentificationDAO.createObject : Could not retrieve specie for identification...",
				e);
			throw new SQLException("Error retrieving user for photo " + photo);
		} catch (SQLException e) {
			logger.error(
				"PhotoIdentificationDAO.createObject : Could not retrieve specie for identification...",
				e);
			throw e;
		}
		return specie;
	}

	/**
	 * @param i
	 * @return
	 */
	private User getUserObject(int userId, Photo photo) throws SQLException {
		User user = null;
		try {
			user = userDao.retrieve(userId);
			//photo.setUser(user);
		} catch (DatabaseDownException e) {
			logger.error(
				"PhotoIdentificationDAO.createObject : Could not retrieve user for identification...",
				e);
			throw new SQLException("Error retrieving user for photo " + photo);
		} catch (SQLException e) {
			logger.error(
				"PhotoIdentificationDAO.createObject : Could not retrieve user for identification...",
				e);
			throw e;
		}
		return user;
	}

	/**
	 * This method returns the SQL statement to insert a new object into database
	 * 
	 * @return The insert SQL statement
	 */
	protected String getInsertSQL() {
		return INSERT_IDENTIFICATION_FOR_PHOTO;
	}

	/**
	 * This method returns the SQL statement to select all object from database
	 * 
	 * @return the SelectALL sql
	 */
	protected String getSelectAllSQL() {
		return SELECT_IDS_FOR_ALL;
	}

	/**
	 * This method returns the SQL statement to select all ids from database
	 * 
	 * @return the SelectALLIDs sql
	 */
	protected String getSelectAllIDsSQL() {
		return SELECT_IDS_FOR_ALL;
	}
}
