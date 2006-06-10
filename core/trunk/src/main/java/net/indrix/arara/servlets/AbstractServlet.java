/*
 * Created on 08/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.utils.PropertiesManager;

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
public class AbstractServlet extends HttpServlet {

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

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
     * This method sends the user to the login page
     * 
     * @param req The http request object
     * @param res The http response object
     * 
     * @return The next page to send the user to
     * @throws ServletException if any servlet error occurs
     * @throws IOException if any IO error occurs
     */
	protected String userNotLogged(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		List errors = new ArrayList();

		logger.debug("errors is not null.");
		errors.add(ServletConstants.USER_NOT_LOGGED);
		// put errors in request 
		req.setAttribute(ServletConstants.ERRORS_KEY, errors);

		String nextPage = ServletConstants.LOGIN_PAGE;
        return nextPage;
	}
}
