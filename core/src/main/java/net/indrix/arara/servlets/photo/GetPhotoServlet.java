/*
 * Created on 09/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.photo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.log4j.Logger;

import net.indrix.dao.DatabaseDownException;
import net.indrix.dao.PhotoDAO;
import net.indrix.vo.Photo;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GetPhotoServlet extends HttpServlet {
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");
        
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
        logger.debug("GetPhotoServlet.doGet called...");
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
        String photoId = req.getParameter("photoId");
        
        PhotoDAO dao = new PhotoDAO();
        try {
            Photo photo = dao.retrieve(Integer.parseInt(photoId));
            logger.debug("Photo retrieved..." + photo);
            logger.debug("writing to outputStream...");
            res.setContentType("image/jpeg");
            InputStream input = photo.getRealImage().getImage();
            OutputStream output = res.getOutputStream();
            int info = 0;
            while ((info = input.read()) != -1){
                output.write(info);
            }
            output.flush();          
        } catch (DatabaseDownException e) {
            logger.debug("DatabaseDownException.....", e);
        } catch (SQLException e) {
            logger.debug("SQLException.....", e);
        }
	}
}