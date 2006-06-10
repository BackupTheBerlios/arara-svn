/*
 * Created on 09/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.PaginationController;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchPhotosServlet extends AbstractSearchPhotosServlet {
	private static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		doGet(req, res);
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);

		String action = req.getParameter(ServletConstants.ACTION);
		String identificationStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
        boolean identification = new Boolean(identificationStr).booleanValue();

		PhotoModel model = new PhotoModel();
		try {
			PaginationController controller = getPaginationController(session);
			List list = null;
			if (identification) {
				logger.debug("Retrieving photos for identification...");
				list = controller.doAction(action, model.retrievePhotosForIdentification());
				req.setAttribute(ServletConstants.IDENTIFICATION_KEY, identificationStr);
			} else {
				list = controller.doAction(action, model.retrievePhotos());
			}
			logger.debug("List of photos retrieved...");
			logger.debug("Putting list of photos in session");
			session.setAttribute(ServletConstants.PHOTOS_LIST, list);
			session.setAttribute(ServletConstants.SERVLET_TO_CALL, "/servlet/searchPhotos");
			nextPage = ServletConstants.ALL_PHOTOS_PAGE;

			if (user != null) {
				loggerActions.info("User " + user.getLogin() + " has selected all photos.");
			} else {
				loggerActions.info("Anonymous has selected all photos.");
			}
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
