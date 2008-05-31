/*
 * Created on 08/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.model.UserNotFoundException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.User;

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
@SuppressWarnings("serial")
public class AbstractServlet extends HttpServlet {

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");
    
    @SuppressWarnings("unchecked")
    protected HashMap parseFormData(HttpServletRequest request) {
        HashMap aData = new HashMap();
        Enumeration<String> it = request.getParameterNames();
        while (it.hasMoreElements()){
            String param = it.nextElement();
            String values[] = request.getParameterValues(param);
            if (values.length == 1){
                aData.put(param, values[0]);
                logger.debug("Adding (single value)" + param + " | " + values[0]);
            } else {
                aData.put(param, values);
                logger.debug("Adding (multiple values)" + param + " | " + values);
            }
        }
        return aData;
    }
    
	@SuppressWarnings("unchecked")
    protected HashMap parseMultiPartFormData(HttpServletRequest request)
			throws ServletException, IOException, FileUploadException {
		logger.debug("Entering parseMultiPartFormData(HttpServletRequest)");
        HashMap aData = null;
        if (request != null && request.getContentType() != null && request.getContentType().contains("multipart/form-data")){
            aData = new HashMap();
            String localRepository = PropertiesManager.getProperty(PropertiesManager.TEMP_FOLDER);
            
            logger.debug(" - Temporary folder: " + localRepository);
            DefaultFileItemFactory factory = new DefaultFileItemFactory(1000000,new java.io.File(localRepository));

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
                    logger.debug("Adding data to map " + key + ","
                            + item.getString().trim());
                } else {
                    String file = item.getName();
                    if ((file != null) && ((!file.trim().equals("")))) {
                        int index = file.lastIndexOf("\\");
                        String filepath = "";
                        if (index > 0) {
                            filepath = file.substring(0, index);
                        }
                        String filename = item.getName().substring(
                                item.getName().lastIndexOf("\\") + 1);

                        aData.put(UploadConstants.FILE_NAME, filename);
                        aData.put(UploadConstants.FILE_URL, filepath);
                        aData.put(UploadConstants.FILE_SIZE, String.valueOf(item
                                .getSize()));
                        aData.put(UploadConstants.FILE_ITEM, item);
                    }
                }
            }
            if (aData.isEmpty()) {
                logger.debug("DATA IS EMPTY");
            }
        } else {
            aData = parseFormData(request);
        }
		logger.debug(" - Leaving parseMultiPartFormData");
		return aData;
	}

	/**
	 * This method sends the user to the login page
	 * 
	 * @param req
	 *            The http request object
	 * @param res
	 *            The http response object
	 * 
	 * @return The next page to send the user to
	 * @throws ServletException
	 *             if any servlet error occurs
	 * @throws IOException
	 *             if any IO error occurs
	 */
	protected String userNotLogged(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();

		logger.debug("errors is not null.");
		errors.add(ServletConstants.USER_NOT_LOGGED);
		// put errors in request
		req.setAttribute(ServletConstants.ERRORS_KEY, errors);

        String nextResourceToExecute = ServletUtil.getResource(req);
        req.setAttribute(ServletConstants.NEXT_RESOURCE_AFTER_LOGIN, nextResourceToExecute);
        
		String nextPage = ServletConstants.LOGIN_PAGE;
		return nextPage;
	}
    
    /**
     * This method verifies if there is a cookie in the request for the user's login
     * 
     * @param req The request from user
     * 
     * @return The user's login if it exists, or null otherwise
     */
    protected String userHasCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        String login = null;
        for (int i = 0; (cookies != null) && i < cookies.length; i++){
            if (ServletConstants.LOGIN_COOKIE_ID.equals(cookies[i].getName())){
                login = cookies[i].getValue();
            }
        }
        return login;
    }
    
    /**
     * Retrieves a user from database, given his/her login
     * 
     * @param login The user's login
     * 
     * @return A user object, or null if the login is not valid.
     */
    protected User getUserFromDatabase(String login) {
        logger.info("Retrieving user " + login + " from DB...");
        UserModel model = new UserModel();
        User user = null;
        try {
            user = model.retrieve(login);
        } catch (DatabaseDownException e) {
            logger.error("Error retrieving user [" + login + "] from DB, given login from cookie");
        } catch (SQLException e) {
            logger.error("Error retrieving user [" + login + "] from DB, given login from cookie");
        } catch (UserNotFoundException e) {
            logger.error("Error retrieving user [" + login + "] from DB, given login from cookie");
        }
        return user;
    }
    
    public static void updateCitiesListForState(UploadBean uploadBean)
    throws DatabaseDownException {
        String stateIdAsStr = uploadBean.getSelectedStateId();
        if (stateIdAsStr != null && stateIdAsStr.length() > 0) {
            CityModel model = new CityModel();
            List list = model.retrieveCitiesForState(Integer.parseInt(stateIdAsStr));
            uploadBean.setCitiesList(list);
        }
    }

    public static void updateSpeciesListForFamily(UploadBean uploadBean)
    throws DatabaseDownException {
        String familyIdAsStr = uploadBean.getSelectedFamilyId();
        if (familyIdAsStr != null && familyIdAsStr.length() > 0) {
            SpecieDAO dao = new SpecieDAO();
            Family family = new Family();
            family.setId(Integer.parseInt(familyIdAsStr));
            List list = ServletUtil.specieForFamilyDataAsLabelValueBean(dao.retrieveForFamily(family));
            uploadBean.setSpecieList(list);
        }
    }    
    
}
