package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class SoundRecentPaginationController extends SoundPaginationController {

    public SoundRecentPaginationController(int photosPerPage) {
        super(photosPerPage, false);
    }

    /**
     * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
     */
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        List listOfSounds = null;
        logger.debug("SoundRecentPaginationController.retrieveAllData: retrieving more recent photos...");
        listOfSounds = model.retrieveSoundIDsForRecentSounds();
        return listOfSounds;
    }

}
