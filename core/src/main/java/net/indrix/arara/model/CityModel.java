/*
 * Created on 20/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.indrix.arara.dao.CityDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.vo.City;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CityModel {
	/**
	 * keep some cities on memory 
	 */
	private static Map cities;

	/**
	 * This method retrieves an <code>City</code> object from list
	 * 
	 * @param id The id of the state to be returned
	 * 
	 * @return an <code>City</code> object from list
	 */
	public City getCity(int id) throws DatabaseDownException, SQLException {
		Integer intId = new Integer(id);
		City city = (City) cities.get(intId);
		if (city == null) {
			CityDAO dao = new CityDAO();
			city = dao.retrieve(id);
		}
		return city;
	}

    /**
     * Retrieve the list of cities for the given state id
     * 
     * @param id the state id
     * 
     * @return The list of cities for the given state id
     * 
     * @throws DatabaseDownException if database is down
     */
	public List retrieveCitiesForState(int id) throws DatabaseDownException {
		CityDAO dao = new CityDAO();
		List list = dao.retrieve();
		return list;
	}

}
