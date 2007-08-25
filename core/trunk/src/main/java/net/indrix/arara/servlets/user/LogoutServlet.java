/*
 * Created on 07/11/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LogoutServlet extends HttpServlet {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
	 * Logger object to be used by this class
	 */
	protected static Logger loggerActions = Logger
			.getLogger("net.indrix.actions");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		RequestDispatcher dispatcher = null;

		if (session == null) {
			// sessão não existe. Usuário acessou logout sem ter logado antes
			dispatcher = this.getServletContext().getRequestDispatcher(
					ServletConstants.ERROR_LOGOUT);
		} else {
			User user = (User) session.getAttribute(ServletConstants.USER_KEY);

			// finaliza sessão
			session.invalidate();

			// envia usuário para página inicial
			dispatcher = this.getServletContext().getRequestDispatcher(
					ServletConstants.LOGOUT_PAGE);

			// coloca usuario no request para JSP processar
			req.setAttribute(ServletConstants.USER_KEY, user);

            if (user != null){
                loggerActions.info("User " + user.getLogin() + " from IP " + req.getRemoteAddr() + " has logged out.");                
            }
		}

		dispatcher.forward(req, res);
	}
}
