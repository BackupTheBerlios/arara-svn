package net.indrix.arara.servlets.photo;

@SuppressWarnings("serial")
public class SearchPhotosByEnglishNameServlet extends AbstractSearchPhotosServlet {
    protected String getServletToCall() {
        return "/servlet/searchPhotosByEnglishName";
    }
    protected int getPaginationConstant() {
        return PAGINATION_FOR_ENGLISH_NAME;
    }    

}
