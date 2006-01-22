/*
 * Created on 02/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.utils.LabelValueBean;
import net.indrix.arara.vo.Specie;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchPhotosBySpecieServlet extends RetrieveFamiliesServlet {
	public void init() {
		logger.debug("Initializing InitSearchPhotosBySpecieServlet...");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		HttpSession session = req.getSession();

		SpecieDAO dao = new SpecieDAO();
		List list = null;
		try {
            logger.debug("Retrieving list of all species...");
			list = dao.retrieve();
            logger.debug("Converting to labelValueBean...");
			list = dataAsLabelValueBean(list);
            logger.debug("Putting to session...");

            UploadPhotoBean uploadBean = new UploadPhotoBean();
			uploadBean.setSpecieList(list);
            session.setAttribute(UploadConstants.UPLOAD_PHOTO_BEAN, uploadBean);            

		} catch (DatabaseDownException e) {
			logger.error(
				"InitSearchPhotosBySpecieServlet.doGet : could not retrieve list of all species",
				e);
		} catch (SQLException e) {
			logger.error(
				"InitSearchPhotosBySpecieServlet.doGet : could not retrieve list of all species",
				e);
		}

		super.doGet(req, res);
	}

	/**
	 * @return
	 */
	protected String getNextPage() {
		return ServletConstants.PHOTO_BY_SPECIE_PAGE;
	}

	/**
	 * @param list
	 * @return
	 */
	private static List dataAsLabelValueBean(List list) {
		List newList = new ArrayList();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Specie s = (Specie) it.next();
			LabelValueBean bean = new LabelValueBean(s.getName(), s.getId());
			newList.add(bean);
		}
		return newList;
	}
}
