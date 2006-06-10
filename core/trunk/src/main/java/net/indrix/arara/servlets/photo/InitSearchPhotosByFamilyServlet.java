/*
 * Created on 14/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import net.indrix.arara.servlets.ServletConstants;


/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchPhotosByFamilyServlet extends RetrieveFamiliesServlet {
    public void init(){
        logger.debug("Initializing InitSearchPhotosByFamilyServlet...");
    }    
    /**
     * @return
     */
    protected String getNextPage() {
        return ServletConstants.PHOTO_BY_FAMILY_PAGE;
    }
}
