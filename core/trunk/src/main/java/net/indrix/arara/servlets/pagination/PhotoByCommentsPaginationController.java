package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class PhotoByCommentsPaginationController extends PhotoPaginationController {

    public PhotoByCommentsPaginationController(int photosPerPage) {
        super(photosPerPage, false);
    }

    /**
     * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
     */
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        List listOfPhotos = null;
        logger.debug("Retrieving photos with more comments...");
        listOfPhotos = model.retrievePhotosIDsWithMoreComments();
        return listOfPhotos;
    }

}
