/*
 * Created on 08/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.upload;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.common.PhotoBeanManager;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Specie;

import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractUploadPhotoServlet extends AbstractServlet {

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	protected static Logger loggerActions = Logger
			.getLogger("net.indrix.actions");

	/**
	 * @param data
	 * @param bean
	 */
	protected boolean updateBean(Map data, UploadPhotoBean bean, List errors) {
		boolean status = false;

		PhotoBeanManager manager = getBeanManager();
		manager.updateBean(data, bean, errors, true);

		if (!errors.isEmpty()) {
			status = false;
		} else {
			status = true;
		}
		return status;
	}

	/**
	 * Return the bean manager to be used
	 * 
	 * @return a new BeanManager instance
	 */
	protected PhotoBeanManager getBeanManager() {
		return new PhotoBeanManager();
	}

	/**
	 * @param photo
	 */
	protected void addPhotoToDatabase(Photo photo)
			throws DatabaseDownException, SQLException,
			ImageProcessingException {
	}

	/**
	 * @param string
	 * @return
	 */
	protected Specie createSpecie(String specieId, String familyId) {
		Specie specie = new Specie();
		specie.setId(Integer.parseInt(specieId));
		Family family = new Family();
		family.setId(Integer.parseInt(familyId));
		specie.setFamily(family);
		return specie;
	}

	/**
	 * @param string
	 * @return
	 */
	protected Date createDate(String string) throws ParseException {
		Date date = null;
		if ((string != null) && (!string.trim().equals(""))) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			date = format.parse(string);
			logger.debug("Date created as " + date);
		} else {
			logger.debug("Date COULD NOT be created");
		}
		return date;
	}
}
