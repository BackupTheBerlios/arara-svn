/*
 * Created on 09/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.photo.upload;

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

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.AgeModel;
import net.indrix.model.PhotoModel;
import net.indrix.model.SexModel;
import net.indrix.servlets.ServletConstants;
import net.indrix.vo.Photo;
import net.indrix.vo.User;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EditPhotoServlet extends AbstractUploadPhotoServlet {
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

	public void init() {
		logger.debug("Initializing EditPhotoServlet...");
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
                try {
                    Photo photo = (Photo)session.getAttribute(ServletConstants.CURRENT_PHOTO);
                    updatePhoto(data, photo);
                    logger.debug("Calling updatePhotoIntoDatabase " + photo);
                    updatePhotoIntoDatabase(photo);
                    logger.debug("Photo updated into database");
                    // next page
                    nextPage = ServletConstants.EDIT_SUCCESS_PAGE;

                    session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
                } catch (ParseException e1) {
                    logger.debug("ParseException.....");
                    errors.add(UploadPhotoConstants.INVALID_DATE);
                } catch (DatabaseDownException e) {
                    logger.debug("DatabaseDownException.....");
                    errors.add(ServletConstants.DATABASE_ERROR);
                } catch (SQLException e) {
                    logger.debug("SQLException.....", e);
                    errors.add(ServletConstants.DATABASE_ERROR);
                } 
				
			} catch (ServletException e) {
				logger.debug("ServletException.....");
			} catch (IOException e) {
				logger.debug("IOException.....");
			} catch (FileUploadException e) {
				// does nothing, since no file is uploaded for edit use case
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

    /** 
     * This method updates the photo into database
     * 
     * @param photo The photo to be updated 
     */
    private void updatePhotoIntoDatabase(Photo photo)
        throws DatabaseDownException, SQLException {
        PhotoModel model = new PhotoModel();
        model.update(photo);
    }

	/**
     * This method creates a Photo object, with the data sent by the user in the form
     * 
	 * @param data The map with data sent by user
     * 
	 * @return A new Photo object
	 */
	private void updatePhoto(Map data, Photo photo)
		throws ParseException {
        photo.getSpecie().setId(getSpecieId((String) data.get(ServletConstants.SPECIE_ID)));
        photo.setAge(AgeModel.getAge(Integer.parseInt((String) data.get(ServletConstants.AGE_ID))));
        photo.setSex(SexModel.getSex(Integer.parseInt((String) data.get(ServletConstants.SEX_ID))));       
		photo.setCamera((String) data.get(ServletConstants.CAMERA));
		photo.setLens((String) data.get(ServletConstants.LENS));
		photo.setFilm((String) data.get(ServletConstants.FILM));
		photo.setLocation((String) data.get(ServletConstants.LOCATION));
		photo.setDate(createDate((String) data.get(ServletConstants.DATE)));
        photo.setComment((String) data.get(ServletConstants.COMMENT));       
	}

	/**
	 * @param string
	 * @return
	 */
	private int getSpecieId(String string) {
		return Integer.parseInt(string);
	}

}
