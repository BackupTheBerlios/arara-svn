/*
 * Created on 25/08/2005
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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.ServletConstants;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class DeletePhotoServlet extends HttpServlet {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.debug("DeletePhotoServlet.doGet called...");

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		String photoId = req.getParameter("photoId");

		PhotoModel model = new PhotoModel();
		try {
			int id = Integer.parseInt(photoId);
			model.delete(id);
			logger.debug("Photo deleted..." + photoId);

			nextPage = ServletConstants.DELETED_PAGE;
		} catch (DatabaseDownException e) {
			logger.debug("DatabaseDownException.....");
			errors.add(ServletConstants.DATABASE_ERROR);
		} catch (SQLException e) {
			logger.debug("SQLException.....", e);
			errors.add(ServletConstants.DATABASE_ERROR);
		}
		if (!errors.isEmpty()) {
			logger.debug("errors is not null.");
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.UPLOAD_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);
	}

}
