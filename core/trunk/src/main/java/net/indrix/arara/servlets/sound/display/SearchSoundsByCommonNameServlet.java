package net.indrix.arara.servlets.sound.display;


public class SearchSoundsByCommonNameServlet extends
        AbstractSearchSoundsServlet {

    protected int getPaginationConstant() {
        return PAGINATION_FOR_COMMON_NAME;
    }
}
