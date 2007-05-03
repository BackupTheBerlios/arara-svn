/*
 * Created on 04/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.ListBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.Family;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class RetrieveSpeciesForFamilyServlet extends AbstractServlet {
	/**
	 * Logger object to be used by this servlet to log statements
	 */
	private static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List <String>errors = new ArrayList<String>();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String pageToShow = req.getParameter(ServletConstants.PAGE_TO_SHOW_KEY);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        String action = req.getParameter(ServletConstants.ACTION);

        logger.debug("RetrieveSpeciesForFamilyServlet:entering doPost method...");
		HttpSession session = req.getSession();
		Map data = null;
		try {
			data = parseMultiPartFormData(req);
			String familyId = (String) data.get(ServletConstants.FAMILY_ID);
			if ((familyId == null) || (familyId.equals(""))) {
                logger.debug("family id not found...");
				errors.add(ServletConstants.SELECT_FAMILY_ERROR);
                ListBean listBean = (ListBean) session.getAttribute(ServletConstants.FAMILY_LIST_KEY);
                listBean.setSelectedId(null);
			} else {
				logger.debug("selected family id " + familyId);

				try {
					List list = retrieveSpecieListForFamilyId(familyId);

					if ((list != null) && (!list.isEmpty())) {
						logger.debug("Setting specie list in request");
						logger.debug("Setting data in request");
						handleList(list, data, req, errors);
                        
                        req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);
                        req.setAttribute(ServletConstants.ACTION, action);
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
                        logger.debug(servletToCall + " | " + action + " | " + pageToShow);
					} else {
						logger.debug("Specie list not found...");
						errors.add(ServletConstants.DATABASE_ERROR);
						nextPage = ServletConstants.INITIAL_PAGE;
					}
				} catch (DatabaseDownException e) {
					logger.fatal("DatabaseDownException when retrieving species for family...",e);
					errors.add(ServletConstants.DATABASE_ERROR);
					nextPage = ServletConstants.INITIAL_PAGE;
				}
			}
			if (!errors.isEmpty()) {
				// coloca erros no request para registrar.jsp processar e
				// apresentar mensagem de erro
				req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			}
		} catch (ServletException e) {
            logger.error("ServletException", e);
		} catch (IOException e) {
            logger.error("IOException", e);
		} catch (FileUploadException e) {
            logger.error("FileUploadException", e);
		}
        logger.debug("Forwarding to page " + nextPage);
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

	/**
	 * @param list
	 * @param dataToBeUploaded
	 */
	private void handleList(List list, Map data, HttpServletRequest req, List <String>errors) {

		HttpSession session = req.getSession();
		String dataToBeUploaded = req.getParameter("data");
		String action = req.getParameter("action");

        logger.debug("Calling factory with " + dataToBeUploaded);
        UploadBeanManagerFactory factory = UploadBeanManagerFactory.getInstance();
        IBeanManager manager = factory.createBean(dataToBeUploaded, action, session);
        if (manager != null){
            manager.updateBean(data, errors, false);
            
            String familyId = (String) data.get(ServletConstants.FAMILY_ID);
            manager.setData(familyId, "Family ID");
            manager.setData(list, "Specie List");
        } else {
            // just searching data... no data to be uploaded
            ListBean bean = new ListBean();
            bean.setList(list);
            bean.setSelectedId(null);
            session.setAttribute(ServletConstants.SPECIE_LIST_KEY, bean);
            ListBean listBean = (ListBean) session.getAttribute(ServletConstants.FAMILY_LIST_KEY);
            String familyId = (String) data.get(ServletConstants.FAMILY_ID);
            listBean.setSelectedId(familyId);            
        }
	}

	private static List retrieveSpecieListForFamilyId(String familyId)
			throws DatabaseDownException {
		SpecieDAO dao = new SpecieDAO();
		Family family = new Family();
		family.setId(Integer.parseInt(familyId));
		List list = ServletUtil.specieForFamilyDataAsLabelValueBean(dao.retrieveForFamily(family));
		return list;
	}
}
