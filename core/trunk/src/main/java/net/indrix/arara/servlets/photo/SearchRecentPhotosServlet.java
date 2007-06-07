package net.indrix.arara.servlets.photo;

@SuppressWarnings("serial")
public class SearchRecentPhotosServlet extends SearchPhotosServlet {
    protected int getPaginationConstant() {
        return PAGINATION_FOR_RECENT;
    } 

    protected String getServletToCall() {
        return "/servlet/searchRecentPhotos";
    }  
}
