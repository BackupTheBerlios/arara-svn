package net.indrix.arara.servlets.sound.display;


public class SearchSoundsBySpecieServlet extends AbstractSearchSoundsServlet {

    protected int getPaginationConstant() {
        return PAGINATION_FOR_SPECIE;
    }
}
