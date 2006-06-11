/*
 * Created on 23/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound.display;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.SoundPaginationController;
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
    public static final int PAGINATION_FOR_ALL_SOUNDS = 0;
    public static final int PAGINATION_FOR_ALL_FAMILY = 1;
    public static final int PAGINATION_FOR_ALL_SPECIE = 2;
    public static final int PAGINATION_FOR_ALL_COMMON_NAME = 3;
    public static final int PAGINATION_FOR_ALL_USER = 4;

    protected static Logger logger = Logger.getLogger("net.indrix.aves");
    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");

    private static String SOUNDS_BY_PAGE_KEY = "sounds.per.page";
	private static int SOUNDS_PER_PAGE;
    
    static {
        SOUNDS_PER_PAGE = Integer.parseInt(PropertiesManager.getProperty(SOUNDS_BY_PAGE_KEY));
    }
    
    protected PaginationController getPaginationController(
        HttpSession session,
        boolean identification,
        int target) {
        
        // the key is the key string plus the target
        String key = ServletConstants.SOUND_PAGINATION_CONTROLLER_KEY + target + identification;
        logger.debug("AbstractSearchSoundsServlet.getPaginationController: retrieving controller with key " + key);
        PaginationController c =
            (PaginationController) session.getAttribute(key);
        if (c == null) {
            switch (target) {
                case PAGINATION_FOR_ALL_SOUNDS :
                    c = new SoundPaginationController(SOUNDS_PER_PAGE, identification);
                    break;
            }
            logger.debug("PaginationController just created");
        } else {
            logger.debug("PaginationController retrieved from session");
        }
        session.setAttribute(key, c);
        session.setAttribute(ServletConstants.SOUND_PAGINATION_CONTROLLER_KEY, c);
        return c;
    }
}
