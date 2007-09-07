package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;

public class PhotoByEnglishNamePaginationController extends
PhotoPaginationController {

    /**
     * @param photosPerPage
     */
    public PhotoByEnglishNamePaginationController(int photosPerPage) {
        super(photosPerPage, false);
    }

    /**
     * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
     */
    protected List retrieveAllData() throws DatabaseDownException, SQLException {
        List listOfPhotos = null;
        if (id != -1){
            listOfPhotos = model.retrieveIDsForSpecie(id);
        } else {
            listOfPhotos = model.retrieveIDsForSpecieEnglishName(text);
        }        
        return listOfPhotos;
    }

}
