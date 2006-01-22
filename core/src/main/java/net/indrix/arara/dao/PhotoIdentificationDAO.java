/*
 * Created on 14/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.indrix.arara.vo.ImageFile;
import net.indrix.arara.vo.Photo;
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
		"INSERT INTO photo_identify "
			+ "(user_id, date, place, camera, lens, film, "
			+ "image, w, h, smallImage, sW, sH, post_date, "
			+ "comment, imageSize, smallImageSize) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL for retrieving all objects from database
     */
    private static final String SELECT_ALL =
        "SELECT p.id, p.date, p.place, p.camera, p.lens, p.film, "
            + "p.image, p.w, p.h, p.imageSize, p.smallImage, p.sW, p.sH, p.smallImageSize, p.comment,"
            + "p.user_id "
            + "from photo_identify p, user u "
            + "where p.user_id = u.id";

	private static final String SELECT_BY_ID =
		"SELECT p.id, p.date, p.place, p.camera, p.lens, p.film, "
			+ "p.image, p.w, p.h, p.imageSize, p.smallImage, p.sW, p.sH, p.smallImageSize, p.comment,"
			+ "p.user_id "
			+ "from photo_identify p, user u "
			+ "where p.id=? and p.user_id=u.id";

	/**
	 * This method retrieves a <code>Photo</code> object based on its id
	 * 
	 * @param id The id of the <code>Photo</code>
	 * 
	 * @return a <code>Photo</code> object 
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public Photo retrieve(int id) throws DatabaseDownException, SQLException {
		Photo photo = (Photo) retrieveObject(id, SELECT_BY_ID);
		return photo;
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
		Photo photo = new Photo();
		ImageFile image = photo.getRealImage();
		ImageFile smallImage = photo.getSmallImage();

		photo.setId(rs.getInt(ID_COLUMN));
		photo.setDate(getDate(rs, DATE_COLUMN));
		photo.setLocation(rs.getString(LOCATION_COLUMN));
		photo.setCamera(rs.getString(CAMERA_COLUMN));
		photo.setLens(rs.getString(LENS_COLUMN));
		photo.setFilm(rs.getString(FILM_COLUMN));
		image.setWidth(rs.getInt(IMAGE_W));
		image.setHeight(rs.getInt(IMAGE_H));
		image.setImageSize(rs.getInt(IMAGE_SIZE));
		Blob blob1 = rs.getBlob(SMALL_IMAGE_COLUMN);
		smallImage.setImage(blob1.getBinaryStream());
		smallImage.setWidth(rs.getInt(SMALL_IMAGE_W));
		smallImage.setHeight(rs.getInt(SMALL_IMAGE_H));
		smallImage.setImageSize(rs.getInt(SMALL_IMAGE_SIZE));

		try {
			UserDAO userDao = new UserDAO();
			User user = userDao.retrieve(rs.getInt(USER_ID_COLUMN));
			photo.setUser(user);
		} catch (DatabaseDownException e) {
			logger.error("PhotoDAO.createObject : Could not retrieve user for photo " + photo, e);
			throw new SQLException("Error retrieving user for photo " + photo);
		} catch (SQLException e) {
			logger.error("PhotoDAO.createObject : Could not retrieve user for photo " + photo, e);
			throw e;
		}
		photo.setSpecie(new Specie());
		photo.setComment(rs.getString(COMMENT_COLUMN));
		return photo;
	}

    /**
     * This method sets the id into the object
     * 
     * @param id The id value
     * @param object The object to set the id
     */
	protected void setObjectId(int id, Object object) throws SQLException {
        Photo photo = (Photo) object;
        photo.setId(id);
	}

	/**
	 * This method set the values into statement, before running the SQL in insert method
	 * 
	 * @param stmt   The statement to insert the values to sql
	 * @param object The object to retrieve the values from
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object) throws SQLException {
		Photo photo = (Photo) object;
		ImageFile image = photo.getRealImage();
		ImageFile smallImage = photo.getSmallImage();
		stmt.setInt(1, photo.getUser().getId());
		logger.debug("User: " + photo.getUser().getId() + " | " + photo.getUser().getName());
		stmt.setDate(2, getSQLDate(photo.getDate()));
		logger.debug("Date: " + photo.getDate());
		stmt.setString(3, photo.getLocation());
		logger.debug("Location: " + photo.getLocation());
		stmt.setString(4, photo.getCamera());
		logger.debug("Camera: " + photo.getCamera());
		stmt.setString(5, photo.getLens());
		logger.debug("Lens: " + photo.getLens());
		stmt.setString(6, photo.getFilm());
		logger.debug("Film: " + photo.getFilm());
		stmt.setBinaryStream(7, image.getImage(), image.getImageSize());
		stmt.setInt(8, image.getWidth());
		stmt.setInt(9, image.getHeight());
		logger.debug("Size: " + image.getWidth() + "," + image.getHeight());
		stmt.setBinaryStream(10, smallImage.getImage(), smallImage.getImageSize());
		stmt.setInt(11, smallImage.getWidth());
		stmt.setInt(12, smallImage.getHeight());
		logger.debug("Small size: " + smallImage.getWidth() + "," + smallImage.getHeight());
		stmt.setTimestamp(13, getTimestamp(getSQLDate()));
		logger.debug("Post date: " + photo.getDate());
		stmt.setString(14, photo.getComment());
		logger.debug("Comment : " + photo.getComment());
		stmt.setInt(15, photo.getRealImage().getImageSize());
		stmt.setInt(16, photo.getSmallImage().getImageSize());
	}

	/* (non-Javadoc)
	 * @see net.indrix.dao.AbstractDAO#setStatementValuesForUpdate(java.sql.PreparedStatement, java.lang.Object)
	 */
	protected void setStatementValuesForUpdate(PreparedStatement stmt, Object object)
		throws SQLException {
		// TODO Auto-generated method stub

	}

    /**
     * This method returns the SQL statement to insert a new object into database
     * 
     * @return The insert SQL statement
     */
    protected String getInsertSQL() {
        return INSERT_FOR_IDENTIFICATION;
    }

    /**
     * This method returns the SQL statement to select all object from database
     * 
     * @return the SelectALL sql
     */
    protected String getSelectAllSQL() {
        return SELECT_ALL;
    }
}
