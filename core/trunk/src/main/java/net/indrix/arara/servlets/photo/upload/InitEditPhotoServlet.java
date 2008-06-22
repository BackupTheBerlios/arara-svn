/*
 * Created on 02/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.utils.jsp.Date;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class InitEditPhotoServlet extends HttpServlet {
	/**
	 * Logger object used to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
        List <String>errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        if (user == null) {
            logger.debug("errors is not null.");
            errors.add(ServletConstants.USER_NOT_LOGGED);
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            nextPage = ServletConstants.LOGIN_PAGE;
        } else {
            int photoId; 
            String photoIdStr = req.getParameter("photoId");
            Photo photo = null;
            if (photoIdStr != null){
                photoId = Integer.parseInt(photoIdStr);           
                PhotoModel model = new PhotoModel();
                try {
                    photo = model.retrieve(photoId);
                    
                    if (photo != null){
                        if ((user.getId() == photo.getUser().getId()) || user.isAdmin() || user.isPhotoEditModerator()){
                            req.setAttribute(ServletConstants.PHOTO_ID, photo.getId());

                            logger.debug("Photo found..." + photo);
                            UploadPhotoBean bean = new UploadPhotoBean();
                            logger.debug("Calling updateBean method...");
                            updateBean(bean, photo, req);
                            logger.debug("Bean updated: " + bean);
                            logger.debug("Setting bean (EDIT_BEAN key) in req...");
                            req.setAttribute(UploadPhotoConstants.UPLOAD_PHOTO_BEAN, bean);
                            nextPage = ServletConstants.EDIT_PAGE;

                            // put states on request
                            List list = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
                            logger.debug("Setting states in bean");
                            bean.setStatesList(list);

                            String identStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
                            req.setAttribute(ServletConstants.IDENTIFICATION_KEY, identStr);                                          
                        } else {
                            // user do not have the right to delete the photo
                            nextPage = ServletConstants.FRAME_PAGE;
                            String pageToShow = ServletConstants.SHOW_MESSAGE_PAGE;
                            String messageKey = "operation.denied";
                            req.setAttribute(ServletConstants.STRING_MESSAGE_KEY, messageKey);
                            req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                        }
                    } else {
                        // Photo does not exist
                        nextPage = ServletConstants.FRAME_PAGE;
                        String pageToShow = ServletConstants.SHOW_MESSAGE_PAGE;
                        String messageKey = "photo.not.found";
                        req.setAttribute(ServletConstants.STRING_MESSAGE_KEY, messageKey);
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);                        
                    }
                } catch (DatabaseDownException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            } 
        }                
        if (!errors.isEmpty()) {
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
        }
        
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	/**
	 * @param bean
	 * @param photo
	 */
	private void updateBean(UploadPhotoBean bean, Photo photo, HttpServletRequest req) {
        String identStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
        boolean identification = Boolean.valueOf(identStr).booleanValue();

		bean.setFamilyList(getFamilyList(req));
		bean.setSelectedFamilyId("" + photo.getSpecie().getFamily().getId());
		bean.setSpecieList(getSpecieList(photo.getSpecie().getFamily().getId()));
		bean.setSelectedSpecieId("" + photo.getSpecie().getId());
		bean.setCamera(photo.getCamera());
		bean.setLens(photo.getLens());
		bean.setLocation(photo.getLocation());
		bean.setFilm(photo.getFilm());
        bean.setFstop(photo.getFstop());
        bean.setShutterSpeed(photo.getShutterSpeed());
        bean.setIso(photo.getIso());
        bean.setZoom(photo.getZoom());
        bean.setFlash(photo.isFlash());
		bean.setDate(Date.getDate(photo.getDate()));
		bean.setComment(photo.getComment());
        bean.setUser(photo.getUser());
        if (photo.getCity().getState() != null){
            bean.setSelectedStateId(getId(photo.getCity().getState().getId()));            
        }
        if (photo.getCity() != null) {
            bean.setSelectedCityId(getId(photo.getCity().getId()));            
        }
        if (photo.getCity() != null && photo.getCity().getState() != null) {
            bean.setCitiesList(getCitiesList(photo.getCity().getState().getId()));
        }        
        
        if (!identification){
            bean.setSelectedAgeId(getId(photo.getAge().getId()));
            bean.setSelectedSexId(getId(photo.getSex().getId()));
        }
	}

	/**
	 * @param photo
	 * @return
	 */
	private String getId(int id) {
		return Integer.toString(id);
	}

	/**
	 * @param req
	 * @return
	 */
	private List getFamilyList(HttpServletRequest req) {
		logger.debug("InitEditPhotoServlet.getFamilyList: entering method");
		HttpSession session = req.getSession();
		UploadBean bean = (UploadBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
		List familyList = null;
		if (bean == null) {
			logger.debug("InitEditPhotoServlet.getFamilyList: bean from session is NULL...");
			FamilyDAO dao = new FamilyDAO();
			try {
				logger.debug("InitEditPhotoServlet.getFamilyList: calling dao.retrieve");
				List list = dao.retrieve();
				familyList = ServletUtil.familyDataAsLabelValueBean(list);
			} catch (DatabaseDownException e) {
				logger.debug("InitEditPhotoServlet.getFamilyList: could not retrieve list", e);
				familyList = null;
			} catch (SQLException e) {
				logger.debug("InitEditPhotoServlet.getFamilyList: could not retrieve list", e);
				familyList = null;
			}

		} else {
			familyList = bean.getFamilyList();
		}
		return familyList;
	}

	/**
	 * @param req
	 * @return
	 */
	private List getSpecieList(int id) {
		List specieList = null;
		SpecieDAO dao = new SpecieDAO();
		Family f = new Family();
		f.setId(id);
		try {
			List list = dao.retrieveForFamily(f);
			specieList = ServletUtil.specieForFamilyDataAsLabelValueBean(list);
		} catch (DatabaseDownException e) {
            logger.error("Could not retrieve list of species for family id " + id, e);
			specieList = null;
		}
		return specieList;
	}

    /**
     * @param id
     * @return
     */
    private List getCitiesList(int id) {
        CityModel model = new CityModel();
        List list = null;
        try {
			list = model.retrieveCitiesForState(id);
		} catch (DatabaseDownException e) {
            logger.error("Could not retrieve list of cities for state id " + id, e);
			list = null;
		}
        return list;
    }


}
