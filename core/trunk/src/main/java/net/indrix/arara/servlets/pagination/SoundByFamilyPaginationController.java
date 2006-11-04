package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class SoundByFamilyPaginationController extends
        SoundPaginationController {

    /**
     * Creates a new PaginationController object, with the given number of
     * elements per page, and with the flag identification
     * 
     * @param soundsPerPage
     *            The amount of sounds per page
     * @param identification
     *            The flag for identification
     */
    public SoundByFamilyPaginationController(int soundsPerPage,
            boolean identification) {
        super(soundsPerPage, identification);
    }

    @Override
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        logger.debug("Retrieving all sounds for family: " + getId() + " | " + getText());
        List listOfSounds = null;
        if (id != -1) {
            listOfSounds = model.retrieveIDsForFamily(getId());
        } else {
            listOfSounds = model.retrieveIDsForFamilyName(getText());
        }
        int s = listOfSounds.size();
        logger.debug(s + " sounds retrieved...");
        return listOfSounds;
    }

}
