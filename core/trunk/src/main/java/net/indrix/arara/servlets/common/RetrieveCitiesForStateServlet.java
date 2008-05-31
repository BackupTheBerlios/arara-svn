/*
 * Created on 18/01/2006
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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;

import org.apache.commons.fileupload.FileUploadException;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class RetrieveCitiesForStateServlet extends AbstractServlet {
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws ServletException, IOException {
        doPost(req, res);
    }
    
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List <String>errors = new ArrayList<String>();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String pageToShow = req.getParameter(ServletConstants.PAGE_TO_SHOW_KEY);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        String action = req.getParameter(ServletConstants.ACTION);
        
        logger.debug("servletToCall" +servletToCall);
        logger.debug("action" +action);

		Map data = null;
		try {
			data = parseMultiPartFormData(req);
			String stateId = (String) data.get(ServletConstants.STATE_ID);
			if ((stateId == null) || (stateId.equals(""))) {
				errors.add(ServletConstants.SELECT_FAMILY_ERROR);
			} else {
				logger.debug("selected state id " + stateId);

				try {
					List list = retrieveCityListForStateId(stateId);

					if ((list != null) && (!list.isEmpty())) {
						logger.debug("Setting data in request");
                        
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
                        req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);
                        req.setAttribute(ServletConstants.ACTION, action);
                        req.setAttribute(ServletConstants.CITIES_KEY, list);
                        req.setAttribute(ServletConstants.SELECTED_STATE_KEY, stateId);
					} else {
						logger.debug("Cities list not found...");
						errors.add(ServletConstants.DATABASE_ERROR);
						nextPage = ServletConstants.INITIAL_PAGE;
					}
				} catch (DatabaseDownException e) {
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
        logger.debug("Forwarding to page " + nextPage);
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

	/**
	 * @param stateId
	 * @return
	 */
	private List retrieveCityListForStateId(String stateId)
			throws NumberFormatException, DatabaseDownException {
		CityModel model = new CityModel();
		List list = model.retrieveCitiesForState(Integer.parseInt(stateId));
		return list;
	}

}
