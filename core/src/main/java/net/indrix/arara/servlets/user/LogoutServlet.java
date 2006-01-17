/*
 * Created on 07/11/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.indrix.servlets.ServletConstants;
import net.indrix.vo.User;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LogoutServlet extends HttpServlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");
    
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		HttpSession session = req.getSession(false);

		RequestDispatcher dispatcher = null;
		
		if (session == null){
			// sess�o n�o existe. Usu�rio acessou logout sem ter logado antes
			dispatcher = this.getServletContext().getRequestDispatcher(ServletConstants.ERROR_LOGOUT);
		} else {
			User user = (User)session.getAttribute(ServletConstants.USER_KEY);

			// finaliza sess�o			
			session.invalidate();
			
			// envia usu�rio para p�gina inicial
			dispatcher = this.getServletContext().getRequestDispatcher(ServletConstants.LOGOUT_PAGE);
			
			// coloca usuario no request para JSP processar
			req.setAttribute(ServletConstants.USER_KEY, user);
            
            loggerActions.info("User " + user.getLogin() + " has logged out.");            
		}
		
		dispatcher.forward(req, res);
	}
}
