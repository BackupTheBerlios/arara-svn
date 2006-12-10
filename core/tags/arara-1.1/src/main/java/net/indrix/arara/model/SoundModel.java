/*
 * Created on 11/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SoundDAO;
import net.indrix.arara.model.file.SoundFileManager;
import net.indrix.arara.vo.Sound;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundModel extends MediaModel{
    public SoundModel() {
        super();
        dao = new SoundDAO();
    }
    
	/**
	 * This method deletes a sound given by the soundId
	 * 
	 * @param soundId
	 *            The id of the sound to be deleted
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public void delete(int soundId) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.delete | soundId " + soundId);
		dao.delete(soundId);
	}

	/**
	 * This method retrieves the id of all sounds from database
	 * 
	 * @return An ArrayList object with Integer objects
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieveSoundsIDs() throws DatabaseDownException, SQLException {
		logger.debug("SoundModel.retrieveSoundsIDs: retrieving sound ids...");
		List list = dao.retrieveIDs();
		logger.debug("SoundModel.retrieveSoundsIDs: sound ids retrieved.");
		return list;
	}

	/**
	 * This method deletes a sound given by the soundId
	 * 
	 * @param soundId
	 *            The id of the sound to be deleted
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public Sound retrieve(int soundId) throws DatabaseDownException,
			SQLException {
		logger.debug("SoundModel.retrieve | soundId " + soundId);
		Sound sound = ((SoundDAO)dao).retrieve(soundId);

		// set the filename, with the full path, to the sound file
		SoundFileManager manager = new SoundFileManager(sound);
		sound.setRelativePath(manager.getRelativePath());
		return sound;
	}

    /**
     * This method updates the sound link, to the given sound
     * 
     * @param sound The Sound object to be updated
     */
	public void updateSoundLink(Sound sound) {
		// set the filename, with the full path, to the sound file
		SoundFileManager manager = new SoundFileManager(sound);
		sound.setRelativePath(manager.getRelativePath());
	}
}
