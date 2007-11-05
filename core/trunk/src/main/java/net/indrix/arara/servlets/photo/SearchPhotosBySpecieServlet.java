/*
 * Created on 21/06/2005
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
@SuppressWarnings("serial")
public class SearchPhotosBySpecieServlet extends AbstractSearchPhotosServlet {

    protected String getServletToCall() {
        return "/servlet/searchPhotosBySpecie";
    }

    protected int getPaginationConstant() {
        return PAGINATION_FOR_SPECIE;
    }
}
