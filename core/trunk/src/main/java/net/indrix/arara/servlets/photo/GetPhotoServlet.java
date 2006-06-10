/*
 * Created on 09/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GetPhotoServlet extends HttpServlet {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		logger.debug("GetPhotoServlet.doGet called...");
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		String photoId = req.getParameter("photoId");

		logger.debug("PhotoId = " + photoId);
		Photo photo = getPhotoFromDatabase(errors, photoId);

		logger.debug("Photo retrieved..." + photo);
		logger.debug("writing to outputStream...");
		res.setContentType("image/jpeg");
		InputStream input = photo.getRealImage().getImage();
		OutputStream output = res.getOutputStream();
		int info = 0;
		while ((info = input.read()) != -1) {
			output.write(info);
		}
		output.flush();
	}

	private Photo getPhotoFromDatabase(List errors, String photoId) {
		// retrieve from database
		PhotoModel model = new PhotoModel();
		Photo photo = null;
		try {
			photo = model.retrieve(Integer.parseInt(photoId));
			//                if (photo != null) {
			//                    retrieveCommentsForPhoto(model, photo);
			//                }
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