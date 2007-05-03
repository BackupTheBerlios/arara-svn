/**
 * 
 */
package net.indrix.arara.servlets.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.DatabaseManager;
import net.indrix.arara.servlets.AbstractServlet;

/**
 * @author Jeff
 *
 */
@SuppressWarnings("serial")
public class RetrieveImage extends AbstractServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
    logger.debug("GetPhotoServlet.doGet called...");
    String photoId = req.getParameter("photoId");
    
    try {
        InputStream input = retrieveImage(photoId);
        logger.debug("writing to outputStream...");
        res.setContentType("image/jpeg");
        OutputStream output = res.getOutputStream();
        int info = 0;
        while ((info = input.read()) != -1){
            output.write(info);
        }
        output.flush();
        input.close();
    } catch (DatabaseDownException e) {
        logger.debug("DatabaseDownException.....", e);
    } catch (SQLException e) {
        logger.debug("SQLException.....", e);
    }
}

    private InputStream retrieveImage(String photoId) throws DatabaseDownException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = Integer.parseInt(photoId);
        
        InputStream input = null;
        try {
            stmt = conn.prepareStatement("Select image from photo where id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("image");
                input = blob.getBinaryStream();
            }
        } catch (SQLException e) {
            logger.error("AbstractDAO.retrieve : could not retrieve data ");
            throw e;
        } finally {
            if (rs != null){
                rs.close();
            }
            if (stmt != null){
                stmt.close();
            }
            if (conn != null){
                conn.close();
            }
        }
        return input;
    }
    
}
