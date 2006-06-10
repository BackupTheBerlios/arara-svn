/*
 * Created on 08/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Specie;

import org.apache.commons.fileupload.DefaultFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AbstractUploadServlet extends HttpServlet {

    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");
    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");

	protected HashMap parseMultiPartFormData(HttpServletRequest request)
		throws ServletException, IOException, FileUploadException {
		logger.debug(" - Entering parseMultiPartFormData(HttpServletRequest)");
		String localRepository = PropertiesManager.getProperty(PropertiesManager.TEMP_FOLDER);
		HashMap aData = new HashMap();
		logger.debug(" - Temporary folder: " + localRepository);
		DefaultFileItemFactory factory =
			new DefaultFileItemFactory(1000000, new java.io.File(localRepository));

		FileUpload upload = new FileUpload(factory);
		List fields = null;

		fields = upload.parseRequest(request);

		FileItem item = null;
		Iterator it = fields.iterator();
		while (it.hasNext()) {
			item = (FileItem) it.next();

			if (item.isFormField()) {
				String key = item.getFieldName();
				aData.put(key, item.getString().trim());
				logger.debug("Adding data to map " + key + "," + item.getString().trim());
			} else {
				String file = item.getName();
				if ((file != null) && ((!file.trim().equals("")))) {
					int index = file.lastIndexOf("\\");
					String filepath = "";
					if (index > 0) {
						filepath = file.substring(0, index);
					}
					String filename =
						item.getName().substring(item.getName().lastIndexOf("\\") + 1);

					aData.put(UploadConstants.FILE_NAME, filename);
					aData.put(UploadConstants.FILE_URL, filepath);
					aData.put(UploadConstants.FILE_SIZE, String.valueOf(item.getSize()));
					aData.put(UploadConstants.FILE_ITEM, item);
				}
			}
		}
		logger.debug(" - Leaving parseMultiPartFormData");
		if (aData.isEmpty()) {
			logger.debug("DATA IS EMPTY");
		}
		return aData;
	}

	/**
	 * @param data
	 * @param bean
	 */
	protected boolean updateBean(Map data, UploadSoundBean bean, List errors) {
		boolean status = false;
		String familyId = (String) data.get(UploadSoundConstants.FAMILY_ID);
		String specieId = (String) data.get(UploadSoundConstants.SPECIE_ID);
		if ((specieId == null) || (specieId.trim().equals(""))) {
			errors.add(UploadSoundConstants.SPECIE_REQUIRED);
		}
        bean.setSelectedFamilyId(familyId);
        bean.setSelectedSpecieId(specieId);
        bean.setFilename((String) data.get(UploadConstants.FILE_NAME));
        bean.setFileSize((String) data.get(UploadConstants.FILE_SIZE));
		FileItem fileItem = (FileItem) data.get(UploadConstants.FILE_ITEM);
		if (fileItem == null) {
			errors.add(UploadConstants.FILE_REQUIRED);
		}
        bean.setFileItem(fileItem);
        bean.setLocation((String) data.get(UploadSoundConstants.LOCATION));
        bean.setCityId((String) data.get(UploadSoundConstants.CITY_ID));
        bean.setStateId((String) data.get(UploadSoundConstants.STATE_ID));
        bean.setComment((String) data.get(UploadSoundConstants.COMMENT));
        bean.setSelectedAgeId((String) data.get(UploadSoundConstants.AGE_ID));
        bean.setSelectedSexId((String) data.get(UploadSoundConstants.SEX_ID));
		if (!errors.isEmpty()) {
			status = false;
		} else {
			status = true;
		}
		return status;
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
        if ((string != null) && (!string.trim().equals(""))){
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(string);
            logger.debug("Date created as " + date);
        } else {
            logger.debug("Date COULD NOT be created");
        }
        return date;
    }
}
