package net.indrix.arara.servlets.sound.display;


public class SearchSoundsByUserServlet extends
        AbstractSearchSoundsServlet {
    
    protected int getPaginationConstant() {
        return PAGINATION_FOR_USER;
    }
}
