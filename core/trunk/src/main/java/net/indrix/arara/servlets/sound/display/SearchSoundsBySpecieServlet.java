package net.indrix.arara.servlets.sound.display;


public class SearchSoundsBySpecieServlet extends AbstractSearchSoundsServlet {

    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_SPECIE;
    }

    @Override
    protected String getServletToCall() {
        return "/servlet/searchSoundsBySpecie";
    }
}
