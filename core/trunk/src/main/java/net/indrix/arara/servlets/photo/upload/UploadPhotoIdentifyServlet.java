/*
 * Created on 13/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.model.UploadPhoto;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.common.UploadBeanManagerFactory;
import net.indrix.arara.servlets.photo.exceptions.InvalidFileException;
import net.indrix.arara.vo.Age;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Sex;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class UploadPhotoIdentifyServlet extends UploadPhotoServlet {
	/**
	 * Init method
	 */
	public void init() {
		logger.debug("Initializing UploadPhotoIdentifyServlet...");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		UploadPhotoBean uploadBean;
        uploadBean = (UploadPhotoBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
		if (uploadBean == null) {
			uploadBean = new UploadPhotoBean();
			// add bean to session
			session.setAttribute(UploadConstants.UPLOAD_PHOTO_BEAN, uploadBean);
		}
		super.doPost(req, res);
	}

	@Override
    protected String getDataToBeUploaded() {
        return UploadBeanManagerFactory.PHOTO_FOR_IDENTIFICATION;
    }

	/**
	 * Retrieve the next page to go
	 * 
	 * @return the next page to go
	 */
	protected String getNextPage() {
		return ServletConstants.UPLOAD_IDENTIFICATION_PAGE;
	}

	/**
	 * @param photo
	 */
	protected void addPhotoToDatabase(Photo photo)
			throws DatabaseDownException, SQLException,
			ImageProcessingException {
		UploadPhoto model = new UploadPhoto();
		model.uploadPhotoForIdentification(photo);
	}

	/**
	 * @param data
	 * @return
	 */
	protected Photo createPhoto(UploadPhotoBean bean, User user)
			throws ParseException, InvalidFileException {
		Photo photo = new Photo();
		photo.setUser(user);
		photo.setSpecie(new Specie());
		photo.setAge(new Age());
		photo.setSex(new Sex());

		photo.setCamera(bean.getCamera());
		photo.setLens(bean.getLens());
		photo.setFilm(bean.getFilm());
		photo.setLocation(bean.getLocation());
		List list = bean.getCitiesList();
		String id = bean.getSelectedCityId();
		photo.setCity(CityModel.getCity(list, id));
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

	/**
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateBirdData(Map data, UploadPhotoBean bean, List errors) {
	}

	protected String getSuccessPage() {
		return ServletConstants.UPLOAD_SUCCESS_PAGE;
	}
}
