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

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
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

	public void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		List erros = new ArrayList();
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = req.getParameter("toPage");
		String dataToBeUploaded = req.getParameter("data");
		HttpSession session = req.getSession();
		Map data = null;
		try {
			data = parseMultiPartFormData(req);
			String familyId = (String) data.get(ServletConstants.FAMILY_ID);
			if ((familyId == null) || (familyId.equals(""))) {
				erros.add(ServletConstants.SELECT_FAMILY_ERROR);
				UploadPhotoBean uploadBean =
					(UploadPhotoBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
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
						handleList(list, session, familyId, dataToBeUploaded);
                        
                        // put states on request
                        List listOfStates = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
                        req.setAttribute(ServletConstants.STATES_KEY, listOfStates);
                        
					} else {
						logger.debug("Specie list not found...");
						erros.add(ServletConstants.DATABASE_ERROR);
						nextPage = ServletConstants.INITIAL_PAGE;
					}
				} catch (DatabaseDownException e) {
					erros.add(ServletConstants.DATABASE_ERROR);
					nextPage = ServletConstants.INITIAL_PAGE;
				}
			}
			if (!erros.isEmpty()) {
				// coloca erros no request para registrar.jsp processar e apresentar mensagem de erro
				req.setAttribute(ServletConstants.ERRORS_KEY, erros);
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
	private void handleList(
		List list,
		HttpSession session,
		String familyId,
		String dataToBeUploaded) {
           
        UploadBean uploadBean = null;
		if (PHOTO.equals(dataToBeUploaded)) {
			uploadBean =
				(UploadPhotoBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
		} else if (SOUND.equals(dataToBeUploaded)){
            uploadBean =
                (UploadSoundBean) session.getAttribute(UploadConstants.UPLOAD_SOUND_BEAN);
		}
        uploadBean.setSpecieList(list);
        uploadBean.setSelectedFamilyId(familyId);
	}

	private static List retrieveSpecieListForFamilyId(String familyId)
		throws DatabaseDownException {
		SpecieDAO dao = new SpecieDAO();
		Family family = new Family();
		family.setId(Integer.parseInt(familyId));
		List list = ServletUtil.specieDataAsLabelValueBean(dao.retrieveForFamily(family));
		return list;
	}
}
