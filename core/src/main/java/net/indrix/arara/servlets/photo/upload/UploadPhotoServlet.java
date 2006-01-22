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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.model.UploadPhoto;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.photo.exceptions.InvalidFileException;
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
public class UploadPhotoServlet extends AbstractUploadPhotoServlet {
	private static final int MAX_PHOTO_SIZE = 250000;
	private static final String MAX_PHOTO_SIZE_STR = "250Kb";

	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * Init method
	 */
	public void init() {
		logger.debug("Initializing UploadPhotoServlet...");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
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
				data = parseMultiPartFormData(req);
				UploadPhotoBean photoBean =
					(UploadPhotoBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);

				logger.debug("Calling updateBean");
				if (updateBean(data, photoBean, errors)) {
					logger.debug("bean updated " + photoBean);
					try {
						Photo photo = createPhoto(photoBean, user);
						// set upload data to session
						session.setAttribute(UploadPhotoConstants.LAST_UPLOAD_BEAN, photoBean);
						if (photo.getRealImage().getImageSize() > MAX_PHOTO_SIZE) {
							logger.debug(
								"photo with size = " + photo.getRealImage().getImageSize());
							errors.add(UploadConstants.INVALID_FILE_SIZE + MAX_PHOTO_SIZE_STR);
						} else {
							logger.debug("Calling addPhotoToDatabase " + photo);
							addPhotoToDatabase(photo);
							logger.debug("Photo added to database");
							// next page
							nextPage = getSuccessPage();

							session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);

							loggerActions.info(
								"User " + user.getLogin() + " has uploaded one photo.");
						}
					} catch (ParseException e1) {
						logger.debug("ParseException.....");
						errors.add(UploadPhotoConstants.INVALID_DATE);
					} catch (InvalidFileException e1) {
						logger.debug("InvalidFileException.....");
						errors.add(UploadConstants.INVALID_FILE);
					} catch (DatabaseDownException e) {
						logger.debug("DatabaseDownException.....");
						errors.add(ServletConstants.DATABASE_ERROR);
					} catch (SQLException e) {
						logger.debug("SQLException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
					} catch (ImageProcessingException e) {
						logger.debug("ImageProcessingException .....", e);
						errors.add(UploadConstants.INVALID_FILE);
					}
				}
			} catch (ServletException e) {
				logger.debug("ServletException.....");
			} catch (IOException e) {
				logger.debug("IOException.....");
			} catch (FileUploadException e) {
				logger.debug("FileUploadException.....", e);
				errors.add(UploadConstants.INVALID_FILE);
			}
			if (!errors.isEmpty()) {
				nextPage = ServletConstants.UPLOAD_PAGE;
			}
		}

		if (!errors.isEmpty()) {
			// put errors in request 
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
		}
		if (!messages.isEmpty()) {
			logger.debug("messages is not null.");
			// put messages in request 
			req.setAttribute(ServletConstants.MESSAGES_KEY, messages);
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	protected String getSuccessPage() {
		return ServletConstants.UPLOAD_SUCCESS_PAGE;
	}

	/** 
	 * @param photo 
	 */
	protected void addPhotoToDatabase(Photo photo)
		throws DatabaseDownException, SQLException, ImageProcessingException {
		UploadPhoto dao = new UploadPhoto();
		dao.uploadPhoto(photo);
	}

	/**
	 * @param data
	 * @return
	 */
	protected Photo createPhoto(UploadPhotoBean bean, User user)
		throws ParseException, InvalidFileException {
		Photo photo = new Photo();
		photo.setUser(user);
		photo.setSpecie(createSpecie(bean.getSelectedSpecieId(), bean.getSelectedFamilyId()));

		photo.setAge(AgeModel.getAge(Integer.parseInt(bean.getSelectedAgeId())));
		photo.setSex(SexModel.getSex(Integer.parseInt(bean.getSelectedSexId())));
		photo.setCamera(bean.getCamera());
		photo.setLens(bean.getLens());
		photo.setFilm(bean.getFilm());
		photo.setLocation(bean.getLocation());
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
