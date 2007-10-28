/*
 * Created on 20/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.common;

import java.util.List;
import java.util.Map;

import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoBeanManager extends BeanManager {
	/**
	 * This method updates in the bean data related to the photo
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateMediaData(Map data, List errors, boolean validate) {
		logger.debug("PhotoBeanManager.updateMediaData: updating media data...");
        
        UploadPhotoBean bean = (UploadPhotoBean)getBean();
		logger.debug(bean);
		logger.debug(data);
		bean.setCamera((String) data.get(ServletConstants.CAMERA));
		bean.setLens((String) data.get(ServletConstants.LENS));
		bean.setFilm((String) data.get(ServletConstants.FILM));
		
        String date = (String) data.get(ServletConstants.DATE); 
        
        if (validate) {
            if ((date == null) || (date.trim().equals(""))) {
                errors.add(UploadPhotoConstants.DATE_REQUIRED);
            }
        }        
        bean.setDate(date);
	}

}
