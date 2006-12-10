package net.indrix.arara.servlets.sound.display;

import javax.servlet.http.HttpServletRequest;

import net.indrix.arara.servlets.ServletUtil;


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

    @Override
    protected String retrieveTextToSearch(HttpServletRequest req) {
        String text = ServletUtil.retrieveTextToSearch(req).replace(' ', '-');
        return text;
    }
}
