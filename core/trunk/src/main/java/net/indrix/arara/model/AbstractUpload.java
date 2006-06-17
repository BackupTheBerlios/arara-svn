/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import net.indrix.arara.utils.PropertiesManager;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AbstractUpload {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * This method creates a String representing a filename
     * 
     * @return a filename
     */
    protected static String createFilename() {
        double d = Math.random();
        String filename = "file" + d + ".jpg";
        String tempFolder = PropertiesManager.getProperty(PropertiesManager.TEMP_FOLDER);
        return tempFolder + filename;
    }

}
