/*
 * Created on 08/12/2005
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

import net.indrix.arara.vo.Age;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AgeDAO {
    private static final String SELECT_ALL = "Select * from age";
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");
    
    public List retrieve() throws DatabaseDownException{
        logger.debug("CommentsDAO.retrieveUsersForPhoto : entering method...");
        List list = new ArrayList();
        CommonNameDAO commonNameDAO = new CommonNameDAO();
        Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            logger.debug(
                "SexDAO.retrieve : running SQL " + SELECT_ALL);
            stmt = conn.prepareStatement(SELECT_ALL);          
            rs = stmt.executeQuery();

            logger.debug("SexDAO.retrieve : adding sexs to list...");
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("age");
                Age age = new Age();
                age.setId(id);
                age.setAge(description);
                
                list.add(age);
                logger.debug("Adding sex: " + age);
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
        logger.debug("CommentsDAO.retrieveUsersForPhoto : finishing method...");
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
