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
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;

import org.apache.commons.fileupload.FileUploadException;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class RetrieveCitiesForStateServlet extends AbstractServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List <String>errors = new ArrayList<String>();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String pageToShow = req.getParameter(ServletConstants.PAGE_TO_SHOW_KEY);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        
        logger.debug("VALORES>>>");
        String [] values = req.getParameterValues("selectedCities");
        for (int i = 0; values != null && i < values.length; i++){
            logger.debug(values[i]);
        }

        values = req.getParameterValues("cityId");
        for (int i = 0; values != null && i < values.length; i++){
            logger.debug(values[i]);
        }

		HttpSession session = req.getSession();
		Map data = null;
		try {
			data = parseMultiPartFormData(req);
			String stateId = (String) data.get(ServletConstants.STATE_ID);
			if ((stateId == null) || (stateId.equals(""))) {
				errors.add(ServletConstants.SELECT_FAMILY_ERROR);
                String key = UploadConstants.UPLOAD_PHOTO_BEAN;
				UploadPhotoBean uploadBean = (UploadPhotoBean) session.getAttribute(key);
				uploadBean.setSelectedFamilyId(null);
				uploadBean.setSelectedSpecieId(null);
				uploadBean.setSpecieList(null);
			} else {
				logger.debug("selected state id " + stateId);

				try {
					List list = retrieveCityListForStateId(stateId);

					if ((list != null) && (!list.isEmpty())) {
						logger.debug("Setting city list in request");
						logger.debug("Setting data in request");
						handleList(list, data, req, errors);
                        
                        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                        req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
                        req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);
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
	 * @param list
	 * @param dataToBeUploaded
	 */
	@SuppressWarnings("unchecked")
    private void handleList(List list, Map data, HttpServletRequest req, List <String>errors) {

		String dataToBeUploaded = req.getParameter("data");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

        UploadBeanManagerFactory factory = UploadBeanManagerFactory.getInstance();
        IBeanManager manager = factory.createBean(dataToBeUploaded, action, session);
        manager.updateBean(data, errors, false);
        manager.setData(list, "City List");

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
