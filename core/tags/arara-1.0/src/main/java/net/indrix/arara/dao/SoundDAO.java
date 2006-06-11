/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.constants.SoundConstants;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.Sound;
import net.indrix.arara.vo.SoundFile;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundDAO extends AbstractDAO implements SoundConstants{

	private static final String INSERT =
		"INSERT INTO sound (specie_id, user_id, age_id, sex_id, fileSize, location, city_id, post_date, comment) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String DELETE_BY_ID = "DELETE FROM sound WHERE id = ?";

	private static final String SELECT_ALL =
		"SELECT snd.id, snd.specie_id, snd.user_id, snd.age_id, snd.sex_id, snd.fileSize, "
			+ "snd.location, snd.city_id, snd.post_date, snd.comment, s.family_id, f.id, f.name "
			+ "from sound snd, specie s, family f "
			+ "where snd.specie_id = s.id and s.family_id = f.id "
			+ "order by f.name";

	private static final String SELECT_BY_ID =
		"SELECT id, specie_id, user_id, age_id, sex_id, fileSize, location, city_id, post_date, comment "
			+ "from sound "
			+ "where id = ?";

	private static final String SELECT_BY_SPECIE_ID =
		"SELECT id, user_id, specie_id, age_id, sex_id, fileSize, location, city_id, post_date, comment "
			+ "from sound "
			+ "where specie_id = ?";

    /**
     * SQL to select id of all photos  
     */
    private static final String SELECT_IDS_FOR_ALL =
        "SELECT s.id, f.id f_id, f.name f_name "
            + "from sound s, specie sp, family f "
            + "where s.specie_id > -1 and s.specie_id = sp.id and sp.family_id = f.id "
            + "order by f_name";

	/**
	 * This method returns the SQL statement to insert a new object into database
	 * 
	 * @return The insert SQL statement
	 */
	protected String getInsertSQL() {
		return INSERT;
	}

	/**
	 * This method returns the SQL statement to delete an object from database
	 * 
	 * @return The delete SQL statement
	 */
	protected String getDeleteSQL() {
		return DELETE_BY_ID;
	}

	/**
	 * This method retrieves all photos from database. It uses the following SQL: <br>
	 *      SELECT * FROM PHOTO
	 *  
	 * @return A list of <code>Photo</code> objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrieve() throws DatabaseDownException, SQLException {
		List list = super.retrieveObject(SELECT_ALL);
		return list;
	}

	/**
	 * 
	 * @return
	 * @throws DatabaseDownException
	 * @throws SQLException
	 */
	public Sound retrieve(int id) throws DatabaseDownException, SQLException {
        logger.debug("SoundDAO.retrieve: running super.retrieveObject(id, " + SELECT_BY_ID + ")");
		Sound object = (Sound) super.retrieveObject(id, SELECT_BY_ID);
		return object;
	}

	/**
	 * This method retrieves a <code>List</code> object with <code>Photo</code> objects, 
	 * based on the id of the specie
	 * 
	 * @param id The id of the <code>List</code>
	 * 
	 * @return a <code>List</code> object with <code>Photo</code> objects, 
	 * based on the id of the specie 
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public Sound retrieveSoundForSpecie(int id) throws DatabaseDownException, SQLException {
		Sound object = (Sound) super.retrieveObject(id, SELECT_BY_SPECIE_ID);
		return object;
	}

	/**
	 * This method retrieves a <code>List</code> object with <code>Photo</code> objects, 
	 * based on the id of the specie
	 * 
	 * @param id The id of the <code>List</code>
	 * 
	 * @return a <code>List</code> object with <code>Photo</code> objects, 
	 * based on the id of the specie 
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrieveForSpecie(int id) throws DatabaseDownException, SQLException {
		List list = super.retrieveObjects(id, SELECT_BY_SPECIE_ID);
		return list;
	}

	/**
	 * @see net.indrix.dao.AbstractDAO#createObject(java.sql.ResultSet)
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		Sound sound = new Sound();
		SoundFile soundFile = sound.getSound();

		sound.setId(rs.getInt(ID_COLUMN));
		sound.setPostDate(getDate(rs, POST_DATE_COLUMN));
        sound.setComment(rs.getString(COMMENT_COLUMN));
		soundFile.setFileSize(rs.getInt(FILESIZE_COLUMN));

		// retrieve age from model
		int id = rs.getInt(AGE_ID_COLUMN);
		sound.setAge(AgeModel.getAge(id));

		// retrieve sex from model
		id = rs.getInt(SEX_ID_COLUMN);
		sound.setSex(SexModel.getSex(id));

		sound.setLocation(rs.getString(LOCATION_COLUMN));
		int cityId = rs.getInt(CITY_ID_COLUMN);
		CityDAO cityDAO = new CityDAO();
		City city = null;
		try {
			city = cityDAO.retrieve(cityId);
		} catch (DatabaseDownException e) {
			throw new SQLException();
		} catch (SQLException e) {
			throw e;
		}
		sound.setCity(city);
        
		SpecieDAO dao = new SpecieDAO();
		Specie specie;
		try {
			specie = dao.retrieve(rs.getInt(SPECIE_ID_COLUMN));
		} catch (DatabaseDownException e) {
			throw new SQLException();
		} catch (SQLException e) {
			throw e;
		}
		sound.setSpecie(specie);

		try {
			UserDAO userDao = new UserDAO();
			User user = userDao.retrieve(rs.getInt(USER_ID_COLUMN));
			sound.setUser(user);
		} catch (DatabaseDownException e) {
			logger.error("SoundDAO.createObject : Could not retrieve user for sound " + sound, e);
			throw new SQLException("Error retrieving user for sound " + sound);
		} catch (SQLException e) {
			logger.error("SoundDAO.createObject : Could not retrieve user for sound " + sound, e);
			throw e;
		}

		return sound;
	}

	/**
	 * @see net.indrix.dao.AbstractDAO#setStatementValues(java.sql.PreparedStatement, java.lang.Object)
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object) throws SQLException {
		Sound sound = (Sound) object;

		stmt.setInt(1, sound.getSpecie().getId());
		stmt.setInt(2, sound.getUser().getId());
		stmt.setInt(3, sound.getAge().getId());
		stmt.setInt(4, sound.getSex().getId());
		stmt.setInt(5, sound.getSound().getFileSize());
		stmt.setString(6, sound.getLocation());
		stmt.setInt(7, sound.getCity().getId());
		stmt.setDate(8, getSQLDate());
		stmt.setString(9, sound.getComment());
	}

	/**
	 * @see net.indrix.dao.AbstractDAO#setStatementValuesForUpdate(java.sql.PreparedStatement, java.lang.Object)
	 */
	protected void setStatementValuesForUpdate(PreparedStatement stmt, Object object)
		throws SQLException {
	}

	/**
	 * @see net.indrix.dao.AbstractDAO#setObjectId(int, java.lang.Object)
	 */
	protected void setObjectId(int id, Object object) throws SQLException {
		Sound sound = (Sound) object;
		sound.setId(id);
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