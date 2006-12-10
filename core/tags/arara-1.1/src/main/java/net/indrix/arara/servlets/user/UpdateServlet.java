/*
 * Created on 15/05/2006
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
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UpdateServlet extends HttpServlet {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.debug("UpdateServlet.doPost called...");

		List errors = new ArrayList();

		// recupera dados do request
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String login = req.getParameter("login");
		String language = req.getParameter("language");
		boolean emailOnPhoto = "on".equals(req.getParameter("emailOnNewPhoto")) ? true
				: false;
		boolean emailOnIdPhoto = "on".equals(req
				.getParameter("emailOnNewIdPhoto")) ? true : false;
		boolean emailOnSound = "on".equals(req.getParameter("emailOnNewSound")) ? true
				: false;
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String msg = null;
		String nextPage = null;
		// verifica se login já existe
		UserModel userModel = new UserModel();

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (user != null) {
			logger.debug("User does not exist. Validating data...");
			user.setName(name);
			user.setEmail(email);
			user.setLanguage(language);
			user.setEmailOnNewPhoto(emailOnPhoto);
			user.setEmailOnNewIdPhoto(emailOnIdPhoto);
			user.setEmailOnNewSound(emailOnSound);
			logger.debug("Data validated...");
			try {
				userModel.update(user);

				logger.debug("User saved in database " + user);

				// direciona usuário para página inicial
				nextPage = ServletConstants.UPDATED_PAGE;
			} catch (DatabaseDownException e1) {
				errors.add(ServletConstants.DATABASE_ERROR);
			} catch (SQLException e1) {
				errors.add(ServletConstants.DATABASE_ERROR);
			}
		} else {
			logger.debug("errors is null.");
			errors.add(ServletConstants.USER_NOT_LOGGED);
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.LOGIN_PAGE;
		}

		if (!errors.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar
			// mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			req.setAttribute(ServletConstants.USER_KEY, null);

			// direciona usuário para página de registro novamente
			nextPage = ServletConstants.UPDATE_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}
}
