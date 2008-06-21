package net.indrix.arara.servlets.photo;

@SuppressWarnings("serial")
public class SearchPhotosByStateServlet extends AbstractSearchPhotosServlet {
    protected String getServletToCall() {
        return "/servlet/searchPhotosByState";
    }

    protected int getPaginationConstant() {
        return PAGINATION_FOR_STATE;
    }

}