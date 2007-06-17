package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.BirdListDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.servlets.birdlist.exception.BirdListDuplicatedNameException;
import net.indrix.arara.vo.BirdList;

/**
 * THis class provides the public methods to deal with lists
 * 
 * @author Jeff
 */
public class BirdListModel {
    /**
     * The DAO object to access the database
     */
    private BirdListDAO dao = new BirdListDAO();

    /**
     * This method inserts a new BirdList to the database
     * 
     * @param list The BirdList object to be inserted into the database
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void insert(BirdList list) throws DatabaseDownException, SQLException {
        try {
            dao.insert(list);
        } catch (DatabaseDownException e) {
            throw e;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062){
                throw new BirdListDuplicatedNameException();
            } else {
                throw e;
            }
            
        }            
    }

    /**
     * This method deletes a BirdList from the database
     * 
     * @param listId The BirdList ID to be deleted from the database
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void delete(int listId) throws DatabaseDownException, SQLException {
        dao.delete(listId);
    }    
    /**
     * This method updates a list into database
     * 
     * @param list THe list to be updated
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void update(BirdList list) throws DatabaseDownException, SQLException{        
        dao.update(list);
    }
    
    /**
     * Ttis method retrieves a BirdList object given its id
     *  
     * @param id The list id
     * 
     * @return a BirdList object given its id
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public BirdList retrieve(int id) throws DatabaseDownException, SQLException {
        BirdList birdList = (BirdList) dao.retrieve(id);
        return birdList;
    }

    /**
     * Ttis method retrieves all public lists
     *  
     * @return a BirdList object given its id
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List<BirdList> retrieve() throws DatabaseDownException, SQLException {
        List<BirdList> list = dao.retrieve();
        return list;
    }
    
    /**
     * Ttis method retrieves all lists for a given user 
     *  
     * @return all BirdList objects for the given user
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List<BirdList> retrieveListsForUser(int userId) throws DatabaseDownException, SQLException {
        List<BirdList> list = dao.retrieveForUser(userId);
        return list;
    }
    
}
