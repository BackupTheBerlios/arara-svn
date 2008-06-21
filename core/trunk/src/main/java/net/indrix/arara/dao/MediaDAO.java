package net.indrix.arara.dao;

import java.sql.SQLException;
import java.util.List;

public abstract class MediaDAO extends AbstractDAO {

    public MediaDAO() {
        super();
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
        List list = retrieveIDs(getSelectIDsForRecentePhotosSQL());
        return list;
    }
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, based on the id of the family
     * 
     * @param id
     *            The id of the <code>List</code>
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the id of the common name
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForFamily(int id) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenID(id, getSelectIDsForFamilySQL());
        return list;
    }

    /**
     * This method retrieves a <code>List</code> object with
     * <code>Photo</code> objects, based on the id of the specie
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
    public List retrieveIDsForSpecie(int id) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenID(id, getSelectIDsForSpecieSQL());
        return list;
    }
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Photo</code> objects, based on the id of the specie and the user
     * 
     * @param specieId The id of the specie
     * @param userId The id of the user
     *                      
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the id of the specie and the user
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForSpecieOfUser(int specieId, int userId) throws DatabaseDownException,
            SQLException {
        int ids[] = {specieId, userId};
        
        List list = retrieveIDsForGivenIDs(ids, getSelectIDsForSpecieOfUserSQL());
        return list;
    }
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Photo</code> objects, based on the id of the common name
     * 
     * @param id
     *            The id of the <code>List</code>
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the id of the common name
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForCommonName(int id) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenID(id, getSelectIDsForCommonNameSQL());
        return list;
    }    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, based on the name of the specie
     * 
     * @param name
     *            The name of the specie
     * 
     * @return a <code>List</code> object with <code>Sound</code> objects,
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
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, based on the name of the family
     * 
     * @param name
     *            The name of the family
     * 
     * @return a <code>List</code> object with <code>Sound</code> objects,
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
     * @return a <code>List</code> object with <code>Sound</code> objects,
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
     * <code>Integer</code> objects, based on the english name of the specie
     * 
     * @param name The english name of the specie
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the name of the specie
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForSpecieEnglishName(String name) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenStringField(name, getSelectIDsForSpecieEnglishNameSQL());
        return list;
    } 
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Photo</code> objects, based on the id of the city
     * 
     * @param id
     *            The id of the <code>City</code>
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the id of the city
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForPlace(int id) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenID(id, getSelectIDsForPlaceSQL());
        return list;
    }
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Photo</code> objects, based on the id of the state
     * 
     * @param id
     *            The id of the <code>State</code>
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects,
     *         based on the id of the state
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForState(int id) throws DatabaseDownException,
            SQLException {
        List list = retrieveIDsForGivenID(id, getSelectIDsForStateSQL());
        return list;
    }    
    
    /**
     * This method returns the SQL statement to select ids for family from
     * database
     * 
     * @return the SELECT_IDS_BY_FAMILY_ID sql
     */
    abstract protected String getSelectIDsForFamilySQL();

    /**
     * This method returns the SQL statement to select ids for specie from
     * database
     * 
     * @return the SELECT_IDS_BY_FAMILY_ID sql
     */
    abstract protected String getSelectIDsForSpecieSQL();

    /**
     * This method returns the SQL statement to select ids for common name from
     * database
     * 
     * @return the SELECT_IDS_BY_COMMON_NAME_ID sql
     */
    protected String getSelectIDsForCommonNameSQL() {
        return null;
    }

    
    /**
     * This method returns the SQL statement to select ids for family from
     * database
     * 
     * @return the SELECT_IDS_FOR_ALL_BY_DATE sql
     */
    abstract protected String getSelectIDsForRecentePhotosSQL();
    
    /**
     * This method returns the SQL statement to select ids for family from
     * database
     * 
     * @return the SELECT_IDS_BY_FAMILY_NAME sql
     */
    abstract protected String getSelectIDsForFamilyNameSQL();
    
    /**
     * This method returns the SQL statement to select ids for family from
     * database
     * 
     * @return the SELECT_IDS_BY_FAMILY_NAME sql
     */
    abstract protected String getSelectIDsForSpecieNameSQL();

    /**
     * This method returns the SQL statement to select ids for species based on their english name
     * from database
     * 
     * @return the SELECT_IDS_BY_ENGLISH_NAME sql
     */
    abstract protected String getSelectIDsForSpecieEnglishNameSQL();
    
    /**
     * This method returns the SQL statement to select ids for family from
     * database
     * 
     * @return the SELECT_IDS_BY_FAMILY_NAME sql
     */
    abstract protected String getSelectIDsForCommonNameByNameSQL();    

    /**
     * This method returns the SQL statement to select ids for city from
     * database
     * 
     * @return the SELECT_IDS_BY_CITY_ID sql
     */
    abstract protected String getSelectIDsForPlaceSQL();

    /**
     * This method returns the SQL statement to select ids for state from
     * database
     * 
     * @return the SELECT_IDS_BY_STATE_ID sql
     */
    abstract protected String getSelectIDsForStateSQL();

    /**
     * This method returns the SQL statement to select ids for a given specie id, of a specific user
     * database
     * 
     * @return the SELECT_IDS_BY_SPECIE_ID_FOR_USER sql
     */
    abstract protected String getSelectIDsForSpecieOfUserSQL();


}