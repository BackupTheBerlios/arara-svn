/*
 * Created on 23/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound.display;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.PaginationController;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.utils.PropertiesManager;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AbstractSearchSoundsServlet extends HttpServlet {
    protected static Logger logger = Logger.getLogger("net.indrix.aves");
    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");

    private static String SOUNDS_BY_PAGE_KEY = "photos.per.page";
	private static int SOUNDS_PER_PAGE;
    static {
        SOUNDS_PER_PAGE = Integer.parseInt(PropertiesManager.getProperty(SOUNDS_BY_PAGE_KEY));
    }
    
    protected PaginationController getPaginationController(HttpSession session) {
        PaginationController c = (PaginationController)session.getAttribute(ServletConstants.PAGINATION_CONTROLLER_KEY);
        if (c == null){
            c = new PaginationController(SOUNDS_PER_PAGE);
            session.setAttribute(ServletConstants.PAGINATION_CONTROLLER_KEY, c);
            logger.debug("PaginationController just created");
        } else {
            logger.debug("PaginationController retrieved from session");
        }
        return c;
    }
}
