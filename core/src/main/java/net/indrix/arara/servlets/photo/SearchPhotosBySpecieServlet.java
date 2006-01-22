/*
 * Created on 21/06/2005
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

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchPhotosBySpecieServlet extends AbstractSearchPhotosServlet {
    static Logger logger = Logger.getLogger("net.indrix.aves");
        
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        
        logger.debug("SearchPhotosBySpecieServlet.doGet starting...");
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        List errors = new ArrayList();
        List messages = new ArrayList();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);

        String specieIdStr = (String) req.getParameter(ServletConstants.SPECIE_ID);
        int specieId = -1;
        if (specieIdStr != null){
            specieId = Integer.parseInt(specieIdStr);
        }

        String action = req.getParameter(ServletConstants.ACTION);
        logger.debug("SearchPhotosBySpecieServlet.doGet retrieved action " + action);

        PhotoModel model = new PhotoModel();
        try {
            logger.debug("Calling retrieveForSpecie("+ specieId + ")");
            List list = null;
            PaginationController controller = getPaginationController(session);           
            if ((action == null) || (action.equals(ServletConstants.BEGIN))){
                list = controller.doAction(action, model.retrievePhotosForSpecie(specieId));
            } else {
                list = controller.doAction(action);
            }
            
            Iterator it = list.iterator();
            while (it.hasNext()){
                Photo photo = (Photo)it.next();
                logger.debug("Photo " + photo);
            }
            logger.debug("List of photos retrieved...");
            logger.debug("Putting list of photos in session");
            session.setAttribute(ServletConstants.PHOTOS_LIST, list);
            nextPage = ServletConstants.ALL_PHOTOS_PAGE;
            
            if (user != null) {
                loggerActions.info("User " + user.getLogin() + " has selected photos by specie.");
            } else {
                loggerActions.info("Anonymous has selected all photos by specie.");
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
