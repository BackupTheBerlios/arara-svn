package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class SoundByUserPaginationController extends SoundPaginationController {

    /**
     * Creates a new PaginationController object, with the given number of
     * elements per page, and with the flag identification
     * 
     * @param soundsPerPage
     *            The amount of sounds per page
     * @param identification
     *            The flag for identification
     */
    public SoundByUserPaginationController(int soundsPerPage, boolean identification) {
        super(soundsPerPage, identification);
    }

    @Override
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        logger.debug("SoundByUserPaginationController.retrieveAllData : retrieving all sounds...");
        List listOfSounds = null;
        listOfSounds = model.retrieveSoundIDsForUser(getId());
        logger.debug("SoundByUserPaginationController.retrieveAllData : "
                + listOfSounds.size() + " sounds retrieved...");
        return listOfSounds;
    }

}
