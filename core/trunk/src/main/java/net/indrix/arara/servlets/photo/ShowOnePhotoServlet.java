/*
 * Created on 16/06/2005
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
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.PhotoPaginationController;
import net.indrix.arara.servlets.pagination.PhotoRecentPaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class ShowOnePhotoServlet extends AbstractSearchPhotosServlet {
    static Logger logger = Logger.getLogger("net.indrix.aves");

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        List<String> errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        
        String photoId = req.getParameter("photoId");
        String identificationStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String action = req.getParameter(ServletConstants.ACTION);       
        if (nextPage == null){
            nextPage = ServletConstants.FRAME_PAGE;
        }
        
        Photo photo = null;
        
        List list = null;
        list = (List) session.getAttribute(ServletConstants.PHOTOS_LIST);
        if (action == null){
            // there is no action. Retrieve all photos, ordered by date
            logger.debug("There is no action. Retrieve all photos, ordered by date");
            try {
                photo = treatPhotoFromEmailLink(errors, photoId, req, session, ServletConstants.BEGIN);
                if (photo != null){
                    session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
                } else {
                    logger.debug("Photo does not exist in DB anymore");
                    nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;                
                }
            } catch (DatabaseDownException e) {
                logger.debug("Photo does not exist in DB anymore");
                nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;                
            } catch (SQLException e) {
                logger.debug("Photo does not exist in DB anymore");
                nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;                
            }
        } else {
            logger.debug("Action = " + action);
            if (list != null){
                logger.debug("List of photos found...");
                Iterator it = list.iterator();
                int id = Integer.parseInt(photoId);
                boolean found = false;
                boolean error = false;
                photo = null;
                Photo previousPhoto = null;
                do{
                    int count = 0;
                    while (it.hasNext() && (!found)) {
                        photo = (Photo) it.next();
                        logger.debug("iteracting over photo " + photo.getId());
                        count++;
                        if (photo.getId() == id) {
                            if (ServletConstants.NEXT.equals(action)){
                                logger.debug("action = NEXT");
                                if (it.hasNext()){
                                    photo = (Photo) it.next();
                                    logger.debug("Retrieving next photo: " + photo.getId());
                                    count++;
                                } else {
                                    // need to load next page
                                    logger.debug("Reached end of page. Trying next page...");
                                    list = getList(session, action);    
                                    if (list == null){
                                        photo = getPhotoFromDatabase(errors, photoId);
                                        if (!errors.isEmpty() || photo == null) {
                                            logger.debug("Photo does not exist in DB anymore");
                                            nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
                                        }
                                        error = true;
                                    } else {
                                        session.setAttribute(ServletConstants.PHOTOS_LIST, list);
                                        
                                        it = list.iterator();
                                        photo = (Photo)it.next();
                                        count = 1;
                                    }
                                }
                            } else {
                                if (ServletConstants.PREVIOUS.equals(action)){
                                    logger.debug("action = PREVIOUS");
                                    if (previousPhoto != null){
                                        photo = previousPhoto;
                                        logger.debug("Retrieving previous photo: " + photo.getId());
                                        count--;
                                    } else {
                                        // need to load previous page
                                        logger.debug("Reached begin of page. Trying previous page...");
                                        list = getList(session, action);    
                                        if (list == null){
                                            photo = getPhotoFromDatabase(errors, photoId);
                                            if (!errors.isEmpty() || photo == null) {
                                                logger.debug("Photo does not exist in DB anymore");
                                                nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
                                            }
                                            error = true;
                                        } else {
                                            session.setAttribute(ServletConstants.PHOTOS_LIST, list);
                                            
                                            it = list.iterator();
                                            count = 0;
                                            while (it.hasNext()){
                                                count++;
                                                photo = (Photo)it.next();
                                            }
                                        }                                    
                                    }
                                }
                            }
                            logger.debug("Photo found !!!! ");
                            found = true;
                            PhotoModel model = new PhotoModel();
                            try {
                                // retrieve photo again, since it could be changed by other user.
                                photo = model.retrieve(photo.getId());
                                retrieveCommentsForPhoto(model, photo);
                            } catch (DatabaseDownException e) {
                                logger.debug("DatabaseDownException.....", e);
                                errors.add(ServletConstants.DATABASE_ERROR);
                            } catch (SQLException e) {
                                logger.debug("SQLException.....", e);
                                errors.add(ServletConstants.DATABASE_ERROR);
                            }
                        }
                        previousPhoto = photo;
                    }
                    if (count > 1 || hasPreviousPage(session, action)){
                        // there are previous photos
                        req.setAttribute("hasPrevious", true);
                    }
                    if (count < list.size() || hasNextPage(session, action)){
                        // there are more photos
                        req.setAttribute("hasNext", true);                
                    }
                    if (!found) {
                        photo = getPhotoFromDatabase(errors, photoId);
                        if (!errors.isEmpty() || photo == null) {
                            logger.debug("Photo does not exist in DB anymore");
                            nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
                        }
                        error = true;
                    }
                } while (!found && !error);
                session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);               
            }
        }

        req.setAttribute(ServletConstants.IDENTIFICATION_KEY,identificationStr);
        req.setAttribute(ServletConstants.VIEW_MODE_KEY, "viewMode");

        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, "/jsp/photo/search/doShowOnePhoto.jsp");
        req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, getServletToCall());

        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        dispatcher.forward(req, res);

    }

    private Photo treatPhotoFromEmailLink(List <String>errors, String photoId, HttpServletRequest req, 
            HttpSession session, String begin) throws DatabaseDownException, SQLException {
        Photo photo = null;
        logger.debug("Looking for photo inside database...");
        photo = getPhotoFromDatabase(errors, photoId);
        
        if (photo != null){
            logger.debug("Retrieving controller...");
            PhotoRecentPaginationController controller = (PhotoRecentPaginationController) getPaginationController(
                    session, false, getPaginationConstant(), ServletConstants.BEGIN);
            List list = null;
            try {
                list = controller.doAction(ServletConstants.BEGIN);
            } catch (InvalidControllerException e) {
                return photo;
            }
            
            boolean found = false;
            
            int id = Integer.parseInt(photoId);
            int count = 0;
            Photo tempPhoto = null;
            
            logger.debug("Iterating over list, with size = " + list.size());
            while (!found){
                Iterator it = list.iterator();

                while (it.hasNext() && !found){
                    tempPhoto = (Photo)it.next();
                    count++;
                    if (tempPhoto.getId() == id){
                        logger.debug("Photo found inside list...");
                        // photo found.
                        if (count > 1 || hasPreviousPage(session, ServletConstants.PREVIOUS)){
                            // there are previous photos
                            logger.debug("Setting hasPrevious into request...");
                            req.setAttribute("hasPrevious", true);
                        }
                        if (count < list.size() || hasNextPage(session, ServletConstants.NEXT)){
                            // there are more photos
                            logger.debug("Setting hasNext into request...");
                            req.setAttribute("hasNext", true);                
                        }
                        
                        session.setAttribute(ServletConstants.PHOTOS_LIST, list);
                        found = true;
                    }
                }
                if (!found){
                    try {
                        list = controller.doAction(ServletConstants.NEXT);
                    } catch (InvalidControllerException e) {
                        return photo;
                    }
                }
            }
        }
        return photo;
        
    }

    /**
     * This method retrieves the photo given by the photoId parameter, from the database.
     * 
     * @param errors List that keeps errors
     * @param photoId THe id of the photo to be retrieved
     * 
     * @return The <code>Photo</code> object
     */
    private Photo getPhotoFromDatabase(List<String> errors, String photoId) {
        // retrieve from database
        PhotoModel model = new PhotoModel();
        Photo photo = null;
        try {
            photo = model.retrieve(Integer.parseInt(photoId));
            if (photo != null) {
                retrieveCommentsForPhoto(model, photo);
            }
            logger.debug("Photo retrieved = " + photo);
        } catch (NumberFormatException e) {
            logger.error("Could not parse photoId " + photoId);
            errors.add(ServletConstants.DATABASE_ERROR);
        } catch (DatabaseDownException e) {
            logger.debug("DatabaseDownException.....");
            errors.add(ServletConstants.DATABASE_ERROR);
        } catch (SQLException e) {
            logger.debug("SQLException.....", e);
            errors.add(ServletConstants.DATABASE_ERROR);
        }
        return photo;
    }

    /**
     * This method retrieves the comments for a given photo
     * 
     * @param model
     * @param photo
     * @throws DatabaseDownException
     * @throws SQLException
     */
    private void retrieveCommentsForPhoto(PhotoModel model, Photo photo)
            throws DatabaseDownException, SQLException {
        // now retrieve the comments
        List comments = model.retrieveCommentsForPhoto(photo);
        photo.setComments(comments);
        if ((comments == null) || (comments.isEmpty())) {
            logger.debug("There is no comment for photo " + photo);
        } else {
            logger.debug(comments.size() + " comments found for photo " + photo);
        }

        // now retrieve the identifications
        List ident = model.retrieveIdentificationsForPhoto(photo);
        photo.setIdentifications(ident);
        if ((ident == null) || (ident.isEmpty())) {
            logger.debug("There is no ident for photo " + photo);
        } else {
            logger.debug(comments.size() + " ident found for photo " + photo);
        }
    }

    private boolean hasNextPage(HttpSession session, String action) {
        logger.debug("Retrieving controller...");
        PhotoPaginationController controller = (PhotoPaginationController) getPaginationController(
                session, false, getPaginationConstant(), action);
        return controller.hasNext();
    }

    private boolean hasPreviousPage(HttpSession session, String action) {
        logger.debug("Retrieving controller...");
        PhotoPaginationController controller = (PhotoPaginationController) getPaginationController(
                session, false, getPaginationConstant(), action);
        return controller.hasPrevious();
    }

    
    private List getList(HttpSession session, String action){
        List list = null;
        logger.debug("Retrieving controller...");
        PhotoPaginationController controller = (PhotoPaginationController) getPaginationController(
                session, false, getPaginationConstant(), action);
        try {
            logger.debug("Controller retrieved... Calling doAction...");
            list = controller.doAction(action);
            logger.debug("List retrieved. Size = " + list.size());
        } catch (InvalidControllerException e) {
            try {
                list = controller.doAction(ServletConstants.BEGIN);
            } catch (InvalidControllerException e1) {
                // this should never happend
                logger.fatal("InvalidControllerException when doing BEGIN action...", e1);
                list = null;
            }
        }
        return list;
    }

    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_RECENT;
    }

    @Override
    protected String getServletToCall() {
        return "/servlet/showOnePhoto";
    }

}
