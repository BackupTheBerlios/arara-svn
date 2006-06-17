/*
 * Created on 04/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

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

import net.indrix.arara.bean.IdentifyPhotoBean;
import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.common.BeanManager;
import net.indrix.arara.servlets.common.PhotoBeanManager;
import net.indrix.arara.servlets.photo.identification.IdentificationPhotoConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;
import net.indrix.arara.vo.Family;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrieveSpeciesForFamilyServlet extends AbstractServlet {
	/**
	 * Logger object to be used by this servlet to log statements
	 */
	private static Logger logger = Logger.getLogger("net.indrix.aves");

	private static final String PHOTO = "PHOTO";

	private static final String SOUND = "SOUND";

	private static final String PHOTO_IDENTIFICATION = "PHOTO_IDENTIFICATION";

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
			String familyId = (String) data.get(ServletConstants.FAMILY_ID);
			if ((familyId == null) || (familyId.equals(""))) {
				errors.add(ServletConstants.SELECT_FAMILY_ERROR);
				UploadPhotoBean uploadBean = (UploadPhotoBean) session
						.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
				uploadBean.setSelectedFamilyId(null);
				uploadBean.setSelectedSpecieId(null);
				uploadBean.setSpecieList(null);
			} else {
				logger.debug("selected family id " + familyId);

				try {
					List list = retrieveSpecieListForFamilyId(familyId);

					if ((list != null) && (!list.isEmpty())) {
						logger.debug("Setting specie list in request");
						logger.debug("Setting data in request");
						handleList(list, data, req, errors);

					} else {
						logger.debug("Specie list not found...");
						errors.add(ServletConstants.DATABASE_ERROR);
						nextPage = ServletConstants.INITIAL_PAGE;
					}
				} catch (DatabaseDownException e) {
					logger
							.fatal(
									"DatabaseDownException when retrieving species for family...",
									e);
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

		HttpSession session = req.getSession();
		String dataToBeUploaded = req.getParameter("data");
		String action = req.getParameter("action");
		String beanKey = null;
		UploadBean uploadBean = null;
		if (PHOTO.equals(dataToBeUploaded)) {
			if ((action == null)
					|| (UploadConstants.UPLOAD_ACTION.equals(action))) {
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
				uploadBean = new UploadPhotoBean();
				session.setAttribute(beanKey, uploadBean);
			}
			PhotoBeanManager manager = new PhotoBeanManager();
			manager.updateBean(data, (UploadPhotoBean) uploadBean, errors,
					false);

		} else if (PHOTO_IDENTIFICATION.equals(dataToBeUploaded)) {
			beanKey = IdentificationPhotoConstants.IDENTIFICATION_PHOTO_BEAN;
			IdentifyPhotoBean bean = (IdentifyPhotoBean) session
					.getAttribute(beanKey);
			if (bean == null) {
				bean = new IdentifyPhotoBean();
				session.setAttribute(beanKey, bean);
			}
			String familyId = (String) data.get(ServletConstants.FAMILY_ID);
			String specieId = (String) data.get(ServletConstants.SPECIE_ID);

			bean.setSelectedFamilyId(familyId);
		} else if (SOUND.equals(dataToBeUploaded)) {
			if ((action == null)
					|| (UploadConstants.UPLOAD_ACTION.equals(action))) {
				logger
						.debug("RetrieveCitiesForStateServlet.handleList : uploading sound");
				beanKey = UploadConstants.UPLOAD_SOUND_BEAN;
			}

			uploadBean = (UploadSoundBean) session.getAttribute(beanKey);
			if (uploadBean == null) {
				uploadBean = new UploadSoundBean();
				session.setAttribute(beanKey, uploadBean);
			}
			BeanManager manager = new BeanManager();
			manager.updateBean(data, (UploadSoundBean) uploadBean, errors,
					false);
		}
		uploadBean.setSpecieList(list);
		String familyId = (String) data.get(ServletConstants.FAMILY_ID);
		uploadBean.setSelectedFamilyId(familyId);
		logger
				.debug("RetrieveCitiesForStateServlet.handleList : setting selectedFamilyId "
						+ familyId);
	}

	private static List retrieveSpecieListForFamilyId(String familyId)
			throws DatabaseDownException {
		SpecieDAO dao = new SpecieDAO();
		Family family = new Family();
		family.setId(Integer.parseInt(familyId));
		List list = ServletUtil.specieForFamilyDataAsLabelValueBean(dao
				.retrieveForFamily(family));
		return list;
	}
}
