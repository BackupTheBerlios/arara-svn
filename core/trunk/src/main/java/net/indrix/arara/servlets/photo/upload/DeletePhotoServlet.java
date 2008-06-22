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
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class DeletePhotoServlet extends HttpServlet {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");
    static Logger loggerActions = Logger .getLogger("net.indrix.actions");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.debug("DeletePhotoServlet.doGet called...");

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
        
		List <String>errors = new ArrayList<String>();
		String photoId = req.getParameter("photoId");
        String ownerId = req.getParameter("userId");
        
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        
        if (user == null) {
            logger.debug("User null.");
            errors.add(ServletConstants.USER_NOT_LOGGED);
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            nextPage = ServletConstants.LOGIN_PAGE;
        } else {
            int photoAuthorId = (ownerId != null) ? Integer.parseInt(ownerId) : -1;
            if (user.getId() == photoAuthorId){
                PhotoModel model = new PhotoModel();
                try {
                    int id = Integer.parseInt(photoId);
                    model.delete(id);
                    logger.debug("Photo deleted..." + photoId);

                    
                    loggerActions.info("User " + user.getLogin() + " from IP " + req.getRemoteAddr() + " has deleted the photo " + photoId);

                    nextPage = ServletConstants.DELETED_PHOTO_PAGE;
                } catch (DatabaseDownException e) {
                    logger.debug("DatabaseDownException.....");
                    errors.add(ServletConstants.DATABASE_ERROR);
                    nextPage = ServletConstants.UPLOAD_PAGE;
                } catch (SQLException e) {
                    logger.debug("SQLException.....", e);
                    errors.add(ServletConstants.DATABASE_ERROR);
                    nextPage = ServletConstants.UPLOAD_PAGE;
                }                            
            } else {
                // user do not have the right to delete the photo
                nextPage = ServletConstants.FRAME_PAGE;
                String pageToShow = ServletConstants.SHOW_MESSAGE_PAGE;
                String messageKey = "operation.denied";
                req.setAttribute(ServletConstants.STRING_MESSAGE_KEY, messageKey);
                req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
            }
        }
		if (!errors.isEmpty()) {
			logger.debug("errors is not null.");
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);			
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);
	}

}
