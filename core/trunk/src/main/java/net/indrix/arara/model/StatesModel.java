/*
 * Created on 10/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.StatesDAO;
import net.indrix.arara.vo.State;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StatesModel {

	/**
	 * keep the list of ages from database
	 */
	private static List states;

	/**
	 * This method initializes the list, retrieving data from database
	 */
	public static void initialize() {
		// retrieve states
		StatesDAO statesDAO = new StatesDAO();
		try {
			states = statesDAO.retrieve();
		} catch (DatabaseDownException e) {
			states = null;
		}
	}

	/**
	 * Getter method for the states attribute
	 * 
	 * @return The attribute as a <code>List</code> object
	 */
	public static List getStates() {
		return states;
	}

	/**
	 * This method retrieves an <code>State</code> object from list
	 * 
	 * @param id
	 *            The id of the state to be returned
	 * 
	 * @return an <code>State</code> object from list
	 */
	public static State getState(int id) {
		State state = null;
		Iterator it = states.iterator();
		boolean found = false;
		while (it.hasNext() && (!found)) {
			state = (State) it.next();
			if (state.getId() == id) {
				found = true;
			}
		}
		if (!found) {
			state = null;
		}
		return state;
	}

	/**
	 * This method retrieves an <code>State</code> object from list, given its
	 * acronym
	 * 
	 * @param acronym
	 *            The acronym of the state to be returned
	 * 
	 * @return an <code>State</code> object from list
	 */
	public static State getState(String acronym) {
		State state = null;
		Iterator it = states.iterator();
		boolean found = false;
		while (it.hasNext() && (!found)) {
			state = (State) it.next();
			if (state.getAcronym().equals(acronym)) {
				found = true;
			}
		}
		if (!found) {
			state = null;
		}
		return state;
	}

	/**
	 * Setter method to the states attribute
	 * 
	 * @param list
	 *            the new <code>List</code> object
	 */
	public static void setStates(List list) {
		states = list;
	}

}
