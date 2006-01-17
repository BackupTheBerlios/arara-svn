/*
 * Created on 10/07/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.log4j;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Log4jInit extends HttpServlet {
    static Logger logger = Logger.getLogger("net.indrix.aves");
    
	public void init() {
        log("Initializing log4j...");
        
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		// if the log4j-init-file is not set, then no point in trying
		if (file != null) {
            log("Configuring log4j..." + prefix + file);
			PropertyConfigurator.configure(prefix + file);
		}
	}

}
