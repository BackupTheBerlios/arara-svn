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
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoPaginationController extends PaginationController {

	/**
	 * The model to use to retrieve data from database
	 */
	PhotoModel model = new PhotoModel();

	/**
	 * @param photosPerPage
	 * @param identification
	 */
	public PhotoPaginationController(int photosPerPage, boolean identification) {
		super(photosPerPage, identification);
	}

	/**
	 * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveDataForPage()
	 */
	protected void retrieveDataForPage() throws DatabaseDownException,
			SQLException {
        if (listOfData != null){
            int end = currentIndex + dataPerPage;
            int size = listOfData.size();
            logger.debug("CurrentIndex = " + currentIndex);
            logger.debug("End = " + end);
            logger.debug("List size = " + size);
            logger.debug("Adding photos...");
            int i = 0;
            for (i = currentIndex; (i < end) && (i < size); i++) {
                int id = ((Integer) listOfData.get(i)).intValue();
                Photo photo = null;
                logger.debug("PaginationController.doAction: retrieving photo (thumbnail) " + id);
                photo = model.retrieveThumbnail(id);

                // if photo is for identification, verify if it's still not identified. One user could
                // have identified it while another user is browsing photos.
                if ((photo != null) && (!isIdentification() || (isIdentification() && photo.getSpecie().getId() == -1))){
                    logger.debug("PaginationController.doAction: photo retrieved. Adding to list..." + photo);
                    viewOfList.add(photo);
                } else {
                    logger.debug("PaginationController.doAction: photo not found");
                    end++;
                }
            }
            logger.debug("Photos added: " + i);
        }

	}

	/**
	 * @see net.indrix.arara.servlets.pagination.PaginationController#retrieveAllData()
	 */
	protected List retrieveAllData() throws DatabaseDownException, SQLException {
		List listOfPhotos = null;
		if (isIdentification()) {
            logger.debug("PhotoPaginationController.retrieveAllData: retrieving photo for identification...");
			listOfPhotos = model.retrievePhotosForIdentification();
		} else {
            logger.debug("PhotoPaginationController.retrieveAllData: retrieving photo...");
			listOfPhotos = model.retrievePhotoIDs();
		}
		return listOfPhotos;
	}
}
