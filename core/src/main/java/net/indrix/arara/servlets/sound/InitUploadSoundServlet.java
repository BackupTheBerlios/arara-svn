/*
 * Created on 03/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.indrix.bean.UploadSoundBean;
import net.indrix.servlets.ServletConstants;
import net.indrix.servlets.UploadConstants;
import net.indrix.servlets.photo.RetrieveFamiliesServlet;
import net.indrix.vo.User;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitUploadSoundServlet extends RetrieveFamiliesServlet {
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
            super.doGet(req, res);
		}
	}

	/**
	 * @return
	 */
	protected String getNextPage() {
		return ServletConstants.UPLOAD_SOUND_PAGE;
	}

    /**
     * This method retrieves or creates a bean, set the list in it and put the bean to the session
     * 
     * @param session Session to store the bean
     * @param list The list retrieved from database
     */
    protected void handleListOfFamilies(HttpSession session, List list) {
        UploadSoundBean uploadBean = (UploadSoundBean)session.getAttribute(UploadSoundConstants.UPLOAD_SOUND_BEAN);
        if (uploadBean == null){
            uploadBean = new UploadSoundBean();
        }
        uploadBean.setFamilyList(list);
        
        // add bean to session
        session.setAttribute(UploadConstants.UPLOAD_SOUND_BEAN, uploadBean);
    }

}
