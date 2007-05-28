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

	private static final String SELECT_NUM_PHOTOS = "SELECT count(*) FROM photo";

	private static final String SELECT_NUM_FAMILIES = "SELECT distinct specie_family_id FROM photo where specie_family_id > -1";

	private static final String SELECT_NUM_SPECIES = "SELECT distinct specie_id FROM photo where specie_id > -1";

	private static final String SELECT_NUM_USERS = "SELECT count(*) FROM user where active = 1";

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
		int number = 0;

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(SELECT_NUM_PHOTOS);
			rs = stmt.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + SELECT_NUM_PHOTOS, e);
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
	public int numberOfFamilies() throws DatabaseDownException, SQLException {
		int number = 0;

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(SELECT_NUM_FAMILIES);
			rs = stmt.executeQuery();
			while (rs.next()) {
				number++;
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + SELECT_NUM_FAMILIES, e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			conn.close();
		}

		return number;
	}

	/**
	 * This method returns the amount of species with photos in database
	 * 
	 * @return the amount of species with photos in database
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public int numberOfSpecies() throws DatabaseDownException, SQLException {
		int number = 0;

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(SELECT_NUM_SPECIES);
			rs = stmt.executeQuery();
			while (rs.next()) {
				number++;
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + SELECT_NUM_SPECIES, e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			conn.close();
		}

		return number;
	}

	/**
	 * This method returns the amount of users in database
	 * 
	 * @return the amount of users in database
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public int numberOfUsers() throws DatabaseDownException, SQLException {
		int number = 0;

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(SELECT_NUM_USERS);
			rs = stmt.executeQuery();
			if (rs.next()) {
				number = rs.getInt(1);
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + SELECT_NUM_USERS, e);
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
