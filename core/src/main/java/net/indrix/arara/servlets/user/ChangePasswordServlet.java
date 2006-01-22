/*
 * Created on 30/08/2005
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
public class ChangePasswordServlet extends HttpServlet {
	/**
	 * The logger object to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		List erros = new ArrayList();

		// retrieve data from request
		String password = req.getParameter("password");
		String newPassword = req.getParameter("newPassword");
		String confirmationPassword = req.getParameter("newPassword2");

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String msg = null;
		String nextPage = null;
		List errors = new ArrayList();

		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (user == null) {
			logger.debug("User not found in session");
			errors.add(ServletConstants.USER_NOT_LOGGED);
			// put errors in request 
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.LOGIN_PAGE;
		} else {
			// verify if the input (current) password is correct
			UserModel userModel = new UserModel();
			boolean passwordOk = userModel.validatePassword(user, password);
			if (passwordOk) {
                // verify if the two new password matche
				if (newPassword.equals(confirmationPassword)) {
					// new password ok. Update user password
					UserModel model = new UserModel();
					try {
						model.changePassword(user, newPassword);
                        nextPage = ServletConstants.PASSWORD_CHANGED_PAGE;
					} catch (DatabaseDownException e) {
						erros.add(ServletConstants.DATABASE_ERROR);
					} catch (SQLException e) {
						erros.add(ServletConstants.DATABASE_ERROR);
					} 
				} else {
					// segunda senha não bate com a primeira
					erros.add(ServletConstants.PASSWORD_MISMATCH);
				}
			} else {
                // invalid password
                erros.add(ServletConstants.INVALID_PASSWORD);                
			}
		}

		if (!erros.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, erros);
			req.setAttribute(ServletConstants.USER_KEY, null);

			nextPage = ServletConstants.CHANGE_PASSWORD_PAGE;
		}

        dispatcher = context.getRequestDispatcher(nextPage);
        logger.debug("Dispatching to " + nextPage);
        
		dispatcher.forward(req, res);
	}

}
