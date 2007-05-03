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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.vo.CommonName;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Specie;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SpecieDAO extends AbstractDAO {
	private static final String ID_COLUMN = "id";

	private static final String NAME_COLUMN = "name";

	private static final String FAMILY_ID_COLUMN = "family_id";

	private static final String FAMILY_NAME_COLUMN = "f_name";
    
    private static final String SUB_FAMILY_NAME_COLUMN = "f_subFamilyName";

	private static final String INSERT = "INSERT INTO specie (name, family_id) values (?, ?)";

	private static final String INSERT_COMMON_NAME = "INSERT INTO specie_has_common_name (specie_id, specie_family_id, common_name_id) "
			+ "values (?, ?, ?)";

	private static final String SELECT_BY_NAME = "SELECT s.id, s.name, s.family_id, f.name f_name, f.subFamilyName f_subFamilyName from specie s, family f where s.family_id=f.id and s.name like ? ORDER BY name";

	private static final String SELECT_BY_ID = "SELECT s.id, s.name, s.family_id, f.name f_name, f.subFamilyName f_subFamilyName from specie s, family f where s.id=? AND s.family_id=f.id ORDER BY name";

	private static final String SELECT_FOR_FAMILY = "SELECT * FROM specie where family_id = ? ORDER BY name";

	private static final String SELECT_ALL = "SELECT s.id, s.name, s.family_id, f.name f_name, f.subFamilyName f_subFamilyName from specie s, family f where s.family_id=f.id and s.id != -1 ORDER BY name";

	/**
	 * The list of species to be kept in memory
	 */
	private static List listInMemory = null;

	/**
	 * This method inserts a new specie to the database. It inserts in two
	 * tables: <br> - SPECIE - SPECIE_HAS_COMMON_NAME
	 * 
	 * @param specie
	 */
	public void insert(Specie specie) throws DatabaseDownException,
			SQLException {

		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);

			stmt = conn.prepareStatement(INSERT,
					Statement.RETURN_GENERATED_KEYS);
			setStatementValues(stmt, specie);
			stmt.execute();

			// retrieve id just created
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				setObjectId(rs.getInt(1), specie);
			} else {
				// throw an exception from here
			}

			List list = specie.getPopularNames();
			Iterator it = list.iterator();
			while (it.hasNext()) {
				CommonName commonName = (CommonName) it.next();
				stmt = conn.prepareStatement(INSERT_COMMON_NAME);
				stmt.setInt(1, specie.getId());
				stmt.setInt(2, specie.getFamily().getId());
				stmt.setInt(3, commonName.getId());
				stmt.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.error("Specie.insert : Could not rollback insert for "
						+ specie, e1);
				throw e;
			}
			logger.error("SpecieDAO.insert : Could not insert data " + specie,
					e);
			throw e;
		} finally {
			closeStatement(stmt);
			conn.close();
		}
	}

	/**
	 * This method retrieves all species from database
	 * 
	 * @return a <code>List</code> object with <code>Specie</code> objects
	 *         inside.
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public List retrieve() throws DatabaseDownException, SQLException {
		if (listInMemory == null) {
			logger.debug("Retrieving species for the first time...");
			listInMemory = super.retrieveObjects(SELECT_ALL);
		} else {
			logger.debug("Retrieving list of species from memory...");
		}
		return listInMemory;
	}

	/**
	 * This method retrieves all species for the given family object
	 * 
	 * @param family
	 *            The <code>Family</code> object to have the species retrieved
	 * 
	 * @return a <code>List</code> object with <code>Specie</code> objects
	 *         inside.
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 */
	public List retrieveForFamily(Family family) throws DatabaseDownException {
		List <Specie>list = new ArrayList<Specie>();
		CommonNameDAO commonNameDAO = new CommonNameDAO();
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(SELECT_FOR_FAMILY);
			stmt.setInt(1, family.getId());
			rs = stmt.executeQuery();
			Specie specie = null;

			while (rs.next()) {
				specie = createSpecieObject(rs, family);
				commonNameDAO.retrieveForSpecie(specie);
				list.add(specie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResultSet(rs);
			closeStatement(stmt);
			try {
				conn.close();
			} catch (SQLException e1) {
				throw new DatabaseDownException();
			}
		}
		return list;
	}

	/**
	 * This method retrieves a <code>Specie</code> object based on its name
	 * 
	 * @param name
	 *            The name of the <code>Specie</code>
	 * 
	 * @return a <code>Specie</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public Specie retrieve(String name) throws DatabaseDownException,
			SQLException {
		Specie specie = (Specie) super.retrieveObject(name, SELECT_BY_NAME);
		CommonNameDAO commonNameDAO = new CommonNameDAO();
		commonNameDAO.retrieveForSpecie(specie);
		logger.debug("SpecieDao.retrieve retrieved specie " + specie);
		return specie;
	}

	/**
	 * This method retrieves a <code>Specie</code> object based on its id
	 * 
	 * @param id
	 *            The id of the <code>Specie</code>
	 * 
	 * @return a <code>Specie</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	public Specie retrieve(int id) throws DatabaseDownException, SQLException {
		Specie specie = (Specie) super.retrieveObject(id, SELECT_BY_ID);
		CommonNameDAO commonNameDAO = new CommonNameDAO();
		commonNameDAO.retrieveForSpecie(specie);
		return specie;
	}

	/**
	 * This method creates a <code>Specie</code> object with the data from
	 * database
	 * 
	 * @param rs
	 *            The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>Specie</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	protected Object createObject(ResultSet rs) throws SQLException {
		Specie specie;
		specie = new Specie();
		specie.setId(rs.getInt(ID_COLUMN));
		specie.setName(rs.getString(NAME_COLUMN));
		Family f = new Family();
		f.setId(rs.getInt(FAMILY_ID_COLUMN));
		f.setName(rs.getString(FAMILY_NAME_COLUMN));
        f.setSubFamilyName(rs.getString(SUB_FAMILY_NAME_COLUMN));
		specie.setFamily(f);
		return specie;
	}

	/**
	 * This method creates a <code>Specie</code> object with the data from
	 * database, and with the given <code>Family object</code>
	 * 
	 * @param rs
	 *            The <code>ResultSet<code> object to retrieve the data
	 * 
	 * @return A new <code>Specie</code> object 
	 * 
	 * @throws SQLException If an error occur while retrieving data from result set
	 */
	private Specie createSpecieObject(ResultSet rs, Family family)
			throws SQLException {
		Specie specie;
		specie = new Specie();
		specie.setId(rs.getInt(ID_COLUMN));
		specie.setName(rs.getString(NAME_COLUMN));
		specie.setFamily(family);
		return specie;
	}

	/**
	 */
	protected void setObjectId(int id, Object object) throws SQLException {
		Specie specie = (Specie) object;
		specie.setId(id);
	}

	/**
	 * This method set the values into statement, before running the SQL in
	 * insert method
	 * 
	 * @param stmt
	 *            The statement to insert the values to sql
	 * @param object
	 *            The object to retrieve the values from
	 * 
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	protected void setStatementValues(PreparedStatement stmt, Object object)
			throws SQLException {
		Specie specie = (Specie) object;
		stmt.setString(1, specie.getName());
		stmt.setInt(2, specie.getFamily().getId());
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
}
