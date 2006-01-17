/*
 * Created on 16/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.photo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.PhotoModel;
import net.indrix.servlets.ServletConstants;
import net.indrix.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ShowOnePhotoServlet extends HttpServlet {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		String photoId = req.getParameter("photoId");
        String identificationStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
        boolean identification = new Boolean(identificationStr).booleanValue();
        
		List list = (List) session.getAttribute(ServletConstants.PHOTOS_LIST);
		if ((photoId == null) || (list == null)) {
			Photo photo = getPhotoFromDatabase(errors, photoId, identification);
			if (photo != null) {
				session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
				nextPage = ServletConstants.ONE_PHOTO_PAGE;
			} else {
				logger.debug("List of photos not found...");
				nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
			}
		} else {
			logger.debug("List of photos found...");
			Iterator it = list.iterator();
			int id = Integer.parseInt(photoId);
			boolean found = false;
			Photo photo = null;
			while (it.hasNext() && (!found)) {
				photo = (Photo) it.next();
				logger.debug("Retrieved photo " + photo);
				if (photo.getId() == id) {
					logger.debug("Photo found !!!! ");
					found = true;
					PhotoModel model = new PhotoModel();
					try {
						retrieveCommentsForPhoto(model, photo);
					} catch (DatabaseDownException e) {
						logger.debug("DatabaseDownException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
					} catch (SQLException e) {
						logger.debug("SQLException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
					}
				}
			}
			if (!found) {
				photo = getPhotoFromDatabase(errors, photoId, identification);
				if (!errors.isEmpty() || photo == null) {
					logger.debug("Photo does not exist in DB anymore");
					nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
				}
			}
            req.setAttribute(ServletConstants.IDENTIFICATION_KEY, identificationStr);
			session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
			nextPage = ServletConstants.ONE_PHOTO_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	private Photo getPhotoFromDatabase(List errors, String photoId, boolean identification) {
		// retrieve from database
		PhotoModel model = new PhotoModel();
		Photo photo = null;
		try {
            if (identification){
                photo = model.retrieveForIdentification(Integer.parseInt(photoId));
            } else {
                photo = model.retrieve(Integer.parseInt(photoId));
                if (photo != null) {
                    retrieveCommentsForPhoto(model, photo);
                }
            }
            logger.debug("Photo retrieved = " + photo);
		} catch (NumberFormatException e) {
			logger.error("Could not parse photoId " + photoId);
			errors.add(ServletConstants.DATABASE_ERROR);
		} catch (DatabaseDownException e) {
			logger.debug("DatabaseDownException.....");
			errors.add(ServletConstants.DATABASE_ERROR);
		} catch (SQLException e) {
			logger.debug("SQLException.....", e);
			errors.add(ServletConstants.DATABASE_ERROR);
		}
		return photo;
	}

	/**
	 * This method retrieves the comments for a given photo
	 * 
	 * @param model
	 * @param photo
	 * @throws DatabaseDownException
	 * @throws SQLException
	 */
	private void retrieveCommentsForPhoto(PhotoModel model, Photo photo)
		throws DatabaseDownException, SQLException {
		// now retrieve the comments
		List comments = model.retrieveCommentsForPhoto(photo);
		photo.setComments(comments);
		if ((comments == null) || (comments.isEmpty())) {
			logger.debug("There is no comment for photo " + photo);
		} else {
			logger.debug(comments.size() + " comments found for photo " + photo);
		}
	}

}
