/*
 * Created on 09/11/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.sql.SQLException;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.StatisticsDAO;
import net.indrix.arara.vo.Statistics;

import org.apache.log4j.Logger;

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
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public static Statistics retrieveStatistics() throws DatabaseDownException,
			SQLException {
		Statistics s = new Statistics();
		StatisticsDAO dao = new StatisticsDAO();

		// retrieve number of photos
		s.setNumberOfPhotos(dao.numberOfPhotos());
        s.setNumberOfSounds(dao.numberOfSounds());
        s.setNumberOfFamiliesWithPhoto(dao.numberOfFamiliesWithPhoto());
        s.setNumberOfFamiliesWithSound(dao.numberOfFamiliesWithSound());
        s.setNumberOfSpeciesWithPhoto(dao.numberOfSpeciesWithPhoto());
        s.setNumberOfSpeciesWithSound(dao.numberOfSpeciesWithSound());

        s.setNumberOfUsers(dao.numberOfUsers());
        s.setNumberOfUsersWithPhoto(dao.numberOfUsersWithPhoto());
        s.setNumberOfUsersWithSound(dao.numberOfUsersWithSound());

		return s;
	}
}
