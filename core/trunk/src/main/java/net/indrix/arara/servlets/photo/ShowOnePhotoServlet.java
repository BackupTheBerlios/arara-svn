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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class ShowOnePhotoServlet extends HttpServlet {
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
        
        List list = null;
        list = (List) session.getAttribute(ServletConstants.PHOTOS_LIST);
        if ((photoId == null) || (list == null)) {
            Photo photo = getPhotoFromDatabase(errors, photoId);
            if (photo != null) {
                session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
            } else {
                logger.debug("List of photos not found...");
                nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
            }
        } else {
            logger.debug("List of photos found...");
            Iterator it = list.iterator();
            int id = Integer.parseInt(photoId);
            boolean found = false;
            Photo photo = null;
            Photo previousPhoto = null;
            int count = 0;
            while (it.hasNext() && (!found)) {
                photo = (Photo) it.next();
                count++;
                if (photo.getId() == id) {
                    if (ServletConstants.NEXT.equals(action) && it.hasNext()){
                        photo = (Photo) it.next();
                        count++;
                    } else {
                        if (ServletConstants.PREVIOUS.equals(action) && previousPhoto != null){
                            photo = previousPhoto;
                            count--;
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
            if (count > 1){
                // there are previous photos
                req.setAttribute("hasPrevious", true);
            }
            if (count < list.size()){
                // there are more photos
                req.setAttribute("hasNext", true);                
            }
            if (!found) {
                photo = getPhotoFromDatabase(errors, photoId);
                if (!errors.isEmpty() || photo == null) {
                    logger.debug("Photo does not exist in DB anymore");
                    nextPage = ServletConstants.ONE_PHOTO_PAGE_ERROR;
                }
            }
            session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
        }

        req.setAttribute(ServletConstants.IDENTIFICATION_KEY,identificationStr);
        req.setAttribute(ServletConstants.VIEW_MODE_KEY, "viewMode");

        String pageToShow = "/jsp/photo/search/doShowOnePhoto.jsp";

        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
        req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, "/servlet/showOnePhoto");

        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        dispatcher.forward(req, res);

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

}
