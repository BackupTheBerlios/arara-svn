/*
 * Created on 11/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.indrix.arara.vo.CommonName;
import net.indrix.arara.vo.Specie;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SpecieCommonNameDAO extends AbstractDAO {

	private static final String INSERT = "INSERT INTO specie_has_common_name (specie_id, common_name_id, specie_family_id) values (?,?,?)";

	/**
	 * This method inserts a new common name object into database. It uses the
	 * following select: <br>
	 * INSERT INTO COMMON_NAME (NAME) values (?)
	 * 
	 * @param commonName
	 *            the common name object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public void insert(Specie s) throws DatabaseDownException, SQLException {
		super.insertObject(s, INSERT);
	}

	/**
	 * This method creates a <code>CommonName</code> object with the data from
	 * database
	 * 
	 * @param rs
	 *            The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>CommonName</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		return null;
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
		Specie specie = (Specie) object;
		CommonName name = (CommonName) specie.getPopularNames().get(0);
		stmt.setInt(1, specie.getId());
		stmt.setInt(2, name.getId());
		stmt.setInt(3, specie.getFamily().getId());
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
		CommonName commonName = (CommonName) object;
		commonName.setId(id);
	}

}
