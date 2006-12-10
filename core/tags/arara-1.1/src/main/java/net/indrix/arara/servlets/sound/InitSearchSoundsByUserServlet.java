package net.indrix.arara.servlets.sound;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.UserDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.common.RetrieveUsersServlet;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchSoundsByUserServlet extends RetrieveUsersServlet {
    /**
     * init method for InitSearchPhotosByUserServlet servlet
     */
    public void init() {
        logger.debug("Initializing InitSearchPhotosByUserServlet...");
    }

    /**
     * This method calls the dao object, and retrieves the data
     * 
     * @param dao The dao to be called
     * 
     * @return A list of users
     * 
     * @throws DatabaseDownException if database is down
     * @throws SQLException if any sql error occurs
     */
    protected List retrieveData(UserDAO dao) throws DatabaseDownException, SQLException {
        return dao.retrieve();
    }
    
    /**
     * This method returns the next page
     * 
     * @return The next page the user shall see
     */
    protected String getNextPage() {
        return ServletConstants.PHOTO_BY_USER_PAGE;
    }
}