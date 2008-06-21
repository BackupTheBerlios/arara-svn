/*
 * Created on 09/11/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StatisticsDAO {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.passaros");

	private static final String SELECT_NUM_PHOTOS = "SELECT count(*) " +
            "FROM photo";

    private static final String SELECT_NUM_SOUNDS = "SELECT count(*) " +
            "FROM sound";

	private static final String SELECT_NUM_FAMILIES_WITH_PHOTO = "SELECT distinct specie_family_id " +
            "FROM photo " +
            "WHERE specie_family_id > -1";

    private static final String SELECT_NUM_FAMILIES_WITH_SOUND = "SELECT distinct sp.family_id " +
            "FROM sound s, specie sp, family f " +
            "WHERE s.specie_id = sp.id and sp.family_id = f.id and sp.family_id > -1";

	private static final String SELECT_NUM_SPECIES_WITH_PHOTO = "SELECT distinct specie_id " +
            "FROM photo " +
            "WHERE specie_id > -1";

    private static final String SELECT_NUM_SPECIES_WITH_SOUND = "SELECT distinct specie_id " +
            "FROM sound " +
            "WHERE specie_id > -1";

	private static final String SELECT_NUM_USERS = "SELECT count(*) " +
            "FROM user " +
            "WHERE active = 1";

    private static final String SELECT_NUM_USERS_WITH_PHOTO = "SELECT count(*) " +
            "FROM user " +
            "WHERE active = 1 and addPhoto = 1 and name not like 'Teste%'";

    private static final String SELECT_NUM_USERS_WITH_SOUND = "SELECT count(*) " +
            "FROM user " +
            "WHERE active = 1 and addSound = 1 and name not like 'Teste%'";

    private static final String SELECT_TOTAL_NUM_OF_SPECIES = "SELECT count(*) from specie";
    
	/**
	 * This method returns the amount of photos in database
	 * 
	 * @return the amount of photos in database
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public int numberOfPhotos() throws DatabaseDownException, SQLException {
        return numberOfRows(SELECT_NUM_PHOTOS);
	}

    /**
     * This method returns the amount of sounds in database
     * 
     * @return the amount of sounds in database
     * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public int numberOfSounds() throws DatabaseDownException, SQLException {
        return numberOfRows(SELECT_NUM_SOUNDS);
    }
  
	/**
	 * This method returns the amount of families with photos in database
	 * 
	 * @return the amount of families with photos in database
	 * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
	 */
	public int numberOfFamiliesWithPhoto() throws DatabaseDownException, SQLException {
        return numberOfRowsByCounting(SELECT_NUM_FAMILIES_WITH_PHOTO);
	}

    /**
     * This method returns the amount of families with sounds in database
     * 
     * @return the amount of families with sounds in database
     * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public int numberOfFamiliesWithSound() throws DatabaseDownException, SQLException {
        return numberOfRowsByCounting(SELECT_NUM_FAMILIES_WITH_SOUND);
    }

    /**
     * This method returns the amount of species in database
     * 
     * @return the amount of species in database
     * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public int numberOfSpecies() throws DatabaseDownException, SQLException {
        return numberOfRows(SELECT_TOTAL_NUM_OF_SPECIES);
    }
    
	/**
	 * This method returns the amount of species with photos in database
	 * 
	 * @return the amount of species with photos in database
	 * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
	 */
	public int numberOfSpeciesWithPhoto() throws DatabaseDownException, SQLException {
        return numberOfRowsByCounting(SELECT_NUM_SPECIES_WITH_PHOTO);
	}

    /**
     * This method returns the amount of species with sounds in database
     * 
     * @return the amount of species with sounds in database
     * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public int numberOfSpeciesWithSound() throws DatabaseDownException, SQLException {
        return numberOfRowsByCounting(SELECT_NUM_SPECIES_WITH_SOUND);
    }

	/**
	 * This method returns the amount of users in database
	 * 
	 * @return the amount of users in database
	 * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
	 */
	public int numberOfUsers() throws DatabaseDownException, SQLException {
        return numberOfRows(SELECT_NUM_USERS);
	}

    /**
     * This method returns the amount of users with photo in database
     * 
     * @return the amount of users with photo in database
     * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public int numberOfUsersWithPhoto() throws DatabaseDownException, SQLException {
        return numberOfRows(SELECT_NUM_USERS_WITH_PHOTO);
    }

    /**
     * This method returns the amount of users with sounds in database
     * 
     * @return the amount of users with sounds in database
     * 
     * @throws DatabaseDownException  If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public int numberOfUsersWithSound() throws DatabaseDownException, SQLException {
        return numberOfRows(SELECT_NUM_USERS_WITH_SOUND);
    }

    /**
     * This method returns the amount of rows in database, for the given select statement
     * 
     * @param sql The select statement
     * 
     * @return the amount of rows in database
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    private int numberOfRows(String sql) throws DatabaseDownException, SQLException {
        int number = 0;

        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                number = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("StatisticsDAO.numberOfRows : could not retrieve data ");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }

        return number;
    }
    
    /**
     * This method returns the amount of families with photos in database
     * 
     * @return the amount of families with photos in database
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    private int numberOfRowsByCounting(String sql) throws DatabaseDownException, SQLException {
        int number = 0;

        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                number++;
            }
        } catch (SQLException e) {
            logger.error("StatisticsDAO.numberOfRowsByCounting : could not retrieve data ");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }

        return number;
    }
    
	/**
	 * This method closes the given statement
	 * 
	 * @param stmt
	 *            The <code>PreparedStatement</code> objec to be closed
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
	 * @param stmt
	 *            The <code>ResultSet</code> objec to be closed
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
