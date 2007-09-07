package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.MediaDAO;

import org.apache.log4j.Logger;

/**
 * This class implements the common behavior for all model classes for the different
 * types of media (photo, sound, video)
 *  
 * @author Jeff
 *
 */
public class MediaModel {
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * The DAO object to be used to retrieve objects data from database
     */
    protected MediaDAO dao = null;

    /**
     * This method retrieves the id of all objects from database, for the given
     * the family id
     * 
     * @param familyId
     *            The id of the family
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForFamily(int familyId)
            throws DatabaseDownException, SQLException {
        logger.debug("MediaModel.retrieveIDsForFamily | familyId " + familyId);
        List list = dao.retrieveIDsForFamily(familyId);
        return list;
    }

    /**
     * This method retrieves the id of all objects from database, for the given
     * the family name
     * 
     * @param familyId
     *            The id of the family
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForFamilyName(String name)
            throws DatabaseDownException, SQLException {
        logger.debug("MediaModel.retrieveIDsForFamilyName | name " + name);
        List list = dao.retrieveIDsForFamilyName(name);
        return list;
    }

    /**
     * This method retrieves the id of all objects from database, for the given
     * the specie id
     * 
     * @param specieId
     *            The id of the specie
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForSpecie(int specieId)
            throws DatabaseDownException, SQLException {
        logger.debug("MediaModel.retrieveIDsForSpecie | specieId " + specieId);
        List list = dao.retrieveIDsForSpecie(specieId);
        return list;
    }

    /**
     * This method retrieves the id of all objects from database, for the given
     * the specie name
     * 
     * @param name
     *            The name of the specie
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForSpecieName(String name)
            throws DatabaseDownException, SQLException {
        logger.debug("MediaModel.retrieveIDsForSpecieName | name " + name);
        List list = dao.retrieveIDsForSpecieName(name);
        return list;
    }

    /**
     * This method retrieves the id of all objects from database, for the given
     * the specie name
     * 
     * @param name
     *            The name of the specie
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForSpecieEnglishName(String name)
            throws DatabaseDownException, SQLException {
        logger.debug("Name " + name);
        List list = dao.retrieveIDsForSpecieEnglishName(name);
        return list;
    }
    
    /**
     * This method retrieves the id of all objects from database, for the given
     * common name id
     * 
     * @param commonNameId
     *            The id of the common name to be retrieved
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForCommonName(int commonNameId)
            throws DatabaseDownException, SQLException {
        logger.debug("MediaModel.retrieveIDsForCommonName: retrieving ids...");
        List list = dao.retrieveIDsForCommonName(commonNameId);
        logger.debug("MediaModel.retrieveIDsForCommonName: ids retrieved.");
        return list;
    }

    /**
     * This method retrieves the id of all objects from database, for the given
     * the family name
     * 
     * @param familyId
     *            The id of the family
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForCommonNameByName(String name)
            throws DatabaseDownException, SQLException {
        logger.debug("MediaModel.retrieveIDsForCommonNameByName " + name);
        List list = dao.retrieveIDsForCommonNameByName(name);
        return list;
    }

    /**
     * This method retrieves the id of all photos from database, for the given
     * user id
     * 
     * @param userId
     *            The id of the user
     * 
     * @return An ArrayList object with Photo objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIDsForUser(int userId) throws DatabaseDownException,
            SQLException {
        logger.debug("MediaModel.retrieveIDsForUser | userId " + userId);
        List list = dao.retrieveIDsForUser(userId);
        return list;
    }
}
