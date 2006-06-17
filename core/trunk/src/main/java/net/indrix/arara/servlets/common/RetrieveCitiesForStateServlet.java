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

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrieveCitiesForStateServlet extends AbstractServlet {
	/**
	 * Logger object to be used by this servlet to log statements
	 */
	private static Logger logger = Logger.getLogger("net.indrix.aves");

	private static final String PHOTO = "PHOTO";

	private static final String SOUND = "SOUND";

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		List errors = new ArrayList();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = req.getParameter("toPage");

		HttpSession session = req.getSession();
		Map data = null;
		try {
			data = parseMultiPartFormData(req);
			String stateId = (String) data.get(ServletConstants.STATE_ID);
			if ((stateId == null) || (stateId.equals(""))) {
				errors.add(ServletConstants.SELECT_FAMILY_ERROR);
				UploadPhotoBean uploadBean = (UploadPhotoBean) session
						.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
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
		dispatcher = context.getRequestDispatcher(nextPage);
		dispatcher.forward(req, res);
	}

	/**
	 * @param list
	 * @param dataToBeUploaded
	 */
	private void handleList(List list, Map data, HttpServletRequest req,
			List errors) {

		String dataToBeUploaded = req.getParameter("data");
		String action = req.getParameter("action");
		HttpSession session = req.getSession();

		String beanKey = null;

		UploadBean uploadBean = null;
		if (PHOTO.equals(dataToBeUploaded)) {
			if (UploadConstants.UPLOAD_ACTION.equals(action)) {
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : uploading photo");
				beanKey = UploadConstants.UPLOAD_PHOTO_BEAN;
			} else if (UploadConstants.EDIT_ACTION.equals(action)) {
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : editing photo");
				beanKey = UploadPhotoConstants.EDIT_BEAN;
			}

			uploadBean = (UploadPhotoBean) session.getAttribute(beanKey);
			if (uploadBean == null) {
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : uploadBean is null. Creating a new one...");
				uploadBean = new UploadPhotoBean();
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : putting bean in session");
				session.setAttribute(beanKey, uploadBean);
			}
			PhotoBeanManager manager = new PhotoBeanManager();
			logger
					.debug("RetrieveCitiesForStateServlet.handleList : calling manager.updateBean... ");
			manager.updateBean(data, (UploadPhotoBean) uploadBean, errors,
					false);
		} else if (SOUND.equals(dataToBeUploaded)) {
			if (UploadConstants.UPLOAD_ACTION.equals(action)) {
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : uploading sound");
				beanKey = UploadConstants.UPLOAD_SOUND_BEAN;
			}

			uploadBean = (UploadSoundBean) session.getAttribute(beanKey);
			if (uploadBean == null) {
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : uploadBean is null. Creating a new one...");
				uploadBean = new UploadSoundBean();
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : putting bean in session");
				session.setAttribute(beanKey, uploadBean);
			}
			SoundBeanManager manager = new SoundBeanManager();
			logger
					.debug("RetrieveCitiesForStateServlet.handleList : calling manager.updateBean... ");
			manager.updateBean(data, (UploadSoundBean) uploadBean, errors,
					false);
		}
		uploadBean.setCitiesList(list);

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
