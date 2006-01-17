/*
 * Created on 14/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.photo;

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

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.PhotoModel;
import net.indrix.servlets.PaginationController;
import net.indrix.servlets.ServletConstants;
import net.indrix.vo.User;

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

        String familyIdStr = (String) req.getParameter(ServletConstants.FAMILY_ID);
        String action = req.getParameter(ServletConstants.ACTION);
        logger.debug("SearchPhotosByFamilyServlet.doGet retrieved action " + action);
        int familyId = -1;
        if ((familyIdStr != null) && (!familyIdStr.equals("")) || (action != null)){
            PhotoModel model = new PhotoModel ();
            try {
                List list = null;
                PaginationController controller = getPaginationController(session);           
                if ((action == null) || (action.equals(ServletConstants.BEGIN))){
                    familyId = Integer.parseInt(familyIdStr);
                    list = controller.doAction(action, model.retrievePhotosForFamily(familyId));
                } else {
                    list = controller.doAction(action);
                }
                logger.debug("List of photos retrieved...");
                logger.debug("Putting list of photos in session");
                session.setAttribute(ServletConstants.PHOTOS_LIST, list);
                session.setAttribute(ServletConstants.SERVLET_TO_CALL, "/servlet/searchPhotosByFamily");
                nextPage = ServletConstants.ALL_PHOTOS_PAGE;
                
                if (user != null) {
                    loggerActions.info("User " + user.getLogin() + " has selected photos by family.");
                } else {
                    loggerActions.info("Anonymous has selected all photos by family.");
                }
                
            } catch (DatabaseDownException e) {
                logger.debug("DatabaseDownException.....");
                errors.add(ServletConstants.DATABASE_ERROR);
            } catch (SQLException e) {
                logger.debug("SQLException.....", e);
                errors.add(ServletConstants.DATABASE_ERROR);
            }
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
