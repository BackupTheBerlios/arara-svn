/*
 * Created on 03/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.model.FamilyModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.model.UploadPhoto;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.common.PhotoBeanManager;
import net.indrix.arara.servlets.common.UploadBeanManagerFactory;
import net.indrix.arara.servlets.exceptions.InvalidFileException;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class UploadPhotoServlet extends AbstractUploadPhotoServlet {
	private static final int MAX_PHOTO_SIZE = 400000;

	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * Init method
	 */
	public void init() {
		logger.debug("Initializing UploadPhotoServlet...");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
			Map data = null;
			try {
                logger.debug("Parsing user data...");
				data = parseMultiPartFormData(req);
				UploadPhotoBean photoBean = null;

                String dataToBeUploaded = getDataToBeUploaded();
                String action = UploadConstants.UPLOAD_ACTION;
                PhotoBeanManager manager = getBeanManager(dataToBeUploaded, action, req);
                
                logger.debug("Updating bean...");
                manager.updateBean(data, errors, true);
                photoBean = (UploadPhotoBean)manager.getBean();
                req.setAttribute(UploadConstants.UPLOAD_PHOTO_BEAN, photoBean);

                if (errors.isEmpty()) {                
					logger.debug("Bean updated " + photoBean);
					try {
                        AbstractServlet.updateCitiesListForState(photoBean);
						Photo photo = createPhoto(photoBean, user);
						if (photo.getRealImage().getImageSize() > MAX_PHOTO_SIZE) {
							logger.debug("photo with size = " + photo.getRealImage().getImageSize());
							errors.add(UploadConstants.INVALID_FILE_SIZE);
                            updatePhotoBean(photoBean);
						} else {
							logger.debug("Calling addPhotoToDatabase " + photo);
                            UploadPhoto dao = new UploadPhoto();
                            dao.uploadPhoto(photo);
							logger.debug("Photo added to database");

							createCookies(photoBean, res);
                            
							// next page
							nextPage = getSuccessPage();

							req.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
                            session.setAttribute(ServletConstants.PHOTOS_LIST, null);

                            logInfo(user, req);
						}
					} catch (ParseException e1) {
						logger.debug("ParseException.....");
						errors.add(UploadPhotoConstants.INVALID_DATE);
                        updatePhotoBean(photoBean);                    
					} catch (InvalidFileException e1) {
						logger.debug("InvalidFileException.....");
						errors.add(UploadConstants.INVALID_FILE);
                        updatePhotoBean(photoBean);                    
					} catch (SQLException e) {
						logger.debug("SQLException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
                        updatePhotoBean(photoBean);                    
					} catch (ImageProcessingException e) {
						logger.debug("ImageProcessingException .....", e);
						errors.add(UploadConstants.INVALID_FILE);
                        updatePhotoBean(photoBean);                    
					}
				} else {
                    logger.debug("Error updating bean...");
                    updatePhotoBean(photoBean);                    
                }
			} catch (ServletException e) {
				logger.debug("ServletException.....", e);
			} catch (IOException e) {
				logger.debug("IOException.....", e);
			} catch (FileUploadException e) {
				logger.debug("FileUploadException.....", e);
				errors.add(UploadConstants.INVALID_FILE);
			} catch (DatabaseDownException e) {
                logger.debug("DatabaseDownException.....", e);
                errors.add(ServletConstants.DATABASE_ERROR);
            }
			if (!errors.isEmpty()) {
				nextPage = getNextPage();
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

    private void updatePhotoBean(UploadPhotoBean photoBean) throws DatabaseDownException {
        // 2. now, update the lists of family and states
        // put family list on bean
        List familyList = ServletUtil.familyDataAsLabelValueBean(FamilyModel.getFamilyList());
        photoBean.setFamilyList(familyList);

        // put states on bean
        List statesList = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
        photoBean.setStatesList(statesList);

        // 3. retrieve list of species for the selected family
        AbstractServlet.updateSpeciesListForFamily(photoBean);
        AbstractServlet.updateCitiesListForState(photoBean);
    }

    private void logInfo(User user, HttpServletRequest req) {
        loggerActions.info("User " + user.getLogin() + " from IP " + req.getRemoteAddr() + " has uploaded one photo.");
    }

    private void createCookies(UploadPhotoBean bean, HttpServletResponse res) {
        // creating a cookie on user's machine
        logger.info("Creating cookie on user's machine...");
        Cookie cameraCookie = new Cookie("camera", bean.getCamera());
        cameraCookie.setMaxAge(60 * 60 * 24 * 7);
        res.addCookie(cameraCookie);
        
        Cookie lensCookie = new Cookie("lens", bean.getLens());
        lensCookie.setMaxAge(60 * 60 * 24 * 7);
        res.addCookie(lensCookie);
        
        Cookie filmCookie = new Cookie("film", bean.getFilm());
        filmCookie.setMaxAge(60 * 60 * 24 * 7);
        res.addCookie(filmCookie);
    }

    @Override
    protected String getDataToBeUploaded() {
        return UploadBeanManagerFactory.PHOTO;
    }
    
	/**
	 * Retrieve the next page to go
	 * 
	 * @return the next page to go
	 */
	protected String getNextPage() {
		return ServletConstants.UPLOAD_PAGE;
	}

	protected String getSuccessPage() {
		return ServletConstants.UPLOAD_SUCCESS_PAGE;
	}

	/**
	 * @param data
	 * @return
	 */
	protected Photo createPhoto(UploadPhotoBean bean, User user) throws ParseException, InvalidFileException {
		Photo photo = new Photo();
		photo.setUser(user);
		photo.setSpecie(createSpecie(bean.getSelectedSpecieId(), bean.getSelectedFamilyId()));

		photo.setAge(AgeModel.getAge(Integer.parseInt(bean.getSelectedAgeId())));
		photo.setSex(SexModel.getSex(Integer.parseInt(bean.getSelectedSexId())));
		photo.setCamera(bean.getCamera());
		photo.setLens(bean.getLens());
		photo.setFilm(bean.getFilm());
        photo.setFstop(bean.getFstop());
        photo.setShutterSpeed(bean.getShutterSpeed());
        photo.setIso(bean.getIso());
        photo.setZoom(bean.getZoom());
        photo.setFlash(bean.isFlash());
		photo.setLocation(bean.getLocation());
		photo.setCity(CityModel.getCity(bean.getCitiesList(), bean.getSelectedCityId()));
		photo.setDate(createDate(bean.getDate()));
		photo.getRealImage().setImageSize(Integer.parseInt(bean.getFileSize()));
		try {
			photo.getRealImage().setImage(bean.getFileItem().getInputStream());
		} catch (IOException e) {
			photo.getRealImage().setImage(null);
			throw new InvalidFileException();
		}
		photo.setComment(bean.getComment());
		return photo;
	}
}
