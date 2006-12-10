/*
 * Created on 24/04/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
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

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SendPasswordServlet extends HttpServlet {
	/**
	 * The logger object to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	protected static Logger loggerActions = Logger
			.getLogger("net.indrix.actions");

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// retrieve data from request
		String login = req.getParameter("login");
		logger
				.debug("SendPasswordServlet.doPost: user is requesting password for login "
						+ login);

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List <String>errors = new ArrayList<String>();

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		// verify if the input (current) password is correct
		UserModel userModel = new UserModel();
		try {
			loggerActions.info("User " + login + " from IP "
					+ req.getRemoteAddr() + " has requested his/her password.");
			userModel.setLocale(req.getLocale());
			user = userModel.sendPassword(login);
            req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, "/jsp/user/doPasswordSent.jsp");
		} catch (DatabaseDownException e) {
            errors.add(ServletConstants.DATABASE_ERROR);
		} catch (SQLException e) {
            errors.add(ServletConstants.DATABASE_ERROR);
		} catch (UserNotFoundException e) {
			// invalid password
            errors.add(ServletConstants.USER_NOT_FOUND);
		}

        nextPage = ServletConstants.FRAME_PAGE;
        
		if (!errors.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar
			// mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			req.setAttribute(ServletConstants.USER_KEY, null);

            req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, "/jsp/user/doForgotPassword.jsp");
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);

		dispatcher.forward(req, res);
	}

}
