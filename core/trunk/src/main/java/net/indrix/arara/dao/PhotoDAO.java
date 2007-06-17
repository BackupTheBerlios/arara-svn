/*
 * Created on 07/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

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
public class PhotoDAO extends MediaDAO implements PhotoConstants {
    /**
     * SQL to insert a new photo into database
     */
    private static final String INSERT = "INSERT INTO photo "
            + "(user_id, date, place, city_id, camera, lens, film, "
            + "w, h, sW, sH, specie_id, specie_family_id, post_date, comment, imageSize, smallImageSize, age_id, sex_id) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
    private static final String SELECT_BY_ID = "" +
            "SELECT p.id, p.date, p.place, p.city_id, p.camera, p.lens, p.film, "
            + "p.w, p.h, p.imageSize, p.sW, p.sH, p.smallImageSize, p.comment, p.post_date,"
            + "s.id s_id, s.name s_name, s.english_name english_name, "
            + "f.id f_id, f.name f_name, f.subFamilyName f_sub_name, "
            + "p.user_id, p.age_id, p.sex_id, "
            + "c.id city_id, c.name city_name, c.state_id state_id "
            + "from photo p, specie s, family f, user u, city c "
            + "where p.id=? and p.specie_id = s.id and p.specie_family_id = f.id and p.user_id=u.id and p.city_id = c.id ";

    /**
     * SQL to select a photo by a given ID. Only the thumbnail image is
     * retrieved
     */
    private static final String SELECT_THUMBNAIL_BY_ID = "" +
            "SELECT p.id, p.date, p.place, p.city_id, p.camera, p.lens, p.film, "
            + "p.w, p.h, p.imageSize, p.sW, p.sH, p.smallImageSize, p.comment, p.post_date,"
            + "s.id s_id, s.name s_name, s.english_name english_name, "
            + "f.id f_id, f.name f_name, f.subFamilyName f_sub_name, "
            + "p.user_id, p.age_id, p.sex_id, "
            + "c.id city_id, c.name city_name, c.state_id state_id "
            + "from photo p, specie s, family f, user u, city c "
            + "where p.id=? and p.specie_id = s.id and p.specie_family_id = f.id and p.user_id=u.id and p.city_id = c.id ";

    /**
     * SQL to select id of all photos
     */
    private static final String SELECT_IDS_FOR_ALL = "" +
            "SELECT p.id, f.id f_id, f.name f_name, s.id s_id, s.name s_name, s.english_name english_name "
            + "from photo p, family f, specie s "
            + "where p.specie_id > -1 and p.specie_id = s.id and p.specie_family_id = f.id "
            + "order by f_name, s_name";

    /**
     * SQL to select id of all photos for slide show (imgWidth="150" imgHeight="115")
     */
    private static final String SELECT_IDS_FOR_SLIDE_SHOW = "" +
            "SELECT p.id " +
            "from photo p " +
            "where (p.sW/p.sH) BETWEEN 1.1 AND 1.5";
    
    /**
     * SQL to select id of all photos, order by post date (desc)
     */
    private static final String SELECT_IDS_FOR_ALL_BY_DATE = "" +
            "SELECT p.id " +
            "from photo p " +
            "order by post_date desc";

    /**
     * SQL to select id of photos by a given family ID
     */
    private static final String SELECT_IDS_BY_FAMILY_ID = "" +
            "SELECT p.id, s.id s_id, s.name s_name, s.english_name english_name  "
            + "from photo p, family f, specie s "
            + "where p.specie_family_id=? and p.specie_family_id = f.id  and p.specie_id = s.id "
            + "order by s_name";

    /**
     * SQL to select id of photos by a given family ID
     */
    private static final String SELECT_IDS_BY_FAMILY_NAME = "" +
            "SELECT p.id, s.id s_id, s.name s_name, s.english_name english_name "
            + "from photo p, family f, specie s "
            + "where p.specie_family_id = f.id  and p.specie_id = s.id and f.name like ? "
            + "order by s_name";

    /**
     * SQL to select ids of photos by a given specie ID
     */
    private static final String SELECT_IDS_BY_SPECIE_ID = "" +
            "SELECT p.id, s.id s_id, s.name s_name, s.english_name english_name  "
            + "from photo p, specie s "
            + "where p.specie_id=? and p.specie_id = s.id " + "order by s_name";

    /**
     * SQL to select ids of photos by a given specie ID
     */
    private static final String SELECT_IDS_BY_SPECIE_NAME = "" +
            "SELECT p.id, s.id s_id, s.name s_name, s.english_name english_name  "
            + "from photo p, specie s "
            + "where p.specie_id = s.id and s.name like ? order by s_name";

    /**
     * SQL to select photo ids by a given common name ID
     */
    private static final String SELECT_IDS_BY_COMMON_NAME_ID = "SELECT p.id "
            + "from photo p, specie_has_common_name shcn "
            + "where shcn.common_name_id = ? and p.specie_id=shcn.specie_id ";

    /**
     * SQL to select photo ids by a given common name name
     */
    private static final String SELECT_IDS_BY_COMMON_NAME_BY_NAME = "SELECT p.id "
            + "from photo p, specie_has_common_name shcn, common_name cn "
            + "where p.specie_id=shcn.specie_id and shcn.common_name_id = cn.id and cn.name like ?";


    /**
     * SQL to select photo ids by a given user ID
     */
    private static final String SELECT_IDS_BY_USER = "" +
            "SELECT p.id, f.id f_id, f.name f_name, s.id s_id, s.name s_name, s.english_name english_name "
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
    protected List retrieveObjects(String sql) throws DatabaseDownException,
            SQLException {
        logger.debug("AbstractDAO.retrieveObjects: running SQL " + sql);
        List list = super.retrieveObjects(sql);
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
        Photo photo = (Photo) retrieveFullObject(id, SELECT_BY_ID);
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
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, of photos more recently added to database
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveIDsForSlideShow() throws DatabaseDownException,
            SQLException {
        List list = retrieveIDs(SELECT_IDS_FOR_SLIDE_SHOW);
        return list;
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
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, based on the name of the family
     * 
     * @param name
     *            The name of the family
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the id of the common name
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForFamilyName(String name) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenStringField(name, getSelectIDsForFamilyNameSQL());
        return list;
    }
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, based on the name of the specie
     * 
     * @param name
     *            The name of the specie
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the name of the specie
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForSpecieName(String name) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenStringField(name, getSelectIDsForSpecieNameSQL());
        return list;
    }    
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, based on the name of the specie
     * 
     * @param name
     *            The name of the specie
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the name of the specie
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForCommonNameByName(String name) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenStringField(name, getSelectIDsForCommonNameByNameSQL());
        return list;
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
        ImageFile image = photo.getRealImage();
        ImageFile smallImage = photo.getSmallImage();

        photo.setId(rs.getInt(ID_COLUMN));
        photo.setDate(getDate(rs, DATE_COLUMN));
        photo.setPostDate(getDate(rs, POST_DATE_COLUMN));
        photo.setLocation(rs.getString(LOCATION_COLUMN));
        photo.setCamera(rs.getString(CAMERA_COLUMN));
        photo.setLens(rs.getString(LENS_COLUMN));
        photo.setFilm(rs.getString(FILM_COLUMN));
        image.setWidth(rs.getInt(IMAGE_W));
        image.setHeight(rs.getInt(IMAGE_H));
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

        User user = getUserObject(rs.getInt(USER_ID_COLUMN));
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
        photo.setPostDate(getDate(rs, POST_DATE_COLUMN));
        photo.setLocation(rs.getString(LOCATION_COLUMN));
        photo.setCamera(rs.getString(CAMERA_COLUMN));
        photo.setLens(rs.getString(LENS_COLUMN));
        photo.setFilm(rs.getString(FILM_COLUMN));
        image.setWidth(rs.getInt(IMAGE_W));
        image.setHeight(rs.getInt(IMAGE_H));
        image.setImageSize(rs.getInt(IMAGE_SIZE));
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

        User user = getUserObject(rs.getInt(USER_ID_COLUMN));
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
        stmt.setInt(8, image.getWidth());
        stmt.setInt(9, image.getHeight());
        logger.debug("Size: " + image.getWidth() + "," + image.getHeight());
        stmt.setInt(10, smallImage.getWidth());
        stmt.setInt(11, smallImage.getHeight());
        logger.debug("Small size: " + smallImage.getWidth() + ","
                + smallImage.getHeight());
        stmt.setInt(12, photo.getSpecie().getId());
        logger.debug("Specie: " + photo.getSpecie().getId() + " | "
                + photo.getSpecie().getName());
        stmt.setInt(13, photo.getSpecie().getFamily().getId());
        logger.debug("Family: " + photo.getSpecie().getFamily().getId() + " | "
                + photo.getSpecie().getFamily().getName());
        stmt.setTimestamp(14, getTimestamp(getSQLDate()));
        logger.debug("Post date: " + photo.getDate());
        stmt.setString(15, photo.getComment());
        logger.debug("Comment from photo: " + photo.getComment());
        stmt.setInt(16, photo.getRealImage().getImageSize());
        stmt.setInt(17, photo.getSmallImage().getImageSize());
        stmt.setInt(18, photo.getAge().getId());
        stmt.setInt(19, photo.getSex().getId());
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

    @Override
    protected String getInsertSQL() {
        return INSERT;
    }

    @Override
    protected String getDeleteSQL() {
        return DELETE_BY_ID;
    }

    @Override
    protected String getUpdateSQL() {
        return UPDATE;
    }

    @Override
    protected String getSelectAllIDsSQL() {
        return SELECT_IDS_FOR_ALL;
    }

    @Override
    protected String getSelectIDsForFamilySQL() {
        return SELECT_IDS_BY_FAMILY_ID;
    }

    @Override
    protected String getSelectIDsForFamilyNameSQL() {
        return SELECT_IDS_BY_FAMILY_NAME;
    }    
    
    @Override
    protected String getSelectIDsForSpecieSQL() {
        return SELECT_IDS_BY_SPECIE_ID;
    }

    @Override
    protected String getSelectIDsForSpecieNameSQL() {
        return SELECT_IDS_BY_SPECIE_NAME;
    }    
    
    @Override
    protected String getSelectIDsForCommonNameSQL() {
        return SELECT_IDS_BY_COMMON_NAME_ID;
    }

    @Override
    protected String getSelectIDsForCommonNameByNameSQL() {
        return SELECT_IDS_BY_COMMON_NAME_BY_NAME;
    }  
    
    @Override
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
        s.setEnglishName(rs.getString(ENGLISH_NAME_COLUMN));
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
            logger.error("PhotoDAO.getSpecieObject : Could not retrieve city for photo " + photo, e);
            throw new SQLException("Error retrieving user for city " + photo);
        } catch (SQLException e) {
            logger.error("PhotoDAO.getSpecieObject : Could not retrieve user for city " + photo, e);
            throw e;
        }
        

        // retrieve sound information for specie
        SoundDAO soundDAO = new SoundDAO();
        List list;
        try {
            list = soundDAO.retrieveForSpecie(s.getId());
        } catch (DatabaseDownException e) {
            logger.error("PhotoDAO.getSpecieObject : Could not retrieve city for photo " + photo, e);
            throw new SQLException("Error retrieving user for city " + photo);
        } catch (SQLException e) {
            logger.error("PhotoDAO.getSpecieObject : Could not retrieve user for city " + photo, e);
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
    private City getCityObject(int cityId, String cityName, int stateId, Photo photo) 
    throws SQLException {
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
    private User getUserObject(int userId) throws SQLException {
        User user = null;
        try {
            user = userDao.retrieve(userId);
        } catch (DatabaseDownException e) {
            logger.error("PhotoDAO.createObject : Could not retrieve user for photo ", e);
            throw new SQLException("Error retrieving user for photo ");
        } catch (SQLException e) {
            logger.error("PhotoDAO.createObject : Could not retrieve user for photo ", e);
            throw e;
        }
        return user;
    }
}
