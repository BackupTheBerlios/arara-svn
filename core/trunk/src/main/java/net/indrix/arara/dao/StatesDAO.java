/*
 * Created on 10/01/2006
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

import net.indrix.arara.vo.State;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StatesDAO {
	/**
	 * SQL statement to retrieve all states
	 */
	private static final String SELECT_ALL = "Select * from states where id != 99 order by acronym ";

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * Retrieve all states from database
	 * 
	 * @return a <code>List</code> object with <code>State</code>
	 * 
	 * @throws DatabaseDownException
	 *             if database is down
	 */
	public List retrieve() throws DatabaseDownException {
		logger.debug("StatesDAO.retrieve : entering method...");
		List <State>list = new ArrayList<State>();
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
