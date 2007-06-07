package net.indrix.arara.servlets.sound.display;


@SuppressWarnings("serial")
public class SearchRecentSoundsServlet extends SearchSoundsServlet {
    protected int getPaginationConstant() {
        return PAGINATION_FOR_RECENT;
    } 

    protected String getServletToCall() {
        return "/servlet/searchRecentSounds";
    }  
}

    