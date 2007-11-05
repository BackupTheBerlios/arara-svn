/*
 * Created on 04/02/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class InitUploadPhotoForIdentificationServlet extends AbstractServlet {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		logger.debug("Entering method...");
		String nextPage = null;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (user == null) {
			logger.debug("User is NOT logged...");
			// The method userNotLogged takes care of user not logged trying to
			// access options for
			// logged users
			nextPage = userNotLogged(req, res);
		} else {
			logger.debug("Retrieving states...");
			// put states on request
			List list = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());

			// reset upload data bean
            UploadPhotoBean uploadBean = new UploadPhotoBean();
			uploadBean.setStatesList(list);
            InitUploadPhotoServlet.updateBeanWithCookies(uploadBean, req);
            
            req.setAttribute(UploadPhotoConstants.UPLOAD_PHOTO_BEAN, uploadBean);

			nextPage = ServletConstants.UPLOAD_IDENTIFICATION_PAGE;
		}
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		dispatcher = context.getRequestDispatcher(nextPage);

		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);
	}


}
