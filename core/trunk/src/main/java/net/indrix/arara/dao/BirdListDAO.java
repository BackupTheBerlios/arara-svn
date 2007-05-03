package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.vo.BirdList;
import net.indrix.arara.vo.BirdListElement;
import net.indrix.arara.vo.BirdListElementStatus;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Sound;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

public class BirdListDAO extends AbstractDAO{
    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String USER_ID_COLUMN = "user_id";
    public static final String LOCATION_COLUMN = "location";
    public static final String TYPE_COLUMN = "type";
    public static final String COMMENT_COLUMN = "comment";

    public static final String CITY_ID_COLUMN = "city_id";
    public static final String LIST_ID_COLUMN = "list_id";
    
    public static final String SPECIE_ID_COLUMN = "specie_id";
    public static final String PHOTO_ID_COLUMN = "photo_id";
    public static final String SOUND_ID_COLUMN = "sound_id";
    public static final String STATUS_ID_COLUMN = "status_id";
    public static final String STATUS_DESC_COLUMN = "status_description";

    /**
     * SQL for inserting a new list
     */
    private static final String INSERT = "INSERT INTO list (name, user_id, location, type, comment) values (?, ?, ?, ?, ?)";
    private static final String INSERT_CITIES = "INSERT INTO list_cities (list_id, city_id) values (?, ?)";
    /**
     * SQL for updating a list
     */
    public static final String UPDATE = "UPDATE list set name = ?, location = ?, city_id = ?, type = ?, comment = ?"
            + "WHERE id = ?";

    /**
     * SQL for deleting a given list
     */
    private static final String DELETE_BY_ID = "DELETE FROM list WHERE id = ?";

    /**
     * Retrieve all public lists
     */
    private static final String SELECT_ALL_PUBLIC = "SELECT * from list where type = " + BirdList.PUBLIC_LIST;
    
    /**
     * SQL for retrieving a given list
     */
    private static final String SELECT_BY_ID = "SELECT * from list where id = ?";
    
    /**
     * SQL for retrieving all species for a given list
     */
    private static final String SELECT_SPECIES_FOR_LIST = "SELECT ls.id, ls.specie_id, ls.photo_id, ls.sound_id, ls.status_id, lst.description status_description " +
            "from list_species ls, list_status lst WHERE ls.list_id = ? and ls.status_id = lst.id";

    /**
     * SQL for retrieving all cities for a given list
     */
    private static final String SELECT_CITIES_FOR_LIST = "SELECT * from list_cities WHERE list_id = ?";
    
    /**
     * SQL for retrieving all lists from a given user
     */
    private static final String SELECT_BY_USER_ID = "SELECT * from list where user_id = ?";

    /**
     * Object to retrieve user data from database
     */
    private UserDAO userDao = new UserDAO();

    /**
     * Object to retrieve city data from database
     */
    private CityDAO cityDao = new CityDAO();

    /**
     * Object to retrieve specie data from database
     */
    private SpecieDAO specieDao = new SpecieDAO();

    /**
     * Object to retrieve photo data from database
     */
    private PhotoDAO photoDao = new PhotoDAO();

    /**
     * Object to retrieve sound data from database
     */
    private SoundDAO soundDao = new SoundDAO();


