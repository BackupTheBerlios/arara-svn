package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

/**
 * This class is responsible for retrieving the data for more recent photos
 * 
 * @author Jeff
 *
 */
public class PhotoRecentPaginationController extends PhotoPaginationController {

    public PhotoRecentPaginationController(int photosPerPage) {
        super(photosPerPage, false);
    }

    /**
     * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
     */
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        List listOfPhotos = null;
        logger.debug("PhotoPaginationController.retrieveAllData: retrieving more recent photos...");
        listOfPhotos = model.retrievePhotoIDsForRecentPhotos();
        return listOfPhotos;
    }

}
