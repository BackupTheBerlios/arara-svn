/*
 * Created on 09/05/2005
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
import net.indrix.arara.vo.User;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SearchSoundsServlet extends AbstractSearchSoundsServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        List<String> errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);

        String action = req.getParameter(ServletConstants.ACTION);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        if (nextPage == null) {
            nextPage = ServletConstants.FRAME_PAGE;
        }

        logger.debug("Retrieved action " + action);

        PaginationController controller = getPaginationController(session,
                false, getPaginationConstant());

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
        logger.debug("Putting list of sounds in session");
        session.setAttribute(ServletConstants.SOUNDS_LIST, list);
        session.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, getServletToCall());

        String pageToShow = "/jsp/sound/display/doShowAllSounds.jsp";
        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
        req.setAttribute(ServletConstants.ACTION, action);
        req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);

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
        }
        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        dispatcher.forward(req, res);
    }

    @Override
    protected int getPaginationConstant() {
        return PAGINATION_FOR_ALL_SOUNDS;
    }

    @Override
    protected String getServletToCall() {
        return "/servlet/searchSounds";
    }
}
