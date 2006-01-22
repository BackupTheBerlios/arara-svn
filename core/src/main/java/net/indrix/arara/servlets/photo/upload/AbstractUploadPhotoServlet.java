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
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Specie;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractUploadPhotoServlet
	extends net.indrix.arara.servlets.AbstractServlet {

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");
	protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");

	/**
	 * @param data
	 * @param bean
	 */
	protected boolean updateBean(Map data, UploadPhotoBean bean, List errors) {
		boolean status = false;

		updateBirdData(data, bean, errors);
		updatePhotoData(data, bean, errors);

		if (!errors.isEmpty()) {
			status = false;
		} else {
			status = true;
		}
		return status;
	}

	/**
	 * This method updates in the bean data related to the photo
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updatePhotoData(Map data, UploadPhotoBean bean, List errors) {
		logger.debug("Updating photo data...");
		logger.debug(bean);
		logger.debug(data);
		bean.setCamera((String) data.get(ServletConstants.CAMERA));
		bean.setLens((String) data.get(ServletConstants.LENS));
		bean.setFilm((String) data.get(ServletConstants.FILM));
		bean.setLocation((String) data.get(ServletConstants.LOCATION));
		bean.setDate((String) data.get(ServletConstants.DATE));
		bean.setFilename((String) data.get(UploadConstants.FILE_NAME));
		bean.setFileSize((String) data.get(UploadConstants.FILE_SIZE));
		bean.setComment((String) data.get(ServletConstants.COMMENT));
		FileItem fileItem = (FileItem) data.get(UploadConstants.FILE_ITEM);
		if (fileItem == null) {
			errors.add(UploadConstants.FILE_REQUIRED);
		}
		bean.setFileItem(fileItem);
	}

	/**
	 * This method updates in the bean data related to the bird
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateBirdData(Map data, UploadPhotoBean bean, List errors) {
		logger.debug("Updating bird data...");
		String familyId = (String) data.get(ServletConstants.FAMILY_ID);
		String specieId = (String) data.get(ServletConstants.SPECIE_ID);
		if ((specieId == null) || (specieId.trim().equals(""))) {
			errors.add(UploadPhotoConstants.SPECIE_REQUIRED);
		}
		bean.setSelectedFamilyId(familyId);
		bean.setSelectedSpecieId(specieId);
		bean.setSelectedAgeId((String) data.get(ServletConstants.AGE_ID));
		bean.setSelectedSexId((String) data.get(ServletConstants.SEX_ID));
	}

	/** 
	 * @param photo 
	 */
	protected void addPhotoToDatabase(Photo photo)
		throws DatabaseDownException, SQLException, ImageProcessingException {
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
