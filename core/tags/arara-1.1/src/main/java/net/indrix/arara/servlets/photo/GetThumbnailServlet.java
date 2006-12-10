/*
 * Created on 16/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GetThumbnailServlet extends HttpServlet {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String photoId = req.getParameter("photoId");

		logger.debug("GetThumbnailServlet.doGet called for photo " + photoId);
		PhotoModel model = new PhotoModel();
		try {
			Photo photo = null;
			photo = model.retrieve(Integer.parseInt(photoId));
			logger.debug("Photo retrieved..." + photo);
			logger.debug("writing to outputStream...");
			res.setContentType("image/jpeg");
			InputStream input = photo.getSmallImage().getImage();
			OutputStream output = res.getOutputStream();
            byte buffer[] = new byte[512];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
			input.close();
			output.close();
		} catch (DatabaseDownException e) {
			logger.debug("DatabaseDownException.....", e);
		} catch (SQLException e) {
			logger.debug("SQLException.....", e);
		}
	}
}