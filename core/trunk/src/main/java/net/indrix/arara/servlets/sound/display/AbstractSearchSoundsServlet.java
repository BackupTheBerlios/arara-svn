/*
 * Created on 23/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound.display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.pagination.PaginationBean;
import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.SoundByCommonNamePaginationController;
import net.indrix.arara.servlets.pagination.SoundByFamilyPaginationController;
import net.indrix.arara.servlets.pagination.SoundBySpeciePaginationController;
import net.indrix.arara.servlets.pagination.SoundByUserPaginationController;
import net.indrix.arara.servlets.pagination.SoundPaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractSearchSoundsServlet extends HttpServlet {
    public static final int PAGINATION_FOR_ALL_SOUNDS = 0;

    public static final int PAGINATION_FOR_FAMILY = 1;

    public static final int PAGINATION_FOR_SPECIE = 2;

    public static final int PAGINATION_FOR_COMMON_NAME = 3;

    public static final int PAGINATION_FOR_USER = 4;

    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    protected static Logger loggerActions = Logger
            .getLogger("net.indrix.actions");

    private static String SOUNDS_BY_PAGE_KEY = "sounds.per.page";

    private static int SOUNDS_PER_PAGE;

    static {
        SOUNDS_PER_PAGE = Integer.parseInt(PropertiesManager
                .getProperty(SOUNDS_BY_PAGE_KEY));
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doGet(req, res);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        List<String> errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);

        String idStr = (String) req.getParameter(ServletConstants.ID);
        String textToSearch = retrieveTextToSearch(req);
        String action = req.getParameter(ServletConstants.ACTION);
        logger.debug("Retrieved action " + action);
        int id = -1;

        PaginationController controller = getPaginationController(session,
                false, getPaginationConstant());

        if ((idStr != null) && (idStr.trim().length() > 0)) {
            id = Integer.parseInt(idStr);
        }
        if ((id != -1) || (textToSearch.trim().length() > 0)
                || !ServletConstants.BEGIN.equals(action)) {
            logger.debug("Setting id = " + id);
            controller.setId(id);
            logger.debug("Setting text = " + textToSearch);
            controller.setText(textToSearch);

            List list = null;
            try {
                list = controller.doAction(action);
            } catch (InvalidControllerException e) {
                try {
                    list = controller.doAction(ServletConstants.BEGIN);
                } catch (InvalidControllerException e1) {
                    // this should never happend
                    logger.fatal("Exception when doing BEGIN action...", e1);
                }
            }
            PaginationBean bean = controller.getPaginationBean();
            logger.debug("number of pages: " + bean.getNumberOfPages());
            logger.debug("current page: " + bean.getCurrentPage());
            session.setAttribute("paginationBean", bean);                
            
            logger.debug("Putting list of sounds in session");
            session.setAttribute(ServletConstants.SOUNDS_LIST, list);

            session.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY,
                    getServletToCall());
            nextPage = ServletConstants.ALL_SOUNDS_PAGE;
        } else {
            errors.add(ServletConstants.SELECT_VALUE_ERROR);
        }

        if (user != null) {
            loggerActions.info("User " + user.getLogin()
                    + " has selected sounds - " + getPaginationConstant());
        } else {
            String ip = req.getRemoteAddr();
            Locale locale = req.getLocale();
            loggerActions.info("Anonymous " + " from IP " + ip + "(" + locale
                    + ") has selected sounds - " + getPaginationConstant());
        }
        if (!errors.isEmpty()) {
            logger.debug("errors is not null.");
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);

            nextPage = ServletConstants.UPLOAD_PAGE;
        }
        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        dispatcher.forward(req, res);
    }

    /**
     * Retrieve and do any needed treatment to the text to search
     * 
     * @param req The request from user
     * 
     * @return The String with the text entered by user
     */
    protected String retrieveTextToSearch(HttpServletRequest req) {
        return ServletUtil.retrieveTextToSearch(req);
    }

    protected abstract int getPaginationConstant();

    protected PaginationController getPaginationController(HttpSession session,
            boolean identification, int target) {

        // the key is the key string plus the target
        String key = ServletConstants.SOUND_PAGINATION_CONTROLLER_KEY + target
                + identification;
        logger.debug("retrieving controller with key " + key);
        PaginationController c = (PaginationController) session
                .getAttribute(key);
        if (c == null) {
            switch (target) {
            case PAGINATION_FOR_ALL_SOUNDS:
                c = new SoundPaginationController(SOUNDS_PER_PAGE,
                        identification);
                break;
            case PAGINATION_FOR_FAMILY:
                c = new SoundByFamilyPaginationController(SOUNDS_PER_PAGE,
                        identification);
                break;
            case PAGINATION_FOR_SPECIE:
                c = new SoundBySpeciePaginationController(SOUNDS_PER_PAGE,
                        identification);
                break;
            case PAGINATION_FOR_COMMON_NAME:
                c = new SoundByCommonNamePaginationController(SOUNDS_PER_PAGE,
                        identification);
                break;
            case PAGINATION_FOR_USER:
                c = new SoundByUserPaginationController(SOUNDS_PER_PAGE,
                        identification);
                break;
            }
            logger.debug("PaginationController just created");
        } else {
            logger.debug("PaginationController retrieved from session");
        }
        session.setAttribute(key, c);
        session.setAttribute(ServletConstants.SOUND_PAGINATION_CONTROLLER_KEY,
                c);
        return c;
    }

    abstract protected String getServletToCall();
}
