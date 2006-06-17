/*
 * Created on 21/04/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.SoundModel;
import net.indrix.arara.vo.Sound;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundPaginationController extends PaginationController {

	/**
	 * The model to use to retrieve data from database
	 */
	SoundModel model = new SoundModel();

	/**
	 * @param photosPerPage
	 * @param identification
	 */
	public SoundPaginationController(int soundsPerPage, boolean identification) {
		super(soundsPerPage);
	}

	/**
	 * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveDataForPage()
	 */
	protected void retrieveDataForPage() throws DatabaseDownException,
			SQLException {
		int end = currentIndex + dataPerPage;
		int size = listOfData.size();
		logger.debug("SoundPaginationController: CurrentIndex = "
				+ currentIndex);
		logger.debug("SoundPaginationController: End = " + end);
		logger.debug("SoundPaginationController: List size = " + size);
		logger.debug("SoundPaginationController: Adding sounds...");
		int i = 0;
		for (i = currentIndex; (i < end) && (i < size); i++) {
			int id = ((Integer) listOfData.get(i)).intValue();
			Sound sound = null;
			logger
					.debug("SoundPaginationController.retrieveDataForPage: retrieving sound "
							+ id);
			sound = model.retrieve(id);
			if (sound != null) {
				logger
						.debug("SoundPaginationController.retrieveDataForPage: sound retrieved. Adding to list");
				viewOfList.add(sound);
			} else {
				logger
						.debug("SoundPaginationController.retrieveDataForPage: sound not found");
				end++;
			}
		}
		logger.debug("sounds added: " + i);

	}

	/**
	 * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
	 */
	protected List retrieveAllData() throws DatabaseDownException, SQLException {
		logger
				.debug("SoundPaginationController.retrieveAllData : retrieving all sounds...");
		List listOfSounds = null;
		listOfSounds = model.retrieveSoundsIDs();
		logger.debug("SoundPaginationController.retrieveAllData : "
				+ listOfSounds.size() + " sounds retrieved...");
		return listOfSounds;
	}

}
