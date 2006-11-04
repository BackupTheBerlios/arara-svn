package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class SoundBySpeciePaginationController extends
        SoundPaginationController {

    
    /**
     * Creates a new PaginationController object, with the given number of elements per page, and
     * with the flag identification
     * 
     * @param soundsPerPage The amount of sounds per page
     * @param identification The flag for identification
     */
    public SoundBySpeciePaginationController(int soundsPerPage, boolean identification) {
        super(soundsPerPage, identification);
    }

    @Override
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        logger.debug("SoundBySpeciePaginationController.retrieveAllData : retrieving all sounds...");
        List listOfSounds = null;
        if (id != -1){
            listOfSounds = model.retrieveIDsForSpecie(getId());
        } else {
            listOfSounds = model.retrieveIDsForSpecieName(getText());
        }
        
        logger.debug("SoundBySpeciePaginationController.retrieveAllData : " + listOfSounds.size() + " sounds retrieved...");
        return listOfSounds;
    }
}
