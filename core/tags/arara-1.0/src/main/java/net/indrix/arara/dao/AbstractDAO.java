/*
 * Created on 06/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractDAO {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * This method inserts a new photo to the database.
     *  
     * @param photo The photo to be added
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void insert(Object o) throws DatabaseDownException, SQLException {
        insertObject(o, getInsertSQL());
    }

    /**
     * This method inserts a new object to the database.
     *  
     * @param id The id of the object to be deleted
     */
    public void delete(int id) throws DatabaseDownException, SQLException {
        deleteObject(id, getDeleteSQL());
    }

    /**
     * This method saves the current object object into database
     * 
     * @param o The object to be updated into database
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public void update(Object o) throws DatabaseDownException, SQLException {
        updateObject(o, getUpdateSQL());
    }

    /**
     * This method retrieves all objects from database. It uses the following SQL: <br>
     *      SELECT * FROM <table>
     *  
     * @return A list of objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieve() throws DatabaseDownException, SQLException {
        List list = retrieveObject(getSelectAllSQL());
        return list;
    }

    /**
     * This method retrieves all ids from database. It uses the following SQL: <br>
     *      SELECT id FROM <table>
     *  
     * @return A list of ids, as Integer objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveIDs() throws DatabaseDownException, SQLException {
        List list = retrieveIDs(getSelectAllIDsSQL());
        return list;
    }


    /**
     * This method returns the SQL statement to select all ids from database
     * 
     * @return the SelectALLIDs sql
     */
    protected String getSelectAllIDsSQL() {
		return null;
	}

	/**
     * This method returns the SQL statement to select all object from database
     * 
	 * @return the SelectALL sql
	 */
	protected String getSelectAllSQL() {
		return null;
	}

	/**
     * This method returns the SQL statement to insert a new object into database
     * 
	 * @return The insert SQL statement
	 */
	protected String getInsertSQL() {
		return null;
	}

    /**
     * This method returns the SQL statement to delete an object from database
     * 
     * @return The delete SQL statement
     */
    protected String getDeleteSQL() {
        return null;
    }

    /**
     * This method returns the SQL statement to update an object in the database
     * 
     * @return The update SQL statement
     */
    protected String getUpdateSQL() {  
        return null;
    }


	/**
	 * This method inserts a new object into database
	 * 
	 * @param user The new user object
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	protected void insertObject(Object object, String sql) throws DatabaseDownException, SQLException {

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setStatementValues(stmt, object);
			stmt.execute();

			// retrieve id just created
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				setObjectId(rs.getInt(1), object);
			} else {
				// throw an exception from here
			}

		} catch (SQLException e) {
			logger.error("AbstractDAO.insert : could not insert data");
			logger.error("Error in SQL : " + sql, e);
			throw e;
		} finally {
			closeStatement(stmt);
			closeResultSet(rs);
            conn.close();
		}
	}

    /**
     * This method inserts a new object into database
     * 
     * @param user The new user object
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    protected void updateObject(Object object, String sql) throws DatabaseDownException, SQLException {

        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            setStatementValuesForUpdate(stmt, object);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("AbstractDAO.update : could not update data");
            logger.error("Error in SQL : " + sql, e);
            logger.error(stmt.toString());
            throw e;
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            conn.close();
        }
    }

    /**
     * This method deletes an object from database
     * 
     * @param id The object id
     * @param sql The sql to run
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    protected void deleteObject(int id, String sql) throws DatabaseDownException, SQLException {

        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            logger.error("AbstractDAO.deleteObject : could not delete data");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeStatement(stmt);
            closeResultSet(rs);
            conn.close();
        }
    }

	/**
	 * This method retrieves all common names from database. It uses the following SQL: <br>
	 * 		SELECT * FROM COMMON_NAME
	 *  
	 * @return A list of <code>CommonName</code> objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	protected List retrieveObject(String sql) throws DatabaseDownException, SQLException {
		List list = new ArrayList();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			Object object = null;

			while (rs.next()) {
				object = createObject(rs);
				list.add(object);
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + sql, e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
            conn.close();
		}
		return list;
	}

    /**
     * This method retrieves all IDS from database. 
     *  
     * @return A list of <code>Integer</code> objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    protected List retrieveIDs(String sql) throws DatabaseDownException, SQLException {
        List list = new ArrayList();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            Object object = null;

            while (rs.next()) {
                int id = rs.getInt("ID");
                list.add(new Integer(id));
            }
        } catch (SQLException e) {
            logger.error("AbstractDAO.retrieve : could not retrieve data ");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }
        return list;
    }

    /**
     * This method retrieves all IDS from database. 
     *  
     * @return A list of <code>Integer</code> objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveIDsForGivenID(int id, String sql) throws DatabaseDownException, SQLException {
        List list = new ArrayList();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            Object object = null;

            while (rs.next()) {
                id = rs.getInt("ID");
                list.add(new Integer(id));
            }
        } catch (SQLException e) {
            logger.error("AbstractDAO.retrieve : could not retrieve data ");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();
        }
        return list;
    }

	/**
	 * This method retrieves a VO for an object based on its id
	 * 
	 * @param id The id of the object to be retrieved
	 * @param sql The SQL to be executed
	 * 
	 * @return a VO object
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	protected Object retrieveObject(int id, String sql) throws DatabaseDownException, SQLException {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Object object = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();

			if (rs.next()) {
				object = createObject(rs);
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + sql, e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
            conn.close();            
		}
		return object;
	}

	/**
	 * This method retrieves a list of VO for an object based on some column id
	 * 
	 * @param id The id associated with the object to be retrieved
	 * @param sql The SQL to be executed
	 * 
	 * @return a VO object
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	protected List retrieveObjects(int id, String sql) throws DatabaseDownException, SQLException {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Object object = null;
		List list = new ArrayList();
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				object = createObject(rs);
				list.add(object);
			}
		} catch (SQLException e) {
			logger.error("AbstractDAO.retrieve : could not retrieve data ");
			logger.error("Error in SQL : " + sql, e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
            conn.close();            
		}
		return list;
	}

    /**
     * This method retrieves a list of VO for an object based on some column id
     * 
     * @param id The id associated with the object to be retrieved
     * @param sql The SQL to be executed
     * 
     * @return a VO object
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    protected List retrieveObjects(String id, String sql) throws DatabaseDownException, SQLException {
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Object object = null;
        List list = new ArrayList();
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                object = createObject(rs);
                list.add(object);
            }
        } catch (SQLException e) {
            logger.error("AbstractDAO.retrieve : could not retrieve data ");
            logger.error("Error in SQL : " + sql, e);
            throw e;
        } finally {
            closeResultSet(rs);
            closeStatement(stmt);
            conn.close();            
        }
        return list;
    }

	/**
	 * This method retrieves a VO object based on its name
	 * 
	 * @param name The name of the VO
	 * 
	 * @return a VO object 
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	protected Object retrieveObject(String name, String sql) throws DatabaseDownException, SQLException {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Object object = null;

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();

			if (rs.next()) {
				object = createObject(rs);
			}
		} catch (SQLException e) {
			logger.error("ObjectDAO.retrieve(String) : could not retrieve data ", e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
            conn.close();            
		}
		return object;
	}

	/**
	 * @param rs
	 * @return
	 */
	abstract protected Object createObject(ResultSet rs) throws SQLException;

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

	/**
	 * This method sets the id into the object
	 * 
	 * @param id The id value
	 * @param object The object to set the id
	 */
	protected abstract void setObjectId(int id, Object object) throws SQLException;

	/**
	 * This method set the values into statement, before running the SQL in insert method
	 * 
	 * @param stmt   The statement to insert the values to sql
	 * @param object The object to retrieve the values from
	 */
	protected abstract void setStatementValues(PreparedStatement stmt, Object object)
		throws SQLException;


    /**
     * This method set the values into statement, before running the SQL in insert method
     * 
     * @param stmt   The statement to insert the values to sql
     * @param object The object to retrieve the values from
     */
    protected abstract void setStatementValuesForUpdate(PreparedStatement stmt, Object object)
        throws SQLException;

    /**
     * this method returns a java.util.Date object from a sql date in a result set
     * 
     * @param rs The result set with the date column
     * 
     * @return returns a java.util.Date object from a sql date in a result set
     * 
     * @throws SQLException If any sql error occurs while retrieving the date
     */
    protected java.util.Date getDate(ResultSet rs, String column) throws SQLException {
        Timestamp dbDate = rs.getTimestamp(column);
        java.util.Date date = null;
        if (dbDate != null) {
            date = new java.util.Date(dbDate.getTime());
        }
        return date;
    }


    /**
     * This method converts the photo date attribute into a java.sql.Date object
     * 
     * @param photo The photo to have the date converted
     * 
     * @return a new java.sql.Date object, or null if the photo date is null
     */
    protected Date getSQLDate(java.util.Date date) {
        if (date != null) {
            Date d = new Date(date.getTime());

            return d;
        }
        return null;
    }

    /**
     * This method creates a new date object with current date
     *  
     * @return a new date object with current date
     */
    protected Date getSQLDate() {
        java.util.Date date = new java.util.Date();
        return new Date(date.getTime());
    }

    /**
     * This method converts the photo date attribute into a java.sql.Date object
     * 
     * @param photo The photo to have the date converted
     * 
     * @return a new java.sql.Date object, or null if the photo date is null
     */
    protected Timestamp getTimestamp(java.util.Date date) {
        if (date != null) {
            Timestamp t = new Timestamp(date.getTime());

            return t;
        }
        return null;
    }
}
