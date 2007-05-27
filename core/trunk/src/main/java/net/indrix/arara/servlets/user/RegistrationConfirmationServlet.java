package net.indrix.arara.servlets.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.model.UserNotFoundException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
/**
 * THis class implements the confirmation for a user registration. 
 */
public class RegistrationConfirmationServlet extends HttpServlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");
    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");

    public void init() {
        logger.debug("Initializing RegistrationConfirmationServlet...");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<String>();

        String id = req.getParameter("id");

        logger.debug("User is trying to validate registration with key: " + id);

        User user = null;
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        try {
            UserModel l = new UserModel();
            logger.debug("Validating user");
            user = l.confirmRegistration(id);
            if (user != null) {
                logger.debug("User confirmation done:" + user);
                loggerActions.info("User " + user.getLogin() + " from IP " + req.getRemoteAddr() + " has confirmed registration.");
                
                // cria sessão e coloca usuário
                HttpSession session = req.getSession(true);
                session.setAttribute(ServletConstants.USER_KEY,user);

                req.setAttribute(ServletConstants.USER_KEY, user);

                String pageToShow = ServletConstants.REGISTRATION_CONFIRMATION_PAGE;
                req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                nextPage = ServletConstants.FRAME_PAGE;
                logger.debug("Re-directing to " + nextPage);
            } else {
                logger.debug("User NOT validated");
                nextPage = ServletConstants.REGISTER_PAGE;
                errors.add(ServletConstants.INVALID_USER);
            }
        } catch (UserNotFoundException e) {
            logger.error("UserNotFoundException");
            nextPage = ServletConstants.REGISTER_PAGE;
            errors.add(ServletConstants.INVALID_USER);
        } catch (DatabaseDownException e) {
            logger.error("DatabaseDownException", e);
            nextPage = ServletConstants.DATABASE_ERROR_PAGE;
        } catch (SQLException e) {
            logger.error("SQLException", e);
            nextPage = ServletConstants.DATABASE_ERROR_PAGE;
        }

        if (!errors.isEmpty()) {
            // coloca erros no request para registrar.jsp processar e apresentar
            // mensagem de erro
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            req.setAttribute(ServletConstants.USER_KEY, user);
        }

        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);
    }

}
