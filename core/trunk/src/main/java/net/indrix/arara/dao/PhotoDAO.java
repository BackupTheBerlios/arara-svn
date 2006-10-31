/*
 * Created on 07/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.constants.PhotoConstants;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.ImageFile;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoDAO extends AbstractDAO implements PhotoConstants {
    /**
     * SQL to insert a new photo into database
     */
    private static final String INSERT = "INSERT INTO photo "
            + "(user_id, date, place, city_id, camera, lens, film, "
            + "image, w, h, smallImage, sW, sH, specie_id, specie_family_id, post_date, comment, imageSize, smallImageSize, age_id, sex_id) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * SQL for updating a photo
     */
    public static final String UPDATE = "UPDATE photo set camera = ?, lens = ?, film = ?, date = ?, place = ?, city_id = ?, comment = ?, specie_id = ?, specie_family_id = ?, sex_id = ?, age_id = ? "
            + "WHERE id = ?";

    /**
     * SQL for deleting a photo object
     */
    private static final String DELETE_BY_ID = "DELETE FROM photo WHERE id = ?";

    /**
     * SQL to select a photo by a given ID
     */
    private static final String SELECT_BY_ID = "SELECT p.id, p.date, p.place, p.city_id, p.camera, p.lens, p.film, "
            + "p.image, p.w, p.h, p.imageSize, p.smallImage, p.sW, p.sH, p.smallImageSize, p.comment,"
            + "s.id s_id, s.name s_name, "
            + "f.id f_id, f.name f_name, f.subFamilyName f_sub_name, "
            + "p.user_id, p.age_id, p.sex_id, "
            + "c.id city_id, c.name city_name, c.state_id state_id "
            + "from photo p, specie s, family f, user u, city c "
            + "where p.id=? and p.specie_id = s.id and p.specie_family_id = f.id and p.user_id=u.id and p.city_id = c.id ";

    /**
     * SQL to select a photo by a given ID. Only the thumbnail image is
     * retrieved
     */
    private static final String SELECT_THUMBNAIL_BY_ID = "SELECT p.id, p.date, p.place, p.city_id, p.camera, p.lens, p.film, "
            + "p.imageSize, p.smallImage, p.sW, p.sH, p.smallImageSize, p.comment,"
            + "s.id s_id, s.name s_name, "
            + "f.id f_id, f.name f_name, f.subFamilyName f_sub_name, "
            + "p.user_id, p.age_id, p.sex_id, "
            + "c.id city_id, c.name city_name, c.state_id state_id "
            + "from photo p, specie s, family f, user u, city c "
            + "where p.id=? and p.specie_id = s.id and p.specie_family_id = f.id and p.user_id=u.id and p.city_id = c.id ";

    /**
     * SQL to select a photo image by a given photo ID
     */
    private static final String SELECT_IMAGE_BY_ID = "SELECT p.image, p.w, p.h, p.imageSize "
            + "from photo p " + "where p.id=?";

    /**
     * SQL to select id of all photos
     */
    private static final String SELECT_IDS_FOR_ALL = "SELECT p.id, f.id f_id, f.name f_name, s.id s_id, s.name s_name "
            + "from photo p, family f, specie s "
            + "where p.specie_id > -1 and p.specie_id = s.id and p.specie_family_id = f.id "
            + "order by f_name, s_name";

    /**
     * SQL to select id of all photos, order by post date (desc)
     */
    private static final String SELECT_IDS_FOR_ALL_BY_DATE = "SELECT p.id from photo p order by post_date desc";

    /**
     * SQL to select id of photos by a given family ID
     */
    private static final String SELECT_IDS_BY_FAMILY_ID = "SELECT p.id, s.id s_id, s.name s_name "
            + "from photo p, family f, specie s "
            + "where p.specie_family_id=? and p.specie_family_id = f.id  and p.specie_id = s.id "
            + "order by s_name";

    /**
     * SQL to select ids of photos by a given specie ID
     */
    private static final String SELECT_IDS_BY_SPECIE_ID = "SELECT p.id, s.id s_id, s.name s_name "
            + "from photo p, specie s "
            + "where p.specie_id=? and p.specie_id = s.id " + "order by s_name";

    /**
     * SQL to select photo ids by a given common name ID
     */
    private static final String SELECT_IDS_BY_COMMON_NAME_ID = "SELECT p.id "
            + "from photo p, specie_has_common_name shcn "
            + "where shcn.common_name_id = ? and p.specie_id=shcn.specie_id ";

    /**
     * SQL to select photo ids by a given user ID
     */
    private static final String SELECT_IDS_BY_USER = "SELECT p.id, f.id f_id, f.name f_name, s.id s_id, s.name s_name "
            + "from photo p, family f, specie s "
            + "where p.specie_id > -1 and p.user_id=? and p.specie_family_id = f.id and p.specie_id = s.id "
            + "order by f_name, s_name";

    /**
     * SQL to verify if a photo is for identification
     */
    private static final String VERIFY_IF_PHOTO_FOR_IDENTIFICATION = "SELECT p.id "
            + "from photo p " + "where p.id = ? and p.specie_id = -1";

    private static final String VERIFY_IF_SPECIE_HAS_PHOTO = "SELECT p.id "
        + "from photo p " + "where p.specie_id = ?";
    
    /**
     * Object to retrieve user data from database
     */
    UserDAO userDao = new UserDAO();

    /**
     * This method retrieves all photos from database.
     * 
     * @return A list of <code>Photo</code> objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    protected List retrieveObject(String sql) throws DatabaseDownException,
            SQLException {
        logger.debug("AbstractDAO.retrieveObjects: running SQL " + sql);
        List list = super.retrieveObject(sql);
        logger.debug("AbstractDAO.retrieveObjects: retrieved " + list.size());
        return list;
    }

    /**
     * This method retrieves a <code>Photo</code> object based on its id
     * 
     * @param id
     *            The id of the <code>Photo</code>
     * 
     * @return a <code>Photo</code> object
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public Photo retrieve(int id) throws DatabaseDownException, SQLException {
        Photo photo = (Photo) retrieveObject(id, SELECT_BY_ID);
        return photo;
    }

    /**
     * This method retrieves a <code>Photo</code> object based on its id
     * 
     * @param id
     *            The id of the <code>Photo</code>
     * 
     * @return a <code>Photo</code> object
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public Photo retrieveThumbnail(int id) throws DatabaseDownException,
            SQLException {
        Photo photo = (Photo) super.retrieveObject(id, SELECT_THUMBNAIL_BY_ID);
        return photo;
    }

    /**
     * This method retrieves a VO for an object based on its id
     * 
     * @param id
     *            The id of the object to be retrieved
     * @param sql
     *            The SQL to be executed
     * 
     * @return a VO object
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    protected Object retrieveObject(int id, String sql)
            throws DatabaseDownException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object object = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                object = createFullObject(rs);
            }
        } catch (SQLException e) {
            logger.error("AbstractDAO.retrieve : could not retrieve data ");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }
        return object;
    }

    /**
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, of photos more recently added to database
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveIDsForRecentPhotos() throws DatabaseDownException,
            SQLException {
        List list = retrieveIDs(SELECT_IDS_FOR_ALL_BY_DATE);
        return list;
    }

    /**
     * This method retrieves the photo image from database, given the photo id
     * 
     * @param i
     *            THe photo id
     * 
     * @return the photo image from database, given the photo id
     */
    public ImageFile retrievePhotoImage(int id) throws DatabaseDownException,
            SQLException {
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ImageFile image = null;

        try {
            stmt = conn.prepareStatement(SELECT_IMAGE_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                image = new ImageFile();
                Blob blob = rs.getBlob(IMAGE_COLUMN);
                image.setImage(blob.getBinaryStream());
                image.setWidth(rs.getInt(IMAGE_W));
                image.setHeight(rs.getInt(IMAGE_H));
                image.setImageSize(rs.getInt(IMAGE_SIZE));
            }
        } catch (SQLException e) {
            logger
                    .error("PhotoDAO.retrievePhotoImage : could not retrieve data ");
            logger.error("Error in SQL : " + SELECT_IMAGE_BY_ID, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }
        return image;

    }

    /**
     * This method verifies wheter an Id exists
     * 
     * @param sql
     *            The SQL to be executed
     * 
     * @return a VO object
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public boolean isPhotoForIdentification(int photoId)
            throws DatabaseDownException, SQLException {

        return isIdInTable(photoId, VERIFY_IF_PHOTO_FOR_IDENTIFICATION);
    }

    /**
     * This method verifies wheter an Id exists
     * 
     * @param sql
     *            The SQL to be executed
     * 
     * @return a VO object
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public boolean hasSpecieAPhoto(int specieId)
            throws DatabaseDownException, SQLException {

        return isIdInTable(specieId, VERIFY_IF_SPECIE_HAS_PHOTO);
    }

    /**
     * This method creates a <code>Photo</code> object with the data from
     * database. The full image is not retrieved at this time.
     * 
     * @param rs
     *            The <code>ResultSet<code> object to retrieve the data
     * 
     * @return A new <code>Photo</code> object 
     * 
     * @throws SQLException If an error occur while retrieving data from result set
     */
    protected Object createObject(ResultSet rs) throws SQLException {
        Photo photo = new Photo();
        ImageFile smallImage = photo.getSmallImage();

        photo.setId(rs.getInt(ID_COLUMN));
        photo.setDate(getDate(rs, DATE_COLUMN));
        photo.setLocation(rs.getString(LOCATION_COLUMN));
        photo.setCamera(rs.getString(CAMERA_COLUMN));
        photo.setLens(rs.getString(LENS_COLUMN));
        photo.setFilm(rs.getString(FILM_COLUMN));
        Blob blob1 = rs.getBlob(SMALL_IMAGE_COLUMN);
        smallImage.setImage(blob1.getBinaryStream());
        smallImage.setWidth(rs.getInt(SMALL_IMAGE_W));
        smallImage.setHeight(rs.getInt(SMALL_IMAGE_H));
        smallImage.setImageSize(rs.getInt(SMALL_IMAGE_SIZE));

        City city = getCityObject(rs.getInt(CITY_ID_COLUMN), rs
                .getString(CITY_NAME_COLUMN), rs.getInt(STATE_ID_COLUMN), photo);
        photo.setCity(city);

        // retrieve age from model
        int id = rs.getInt(AGE_ID_COLUMN);
        photo.setAge(AgeModel.getAge(id));

        // retrieve sex from model
        id = rs.getInt(SEX_ID_COLUMN);
        photo.setSex(SexModel.getSex(id));

        User user = getUserObject(rs.getInt(USER_ID_COLUMN), photo);
        photo.setUser(user);

        Specie specie = getSpecieObject(rs, photo);
        photo.setSpecie(specie);

        photo.setComment(rs.getString(COMMENT_COLUMN));
        return photo;
    }

    /**
     * This method creates a <code>Photo</code> object with the data from
     * database
     * 
     * @param rs
     *            The <code>ResultSet<code> object to retrieve the data
     * 
     * @return A new <code>Photo</code> object 
     * 
     * @throws SQLException If an error occur while retrieving data from result set
     */
    protected Object createFullObject(ResultSet rs) throws SQLException {
        Photo photo = new Photo();
        ImageFile image = photo.getRealImage();
        ImageFile smallImage = photo.getSmallImage();

        photo.setId(rs.getInt(ID_COLUMN));
        photo.setDate(getDate(rs, DATE_COLUMN));
        photo.setLocation(rs.getString(LOCATION_COLUMN));
        photo.setCamera(rs.getString(CAMERA_COLUMN));
        photo.setLens(rs.getString(LENS_COLUMN));
        photo.setFilm(rs.getString(FILM_COLUMN));
        Blob blob = rs.getBlob(IMAGE_COLUMN);
        image.setImage(blob.getBinaryStream());
        image.setWidth(rs.getInt(IMAGE_W));
        image.setHeight(rs.getInt(IMAGE_H));
        image.setImageSize(rs.getInt(IMAGE_SIZE));
        Blob blob1 = rs.getBlob(SMALL_IMAGE_COLUMN);
        smallImage.setImage(blob1.getBinaryStream());
        smallImage.setWidth(rs.getInt(SMALL_IMAGE_W));
        smallImage.setHeight(rs.getInt(SMALL_IMAGE_H));
        smallImage.setImageSize(rs.getInt(SMALL_IMAGE_SIZE));

        City city = getCityObject(rs.getInt(CITY_ID_COLUMN), rs
                .getString(CITY_NAME_COLUMN), rs.getInt(STATE_ID_COLUMN), photo);
        photo.setCity(city);

        // retrieve age from model
        int id = rs.getInt(AGE_ID_COLUMN);
        photo.setAge(AgeModel.getAge(id));

        // retrieve sex from model
        id = rs.getInt(SEX_ID_COLUMN);
        photo.setSex(SexModel.getSex(id));

        User user = getUserObject(rs.getInt(USER_ID_COLUMN), photo);
        photo.setUser(user);

        Specie specie = getSpecieObject(rs, photo);
        photo.setSpecie(specie);

        photo.setComment(rs.getString(COMMENT_COLUMN));
        return photo;
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
        Photo photo = (Photo) object;
        photo.setId(id);
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
        Photo photo = (Photo) object;
        ImageFile image = photo.getRealImage();
        ImageFile smallImage = photo.getSmallImage();
        stmt.setInt(1, photo.getUser().getId());
        logger.debug("User: " + photo.getUser().getId() + " | "
                + photo.getUser().getName());
        stmt.setDate(2, getSQLDate(photo.getDate()));
        logger.debug("Date: " + photo.getDate());
        stmt.setString(3, photo.getLocation());
        logger.debug("Location: " + photo.getLocation());
        stmt.setInt(4, photo.getCity().getId());
        logger.debug("City:" + photo.getCity());
        stmt.setString(5, photo.getCamera());
        logger.debug("Camera: " + photo.getCamera());
        stmt.setString(6, photo.getLens());
        logger.debug("Lens: " + photo.getLens());
        stmt.setString(7, photo.getFilm());
        logger.debug("Film: " + photo.getFilm());
        stmt.setBinaryStream(8, image.getImage(), image.getImageSize());
        stmt.setInt(9, image.getWidth());
        stmt.setInt(10, image.getHeight());
        logger.debug("Size: " + image.getWidth() + "," + image.getHeight());
        stmt.setBinaryStream(11, smallImage.getImage(), smallImage
                .getImageSize());
        stmt.setInt(12, smallImage.getWidth());
        stmt.setInt(13, smallImage.getHeight());
        logger.debug("Small size: " + smallImage.getWidth() + ","
                + smallImage.getHeight());
        stmt.setInt(14, photo.getSpecie().getId());
        logger.debug("Specie: " + photo.getSpecie().getId() + " | "
                + photo.getSpecie().getName());
        stmt.setInt(15, photo.getSpecie().getFamily().getId());
        logger.debug("Family: " + photo.getSpecie().getFamily().getId() + " | "
                + photo.getSpecie().getFamily().getName());
        stmt.setTimestamp(16, getTimestamp(getSQLDate()));
        logger.debug("Post date: " + photo.getDate());
        stmt.setString(17, photo.getComment());
        logger.debug("Comment from photo: " + photo.getComment());
        stmt.setInt(18, photo.getRealImage().getImageSize());
        stmt.setInt(19, photo.getSmallImage().getImageSize());
        stmt.setInt(20, photo.getAge().getId());
        stmt.setInt(21, photo.getSex().getId());
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
        logger.debug("PhotoDAO.setStatementValuesForUpdate: Entering method");
        Photo photo = (Photo) object;
        logger.debug("Photo = " + photo);
        stmt.setString(1, photo.getCamera());
        stmt.setString(2, photo.getLens());
        stmt.setString(3, photo.getFilm());
        stmt.setDate(4, getSQLDate(photo.getDate()));
        stmt.setString(5, photo.getLocation());
        stmt.setInt(6, photo.getCity().getId());
        stmt.setString(7, photo.getComment());
        stmt.setInt(8, photo.getSpecie().getId());
        stmt.setInt(9, photo.getSpecie().getFamily().getId());
        stmt.setInt(10, photo.getSex().getId());
        stmt.setInt(11, photo.getAge().getId());
        stmt.setInt(12, photo.getId());
        logger.debug("PhotoDAO.setStatementValuesForUpdate: Exiting method");
    }

    /**
     * @param stmt
     * @param sql
     */
    protected void setStatementValuesForExistsSQL(PreparedStatement stmt,
            String sql) {
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
     * This method returns the SQL statement to delete an object from database
     * 
     * @return The delete SQL statement
     */
    protected String getDeleteSQL() {
        return DELETE_BY_ID;
    }

    /**
     * This method returns the SQL statement to update an object in the database
     * 
     * @return The update SQL statement
     */
    protected String getUpdateSQL() {
        return UPDATE;
    }

    /**
     * This method returns the SQL statement to select all ids from database
     * 
     * @return the SelectALLIDs sql
     */
    protected String getSelectAllIDsSQL() {
        return SELECT_IDS_FOR_ALL;
    }

    /**
     * This method returns the SQL statement to select ids for family from
     * database
     * 
     * @return the SELECT_IDS_BY_FAMILY_ID sql
     */
    protected String getSelectIDsForFamilySQL() {
        return SELECT_IDS_BY_FAMILY_ID;
    }

    /**
     * This method returns the SQL statement to select ids for specie from
     * database
     * 
     * @return the SELECT_IDS_BY_SPECIE_ID sql
     */
    protected String getSelectIDsForSpecieSQL() {
        return SELECT_IDS_BY_SPECIE_ID;
    }

    /**
     * This method returns the SQL statement to select ids for common name from
     * database
     * 
     * @return the SELECT_IDS_BY_COMMON_NAME_ID sql
     */
    protected String getSelectIDsForCommonNameSQL() {
        return SELECT_IDS_BY_COMMON_NAME_ID;
    }

    /**
     * This method returns the SQL statement to select ids for user from
     * database
     * 
     * @return the SELECT_IDS_BY_USER sql
     */
    protected String getSelectIDsForUserSQL() {
        return SELECT_IDS_BY_USER;
    }
 
    /**
     * @param rs
     * @return
     */
    private Specie getSpecieObject(ResultSet rs, Photo photo)
            throws SQLException {
        Specie s = new Specie();
        s.setId(rs.getInt(SPECIE_ID_COLUMN));
        s.setName(rs.getString(SPECIE_NAME_COLUMN));
        Family f = new Family();
        f.setId(rs.getInt(FAMILY_ID_COLUMN));
        f.setName(rs.getString(FAMILY_NAME_COLUMN));
        f.setSubFamilyName(rs.getString(FAMILY_SUB_NAME_COLUMN));
        s.setFamily(f);

        // retrieve common names for specie
        CommonNameDAO dao = new CommonNameDAO();
        try {
            dao.retrieveForSpecie(s);
        } catch (DatabaseDownException e) {
            logger.error(
                    "PhotoDAO.getSpecieObject : Could not retrieve city for photo "
                            + photo, e);
            throw new SQLException("Error retrieving user for city " + photo);
        } catch (SQLException e) {
            logger.error(
                    "PhotoDAO.getSpecieObject : Could not retrieve user for city "
                            + photo, e);
            throw e;
        }
        ;

        // retrieve sound information for specie
        SoundDAO soundDAO = new SoundDAO();
        List list;
        try {
            list = soundDAO.retrieveForSpecie(s.getId());
        } catch (DatabaseDownException e) {
            logger.error(
                    "PhotoDAO.getSpecieObject : Could not retrieve city for photo "
                            + photo, e);
            throw new SQLException("Error retrieving user for city " + photo);
        } catch (SQLException e) {
            logger.error(
                    "PhotoDAO.getSpecieObject : Could not retrieve user for city "
                            + photo, e);
            throw e;
        }
        ;
        s.setSoundAvailable(!list.isEmpty());
        if (s.isSoundAvailable()) {
            s.setSounds(list);
        }
        return s;
    }

    /**
     * @param i
     * @return
     */
    private City getCityObject(int cityId, String cityName, int stateId,
            Photo photo) throws SQLException {
        City city = null;
        city = new City();
        city.setId(cityId);
        city.setName(cityName);
        city.setState(StatesModel.getState(stateId));
        return city;
    }

    /**
     * @param i
     * @return
     */
    private User getUserObject(int userId, Photo photo) throws SQLException {
        User user = null;
        try {
            user = userDao.retrieve(userId);
            photo.setUser(user);
        } catch (DatabaseDownException e) {
            logger.error(
                    "PhotoDAO.createObject : Could not retrieve user for photo "
                            + photo, e);
            throw new SQLException("Error retrieving user for photo " + photo);
        } catch (SQLException e) {
            logger.error(
                    "PhotoDAO.createObject : Could not retrieve user for photo "
                            + photo, e);
            throw e;
        }
        return user;
    }
}
