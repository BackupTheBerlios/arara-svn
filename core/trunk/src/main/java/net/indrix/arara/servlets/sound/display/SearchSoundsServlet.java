/*
 * Created on 09/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound.display;


/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchSoundsServlet extends AbstractSearchSoundsServlet {
    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_ALL_SOUNDS;
    }
}
