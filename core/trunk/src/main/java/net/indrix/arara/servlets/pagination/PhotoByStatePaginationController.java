package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class PhotoByStatePaginationController extends PhotoPaginationController {

    /**
     * @param photosPerPage
     */
    public PhotoByStatePaginationController(int photosPerPage) {
        super(photosPerPage, false);
    }

    /**
     * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
     */
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        List listOfPhotos = null;
        listOfPhotos = model.retrieveIDsForState(id);
        return listOfPhotos;
    }

}