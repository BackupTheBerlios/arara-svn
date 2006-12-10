/*
 * Created on 04/02/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.common;

import java.util.List;
import java.util.Map;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;

import org.apache.commons.fileupload.FileItem;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoForIdentificationBeanManager extends PhotoBeanManager {
	/**
	 * This method updates in the bean data related to the bird
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateBirdData(Map data, UploadBean bean, List <String>errors,
			boolean validate) {
		logger
				.debug("PhotoForIdentificationBeanManager.updateBirdData: updating bird data...");
		String stateId = (String) data.get(ServletConstants.STATE_ID);
		String cityId = (String) data.get(ServletConstants.CITY_ID);
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
		if (validate) {
			if ((cityId == null) || (cityId.trim().equals(""))) {
				errors.add(UploadPhotoConstants.CITY_REQUIRED);
			}
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
		logger
				.debug("PhotoForIdentificationBeanManager.updateMediaData: updating media data...");
		updateMediaData(data, (UploadPhotoBean) bean, errors, validate);
	}

	/**
	 * This method updates in the bean data related to the photo
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateMediaData(Map data, UploadPhotoBean bean, List errors,
			boolean validate) {
		logger
				.debug("PhotoForIdentificationBeanManager.updateMediaData: updating media data...");
		logger.debug(bean);
		logger.debug(data);
		bean.setCamera((String) data.get(ServletConstants.CAMERA));
		bean.setLens((String) data.get(ServletConstants.LENS));
		bean.setFilm((String) data.get(ServletConstants.FILM));
		bean.setDate((String) data.get(ServletConstants.DATE));
	}

}
