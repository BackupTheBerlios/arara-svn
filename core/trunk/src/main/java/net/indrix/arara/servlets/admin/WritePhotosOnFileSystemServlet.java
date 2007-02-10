package net.indrix.arara.servlets.admin;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.model.file.PhotoFileManager;

public class WritePhotosOnFileSystemServlet extends HttpServlet{
    /**
     * The logger object to log messages
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        logger.debug("WritePhotosOnFileSystemServlet.doGet called");
        PhotoModel model = new PhotoModel();
        PhotoFileManager manager = new PhotoFileManager(null);
        StringBuffer buffer = new StringBuffer();
        try {
            List list = model.retrievePhotoIDsForRecentPhotos();
            Iterator it = list.iterator();
            
            while (it.hasNext()){
                Integer idInteger = (Integer)it.next();
                logger.debug("Retrieving photo " + idInteger);
                Photo photo = model.retrieve(idInteger.intValue());
                logger.debug("Photo retrieved " + photo);
                manager.setPhoto(photo);
                logger.debug("Writing photo " + photo.getId());
                manager.writeFile();
                
                buffer.append(photo);
                buffer.append("\n");
            }
        } catch (DatabaseDownException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.debug("WritePhotosOnFileSystemServlet finished");
        
        Writer w = res.getWriter();
        w.write(buffer.toString());
        w.flush();
        w.close();
    }    

}
