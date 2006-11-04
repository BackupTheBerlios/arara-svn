package net.indrix.arara.servlets.sound.display;


public class SearchSoundsByCommonNameServlet extends
        AbstractSearchSoundsServlet {

    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_COMMON_NAME;
    }

    @Override
    protected String getServletToCall() {
        return "/servlet/searchPhotosByCommonName";
    }    
}
