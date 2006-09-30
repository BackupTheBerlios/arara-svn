package net.indrix.arara.servlets.sound.display;


public class SearchSoundsByFamilyServlet extends AbstractSearchSoundsServlet {

    protected int getPaginationConstant() {
        return PAGINATION_FOR_FAMILY;
    }    
}
