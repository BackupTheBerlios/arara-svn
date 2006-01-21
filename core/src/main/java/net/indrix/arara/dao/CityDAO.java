/*
 * Created on 16/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.indrix.arara.model.StatesModel;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.State;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CityDAO {
    /**
     * SQL statement to retrieve all states
     */
    private static final String INSERT = "Insert into city (state_id, name) values (?, ?)";

    /**
     * SQL statement to retrieve all cities
     */
    private static final String SELECT_ALL = "Select * from city order by name";

    /**
     * SQL statement to retrieve the city with the given id
     */
    private static final String SELECT_BY_ID = "Select * from city where id = ? order by name";
    
    /**
     * SQL statement to retrieve all states for a given state
     */
    private static final String SELECT_FOR_STATE = "Select * from city where state_id = ? order by name";
    
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * Insert a new City into database
     * 
     * @param city The city object to be inserted to database
     * 
     * @throws DatabaseDownException if database is down
     */
    public void insert(City city) throws DatabaseDownException {
        logger.debug("CityDAO.insert : entering method...");
        List list = new ArrayList();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            logger.debug("StatesDAO.insert : running SQL " + INSERT);
            stmt = conn.prepareStatement(INSERT);
            stmt.setInt(1, city.getState().getId());
            stmt.setString(2, city.getName());
            stmt.execute();

            // retrieve id just created
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                city.setId(rs.getInt(1));
            } else {
                // throw an exception from here
            }           
        } catch (SQLException e) {
            logger.debug("SQLException !", e);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new DatabaseDownException();
            }
        }
        logger.debug("StatesDAO.insert : finishing method...");
    }

    /**
     * Retrieve all states from database
     * 
     * @return a <code>List</code> object with <code>State</code>
     * 
     * @throws DatabaseDownException if database is down
     */
    public List retrieve() throws DatabaseDownException {
        logger.debug("CityDAO.retrieve : entering method...");
        List list = new ArrayList();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            logger.debug("StatesDAO.retrieve : running SQL " + SELECT_ALL);
            stmt = conn.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String acronym = rs.getString("acronym");
                String description = rs.getString("description");
                State state = new State();
                state.setId(id);
                state.setAcronym(acronym);
                state.setDescription(description);

                list.add(state);
                logger.debug("Adding state: " + state);
            }
        } catch (SQLException e) {
            logger.debug("SQLException !", e);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new DatabaseDownException();
            }
        }
        logger.debug("StatesDAO.retrieve : finishing method...");
        return list;
    }

    /**
     * This method retrieves a <code>Specie</code> object based on its id
     * 
     * @param id The id of the <code>Specie</code>
     * 
     * @return a <code>Specie</code> object 
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public City retrieve(int id) throws DatabaseDownException, SQLException {
        logger.debug("CityDAO.retrieve : entering method...");
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        City city = null;
        try {
            logger.debug("CityDAO.retrieve : running SQL " + SELECT_ALL);
            stmt = conn.prepareStatement(SELECT_ALL);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
                int stateId = rs.getInt("state_id");
                String name = rs.getString("name");
                city = new City();
                city.setId(id);
                city.setName(name);
                city.setState(StatesModel.getState(stateId));
            }
        } catch (SQLException e) {
            logger.debug("SQLException !", e);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new DatabaseDownException();
            }
        }
        logger.debug("CityDAO.retrieve : finishing method...");
        return city;
    }

    /**
     * Retrieve all cities from database, for the given state
     * 
     * @return a <code>List</code> object with <code>City</code>
     * 
     * @throws DatabaseDownException if database is down
     */
    public List retrieveForState(int id) throws DatabaseDownException {
        logger.debug("CityDAO.retrieveForState : entering method...");
        List list = new ArrayList();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            logger.debug("CityDAO.retrieveForState : running SQL " + SELECT_FOR_STATE);
            stmt = conn.prepareStatement(SELECT_FOR_STATE);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
                String name = rs.getString("name");
                int stateId = rs.getInt("state_id");
                City city = new City();
                city.setId(id);
                city.setName(name);
                city.setState(StatesModel.getState(stateId));

                list.add(city);
                logger.debug("Adding city: " + city);
            }
        } catch (SQLException e) {
            logger.debug("SQLException !", e);
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            try {
                conn.close();
            } catch (SQLException e1) {
                throw new DatabaseDownException();
            }
        }
        logger.debug("CityDAO.retrieveForState : finishing method...");
        return list;
    }

    /**
     * This method closes the given statement
     * 
     * @param stmt The <code>PreparedStatement</code> objec to be closed
     */
    protected void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                // ignore
            }
        }
    }

    /**
     * This method closes the given result set
     * 
     * @param stmt The <code>ResultSet</code> objec to be closed
     */
    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                // ignore
            }
        }
    }


}
