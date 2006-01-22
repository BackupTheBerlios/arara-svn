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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

/**
 * @author alunos
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RegisterServlet extends HttpServlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
        logger.debug("RegisterServlet.doPost called...");

		List erros = new ArrayList();

		// recupera dados do request
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String confirmationPassword = req.getParameter("password2");
        boolean emailOnPhoto = "on".equals(req.getParameter("emailOnNewPhoto")) ? true : false;
        boolean emailOnSound = "on".equals(req.getParameter("emailOnNewSound")) ? true : false;
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String msg = null;

		// verifica se as senhas digitadas estão iguais
        logger.debug("Verifying if passwords match");
		if (password.equals(confirmationPassword)) {
			// verifica se login já existe
			UserModel userModel = new UserModel();
			try {
                logger.debug("Verifying if user exists");
				if (userModel.userExists(login)) {
                    logger.debug("User exists");
					// login já existe. Usuário tem que escolher outro.
					erros.add(ServletConstants.LOGIN_ALREADY_EXIST);
				} else {
                    logger.debug("User does not exist. Validating data...");
					User user = null;
					user = new User();
					user.setName(name);
					user.setEmail(email);
					user.setLogin(login);
					user.setPassword(password);
                    user.setEmailOnNewPhoto(emailOnPhoto);
                    user.setEmailOnNewSound(emailOnSound);
					if (user.validar()) {
                        logger.debug("Data validated...");
						try {
							userModel.insert(user);

                            logger.debug("User saved in database " + user);
							// cria sessão e coloca usuário
							HttpSession session = req.getSession(true);
							session.setAttribute(ServletConstants.USER_KEY, user);

							// direciona usuário para página inicial
							dispatcher =
								context.getRequestDispatcher(ServletConstants.REGISTERED_PAGE);
						} catch (DatabaseDownException e1) {
							erros.add(ServletConstants.DATABASE_ERROR);
						} catch (SQLException e1) {
							erros.add(ServletConstants.DATABASE_ERROR);
						}
					} else {
						erros.add(ServletConstants.FIELDS_REQUIRED);
					}
				}
			} catch (DatabaseDownException e) {
				erros.add(ServletConstants.DATABASE_ERROR);
			} catch (SQLException e) {
				erros.add(ServletConstants.DATABASE_ERROR);
			}
		} else {
			// segunda senha não bate com a primeira
			erros.add(ServletConstants.PASSWORD_MISMATCH);
		}

		if (!erros.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, erros);
			req.setAttribute(ServletConstants.USER_KEY, null);

			// direciona usuário para página de registro novamente
			dispatcher = context.getRequestDispatcher(ServletConstants.REGISTER_PAGE);
		}
		dispatcher.forward(req, res);
	}
}
