/*
 * Created on 02/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.indrix.arara.utils.LabelValueBean;
import net.indrix.arara.vo.CommonName;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.State;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ServletUtil {
	static Logger logger = Logger.getLogger("net.indrix.aves");

	private static List <LabelValueBean>familyListInMemory = null;

	private static List <LabelValueBean>specieListInMemory = null;

	private static List <LabelValueBean>statesListInMemory = null;

	private static List <LabelValueBean>commonNamesListInMemory = null;

    /**
     * Retrieve and do any needed treatment to the text to search
     * 
     * @param req The request from user
     * 
     * @return The String with the text entered by user
     */
    public static String retrieveTextToSearch(HttpServletRequest req) {
        String text = req.getParameter(ServletConstants.TEXT_ID);
        if (text == null){
            text = "";
        } else {
            text = text.trim().toLowerCase();
            if (text.length() > 0){
                text += "%";                
            }
        }
        return text;
    }
    
	/**
	 * This method retrives from the request the resource that was requested
	 * from the user
	 * 
	 * @param req
	 *            The servlet request with the user requests
	 * 
	 * @return A string object with the resource requested by user.
	 */
	public static String getResource(HttpServletRequest req) {
		String queryString = req.getQueryString();
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		int start = uri.indexOf(contextPath) + contextPath.length();
		String nextResourceToExecute = uri.substring(start) + "?" + queryString;
		return nextResourceToExecute;
	}

	/**
	 * @param list
	 * @return
	 */
	public static List familyDataAsLabelValueBean(List list) {
		if (familyListInMemory == null) {
			logger.debug("Creating family data as LabelValueBean for the first time...");
			familyListInMemory = new ArrayList<LabelValueBean>();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Family f = (Family) it.next();
				String label = f.getName();
				if (f.getSubFamilyName() != null) {
					label += " - " + f.getSubFamilyName();
				}
				LabelValueBean bean = new LabelValueBean(label, f.getId());
				familyListInMemory.add(bean);
			}
			familyListInMemory.add(0, new LabelValueBean("", ""));
		} else {
			logger
					.debug("Returning the family data as LabelValueBean without creating objects...");
		}
		return familyListInMemory;
	}

	/**
	 * @param list
	 * @return
	 */
	public static List specieDataAsLabelValueBean(List list) {
		if (specieListInMemory == null) {
			logger
					.debug("Creating species data as LabelValueBean for the first time...");
			specieListInMemory = new ArrayList<LabelValueBean>();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				Specie s = (Specie) it.next();
				LabelValueBean bean = new LabelValueBean(s.getName(), s.getId());
				specieListInMemory.add(bean);
			}
			specieListInMemory.add(0, new LabelValueBean("", ""));
		} else {
			logger
					.debug("Returning the species data as LabelValueBean without creating objects...");
		}
		return specieListInMemory;
	}

	/**
	 * @param list
	 * @return
	 */
	public static List specieForFamilyDataAsLabelValueBean(List list) {
		List <LabelValueBean>newList = new ArrayList<LabelValueBean>();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Specie s = (Specie) it.next();
			LabelValueBean bean = new LabelValueBean(s.getName(), s.getId());
			newList.add(bean);
		}
		return newList;
	}

	/**
	 * @param list
	 * @return
	 */
	public static List commonNameDataAsLabelValueBean(List list) {
		if (commonNamesListInMemory == null) {
			logger.debug("Creating common names data as LabelValueBean for the first time...");
			commonNamesListInMemory = new ArrayList<LabelValueBean>();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				CommonName c = (CommonName) it.next();
				String label = c.getName();
				LabelValueBean bean = new LabelValueBean(label, c.getId());
				commonNamesListInMemory.add(bean);
			}
			commonNamesListInMemory.add(0, new LabelValueBean("", ""));
		} else {
			logger
					.debug("Returning the common names data as LabelValueBean without creating objects...");
		}
		return commonNamesListInMemory;
	}

     /**
     * @param list
     * @return
     */
    public static List userDataAsLabelValueBean(List list) {
        List <LabelValueBean>newList = new ArrayList<LabelValueBean>();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            User user = (User) it.next();
            LabelValueBean bean = new LabelValueBean(user.getName(), user.getId());
            newList.add(bean);
            logger.debug("adding bean to list " + bean);
        }
        return newList;
    }
    
	/**
	 * @param list
	 * @return
	 */
	public static List statesDataAsLabelValueBean(List list) {
		if (statesListInMemory == null) {
			logger.debug("Creating states data as LabelValueBean for the first time...");
			statesListInMemory = new ArrayList<LabelValueBean>();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				State state = (State) it.next();
				String label = state.getAcronym();
				LabelValueBean bean = new LabelValueBean(label, state.getId());
				statesListInMemory.add(bean);
			}
			statesListInMemory.add(0, new LabelValueBean("", ""));
		} else {
			logger
					.debug("Returning the states data as LabelValueBean without creating objects...");
		}
		return statesListInMemory;
	}
}
