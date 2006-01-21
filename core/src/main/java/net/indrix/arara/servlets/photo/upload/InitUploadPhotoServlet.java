/*
 * Created on 03/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.photo.RetrieveFamiliesServlet;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitUploadPhotoServlet extends RetrieveFamiliesServlet {
    static Logger logger = Logger.getLogger("net.indrix.aves");
        
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (user == null) {
			logger.debug("errors is not null.");
			errors.add(ServletConstants.USER_NOT_LOGGED);
			// put errors in request 
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.LOGIN_PAGE;
                      
            RequestDispatcher dispatcher = null;
            ServletContext context = this.getServletContext();
            dispatcher = context.getRequestDispatcher(nextPage);
            logger.debug("Dispatching to " + nextPage);
            dispatcher.forward(req, res);
            
		} else {
            // put states on request
            List list = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
            req.setAttribute(ServletConstants.STATES_KEY, list);
                        
            super.doGet(req, res);
		}
	}

	/**
	 * @return
	 */
	protected String getNextPage() {
		return ServletConstants.UPLOAD_PAGE;
	}

}
