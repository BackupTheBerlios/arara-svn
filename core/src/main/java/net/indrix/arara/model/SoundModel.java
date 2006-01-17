/*
 * Created on 11/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.model;

import java.sql.SQLException;
import java.util.List;

import net.indrix.dao.DatabaseDownException;
import net.indrix.dao.SoundDAO;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundModel {
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * This method deletes a photo given by the soundId
     * 
     * @param soundId The id of the photo to be deleted
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void delete(int soundId) throws DatabaseDownException, SQLException {
        logger.debug("PhotoModel.delete | soundId " + soundId);
        SoundDAO dao = new SoundDAO();
        dao.delete(soundId);
    }

    /**
     * This method retrieves all sounds from database
     * 
     * @return An ArrayList object with Sound objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveSounds() throws DatabaseDownException, SQLException {
        logger.debug("SoundModel.retrieveSounds");
        SoundDAO dao = new SoundDAO();
        List list = dao.retrieve();
        return list;
    }
}
