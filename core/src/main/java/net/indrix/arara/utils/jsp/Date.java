/*
 * Created on 19/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.utils.jsp;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Date {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");
    
    public static final String FORMAT = "dd/MM/yyyy";
    public static final String FORMAT_WITH_TIME = "dd/MM/yyyy - HH:mm";
    public static String getDate(java.util.Date date){
        logger.debug("converting date " + date);
        String d = "";
        if (date != null){ 
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
            d = formatter.format(date);
            logger.debug("date converted to " + d);
        }
        return d;
    }

    public static String getDateTime(java.util.Date date){
        logger.debug("converting date " + date);
        String d = "";
        if (date != null){ 
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_WITH_TIME);
            d = formatter.format(date);
            logger.debug("date converted to " + d);
        }
        return d;
    }
}
