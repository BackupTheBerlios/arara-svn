/*
 * Created on 20/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.common;

import java.util.List;
import java.util.Map;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BeanManager {
	/**
	 * Logger object to be used by this servlet to log statements
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * @param data
	 * @param bean
	 */
	public boolean updateBean(Map data, UploadBean bean, List errors,
			boolean validate) {
		boolean status = false;

		updateBirdData(data, bean, errors, validate);
		updateMediaData(data, bean, errors, validate);

		if (!errors.isEmpty()) {
			status = false;
		} else {
			status = true;
		}
		return status;
	}

	/**
	 * This method updates in the bean data related to the bird
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateBirdData(Map data, UploadBean bean, List errors,
			boolean validate) {
		logger.debug("BeanManager.updateBirdData: updating bird data...");
		String familyId = (String) data.get(ServletConstants.FAMILY_ID);
		String specieId = (String) data.get(ServletConstants.SPECIE_ID);
		String stateId = (String) data.get(ServletConstants.STATE_ID);
		String cityId = (String) data.get(ServletConstants.CITY_ID);
		if (validate) {
			if ((specieId == null) || (specieId.trim().equals(""))) {
				errors.add(UploadPhotoConstants.SPECIE_REQUIRED);
			}
			if ((stateId == null) || (stateId.trim().equals(""))) {
				errors.add(UploadPhotoConstants.STATE_REQUIRED);
			}
			if ((cityId == null) || (cityId.trim().equals(""))) {
				errors.add(UploadPhotoConstants.CITY_REQUIRED);
			}
		}
		bean.setSelectedFamilyId(familyId);
		bean.setSelectedSpecieId(specieId);
		bean.setSelectedCityId(cityId);
		bean.setSelectedStateId(stateId);
		bean.setSelectedAgeId((String) data.get(ServletConstants.AGE_ID));
		bean.setSelectedSexId((String) data.get(ServletConstants.SEX_ID));

		bean.setLocation((String) data.get(ServletConstants.LOCATION));
		bean.setFilename((String) data.get(UploadConstants.FILE_NAME));
		bean.setFileSize((String) data.get(UploadConstants.FILE_SIZE));
		bean.setComment((String) data.get(ServletConstants.COMMENT));
		FileItem fileItem = (FileItem) data.get(UploadConstants.FILE_ITEM);
		if (validate && (fileItem == null)) {
			errors.add(UploadConstants.FILE_REQUIRED);
		}
		bean.setFileItem(fileItem);
	}

	/**
	 * This method updates in the bean data related to the photo
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateMediaData(Map data, UploadBean bean, List errors,
			boolean validate) {
		logger.debug("BeanManager.updateMediaData: updating media data...");
	}

}
