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
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.servlets.ServletConstants;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoBeanManager extends BeanManager {
	protected void updateMediaData(Map data, UploadBean bean, List errors,
			boolean validate) {
		logger
				.debug("PhotoBeanManager.updateMediaData: updating media data...");
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
				.debug("PhotoBeanManager.updateMediaData: updating media data...");
		logger.debug(bean);
		logger.debug(data);
		bean.setCamera((String) data.get(ServletConstants.CAMERA));
		bean.setLens((String) data.get(ServletConstants.LENS));
		bean.setFilm((String) data.get(ServletConstants.FILM));
		bean.setDate((String) data.get(ServletConstants.DATE));
	}

}
