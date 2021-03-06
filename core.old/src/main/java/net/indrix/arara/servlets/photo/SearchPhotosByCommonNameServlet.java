/*
 * Created on 06/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchPhotosByCommonNameServlet extends AbstractSearchPhotosServlet {

    /**
     * 
     */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);

		String commonNameIdStr = (String) req.getParameter(ServletConstants.COMMON_NAME_ID);
		int commonNameId = -1;
		if (commonNameIdStr != null) {
			commonNameId = Integer.parseInt(commonNameIdStr);
		}

		String action = req.getParameter(ServletConstants.ACTION);

		PhotoModel model = new PhotoModel();
		try {
			List list = null;
			PaginationController controller = getPaginationController(session);
			if ((action == null) || (action.equals(ServletConstants.BEGIN))) {
				list = controller.doAction(action, model.retrievePhotosForCommonName(commonNameId));
			} else {
				list = controller.doAction(action);
			}

			Iterator it = list.iterator();
			while (it.hasNext()) {
				Photo photo = (Photo) it.next();
				logger.debug("Photo " + photo);
			}
			logger.debug("List of photos retrieved...");
			logger.debug("Putting list of photos in session");
			session.setAttribute(ServletConstants.PHOTOS_LIST, list);
			session.setAttribute(ServletConstants.SERVLET_TO_CALL, "/servlet/searchPhotosByUser");
			nextPage = ServletConstants.ALL_PHOTOS_PAGE;
            
            if (user != null) {
                loggerActions.info("User " + user.getLogin() + " has selected photos by common name.");
            } else {
                loggerActions.info("Anonymous has selected all photos by common name.");
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
