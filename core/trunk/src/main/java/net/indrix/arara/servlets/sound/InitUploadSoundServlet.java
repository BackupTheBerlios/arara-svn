/*
 * Created on 03/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.common.RetrieveFamiliesServlet;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class InitUploadSoundServlet extends RetrieveFamiliesServlet {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		String nextPage = null;
		List <String>errors = new ArrayList<String>();
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
			// reset upload data bean
			UploadBean uploadBean = new UploadSoundBean();
			req.setAttribute(UploadPhotoConstants.UPLOAD_SOUND_BEAN, uploadBean);

			super.doGet(req, res);
		}
	}

	/**
	 * @return
	 */
	protected String getNextPage(HttpServletRequest req) {
		return ServletConstants.UPLOAD_SOUND_PAGE;
	}

	/**
	 * By default, user shall not be validated. However, for upload it shall be
	 * 
	 * @return true
	 */
	protected boolean testeUser() {
		return true;
	}

	/**
	 * This method retrieves or creates a bean, set the list in it and put the
	 * bean to the session
	 * 
	 * @param session
	 *            Session to store the bean
	 * @param list
	 *            The list retrieved from database
	 */
	protected void handleListOfFamilies(HttpServletRequest req, List list) {
		UploadSoundBean uploadBean = (UploadSoundBean) req.getAttribute(UploadSoundConstants.UPLOAD_SOUND_BEAN);
		if (uploadBean == null) {
			uploadBean = new UploadSoundBean();
		}
		uploadBean.setFamilyList(list);

        // put states on request
        List statesList = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
        uploadBean.setStatesList(statesList);
        
		// add bean to session
		req.setAttribute(UploadConstants.UPLOAD_SOUND_BEAN, uploadBean);
	}

}
