/*
 * Created on 08/10/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

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
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommentPhotoServlet extends HttpServlet {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * Constant for a not null comment
     */
    private static String COMMENT_NOT_NULL = "Comentário não pode ser null";
     
	/**
	 * Init servlet
	 */
	public void init() {
		logger.debug("Initializing CommentPhotoServlet...");
	}

	/**
	 * 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

        logger.debug("CommentPhotoServlet.doPost called...");
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
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
		} else {
			String photoId = req.getParameter(ServletConstants.PHOTO_ID);
			String comment = req.getParameter(ServletConstants.COMMENT);
            logger.debug("PhotoId to have a comment: " + photoId);
            logger.debug("Comment: " + comment);
			if (validateData(comment)) {
				// retrieve current photo
				Photo photo = (Photo) session.getAttribute(ServletConstants.CURRENT_PHOTO);
				if (photo != null) {
					PhotoModel model = new PhotoModel();
					try {
						model.insertComment(photo, user, comment);
                        logger.debug("Comment added to database");
                        
                        try {
                            retrieveCommentsForPhoto(model, photo);
                        } catch (DatabaseDownException e) {
                            logger.debug("DatabaseDownException.....", e);
                            errors.add(ServletConstants.DATABASE_ERROR);
                        } catch (SQLException e) {
                            logger.debug("SQLException.....", e);
                            errors.add(ServletConstants.DATABASE_ERROR);
                        }
                        
						// next page
						nextPage = ServletConstants.ONE_PHOTO_PAGE;
					} catch (DatabaseDownException e) {
						logger.debug("DatabaseDownException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
					} catch (SQLException e) {
						logger.debug("SQLException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
					}
				} else {
					logger.error("Current photo does not exist");
				}
			} else {
                logger.debug("Comment is invalid...");
                errors.add(COMMENT_NOT_NULL);
			}
		}

		if (!errors.isEmpty()) {
            nextPage = ServletConstants.DATABASE_ERROR_PAGE;
			// put errors in request 
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
		}
		if (!messages.isEmpty()) {
			logger.debug("messages is not null.");
			// put messages in request 
			req.setAttribute(ServletConstants.MESSAGES_KEY, messages);
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

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
        if ((comments == null) || (comments.isEmpty())){
            logger.debug("There is no comment for photo " + photo);
        } else {
            logger.debug(comments.size() + " comments found for photo " + photo);
        }
    }

	/**
	 * validate data
	 * 
	 * @param comment The comment send by user
	 * 
	 * @return true if data correct, false otherwise
	 */
	private boolean validateData(String comment) {
		return (comment != null) && (!"".equals(comment.trim()));
	}

}
