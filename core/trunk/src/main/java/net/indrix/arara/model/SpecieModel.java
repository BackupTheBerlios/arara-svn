package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.SpecieHasPhoto;

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
        SpecieDAO specieDAO = new SpecieDAO();
        try {
            specieList = specieDAO.retrieve();
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

    /**
     * This method retrieves all species that has a photo
     * 
     * 
     * @return a <code>List</code> object with <code>Specie</code> objects inside.
     * 
     * @throws DatabaseDownException If the database is down
     */
    public List retrieveSpeciesWithPhoto() throws DatabaseDownException {
        // retrieve species
        List specieList;
        SpecieDAO specieDAO = new SpecieDAO();
        try {
            specieList = specieDAO.retrieveSpeciesWithPhoto();
        } catch (DatabaseDownException e) {
            specieList = null;
        } catch (SQLException e) {
            specieList = null;
        } 
        return specieList;
    }

    /**
     * This method retrieves all species that has a photo
     * 
     * 
     * @return a <code>List</code> object with <code>Specie</code> objects inside.
     * 
     * @throws DatabaseDownException If the database is down
     */
    public List retrieveSpeciesWithoutPhoto() throws DatabaseDownException {
        // retrieve species
        List specieList;
        SpecieDAO specieDAO = new SpecieDAO();
        try {
            specieList = specieDAO.retrieveSpeciesWithoutPhoto();
        } catch (DatabaseDownException e) {
            specieList = null;
        } catch (SQLException e) {
            specieList = null;
        } 
        return specieList;
    }
    
    /**
     * This method retrieves all species, and a flag whether the specie has a photo or not
     * 
     * 
     * @return a <code>List</code> object with <code>Specie</code> objects inside.
     * 
     * @throws DatabaseDownException If the database is down
     */
    public List retrieveAllSpeciesWithPhoto() throws DatabaseDownException {
        // retrieve species
        List specieList;
        SpecieDAO specieDAO = new SpecieDAO();
        try {
            specieList = specieDAO.retrieveSpeciesWithPhotoFlag();
        } catch (DatabaseDownException e) {
            specieList = null;
        } catch (SQLException e) {
            specieList = null;
        } 
        return specieList;
    }


}
