/*
 * Created on 06/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

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

import net.indrix.arara.dao.CommonNameDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchPhotosByCommonNameServlet extends HttpServlet {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * init method for InitSearchPhotosByUserServlet servlet
	 */
	public void init() {
		logger.debug("Initializing InitSearchPhotosByCommonNameServlet...");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List erros = new ArrayList();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		HttpSession session = req.getSession();
		logger.debug("Retrieving data...");
		CommonNameDAO dao = new CommonNameDAO();
		try {
			List list = ServletUtil.commonNameDataAsLabelValueBean(dao
					.retrieve());
			if ((list != null) && (!list.isEmpty())) {
				logger.debug("Setting data in request");

				if ((list != null) && (!list.isEmpty())) {
					logger
							.debug("Common Name data found... putting to session.");
					nextPage = ServletConstants.PHOTO_BY_COMMON_NAME_PAGE;
					session.setAttribute(ServletConstants.COMMON_NAME_LIST_KEY,
							list);
				} else {
					logger.debug("Data not found...");
					erros.add(ServletConstants.DATABASE_ERROR);
					nextPage = ServletConstants.INITIAL_PAGE;
				}
			} else {
				logger.debug("Data not found...");
				erros.add(ServletConstants.DATABASE_ERROR);
				nextPage = ServletConstants.INITIAL_PAGE;
			}
		} catch (DatabaseDownException e) {
			erros.add(ServletConstants.DATABASE_ERROR);
			nextPage = ServletConstants.INITIAL_PAGE;
		} catch (SQLException e) {
			erros.add(ServletConstants.DATABASE_ERROR);
			nextPage = ServletConstants.INITIAL_PAGE;
		}

		if (!erros.isEmpty()) {
			// coloca erros no request para registrar.jsp processar e apresentar
			// mensagem de erro
			req.setAttribute(ServletConstants.ERRORS_KEY, erros);

			// direciona usuário para página de registro novamente
			nextPage = ServletConstants.INITIAL_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}
}
