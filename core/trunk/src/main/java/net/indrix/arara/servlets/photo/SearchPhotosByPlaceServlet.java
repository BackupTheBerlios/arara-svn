package net.indrix.arara.servlets.photo;

@SuppressWarnings("serial")
public class SearchPhotosByPlaceServlet extends AbstractSearchPhotosServlet {
    protected String getServletToCall() {
        return "/servlet/searchPhotosByPlace";
    }

    protected int getPaginationConstant() {
        return PAGINATION_FOR_PLACE;
    }

}