/*
 * Created on 20/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.model;

import java.util.Iterator;
import java.util.List;

import net.indrix.dao.AgeDAO;
import net.indrix.dao.DatabaseDownException;
import net.indrix.vo.Age;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AgeModel {

    /**
     * keep the list of ages from database 
     */
    private static List ages;
    
    /**
     * This method initializes the list, retrieving data from database
     */
    public static void initialize(){
        // retrieve age 
        AgeDAO ageDAO = new AgeDAO();
        try {
			ages = ageDAO.retrieve();
		} catch (DatabaseDownException e) {
            ages = null;
		}
    }
	/**
     * Getter method for the ages attribute
     * 
	 * @return The attribute as a <code>List</code> object
	 */
	public static List getAges() {
		return ages;
	}

    /**
     * This method retrieves an <code>Age</code> object from list
     * 
     * @param id The id of the age to be returned
     * 
     * @return an <code>Age</code> object from list
     */
    public static Age getAge(int id){
        Age age = null;
        Iterator it = ages.iterator();
        boolean found = false;
        while (it.hasNext() && (!found)){
            age = (Age)it.next();
            if (age.getId() == id){
                found = true;   
            }
        }
        if (!found){
            age = null;
        }
        return age;
    }

	/**
     * Setter method to the ages attribute
     * 
	 * @param list the new <code>List</code> object
	 */
	public static void setAges(List list) {
		ages = list;
	}

}
