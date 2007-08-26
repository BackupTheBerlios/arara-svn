package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.vo.Family;

public class FamilyModel {

    /**
     * keep the list of ages from database
     */
    private static List familyList;

    /**
     * This method initializes the list, retrieving data from database
     */
    public static void initialize() {
        // retrieve families
        FamilyDAO familyDAO = new FamilyDAO();
        try {
            familyList = familyDAO.retrieve();
        } catch (DatabaseDownException e) {
            familyList = null;
        } catch (SQLException e) {
            familyList = null;
        }
    }

    /**
     * Getter method for the familyList attribute
     * 
     * @return The attribute as a <code>List</code> object
     */
    public static List getFamilyList() {
        return familyList;
    }

    /**
     * This method retrieves an <code>Family</code> object from list
     * 
     * @param id The id of the family to be returned
     * 
     * @return an <code>Family</code> object from list
     */
    public static Family getFamily(int id) {
        Family family = null;
        Iterator it = familyList.iterator();
        boolean found = false;
        while (it.hasNext() && (!found)) {
            family = (Family) it.next();
            if (family.getId() == id) {
                found = true;
            }
        }
        if (!found) {
            family = null;
        }
        return family;
    }

    /**
     * Setter method to the familyList attribute
     * 
     * @param list the new <code>List</code> object
     */
    public static void setFamilyList(List list) {
        familyList = list;
    }


}
