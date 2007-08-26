package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.vo.Specie;

public class SpecieModel {
    /**
     * keep the list of ages from database
     */
    private static List specieList;

    /**
     * This method initializes the list, retrieving data from database
     */
    public static void initialize() {
        // retrieve families
        SpecieDAO familyDAO = new SpecieDAO();
        try {
            specieList = familyDAO.retrieve();
        } catch (DatabaseDownException e) {
            specieList = null;
        } catch (SQLException e) {
            specieList = null;
        }
    }

    /**
     * Getter method for the familyList attribute
     * 
     * @return The attribute as a <code>List</code> object
     */
    public static List getSpecieList() {
        return specieList;
    }

    /**
     * This method retrieves an <code>Specie</code> object from list
     * 
     * @param id The id of the family to be returned
     * 
     * @return an <code>Specie</code> object from list
     */
    public static Specie getSpecie(int id) {
        Specie family = null;
        Iterator it = specieList.iterator();
        boolean found = false;
        while (it.hasNext() && (!found)) {
            family = (Specie) it.next();
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
    public static void setSpecieList(List list) {
        specieList = list;
    }


}
