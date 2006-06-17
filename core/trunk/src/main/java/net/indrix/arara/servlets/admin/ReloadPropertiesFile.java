/*
 * Created on 11/10/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.utils.PropertiesManager;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ReloadPropertiesFile extends HttpServlet {
	/**
	 * The logger object to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.debug("ReloadPropertiesFile.doGet called");
		PropertiesManager.reLoad();
		logger.debug("properties reloaded");
	}
}