    @SuppressWarnings("unchecked")
    @Override
    public void insert(Object o) throws DatabaseDownException, SQLException {
        logger.debug("BirdListDAO.insert: Calling super to insert list data...");
        super.insert(o);
        
        logger.debug("BirdListDAO.insert: inserting cities...");
        BirdList birdList = (BirdList)o;
        
        // now inserts all the cities
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(INSERT_CITIES, Statement.RETURN_GENERATED_KEYS);
            
            Iterator<City> it = birdList.getCities().iterator();
            while (it.hasNext()){
                City city = it.next();
                stmt.setInt(1, birdList.getId());
                stmt.setInt(2, city.getId());
                stmt.execute();

                // retrieve id just created
                rs = stmt.getGeneratedKeys();
                if (!rs.next()) {
                    logger.error("BirdListDAO.insert: could not insert a new data into DB...");
                    throw new SQLException();
                }
            }


        } catch (SQLException e) {
            logger.error("BirdListDAO.insert : could not insert data");
            logger.error("Error in SQL : " + INSERT_CITIES, e);
            throw e;
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            closeConnection(conn);
        }
        
    }
    
    /**
     * This method retrieves all public lists from database
     * 
     * @return an ArrayList object with BirdList objects, not fully loaded
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List<BirdList> retrieve() throws DatabaseDownException, SQLException {
        List<BirdList> list = super.retrieveObjects(SELECT_ALL_PUBLIC, true);
        return list;
    }
    
    /**
     * This method retrieves a <code>BirdList</code> object based on its id
     * 
     * @param id
     *            The id of the <code>BirdList</code>
     * 
     * @return a <code>BirdList</code> object
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public BirdList retrieve(int id) throws DatabaseDownException, SQLException {
        BirdList list = (BirdList) retrieveFullObject(id, SELECT_BY_ID);
        return list;
    }
  
    /**
     * This method retrieves all lists from database, for a given user
     * 
     * @return an ArrayList object with BirdList objects, not fully loaded
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List<BirdList> retrieveForUser(int userId) throws DatabaseDownException, SQLException {
        List<BirdList> list = super.retrieveObjects(userId, SELECT_BY_USER_ID);
        return list;
    }
    
    @Override
    protected Object createObject(ResultSet rs) throws SQLException {
        
        BirdList birdList = (BirdList)createLightObject(rs);
        int listId = rs.getInt(ID_COLUMN);
        birdList.setCities(retrieveCities(listId));
        
        return birdList;
    }

    /**
     * This method creates a BirdList object, but without loading the cities.
     * 
     * @param rs The resultset from database
     * 
     * @return A BirdList object, without the citites
     */
    protected Object createLightObject(ResultSet rs) throws SQLException {
        BirdList birdList;
        birdList = new BirdList();
        int listId = rs.getInt(ID_COLUMN);
        birdList.setId(listId);
        birdList.setName(rs.getString(NAME_COLUMN));
        birdList.setUser(getUserObject(rs.getInt(USER_ID_COLUMN)));
        birdList.setLocation(rs.getString(LOCATION_COLUMN));
        birdList.setComment(rs.getString(COMMENT_COLUMN));
        birdList.setType(rs.getInt(TYPE_COLUMN));
        
        return birdList;
    }
    
    /**
     * This method creates a BirdList object with the data from database, fully loaded
     * 
     * @param rs
     *            The <code>ResultSet<code> object to retrieve the data
     * 
     * @return A new object 
     * 
     * @throws SQLException If an error occur while retrieving data from result set
     */
    protected Object createFullObject(ResultSet rs) throws SQLException{
        BirdList birdList = (BirdList)createObject(rs);
        
        // retrieve all species for the given list
        
        return birdList;
    }    
    
    @Override
    protected void setObjectId(int id, Object object) throws SQLException {
        BirdList birdList = (BirdList)object;
        birdList.setId(id);
    }

    @Override
    protected void setStatementValues(PreparedStatement stmt, Object object) throws SQLException {
        BirdList birdList = (BirdList)object;
        stmt.setString(1, birdList.getName());
        stmt.setInt(2, birdList.getUser().getId());
        stmt.setString(3, birdList.getLocation());
        stmt.setInt(4, birdList.getType());
        stmt.setString(5, birdList.getComment());
    }

    @Override
    protected void setStatementValuesForUpdate(PreparedStatement stmt, Object object) throws SQLException {
        BirdList birdList = (BirdList)object;       
        stmt.setString(1, birdList.getName());
        stmt.setInt(2, birdList.getUser().getId());
        stmt.setString(3, birdList.getLocation());
        stmt.setInt(4, birdList.getType());
        stmt.setString(5, birdList.getComment());
    }

    /**
     * This method returns the SQL statement to select all object from database
     * 
     * @return the SelectALL sql
     */
    protected String getSelectAllSQL() {
        return SELECT_ALL_PUBLIC;
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
     * This method retrieves all species from database, for the given list.
     * 
     * @return A list of <code>BirdListElement</code> objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    private List<BirdListElement> retrieveSpecies(int listId) throws DatabaseDownException, SQLException {       
        List<BirdListElement> list = new ArrayList<BirdListElement>();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(SELECT_SPECIES_FOR_LIST);
            stmt.setInt(1, listId);
            rs = stmt.executeQuery();
            BirdListElement element = null;

            while (rs.next()) {
                int id = rs.getInt(ID_COLUMN);
                int specieId = rs.getInt(SPECIE_ID_COLUMN);
                int photoId = rs.getInt(PHOTO_ID_COLUMN);
                int soundId = rs.getInt(SOUND_ID_COLUMN);
                int statusId = rs.getInt(STATUS_ID_COLUMN);
                String statusDescription = rs.getString(STATUS_DESC_COLUMN);
                
                Specie specie = specieDao.retrieve(specieId);
                Photo photo = photoDao.retrieve(photoId);
                Sound sound = soundDao.retrieve(soundId);
                
                BirdListElementStatus status = new BirdListElementStatus();
                status.setId(statusId);
                status.setDescription(statusDescription);
                
                element = new BirdListElement();
                element.setId(id);
                element.setSpecie(specie);
                element.setPhoto(photo);
                element.setSound(sound);
                element.setStatus(status);
                
                list.add(element);
            }
        } catch (SQLException e) {
            logger.error("BirdListDAO.retrieveSpecies : could not retrieve data ", e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }
        return list;
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

    /**
     * This method retrieves all cities from database, for the given list.
     * 
     * @return A list of <code>City</code> objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    private List<City> retrieveCities(int listId) throws SQLException {       
        List<City> list = new ArrayList<City>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
            stmt = conn.prepareStatement(SELECT_CITIES_FOR_LIST);
            stmt.setInt(1, listId);
            rs = stmt.executeQuery();
            City city = null;

            while (rs.next()) {
                int cityId = rs.getInt(CITY_ID_COLUMN);
                city = cityDao.retrieve(cityId);
                list.add(city);
            }
        } catch (DatabaseDownException e) {
            logger.error("BirdListDAO.createObject : Could not retrieve city for list ", e);
            throw new SQLException("Error retrieving city for list ");
        } catch (SQLException e) {
            logger.error("BirdListDAO.retrieveSpecies : could not retrieve data ", e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            if (conn != null){
                conn.close();                
            }
        }
        return list;
    }

}
