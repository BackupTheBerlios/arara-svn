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
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieDAO;
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
public class InitIdentificationPhotoServlet extends AbstractIdentificationServlet {
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
		List errors = new ArrayList();
		List messages = new ArrayList();
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
            FamilyDAO familyDao = new FamilyDAO();
            SpecieDAO specieDao = new SpecieDAO();
            List listOfSpecies = null;
            List listOfFamilies = null;
            try {
                logger.debug("Retrieving list of all families...");
                listOfFamilies = familyDao.retrieve();
                logger.debug("Converting to labelValueBean...");
                listOfFamilies = ServletUtil.familyDataAsLabelValueBean(listOfFamilies);
                logger.debug("Putting to session...");

                logger.debug("Retrieving list of all species...");
                listOfSpecies = specieDao.retrieve();
                logger.debug("Converting to labelValueBean...");
                listOfSpecies = ServletUtil.specieDataAsLabelValueBean(listOfSpecies);
                logger.debug("Putting to session...");

                IdentifyPhotoBean identificationBean = new IdentifyPhotoBean();
                identificationBean.setFamilyList(listOfFamilies);
                identificationBean.setSpecieList(listOfSpecies);
                session.setAttribute(
                    IdentificationPhotoConstants.IDENTIFICATION_PHOTO_BEAN,
                    identificationBean);

            } catch (DatabaseDownException e) {
                logger.error(
                    "InitSearchPhotosBySpecieServlet.doGet : could not retrieve list of all species",
                    e);
            } catch (SQLException e) {
                logger.error(
                    "InitSearchPhotosBySpecieServlet.doGet : could not retrieve list of all species",
                    e);
            }

			if ((photoId == null) || (list == null)) {
				Photo photo = getPhotoFromDatabase(errors, photoId);
				if (photo != null) {
					session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
					nextPage = ServletConstants.ONE_PHOTO_PAGE;

					// retrieve all identifications already done for the given photo
					try {
						retrieveIdentificationsForPhoto(model, photo);
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
				PhotoModel photoModel = new PhotoModel();
				while (it.hasNext() && (!found)) {
					photo = (Photo) it.next();
					if (photo.getId() == id) {
						logger.debug("Photo found !!!! ");
						found = true;
						try {
							photoModel.retrievePhotoImage(photo);

							// retrieve all identifications already done for the given photo
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
					if (!errors.isEmpty() || photo == null) {
						logger.debug("Photo does not exist in DB anymore");
						nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
					}
				}

				session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
				nextPage = ServletConstants.ONE_PHOTO_PAGE;
			}
			req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
            req.setAttribute(ServletConstants.VIEW_MODE_KEY, "identificationMode");
            
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	private Photo getPhotoFromDatabase(List errors, String photoId) {
		// retrieve from database
		PhotoModel model = new PhotoModel();
		Photo photo = null;
		try {
			photo = model.retrieve(Integer.parseInt(photoId));
			if (photo != null) {
				retrieveIdentificationsForPhoto(model, photo);
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
}
