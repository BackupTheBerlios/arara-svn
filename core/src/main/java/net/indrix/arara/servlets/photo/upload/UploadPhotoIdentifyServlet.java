/*
 * Created on 13/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.photo.upload;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.bean.UploadPhotoBean;
import net.indrix.dao.DatabaseDownException;
import net.indrix.model.UploadPhoto;
import net.indrix.model.exceptions.ImageProcessingException;
import net.indrix.servlets.ServletConstants;
import net.indrix.servlets.UploadConstants;
import net.indrix.servlets.photo.exceptions.InvalidFileException;
import net.indrix.vo.Age;
import net.indrix.vo.Photo;
import net.indrix.vo.Sex;
import net.indrix.vo.Specie;
import net.indrix.vo.User;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
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
        UploadPhotoBean uploadBean = (UploadPhotoBean)session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
        if (uploadBean == null){
            uploadBean = new UploadPhotoBean();
            // add bean to session
            session.setAttribute(UploadConstants.UPLOAD_PHOTO_BEAN, uploadBean);
        }      
        super.doPost(req, res);
	}

    /** 
     * @param photo 
     */
    protected void addPhotoToDatabase(Photo photo)
        throws DatabaseDownException, SQLException, ImageProcessingException {
        UploadPhoto dao = new UploadPhoto();
        dao.uploadPhotoForIdentification(photo);
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
        return ServletConstants.UPLOAD_IDENTIFY_SUCCESS_PAGE;
    }
}
