/*
 * Created on 09/09/2005
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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.Age;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Sex;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
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
		List <String>errors = new ArrayList<String>();
		HttpSession session = req.getSession();

		String identStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
		boolean identification = Boolean.valueOf(identStr).booleanValue();

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
                    PhotoModel model = new PhotoModel();
                    String photoId = (String)data.get(ServletConstants.PHOTO_ID);
                    Photo photo = model.retrieve(Integer.parseInt(photoId));
                    
					updatePhoto(data, photo, user, identification);
					logger.debug("Calling updatePhotoIntoDatabase " + photo);
					updatePhotoIntoDatabase(photo);
					logger.debug("Photo updated into database");
					// next page
					nextPage = ServletConstants.EDIT_SUCCESS_PAGE;

					session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
                    
                    //session.setAttribute(ServletConstants.PHOTOS_LIST, null);                    
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
                logger.debug("IOException.....", e);
                errors.add(ServletConstants.DATABASE_ERROR);
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
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	/**
	 * This method updates the photo into database
	 * 
	 * @param photo
	 *            The photo to be updated
	 * @throws IOException 
	 */
	private void updatePhotoIntoDatabase(Photo photo)
			throws DatabaseDownException, SQLException, IOException {
		PhotoModel model = new PhotoModel();
		model.update(photo);
	}

	/**
	 * This method creates a Photo object, with the data sent by the user in the
	 * form
	 * 
	 * @param data
	 *            The map with data sent by user
	 * 
	 * @return A new Photo object
	 */
	private void updatePhoto(Map data, Photo photo, User user, boolean identification)
			throws ParseException, NumberFormatException,
			DatabaseDownException, SQLException {

		if (!identification) {
			photo.getSpecie().getFamily().setId(
					getId((String) data.get(ServletConstants.FAMILY_ID)));
			photo.getSpecie().setId(
					getId((String) data.get(ServletConstants.SPECIE_ID)));
			photo.setAge(AgeModel.getAge(Integer.parseInt((String) data
					.get(ServletConstants.AGE_ID))));
			photo.setSex(SexModel.getSex(Integer.parseInt((String) data
					.get(ServletConstants.SEX_ID))));
		} else {
            // while user was editing photo, one moderator might have finished the identification.
            // Verify if the specie data in the photo is valid.
            if (photo.getSpecie() == null){
                photo.setSpecie(new Specie());
                photo.setAge(new Age());
                photo.setSex(new Sex());                
            } else {
                if (photo.getAge() == null){
                    photo.setAge(new Age());
                }
                if (photo.getSex() == null){
                    photo.setSex(new Sex());
                }
            }
		}

        // verify whether the editing is being performed by the photo author or a moderator 
        if (user.getId() == photo.getUser().getId()){
            photo.setCamera((String) data.get(ServletConstants.CAMERA));
            photo.setLens((String) data.get(ServletConstants.LENS));
            photo.setFilm((String) data.get(ServletConstants.FILM));
            photo.setFstop((String) data.get(ServletConstants.F_STOP));
            photo.setShutterSpeed((String) data.get(ServletConstants.SHUTTER_SPEED));
            photo.setIso((String) data.get(ServletConstants.ISO));
            photo.setZoom((String) data.get(ServletConstants.ZOOM));

            photo.setFlash(data.get(ServletConstants.FLASH) != null ? true : false);            

            photo.setLocation((String) data.get(ServletConstants.LOCATION));
            photo.setCity(getCity((String) data.get(ServletConstants.CITY_ID)));
            photo.setDate(createDate((String) data.get(ServletConstants.DATE)));
            photo.setComment((String) data.get(ServletConstants.COMMENT));            
        }
	}

	/**
	 * @param cityId
	 * @return
	 */
	private City getCity(String cityId) throws NumberFormatException,
			DatabaseDownException, SQLException {
		City city = null;
		CityModel model = new CityModel();
		city = model.getCity(Integer.parseInt(cityId));
		return city;
	}

	/**
	 * @param string
	 * @return
	 */
	private int getId(String string) {
		return Integer.parseInt(string);
	}

    @Override
    protected String getDataToBeUploaded() {
        // TODO Auto-generated method stub
        return null;
    }

}
