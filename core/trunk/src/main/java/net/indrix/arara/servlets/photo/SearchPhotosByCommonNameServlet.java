/*
 * Created on 06/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import javax.servlet.http.HttpServletRequest;

import net.indrix.arara.servlets.ServletUtil;


/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchPhotosByCommonNameServlet extends AbstractSearchPhotosServlet {
    
    protected String getServletToCall() {
        return "/servlet/searchPhotosByCommonName";
    }    
    protected int getPaginationConstant() {
        return PAGINATION_FOR_COMMON_NAME;
    }
    
    /**
     * Retrieve and do any needed treatment to the text to search
     * 
     * @param req The request from user
     * 
     * @return The String with the text entered by user
     */
    protected String retrieveTextToSearch(HttpServletRequest req) {
        String text = ServletUtil.retrieveTextToSearch(req).replace(' ', '-');
        return text;
    }
    
}
