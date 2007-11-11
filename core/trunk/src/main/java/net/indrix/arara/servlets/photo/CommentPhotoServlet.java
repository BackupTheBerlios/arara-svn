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
@SuppressWarnings("serial")
public class CommentPhotoServlet extends HttpServlet {
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    protected static Logger loggerActions = Logger
            .getLogger("net.indrix.actions");

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

        logger.debug("Entering method...");
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        List<String> errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        String identificationStr = req.getParameter(ServletConstants.IDENTIFICATION_KEY);

        if (user == null) {
            logger.debug("User null.");
            errors.add(ServletConstants.USER_NOT_LOGGED);
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            nextPage = ServletConstants.LOGIN_PAGE;
        } else {
            String photoId = req.getParameter(ServletConstants.PHOTO_ID);
            String comment = req.getParameter(ServletConstants.COMMENT);
            String voteStr = req.getParameter(ServletConstants.VOTE);
            int vote = -1;
            if (voteStr != null) {
                vote = getVoteNumber(voteStr);
            }
            logger.debug("PhotoId to have a comment: " + photoId);
            logger.debug("Comment: " + comment);
            logger.debug("Vote: " + vote);
            if (validateData(comment)) {
                // retrieve current photo
                comment = formatComment(comment);
                PhotoModel model = new PhotoModel();
                try {
                    Photo photo = model.retrieve(Integer.parseInt(photoId));
                    if (photo != null) {
                        req.setAttribute(ServletConstants.CURRENT_PHOTO, photo);

                        model.insertComment(photo, user, comment);
                        logger.debug("Comment added to database");
                        
                        //model.insetVote(photo, user, vote);

                        if (user != null) {
                            String login = user.getLogin();
                            String msg = "User " + login + " has commented photo " + photo.getId();
                            loggerActions.info(msg);
                            if (vote > 0){
                                msg = "User " + login + " has voted photo " + photo.getId();
                                msg += " with vote " + vote;
                                loggerActions.info(msg);
                            }
                        }

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
                        nextPage = ServletConstants.FRAME_PAGE;
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY,
                                "/jsp/photo/search/doShowOnePhoto.jsp");
                    } else {
                        logger.error("Current photo does not exist");
                    }
                } catch (DatabaseDownException e) {
                    logger.debug("DatabaseDownException.....", e);
                    errors.add(ServletConstants.DATABASE_ERROR);
                } catch (SQLException e) {
                    logger.debug("SQLException.....", e);
                    errors.add(ServletConstants.DATABASE_ERROR);
                }
            } else {
                logger.debug("Comment is invalid...");
                errors.add(ServletConstants.COMMENT_NOT_NULL);
            }
        }

        if (!errors.isEmpty()) {
            nextPage = ServletConstants.FRAME_PAGE;
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
        }

        req.setAttribute(ServletConstants.IDENTIFICATION_KEY,identificationStr);
        req.setAttribute(ServletConstants.VIEW_MODE_KEY, "viewMode");
        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        dispatcher.forward(req, res);

    }

    private int getVoteNumber(String voteStr) {
        int vote = -1;
        if ("one".equals(voteStr)) {
            vote = 1;
        } else if ("two".equals(voteStr)) {
            vote = 2;
        } else if ("three".equals(voteStr)) {
            vote = 3;
        } else if ("four".equals(voteStr)) {
            vote = 4;
        } else if ("five".equals(voteStr)) {
            vote = 5;
        }
        return vote;
    }

    /**
     * This method formats the comment, breaking lines bigger than 80
     * characters...
     * 
     * @param comment
     *            THe comment to be formatted.
     * 
     * @return THe formatted comment
     */
    private String formatComment(String comment) {
        String newComment = "";
        if (comment.length() > 81){
            boolean finished = false;
            while (!finished){
                newComment = newComment + comment.substring(0, 80);
                comment = comment.substring(80);
                int index = comment.indexOf(" ");
                if (index >= 0){
                    newComment = newComment + comment.substring(0, index) + "\n" ;
                    comment = comment.substring(index+1);
                    if (comment.length() < 81){
                        newComment += comment;
                        finished = true;
                    }
                } else {
                    finished = true;
                    newComment += "\n" + comment;
                }
            }
        } else {
            newComment = comment;
        }
        return newComment;
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
            logger
                    .debug(comments.size() + " comments found for photo "
                            + photo);
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

    /**
     * validate data
     * 
     * @param comment
     *            The comment send by user
     * 
     * @return true if data correct, false otherwise
     */
    private boolean validateData(String comment) {
        return (comment != null) && (!"".equals(comment.trim()));
    }

}

