/*
 * Created on 05/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package net.indrix.servlets.user;

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

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.UserModel;
import net.indrix.model.UserNotFoundException;
import net.indrix.servlets.ServletConstants;
import net.indrix.vo.User;

import org.apache.log4j.Logger;
/**
 * @author alunos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LoginServlet extends HttpServlet {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");
    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");

	public void init() {
		logger.debug("Initializing LoginServlet...");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		List erros = new ArrayList();

		String login = req.getParameter("login");
		String password = req.getParameter("password");

		logger.debug("User is trying to login with data: " + login);

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
                loggerActions.info("User " + login + " has logged in.");
				HttpSession session = req.getSession(true);
				session.setAttribute(ServletConstants.USER_KEY, user);

				req.setAttribute(ServletConstants.USER_KEY, user);
				nextPage = ServletConstants.INITIAL_PAGE;
			} else {
                logger.debug("User NOT validated");
				nextPage = ServletConstants.LOGIN_PAGE;
				erros.add(ServletConstants.INVALID_PASSWORD);
			}
		} catch (UserNotFoundException e) {
            logger.error("UserNotFoundException");
            nextPage = ServletConstants.LOGIN_PAGE;
            erros.add(ServletConstants.INVALID_USER);
		} catch (DatabaseDownException e) {
			logger.error("DatabaseDownException", e);
			nextPage = ServletConstants.DATABASE_ERROR_PAGE;
		} catch (SQLException e) {
			logger.error("SQLException", e);
			nextPage = ServletConstants.DATABASE_ERROR_PAGE;
		}

		if (!erros.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, erros);
			req.setAttribute(ServletConstants.USER_KEY, user);
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

}
