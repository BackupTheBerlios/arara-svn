/*
 * Created on 09/11/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.model;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import net.indrix.dao.DatabaseDownException;
import net.indrix.dao.StatisticsDAO;
import net.indrix.vo.Statistics;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StatisticsModel {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * This method retrieves some statistics about photos
     * 
     * @return A <code>Statistics</code> object
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
	public static Statistics retrieveStatistics() throws DatabaseDownException, SQLException {
		Statistics s = null;
        
        logger.debug("StatisticsModel.retrieveStatistics: creating DAO");
		StatisticsDAO dao = new StatisticsDAO();

        // retrieve number of photos
        logger.debug("StatisticsModel.retrieveStatistics: retrieving # of photos");
		int photos = dao.numberOfPhotos();
        logger.debug("StatisticsModel.retrieveStatistics: # of photos = " + photos);
        
        // retrieve number of families      
        logger.debug("StatisticsModel.retrieveStatistics: retrieving # of families");
		int families = dao.numberOfFamilies();
        logger.debug("StatisticsModel.retrieveStatistics: # of families = " + families);

        // retrieve number of species      
        logger.debug("StatisticsModel.retrieveStatistics: retrieving # of species");
        int species = dao.numberOfSpecies();
        logger.debug("StatisticsModel.retrieveStatistics: # of species = " + species);
        
        // retrieve number of users
        logger.debug("StatisticsModel.retrieveStatistics: retrieving # of users");
        int users = dao.numberOfUsers();
        logger.debug("StatisticsModel.retrieveStatistics: # of users = " + users);
                
		s = new Statistics();
		s.setNumberOfPhotos(photos);
		s.setNumberOfFamilies(families);
        s.setNumberOfSpecies(species);
        s.setNumberOfUsers(users);
		return s;
	}
}
