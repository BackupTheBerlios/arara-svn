/*
 * Created on 14/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;


/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class SearchPhotosByUserServlet extends AbstractSearchPhotosServlet {
    protected String getServletToCall() {
        return "/servlet/searchPhotosByUser";
    }

    protected int getPaginationConstant() {
        return PAGINATION_FOR_USER;
    }

}
