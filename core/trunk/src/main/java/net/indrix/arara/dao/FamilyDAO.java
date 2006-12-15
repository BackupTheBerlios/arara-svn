/*
 * Created on 06/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.vo.Family;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class FamilyDAO extends AbstractDAO {
	private static final String ID_COLUMN = "id";

	private static final String NAME_COLUMN = "name";

	private static final String SUB_FAMILY_NAME_COLUMN = "subFamilyName";

	private static final String INSERT = "INSERT INTO family (name, subFamilyName) values (?,?)";

	private static final String SELECT_BY_NAME = "SELECT * FROM family WHERE name=? and id != -1 ORDER BY name";

	private static final String SELECT_BY_ID = "SELECT * FROM family WHERE id=? ORDER BY name";

	private static final String SELECT_ALL = "SELECT * FROM family WHERE name != 'indefinido' ORDER BY name";

	/**
	 * The list of families to be kept in memory
	 */
	private static List listInMemory = null;

	public void insert(Family family) throws DatabaseDownException,
			SQLException {
		super.insertObject(family, INSERT);
	}

	/**
	 * This method retrieves all families from database
	 * 
	 * @return a <code>List</code> object with <code>Family</code> objects
	 *         inside.
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieve() throws DatabaseDownException, SQLException {
		if (listInMemory == null) {
			logger.debug("Retrieving families for the first time...");
			listInMemory = super.retrieveObject(SELECT_ALL);
		} else {
			logger.debug("Retrieving list of families from memory...");
		}
		return listInMemory;
	}

	/**
	 * This method retrieves a <code>Family</code> object based on its name
	 * 
	 * @param name
	 *            The name of the <code>Family</code>
	 * 
	 * @return a <code>Family</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public Family retrieve(String name) throws DatabaseDownException,
			SQLException {
		Family family = (Family) super.retrieveObject(name, SELECT_BY_NAME);
		return family;
	}

	/**
	 * This method retrieves a <code>Family</code> object based on its id
	 * 
	 * @param id
	 *            The id of the <code>Family</code>
	 * 
	 * @return a <code>Family</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public Family retrieve(int id) throws DatabaseDownException, SQLException {
		Family family = (Family) super.retrieveObject(id, SELECT_BY_ID);
		return family;
	}

	/**
	 * This method creates a <code>Family</code> object with the data from
	 * database
	 * 
	 * @param rs
	 *            The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>Family</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		Family family;
		family = new Family();
		family.setId(rs.getInt(ID_COLUMN));
		family.setName(rs.getString(NAME_COLUMN));
		family.setSubFamilyName(rs.getString(SUB_FAMILY_NAME_COLUMN));
		return family;
	}

	/**
	 * This method set the values into statement, before running the SQL in
	 * insert method
	 * 
	 * @param stmt
	 *            The statement to insert the values to sql
	 * @param object
	 *            The object to retrieve the values from
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object)
			throws SQLException {
		Family family = (Family) object;
		stmt.setString(1, family.getName());
		stmt.setString(2, family.getSubFamilyName());
	}

	/**
	 * This method set the values into statement, before running the SQL in
	 * update method
	 * 
	 * @param stmt
	 *            The statement to update the values to sql
	 * @param object
	 *            The object to retrieve the values from
	 * 
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	protected void setStatementValuesForUpdate(PreparedStatement stmt,
			Object object) throws SQLException {
	}

	/**
	 * This method sets the id into the object
	 * 
	 * @param id
	 *            The id value
	 * @param object
	 *            The object to set the id
	 */
	protected void setObjectId(int id, Object object) throws SQLException {
		Family family = (Family) object;
		family.setId(id);
	}
}