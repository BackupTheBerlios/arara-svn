package net.indrix.arara.servlets.sound.display;


public class SearchSoundsByUserServlet extends
        AbstractSearchSoundsServlet {
    
    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_USER;
    }

    @Override
    protected String getServletToCall() {
        return "/servlet/searchSoundsByUser";
    }
}
