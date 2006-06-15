/*
 * Created on 25/04/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractResourceBundle {
	/**
	 * Logger object to be used by this class.
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * The map with locales as key, HashMap objects as values
	 */
	private static Map bundles = new HashMap();

	protected static AbstractResourceBundle instance;

	/**
	 * Getter method to the unique instance of the class
	 * 
	 * @return
	 */
	public static synchronized AbstractResourceBundle getInstance() {
		return null;
	}

	/**
	 * This method retrieves the value for the given key, for the given locale
	 * 
	 * @param key
	 *            The key to search
	 * @param l
	 *            The locale
	 * 
	 * @return The value corresponding to the key
	 */
	public String getString(String key, Locale l) { 

		Map map = (Map) bundles.get(l);
		if (map == null) {
			logger.debug("Could not find map for Locale " + l);
			map = loadBundle(getBundleToLoad(), l);
			bundles.put(l, map);
		}

		String s = null;
		s = (String) map.get(key);
		return s;
	}

	/**
	 * load the bundle given by baseBundle, for the given locale
	 * 
	 * @param baseBundle
	 *            The base bundle name
	 * @param l
	 *            The bundle locale
	 * 
	 * @return A Map object with the contents of the given baseBundle
	 */
	private synchronized Map loadBundle(String baseBundle, Locale l) {
		logger.debug("AbstractResourceBundle.loadBundle : loadBundle "
				+ baseBundle + " | " + l);
		ResourceBundle bundle = ResourceBundle.getBundle(baseBundle, l);
		Map map = new HashMap();
		if (bundle != null) {
			Enumeration enumList = bundle.getKeys();
			while (enumList.hasMoreElements()) {
				String key = (String) enumList.nextElement();
				String value = bundle.getString(key);
				map.put(key, value);
			}
		} else {
			logger.debug("Bundle " + baseBundle + " not found for locale " + l);
		}
		return map;
	}

	/**
	 * 
	 */
	protected abstract String getBundleToLoad();
}
