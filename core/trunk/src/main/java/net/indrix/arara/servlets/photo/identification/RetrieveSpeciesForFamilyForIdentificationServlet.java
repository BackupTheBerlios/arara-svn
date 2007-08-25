/*
 * Created on 04/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.identification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.IdentifyPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.FamilyModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrieveSpeciesForFamilyForIdentificationServlet extends
		AbstractIdentificationServlet {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		logger.debug("RetrieveSpeciesForFamilyForIdentificationServlet.doPost: entering method...");
		List <String>errors = new ArrayList<String>();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		HttpSession session = req.getSession();
		String nextPage = null;

		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (user == null) {
			logger.debug("InitIdentificationPhotoServlet: USER is not logged...");
			errors.add(ServletConstants.USER_NOT_LOGGED);
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.LOGIN_PAGE;
		} else {
			logger.debug("Parsing data...");
			String familyId = req.getParameter(ServletConstants.FAMILY_ID);
			if ((familyId == null) || (familyId.equals(""))) {
				errors.add(ServletConstants.SELECT_FAMILY_ERROR);
				IdentifyPhotoBean bean = new IdentifyPhotoBean();
				bean.setSelectedFamilyId(null);
				bean.setSelectedSpecieId(null);
				bean.setSpecieList(null);
                
                req.setAttribute(IdentificationPhotoConstants.IDENTIFICATION_PHOTO_BEAN, bean);
			} else {
				logger.debug("selected family id " + familyId);

				try {
					List list = retrieveSpecieListForFamilyId(familyId);

					if ((list != null) && (!list.isEmpty())) {
						logger.debug("Setting specie list in request");
						logger.debug("Setting data in request");
						handleList(list, req, errors);
                        nextPage = ServletConstants.FRAME_PAGE;
						req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
						req.setAttribute(ServletConstants.VIEW_MODE_KEY, "identificationMode");
                        String pageToShow = "/jsp/photo/search/doShowOnePhoto.jsp";
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);

                        // retrieve photo under identification
                        String photoId = req.getParameter("photoId");
                        Photo photo = (Photo) getPhotoFromDatabase(errors, photoId);
                        req.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
					} else {
						logger.debug("Specie list not found...");
						errors.add(ServletConstants.DATABASE_ERROR);
						nextPage = ServletConstants.INITIAL_PAGE;
					}
				} catch (DatabaseDownException e) {
					errors.add(ServletConstants.DATABASE_ERROR);
					nextPage = ServletConstants.INITIAL_PAGE;
				}
			}
			if (!errors.isEmpty()) {
				// coloca erros no request para registrar.jsp processar e
				// apresentar mensagem de erro
				req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			}
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

	/**
	 * @param list
	 * @param dataToBeUploaded
	 */
	private void handleList(List list, HttpServletRequest req, List <String>errors) {

		HttpSession session = req.getSession();
		IdentifyPhotoBean bean = null;

		String beanKey = IdentificationPhotoConstants.IDENTIFICATION_PHOTO_BEAN;

        // populate the bean with data from request
		bean = handleBean(req, session, beanKey, errors);
		bean.setSpecieList(list);
        
        List listOfFamilies = ServletUtil.familyDataAsLabelValueBean(FamilyModel.getFamilyList());
        bean.setFamilyList(listOfFamilies);        
	}

	private static List retrieveSpecieListForFamilyId(String familyId)
			throws DatabaseDownException {
		SpecieDAO dao = new SpecieDAO();
		Family family = new Family();
		family.setId(Integer.parseInt(familyId));
		List list = ServletUtil.specieForFamilyDataAsLabelValueBean(dao.retrieveForFamily(family));
		return list;
	}
}
