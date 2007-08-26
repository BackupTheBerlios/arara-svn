/*
 * Created on 12/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.FamilyModel;
import net.indrix.arara.model.PhotoUtil;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.model.SpecieModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.model.file.AbstractFileManager;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class InitServlet extends HttpServlet {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	private static final String WIDTH_PARAM = "width";

	private static final int DEFAULT_WIDTH_PARAM = 100;

	public void init() {
		logger.debug("Initializing InitServlet...");

		log("Retrieving width from config...");
		logger.debug("Retrieving width from config...");

		try {
			int width = Integer.parseInt(this.getInitParameter(WIDTH_PARAM));
			log("Setting width with value " + width);
			PhotoUtil.setSmallWidth(width);

			ServletContext context = this.getServletContext();
			AgeModel.initialize();
			context.setAttribute(ServletConstants.AGE_KEY, AgeModel.getAges());

			SexModel.initialize();
			context.setAttribute(ServletConstants.SEX_KEY, SexModel.getSex());

			StatesModel.initialize();
			context.setAttribute(ServletConstants.STATES_KEY, StatesModel.getStates());

            FamilyModel.initialize();
            context.setAttribute(ServletConstants.FAMILY_LIST_KEY, FamilyModel.getFamilyList());
            
            SpecieModel.initialize();
            context.setAttribute(ServletConstants.SPECIE_LIST_KEY, SpecieModel.getSpecieList());
            
			String link = AbstractFileManager.getRootLink();
			context.setAttribute("linkKey", link);
            
            context.setAttribute("maintenance", false);
		} catch (Exception e) {
			log("Setting width with default value " + WIDTH_PARAM);
			PhotoUtil.setSmallWidth(DEFAULT_WIDTH_PARAM);
		}

	}
}
