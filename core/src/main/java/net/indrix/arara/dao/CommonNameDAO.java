/*
 * Created on 06/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.vo.CommonName;
import net.indrix.arara.vo.Specie;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CommonNameDAO extends AbstractDAO {
	private static final String ID_COLUMN = "id";
	private static final String NAME_COLUMN = "name";
	
	private static final String INSERT = "INSERT INTO common_name (name) values (?)";
	private static final String SELECT_BY_NAME = "SELECT * FROM common_name WHERE name=?";
	private static final String SELECT_BY_ID = "SELECT * FROM common_name WHERE id=?";
	private static final String SELECT_ALL = "SELECT * FROM common_name order by name";
	private static final String SELECT_FOR_SPECIE =
		"SELECT id, name FROM common_name c, specie_has_common_name sc " +        "WHERE c.id = sc.common_name_id and specie_id = ?";

    private static List listInMemory = null;
    
	/**
	 * This method inserts a new common name object into database. It uses the following select: <br>
	 * 	INSERT INTO COMMON_NAME (NAME) values (?)
	 * 
	 * @param commonName the common name object
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public void insert(CommonName commonName) throws DatabaseDownException, SQLException {
		super.insertObject(commonName, INSERT);
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
	public List retrieve() throws DatabaseDownException, SQLException {
        if (listInMemory == null){
            listInMemory = super.retrieveObject(SELECT_ALL); 
        }
		return listInMemory;
	}

	/**
	 * This method retrieves a <code>CommonName</code> object based on its name
	 * 
	 * @param name The name of the <code>CommonName</code>
	 * 
	 * @return a <code>CommonName</code> object 
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public CommonName retrieve(String name) throws DatabaseDownException, SQLException {
		CommonName commonName = (CommonName) super.retrieveObject(name, SELECT_BY_NAME);
		return commonName;
	}

	/**
	 * This method retrieves a <code>CommonName</code> object based on its id
	 * 
	 * @param id The id of the <code>CommonName</code>
	 * 
	 * @return a <code>CommonName</code> object 
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public CommonName retrieve(int id) throws DatabaseDownException, SQLException {
		CommonName commonName = (CommonName) super.retrieveObject(id, SELECT_BY_ID);
		return commonName;
	}


	/**
	 * This method retrieves the common names of the given specie
	 * 
	 * @param specie The specie that will get the common names
	 * 
	 * @throws SQLException If some SQL Exception occurs
	 */
	public void retrieveForSpecie(Specie specie) throws SQLException, DatabaseDownException {
        Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		CommonName commonName = null;

		try {
			stmt = conn.prepareStatement(SELECT_FOR_SPECIE);
			stmt.setInt(1, specie.getId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				commonName = (CommonName)createObject(rs);
				specie.addPopularName(commonName);
			}
		} catch (SQLException e) {
			logger.error("CommonNameDAO.addCommonNamesForSpecie : Could not retrive data", e);
			throw e;
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
            conn.close();
		}
	}

	/**
	 * This method creates a <code>CommonName</code> object with the data from database
	 * 
	 * @param rs The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>CommonName</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		CommonName commonName;
		commonName = new CommonName();
		commonName.setId(rs.getInt(ID_COLUMN));
		commonName.setName(rs.getString(NAME_COLUMN));
		return commonName;
	}

	/**
	 * This method set the values into statement, before running the SQL in insert method
	 * 
	 * @param stmt   The statement to insert the values to sql
	 * @param object The object to retrieve the values from
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object) throws SQLException {
		CommonName commonName = (CommonName) object;
		stmt.setString(1, commonName.getName());
	}

    /**
     * This method set the values into statement, before running the SQL in update method
     * 
     * @param stmt   The statement to update the values to sql
     * @param object The object to retrieve the values from
     * 
     * @throws SQLException If some SQL Exception occurs
     */
    protected void setStatementValuesForUpdate(PreparedStatement stmt, Object object)
        throws SQLException {
    }

	/**
	 * This method sets the id into the object
	 * 
	 * @param id The id value
	 * @param object The object to set the id
	 */
	protected void setObjectId(int id, Object object) throws SQLException {
		CommonName commonName = (CommonName) object;
		commonName.setId(id);
	}
}
