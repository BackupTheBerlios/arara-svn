/*
 * Created on 14/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.PhotoPaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
import net.indrix.arara.vo.User;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchPhotosByFamilyServlet extends AbstractSearchPhotosServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);

		String familyIdStr = (String) req.getParameter(ServletConstants.ID);
		String action = req.getParameter(ServletConstants.ACTION);
		logger.debug("SearchPhotosByFamilyServlet.doGet retrieved action "
				+ action);
		int familyId = -1;
		if ((familyIdStr != null) && (!familyIdStr.equals(""))
				|| (action != null)) {
			PhotoModel model = new PhotoModel();
			List list = null;
			PhotoPaginationController controller = (PhotoPaginationController) getPaginationController(
					session, false, PAGINATION_FOR_ALL_FAMILY);
			familyId = Integer.parseInt(familyIdStr);
			controller.setId(familyId);
			try {
				list = controller.doAction(action);
			} catch (InvalidControllerException e) {
				try {
					list = controller.doAction(ServletConstants.BEGIN);
				} catch (InvalidControllerException e1) {
					// this should never happend
					logger
							.fatal(
									"InvalidControllerException when doing BEGIN action...",
									e1);
				}
			}
			logger.debug("List of photos retrieved...");
			logger.debug("Putting list of photos in session");
			session.setAttribute(ServletConstants.PHOTOS_LIST, list);
			session.setAttribute(ServletConstants.SERVLET_TO_CALL,
					"/servlet/searchPhotosByFamily");
			nextPage = ServletConstants.ALL_PHOTOS_PAGE;

			if (user != null) {
				loggerActions.info("User " + user.getLogin()
						+ " has selected photos by family.");
			} else {
				String ip = req.getRemoteAddr();
				Locale locale = req.getLocale();
				loggerActions.info("Anonymous " + " from IP " + ip + "("
						+ locale + ") has selected all photos by family.");
			}

			// adding the family id to request, so it can be sent back by view
			req.setAttribute(ServletConstants.ID, familyIdStr);

		} else {
			errors.add(ServletConstants.SELECT_FAMILY_ERROR);
		}

		if (!errors.isEmpty()) {
			logger.debug("errors is not null.");
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.PHOTO_BY_FAMILY_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);
	}

}
