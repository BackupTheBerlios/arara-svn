/*
 * Created on 20/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SexDAO;
import net.indrix.arara.vo.Sex;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SexModel {

    /**
     * keep the list of ages from database 
     */
    private static List sexList;
    
    /**
     * This method initializes the list, retrieving data from database
     */
    public static void initialize(){
        // retrieve sex 
        SexDAO sexDAO = new SexDAO();
        try {
            sexList = sexDAO.retrieve();
        } catch (DatabaseDownException e) {
            sexList = null;
        }
    }

    /**
     * Getter method for the sex attribute
     * 
     * @return The attribute as a <code>List</code> object
     */
    public static List getSex() {
        return sexList;
    }

    /**
     * This method retrieves an <code>Sex</code> object from list
     * 
     * @param id The id of the sex to be returned
     * 
     * @return an <code>Sex</code> object from list
     */
    public static Sex getSex(int id){
        Sex sex = null;
        Iterator it = sexList.iterator();
        boolean found = false;
        while (it.hasNext() && (!found)){
            sex = (Sex)it.next();
            if (sex.getId() == id){
                found = true;   
            }
        }
        if (!found){
            sex = null;
        }
        return sex;
    }

    /**
     * Setter method to the sex attribute
     * 
     * @param list the new <code>List</code> object
     */
    public static void setSex(List list) {
        sexList = list;
    }

}
