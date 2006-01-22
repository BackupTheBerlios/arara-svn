/*
 * Created on 28/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import net.indrix.arara.servlets.ServletConstants;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchPhotosByUserServlet extends RetrieveUsersServlet {
    /**
     * init method for InitSearchPhotosByUserServlet servlet
     */
    public void init(){
        logger.debug("Initializing InitSearchPhotosByUserServlet...");
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
