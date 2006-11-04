package net.indrix.arara.servlets.sound.display;


public class SearchSoundsByFamilyServlet extends AbstractSearchSoundsServlet {

    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_FAMILY;
    }

    @Override
    protected String getServletToCall() {
        return "/servlet/searchSoundsByFamily";
    }    
}
