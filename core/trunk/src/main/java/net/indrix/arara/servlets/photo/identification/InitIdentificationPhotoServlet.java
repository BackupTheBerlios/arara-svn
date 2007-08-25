/*
 * Created on 07/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.identification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.IdentifyPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitIdentificationPhotoServlet extends
		AbstractIdentificationServlet {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.info("InitIdentificationPhotoServlet.doGet : entering method...");
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List <String>errors = new ArrayList<String>();
		HttpSession session = req.getSession();

		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (user == null) {
			logger.debug("InitIdentificationPhotoServlet: USER is not logged...");
			errors.add(ServletConstants.USER_NOT_LOGGED);
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.LOGIN_PAGE;

			String nextResourceToExecute = ServletUtil.getResource(req);
			req.setAttribute(ServletConstants.NEXT_RESOURCE_AFTER_LOGIN, nextResourceToExecute);
		} else {
			PhotoModel model = new PhotoModel();

			String photoId = req.getParameter("photoId");

			List list = null;
			list = (List) session.getAttribute(ServletConstants.PHOTOS_LIST);

			// retrieve families and species
            IdentifyPhotoBean identificationBean = new IdentifyPhotoBean();                
			setListOfFamilies(identificationBean);
			setListOfSpecies(identificationBean);                
			req.setAttribute(IdentificationPhotoConstants.IDENTIFICATION_PHOTO_BEAN, identificationBean);
            
            req.setAttribute(ServletConstants.NEXT_PAGE_KEY, ServletConstants.FRAME_PAGE);

			if ((photoId == null) || (list == null)) {
				Photo photo = getPhotoFromDatabase(errors, photoId);
				if (photo != null) {
					req.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
					nextPage = ServletConstants.FRAME_PAGE;
                    String pageToShow = "/jsp/photo/search/doShowOnePhoto.jsp";
					// retrieve all identifications already done for the given
					// photo
					try {
						retrieveIdentificationsForPhoto(model, photo);
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
					} catch (DatabaseDownException e) {
						logger.debug("DatabaseDownException.....");
						errors.add(ServletConstants.DATABASE_ERROR);
					} catch (SQLException e) {
						logger.debug("SQLException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
					}
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
					if (photo.getId() == id) {
						logger.debug("Photo found !!!! ");
						found = true;
						try {
							// retrieve all identifications already done for the
							// given photo
							retrieveIdentificationsForPhoto(model, photo);
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
					photo = getPhotoFromDatabase(errors, photoId);
				}

                if (!errors.isEmpty() || photo == null) {
                    logger.debug("Photo does not exist in DB anymore");
                    nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
                } else {
                    req.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
                    nextPage = ServletConstants.FRAME_PAGE;
                    String pageToShow = "/jsp/photo/search/doShowOnePhoto.jsp";
                    req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                    
                }

			}
			req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
			req.setAttribute(ServletConstants.VIEW_MODE_KEY, "identificationMode");

		}

		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);
	}
}
