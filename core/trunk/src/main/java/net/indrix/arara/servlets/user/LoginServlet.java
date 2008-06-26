/*
 * Created on 05/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.indrix.arara.servlets.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.model.UserNotFoundException;
import net.indrix.arara.model.exceptions.UserNotValidatedException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author alunos
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
@SuppressWarnings("serial")
public class LoginServlet extends HttpServlet {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	protected static Logger loggerActions = Logger
			.getLogger("net.indrix.actions");

	public void init() {
		logger.debug("Initializing LoginServlet...");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List<String> erros = new ArrayList<String>();

		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String nextResource = req.getParameter("nextResource");
        String rememberParameter = req.getParameter("remember");
        boolean remember = !"on".equals(rememberParameter) || rememberParameter == null ? false : true;
        
		logger.debug("Locale:" + req.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("Resources", req.getLocale());
		if (bundle == null) {
			logger.debug("BUNDLE = NULL");
		} else {
			logger.debug("menu.common.home = " + bundle.getString("menu.common.home"));
		}

		logger.debug("User is trying to login with data: " + login + " | Remember:" + remember);

		User user = null;
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		try {
			UserModel l = new UserModel();
			logger.debug("Validating user");
			user = l.login(login, password);
			if (user != null) {
				logger.debug("User validated " + user);
				loggerActions.info("User " + login + " from IP " + req.getRemoteAddr() + " has logged in.");
				HttpSession session = req.getSession(true);
				session.setAttribute(ServletConstants.USER_KEY, user);

				req.setAttribute(ServletConstants.USER_KEY, user);
                
                // creating a cookie on user's machine
                Cookie cookie = new Cookie("login", user.getLogin());
                if (remember){
                    logger.info("Creating cookie on user's machine...");
                    cookie.setMaxAge(60 * 60 * 24 * 7);
                } else {
                    logger.info("Deleting cookie on user's machine...");
                    cookie.setMaxAge(0);
                    cookie.setPath("null");
                }
                res.addCookie(cookie);                    

                if ((nextResource != null) && (nextResource.length() > 0)) {
					nextPage = nextResource;
				} else {
					nextPage = ServletConstants.INITIAL_PAGE;
				}
				logger.debug("Re-directing to " + nextPage);
			} else {
				logger.debug("User NOT validated");
				nextPage = ServletConstants.LOGIN_PAGE;
				erros.add(ServletConstants.ACCESS_DENIED);
			}
		} catch (UserNotFoundException e) {
			logger.error("UserNotFoundException");
			nextPage = ServletConstants.LOGIN_PAGE;
			erros.add(ServletConstants.ACCESS_DENIED);
		} catch (DatabaseDownException e) {
			logger.error("DatabaseDownException", e);
			nextPage = ServletConstants.DATABASE_ERROR_PAGE;
		} catch (SQLException e) {
			logger.error("SQLException", e);
			nextPage = ServletConstants.DATABASE_ERROR_PAGE;
		} catch (UserNotValidatedException e) {
            logger.error("UserNotFoundException");
            nextPage = ServletConstants.LOGIN_PAGE;
            erros.add(ServletConstants.USER_NOT_VALIDATED_ERROR);           
        }

		if (!erros.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar
			// mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, erros);
			req.setAttribute(ServletConstants.USER_KEY, user);
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

}
