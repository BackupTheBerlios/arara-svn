package net.indrix.arara.servlets.common;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.AbstractDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.UserDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;

public class InitSearchByUserServlet extends AbstractInitSearchServlet {
    /**
     * Retrieve the DAO to be used
     * @return
     */
    protected AbstractDAO getDao(){
        return new UserDAO();
    }

    /**
     * Call the retrieve method of dao, and returns the list of data
     * 
     * @param dao
     *            The dao to be used to retrieve the data
     * 
     * @return A list of CommonName values, as a DataValueBean objects
     */
    protected List getListOfData(AbstractDAO dao) throws DatabaseDownException,
            SQLException {
        return ServletUtil.userDataAsLabelValueBean(dao.retrieve());
    }

    /**
     * The key to put the list of users in session
     * 
     * @return The key to put the list of users in session
     */
    protected String getListKeyToSession() {
        return ServletConstants.USER_LIST_KEY;
    }

    
}
