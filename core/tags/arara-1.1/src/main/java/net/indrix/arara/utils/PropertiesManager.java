/*
 * Created on 19/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PropertiesManager {
	/**
	 * File of properties for WIN system
	 */
	private static String WIN_PROPERTIES_FILE = "files_win.properties";

	/**
	 * File of properties for LINUX system
	 */
	private static String LINUX_PROPERTIES_FILE = "files_linux.properties";

	/**
	 * The temporary folder where images will be created
	 */
	public static String TEMP_FOLDER = "temp.folder";

	/**
	 * The properties
	 */
	public static Properties properties = null;

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * @return
	 */
	private static String getFileToUse() {
		String file = null;
		if (SO.getSO() == SO.WIN) {
			file = WIN_PROPERTIES_FILE;
		} else {
			file = LINUX_PROPERTIES_FILE;
		}
		return file;
	}

	/**
	 * This method returns the given property from properties file
	 * 
	 * @param property
	 *            The property to be retrieved
	 * 
	 * @return the value associated with the property
	 */
	public static String getProperty(String property) {

		String propertyValue = null;
		logger.debug("Loading property " + property);
		if (properties == null) {
			loadPropertiesFromFile();
		}
		propertyValue = (String) properties.get(property);
		if (propertyValue == null) {
			loadPropertiesFromFile();
		}
		logger.debug("Property retrieved with value " + propertyValue);

		return propertyValue;
	}

	/**
	 * This method loads the file that contains the properties
	 * 
	 * @param property
	 */
	private static void loadPropertiesFromFile() {
		String file = getFileToUse();
		try {
//			java.net.URL url = PropertiesManager.class.getResource(file);
//			java.io.FileInputStream input = new java.io.FileInputStream(url.getFile());
//			InputStream input = PropertiesManager.class.getResource(file);
			
			ClassLoader cl = PropertiesManager.class.getClassLoader();
			InputStream input = cl.getResourceAsStream(file);
			
			logger.debug("InputStream = " + input);
			
			if (input != null){
				properties = new Properties();
				properties.load(input);				
			}
			else {
				logger.error("File=" + file + "input=" + input + "Classloader=" + cl.toString());
				throw new IOException("Error reading " + file);
			}
		} catch (IOException e) {
			logger.error("Error reading property", e);
		}
	}

	/**
	 * This method reloads the files of properties
	 */
	public static void reLoad() {
		loadPropertiesFromFile();
	}
}
