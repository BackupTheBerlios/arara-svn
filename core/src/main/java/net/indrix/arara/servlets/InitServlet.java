/*
 * Created on 12/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets;

import javax.servlet.http.HttpServlet;

import net.indrix.model.AgeModel;
import net.indrix.model.PhotoUtil;
import net.indrix.model.SexModel;
import net.indrix.model.StatesModel;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitServlet extends HttpServlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    private static final String WIDTH_PARAM = "width";
    private static final int DEFAULT_WIDTH_PARAM = 100;
	public void init(){
        logger.debug("Initializing InitServlet...");
        
        log("Retrieving width from config...");
        logger.debug("Retrieving width from config...");
        try {
            int width = Integer.parseInt(this.getInitParameter(WIDTH_PARAM));
            log("Setting width with value " + width);
            PhotoUtil.setSmallWidth(width);

            AgeModel.initialize();
            this.getServletContext().setAttribute(ServletConstants.AGE_KEY, AgeModel.getAges());
            
            SexModel.initialize();
            this.getServletContext().setAttribute(ServletConstants.SEX_KEY, SexModel.getSex());
            
            StatesModel.initialize();
            this.getServletContext().setAttribute(ServletConstants.STATES_KEY, StatesModel.getStates());
        } catch (Exception e){
            log("Setting width with default value " + WIDTH_PARAM);
            PhotoUtil.setSmallWidth(DEFAULT_WIDTH_PARAM);
        }
        
	}
}
