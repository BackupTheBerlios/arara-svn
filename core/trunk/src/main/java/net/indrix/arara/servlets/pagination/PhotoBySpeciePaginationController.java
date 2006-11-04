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

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoBySpeciePaginationController extends
		PhotoPaginationController {

	/**
	 * @param photosPerPage
	 * @param identification
	 */
	public PhotoBySpeciePaginationController(int photosPerPage) {
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
            listOfPhotos = model.retrieveIDsForSpecieName(text);
        }        
		return listOfPhotos;
	}

}
