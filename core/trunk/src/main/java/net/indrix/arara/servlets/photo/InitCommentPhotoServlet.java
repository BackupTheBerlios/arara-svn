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

import org.apache.log4j.Logger;

public class InitCommentPhotoServlet extends HttpServlet {
    /**
     * Logger object used to log messages
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * 
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        HttpSession session = req.getSession();
        
        PhotoModel model = new PhotoModel();
        Photo photo = null;
        int photoId = Integer.parseInt(req.getParameter("photoId"));
        try {
            photo = model.retrieve(photoId);
            session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
        } catch (DatabaseDownException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        nextPage = ServletConstants.COMMENT_PAGE;
      
        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        dispatcher.forward(req, res);

    }
}
