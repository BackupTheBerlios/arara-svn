/*
 * Created on 23/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

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

import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.pagination.PaginationBean;
import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.PhotoByCommentsOfWeekPaginationController;
import net.indrix.arara.servlets.pagination.PhotoByCommentsPaginationController;
import net.indrix.arara.servlets.pagination.PhotoByCommonNamePaginationController;
import net.indrix.arara.servlets.pagination.PhotoByFamilyPaginationController;
import net.indrix.arara.servlets.pagination.PhotoByPlacePaginationController;
import net.indrix.arara.servlets.pagination.PhotoBySpeciePaginationController;
import net.indrix.arara.servlets.pagination.PhotoByStatePaginationController;
import net.indrix.arara.servlets.pagination.PhotoByUserPaginationController;
import net.indrix.arara.servlets.pagination.PhotoPaginationController;
import net.indrix.arara.servlets.pagination.PhotoRecentPaginationController;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractSearchPhotosServlet extends AbstractServlet {
    private static final String PAGE_NUMBER = "pageNumber";
    
    protected static final String[] textForSearch = {"All", "Family", "Specie", "Common Name", "User", "More recent", "English Name", "More comments", "More comments of Week", "Cidade", "Estado"};
    
    public static final int PAGINATION_FOR_ALL_PHOTOS = 0;

    public static final int PAGINATION_FOR_FAMILY = 1;

    public static final int PAGINATION_FOR_SPECIE = 2;

    public static final int PAGINATION_FOR_COMMON_NAME = 3;

    public static final int PAGINATION_FOR_USER = 4;

    public static final int PAGINATION_FOR_RECENT = 5;
    
    public static final int PAGINATION_FOR_ENGLISH_NAME = 6;
    
    public static final int PAGINATION_FOR_MORE_COMMENTS = 7;
    
    public static final int PAGINATION_FOR_MORE_COMMENTS_OF_WEEK = 8;

    public static final int PAGINATION_FOR_PLACE = 9;
    
    public static final int PAGINATION_FOR_STATE = 10;

    private static String PHOTOS_BY_PAGE_KEY = "photos.per.page";

    private static int PHOTOS_PER_PAGE;
    static {
        PHOTOS_PER_PAGE = Integer.parseInt(PropertiesManager
                .getProperty(PHOTOS_BY_PAGE_KEY));
    }
  
    /**
     * 
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        List<String> errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);

        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String pageToShow = req.getParameter(ServletConstants.PAGE_TO_SHOW_KEY);
        String action = req.getParameter(ServletConstants.ACTION);

        if (action == null || action.equals("")) {
            action = ServletConstants.BEGIN;
        }

        String idStr = (String) req.getParameter(ServletConstants.ID);
        String textToSearch = retrieveTextToSearch(req);
        int id = -1;
        if ((idStr != null) && (idStr.trim().length() > 0)) {
            id = Integer.parseInt(idStr);
        }
        if ((id != -1) || (textToSearch.trim().length() > 0)
                || !ServletConstants.BEGIN.equals(action)) {
            List list = null;
            PhotoPaginationController controller = (PhotoPaginationController) getPaginationController(
                    session, false, getPaginationConstant(), action);
            handlePageNumber(controller, req);            
            controller.setId(id);
            controller.setText(textToSearch);           
            try {
                list = controller.doAction(action);
            } catch (InvalidControllerException e) {
                try {
                    list = controller.doAction(ServletConstants.BEGIN);
                } catch (InvalidControllerException e1) {
                    // this should never happend
                    logger.fatal("InvalidControllerException when doing BEGIN action...", e1);
                }
            }
            PaginationBean bean = controller.getPaginationBean();
            logger.debug("number of pages: " + bean.getNumberOfPages());
            logger.debug("current page: " + bean.getCurrentPage());
            session.setAttribute("paginationBean", bean);                

            logger.debug("List of photos retrieved...");
            logger.debug("Putting list of photos in session");
            session.setAttribute(ServletConstants.PHOTOS_LIST, list);
            session.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, getServletToCall());

            pageToShow = "/jsp/photo/search/doShowAllPhotos.jsp";

            // adding the user id to request, so it can be sent back by view
            req.setAttribute(ServletConstants.ID, idStr);

            String ip = req.getRemoteAddr();
            if (user != null) {
                loggerActions.info("User " + user.getLogin() + " from IP "
                        + ip + " has selected photos - "
                        + textForSearch[getPaginationConstant()]);
            } else {
                Locale locale = req.getLocale();
                loggerActions.info("Anonymous " + " from IP " + ip + "("
                        + locale + ") has selected photos - "
                        + textForSearch[getPaginationConstant()]);
            }

        } else {
            errors.add(ServletConstants.SELECT_VALUE_ERROR);
        }

        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
        req.setAttribute(ServletConstants.ACTION, action);

        if (!errors.isEmpty()) {
            logger.debug("errors is not null.");
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
        }
        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        logger.debug("Data on request: " + nextPage + " | " + pageToShow + " | " + action);
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

    protected abstract String getServletToCall();

    protected abstract int getPaginationConstant();

    protected void handlePageNumber(PaginationController controller, HttpServletRequest req){
        String pageNumber = req.getParameter(PAGE_NUMBER);
        if (pageNumber != null && pageNumber.trim().length() > 0){
            int number = Integer.parseInt(pageNumber);
            if (number <= 0){
                number = 1;
            }
            logger.debug("Setting page " + number + " to controller...");
            controller.setPageToGo(number);            
        } else {
            logger.debug("Could not retrieve page number");
        }
    }
    
    protected PaginationController getPaginationController(HttpSession session,
            boolean ident, int target, String action) {

        // the key is the key string plus the target
        String key = ServletConstants.PHOTO_PAGINATION_CONTROLLER_KEY;
        logger.debug("Retrieving controller with key " + key);
        PaginationController c = null;
        if (!ServletConstants.BEGIN.equals(action)){
            c = (PaginationController) session.getAttribute(key);
        }
        if (c == null) {
            switch (target) {
            case PAGINATION_FOR_ALL_PHOTOS:
                c = new PhotoPaginationController(PHOTOS_PER_PAGE, ident);
                break;
            case PAGINATION_FOR_FAMILY:
                c = new PhotoByFamilyPaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_SPECIE:
                c = new PhotoBySpeciePaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_COMMON_NAME:
                c = new PhotoByCommonNamePaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_USER:
                c = new PhotoByUserPaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_RECENT:
                c = new PhotoRecentPaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_MORE_COMMENTS:
                c = new PhotoByCommentsPaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_MORE_COMMENTS_OF_WEEK:
                c = new PhotoByCommentsOfWeekPaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_PLACE:
                c = new PhotoByPlacePaginationController(PHOTOS_PER_PAGE);
                break;
            case PAGINATION_FOR_STATE:
                c = new PhotoByStatePaginationController(PHOTOS_PER_PAGE);
                break;
                
            }
            logger.debug("PaginationController just created");
        } else {
            logger.debug("PaginationController retrieved from session");
        }
        session.setAttribute(key, c);
        session.setAttribute(ServletConstants.PHOTO_PAGINATION_CONTROLLER_KEY, c);
        return c;
    }
}
