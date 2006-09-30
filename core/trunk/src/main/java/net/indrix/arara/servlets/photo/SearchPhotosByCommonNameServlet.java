/*
 * Created on 06/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;


/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchPhotosByCommonNameServlet extends
		AbstractSearchPhotosServlet {
    
    protected String getServletToCall() {
        return "/servlet/searchPhotosByCommonName";
    }    
    protected int getPaginationConstant() {
        return PAGINATION_FOR_COMMON_NAME;
    }
    
}
