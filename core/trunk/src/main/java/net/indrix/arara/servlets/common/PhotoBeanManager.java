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
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoBeanManager extends BeanManager {
    private static final String validExtensions[] = { "jpg", "JPG" };

    /**
	 * This method updates in the bean data related to the photo
	 * 
	 * @param data
	 * @param bean
	 * @param errors
	 */
	protected void updateMediaData(Map data, List<String> errors, boolean validate) {
		logger.debug("Updating media data...");
        
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
                logger.debug("DATE_REQUIRED...");
            }
            if (errors.isEmpty() && !checkExtension(bean.getFilename())) {
                errors.add(UploadConstants.INVALID_FILE);
                logger.debug("INVALID_FILE...");
            }

        }        
        bean.setDate(date);
	}
    /**
     * This method verifies if the given file is valid
     * 
     * @param filename
     *            The file uploaded by user
     * 
     * @return true if file extension is valid, false otherwise
     */
    protected boolean checkExtension(String filename) {
        boolean valid = false;
        if (filename != null){
            int index = filename.lastIndexOf(".");
            if (index > 0) {
                String extension = filename.substring(index + 1);
                logger.debug("Extension : " + extension);
                for (int i = 0; i < (validExtensions.length) && (!valid); i++) {
                    if (validExtensions[i].equals(extension)) {
                        valid = true;
                    }
                }
            } else {
                valid = false;
            }            
        }
        return valid;
    }

}
