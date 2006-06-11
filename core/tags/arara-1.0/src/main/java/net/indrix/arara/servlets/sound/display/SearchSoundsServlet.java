/*
 * Created on 09/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound.display;

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

import net.indrix.arara.model.SoundModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
import net.indrix.arara.vo.User;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchSoundsServlet extends AbstractSearchSoundsServlet {
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

		SoundModel model = new SoundModel();
		PaginationController controller = getPaginationController(session, false, PAGINATION_FOR_ALL_SOUNDS);
        List list = null;
        try {
            list = controller.doAction(action);
        } catch (InvalidControllerException e) {
            try {
                list = controller.doAction(ServletConstants.BEGIN);
            } catch (InvalidControllerException e1) {
                // this should never happend
                logger.fatal("InvalidControllerException when doing BEGIN action...", e1);
            }
        }
		logger.debug("List of sounds retrieved...");
		logger.debug("Putting list of sounds in session");
		session.setAttribute(ServletConstants.SOUNDS_LIST, list);
        
		session.setAttribute(ServletConstants.SERVLET_TO_CALL, "/servlet/searchSounds");
		nextPage = ServletConstants.ALL_SOUNDS_PAGE;

		if (user != null) {
			loggerActions.info("User " + user.getLogin() + " has selected all sounds.");
		} else {
            String ip = req.getRemoteAddr();               
            Locale locale = req.getLocale();
            loggerActions.info("Anonymous "+ " from IP " + ip + "(" + locale + ") has selected all sounds.");
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
