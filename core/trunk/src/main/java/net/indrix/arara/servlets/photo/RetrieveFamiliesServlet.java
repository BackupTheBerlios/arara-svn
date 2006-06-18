/*
 * Created on 14/05/2005
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class RetrieveFamiliesServlet extends AbstractServlet {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List erros = new ArrayList();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
		if (testeUser() && (user == null)) {
			logger.debug("InitUploadPhotoForIdentificationServlet.doGet : user is NOT logged...");
			// The method userNotLogged takes care of user not logged trying to
			// access options for
			// logged users
			nextPage = userNotLogged(req, res);
		} else {
			logger.debug("Retrieving data...");
			FamilyDAO dao = new FamilyDAO();

			try {
				List list = ServletUtil.familyDataAsLabelValueBean(dao.retrieve());
				if ((list != null) && (!list.isEmpty())) {
					logger.debug("Setting data in request");

					// handle the list, creating a bean and adding it to session
					handleListOfFamilies(session, list);

					// set next page to go
					nextPage = getNextPage();
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
				// coloca erros no request para registrar.jsp processar e
				// apresentar mensagem de erro
				req.setAttribute(ServletConstants.ERRORS_KEY, erros);

				// direciona usuário para página de registro novamente
				nextPage = ServletConstants.INITIAL_PAGE;
			}
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

	/**
	 * By default, user shall not be validated, since this is a search option
	 * 
	 * @return false
	 */
	protected boolean testeUser() {
		return false;
	}

	/**
	 * This method retrieves or creates a bean, set the list in it and put the
	 * bean to the session
	 * 
	 * @param session
	 *            Session to store the bean
	 * @param list
	 *            The list retrieved from database
	 */
	protected void handleListOfFamilies(HttpSession session, List list) {
		UploadPhotoBean uploadBean = (UploadPhotoBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
		if (uploadBean == null) {
			uploadBean = new UploadPhotoBean();
		}
		uploadBean.setFamilyList(list);

		// add bean to session
		session.setAttribute(UploadConstants.UPLOAD_PHOTO_BEAN, uploadBean);
	}

	/**
	 * @return
	 */
	abstract protected String getNextPage();

}
