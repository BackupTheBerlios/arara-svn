/*
 * Created on 02/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.photo.upload;

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

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;

import net.indrix.arara.utils.jsp.Date;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;
/*
 * Created on 02/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

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

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.utils.jsp.Date;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitEditPhotoServlet extends HttpServlet {
	/**
	 * Logger object used to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		Photo photo = (Photo) session.getAttribute(ServletConstants.CURRENT_PHOTO);
		if (photo != null) {
            UploadPhotoBean bean = new UploadPhotoBean();
			updateBean(bean, photo, req);
			session.setAttribute(UploadPhotoConstants.EDIT_BEAN, bean);
			nextPage = ServletConstants.EDIT_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	/**
	 * @param bean
	 * @param photo
	 */
	private void updateBean(UploadPhotoBean bean, Photo photo, HttpServletRequest req) {
        bean.setFamilyList(getFamilyList(req));
        bean.setSelectedFamilyId("" + photo.getSpecie().getFamily().getId());
        bean.setSpecieList(getSpecieList(req, photo.getSpecie().getFamily().getId()));
        bean.setSelectedSpecieId("" + photo.getSpecie().getId());
		bean.setCamera(photo.getCamera());
		bean.setLens(photo.getLens());
        bean.setLocation(photo.getLocation());
		bean.setFilm(photo.getFilm());
		bean.setDate(Date.getDate(photo.getDate()));
        bean.setComment(photo.getComment());
	}

	/**
	 * @param req
	 * @return
	 */
	private List getFamilyList(HttpServletRequest req) {
		HttpSession session = req.getSession();
		UploadBean bean = (UploadBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
		List familyList = null;
		if (bean == null) {
			FamilyDAO dao = new FamilyDAO();
			try {
				List list = dao.retrieve();
				familyList = ServletUtil.familyDataAsLabelValueBean(list);
			} catch (DatabaseDownException e) {
				familyList = null;
			} catch (SQLException e) {
				familyList = null;
			}

		} else {
			familyList = bean.getFamilyList();
		}
		return familyList;
	}

	/**
	 * @param req
	 * @return
	 */
	private List getSpecieList(HttpServletRequest req, int id) {
		HttpSession session = req.getSession();
		UploadBean bean = (UploadBean) session.getAttribute(UploadPhotoConstants.UPLOAD_BEAN);
		List specieList = null;
		if (bean == null) {
			SpecieDAO dao = new SpecieDAO();
			Family f = new Family();
			f.setId(id);
			try {
                List list = dao.retrieveForFamily(f);
				specieList = ServletUtil.specieDataAsLabelValueBean(list);
			} catch (DatabaseDownException e) {
                specieList = null;
			}
		} else {
			specieList = bean.getSpecieList();
		}
		return specieList;
	}
}

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitEditPhotoServlet extends HttpServlet {
	/**
	 * Logger object used to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List errors = new ArrayList();
		List messages = new ArrayList();
		HttpSession session = req.getSession();
		Photo photo = (Photo) session.getAttribute(ServletConstants.CURRENT_PHOTO);
		if (photo != null) {
            UploadPhotoBean bean = new UploadPhotoBean();
			updateBean(bean, photo, req);
			session.setAttribute(UploadPhotoConstants.EDIT_BEAN, bean);
			nextPage = ServletConstants.EDIT_PAGE;
		}
		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

	/**
	 * @param bean
	 * @param photo
	 */
	private void updateBean(UploadPhotoBean bean, Photo photo, HttpServletRequest req) {
        bean.setFamilyList(getFamilyList(req));
        bean.setSelectedFamilyId("" + photo.getSpecie().getFamily().getId());
        bean.setSpecieList(getSpecieList(req, photo.getSpecie().getFamily().getId()));
        bean.setSelectedSpecieId("" + photo.getSpecie().getId());
		bean.setCamera(photo.getCamera());
		bean.setLens(photo.getLens());
        bean.setLocation(photo.getLocation());
		bean.setFilm(photo.getFilm());
		bean.setDate(Date.getDate(photo.getDate()));
        bean.setComment(photo.getComment());
	}

	/**
	 * @param req
	 * @return
	 */
	private List getFamilyList(HttpServletRequest req) {
		HttpSession session = req.getSession();
		UploadBean bean = (UploadBean) session.getAttribute(UploadConstants.UPLOAD_PHOTO_BEAN);
		List familyList = null;
		if (bean == null) {
			FamilyDAO dao = new FamilyDAO();
			try {
				List list = dao.retrieve();
				familyList = ServletUtil.familyDataAsLabelValueBean(list);
			} catch (DatabaseDownException e) {
				familyList = null;
			} catch (SQLException e) {
				familyList = null;
			}

		} else {
			familyList = bean.getFamilyList();
		}
		return familyList;
	}

	/**
	 * @param req
	 * @return
	 */
	private List getSpecieList(HttpServletRequest req, int id) {
		HttpSession session = req.getSession();
		UploadBean bean = (UploadBean) session.getAttribute(UploadPhotoConstants.UPLOAD_BEAN);
		List specieList = null;
		if (bean == null) {
			SpecieDAO dao = new SpecieDAO();
			Family f = new Family();
			f.setId(id);
			try {
                List list = dao.retrieveForFamily(f);
				specieList = ServletUtil.specieDataAsLabelValueBean(list);
			} catch (DatabaseDownException e) {
                specieList = null;
			}
		} else {
			specieList = bean.getSpecieList();
		}
		return specieList;
	}
}
