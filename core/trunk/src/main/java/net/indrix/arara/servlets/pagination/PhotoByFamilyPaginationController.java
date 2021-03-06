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
public class PhotoByFamilyPaginationController extends
		PhotoPaginationController {

	/**
	 * @param photosPerPage
	 * @param identification
	 */
	public PhotoByFamilyPaginationController(int photosPerPage) {
		super(photosPerPage, false);
	}

	/**
	 * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
	 */
	protected List retrieveAllData() throws DatabaseDownException, SQLException {
		List listOfPhotos = null;
        if (id != -1){
            listOfPhotos = model.retrieveIDsForFamily(id);            
        } else {
            listOfPhotos = model.retrieveIDsForFamilyName(text);            
        }
		return listOfPhotos;
	}

}
