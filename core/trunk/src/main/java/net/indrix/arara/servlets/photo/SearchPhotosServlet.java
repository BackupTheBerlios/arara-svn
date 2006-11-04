/*
 * Created on 09/05/2005
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

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
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
		logger.info("SearchPhotosServlet.doGet: entering method...");
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		List <String>errors = new ArrayList<String>();
        
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);

		String action = req.getParameter(ServletConstants.ACTION);       
		String identificationStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        if (nextPage == null){
            nextPage = ServletConstants.FRAME_PAGE;
        }
        
		boolean identification = new Boolean(identificationStr).booleanValue();

		logger.debug("Retrieving controller ...");
		PaginationController controller = getPaginationController(session, identification, getPaginationConstant());
		List list = null;
		if (identification) {
			logger.debug("Retrieving photos for identification...");
			list = getListOfPhotos(action, controller);
			req.setAttribute(ServletConstants.IDENTIFICATION_KEY, identificationStr);
		} else {
			list = getListOfPhotos(action, controller);
		}
		logger.debug("List of photos retrieved...");
		logger.debug("Putting list of photos in session");
		req.setAttribute("identification", identificationStr);
		session.setAttribute(ServletConstants.PHOTOS_LIST, list);
		session.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);
        
        String pageToShow = "/jsp/photo/search/doShowAllPhotos.jsp";

        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
        req.setAttribute(ServletConstants.ACTION, action);
        
		if (user != null) {
			loggerActions.info("User " + user.getLogin() + " has selected all photos.");
		} else {
			String ip = req.getRemoteAddr();
			Locale locale = req.getLocale();
			loggerActions.info("Anonymous " + " from IP " + ip + "(" + locale + ") has selected all photos.");
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

    protected String getServletToCall() {
        return "/servlet/searchPhotos";
    }  

    protected int getPaginationConstant() {
        return PAGINATION_FOR_ALL_PHOTOS;
    }  
    
	private List getListOfPhotos(String action, PaginationController controller) {
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

		return list;
	}
}
