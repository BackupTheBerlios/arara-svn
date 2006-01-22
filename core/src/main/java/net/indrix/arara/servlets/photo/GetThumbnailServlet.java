/*
 * Created on 16/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GetThumbnailServlet extends HttpServlet {
    static Logger logger = Logger.getLogger("net.indrix.aves");
        
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        List errors = new ArrayList();
        List messages = new ArrayList();
        HttpSession session = req.getSession();
        String photoId = req.getParameter("photoId");
        String identificationStr = req.getParameter("identification");
        boolean identify = new Boolean(identificationStr).booleanValue();

        logger.debug("GetThumbnailServlet.doGet called for photo " + photoId);
        PhotoModel model = new PhotoModel();
        try {
            Photo photo = null;
            if (identify){
                photo = model.retrieveForIdentification(Integer.parseInt(photoId));
                logger.debug("Photo for identification retrieved..." + photo);
            } else {
                photo = model.retrieve(Integer.parseInt(photoId));
                logger.debug("Photo retrieved..." + photo);
            }
            logger.debug("writing to outputStream...");
            res.setContentType("image/jpeg");
            InputStream input = photo.getSmallImage().getImage();
            OutputStream output = res.getOutputStream();
            int info = 0;
            while ((info = input.read()) != -1){
                output.write(info);
            }
            output.flush();
            input.close();
            output.close();       
        } catch (DatabaseDownException e) {
            logger.debug("DatabaseDownException.....", e);
        } catch (SQLException e) {
            logger.debug("SQLException.....", e);
        }
    }
}