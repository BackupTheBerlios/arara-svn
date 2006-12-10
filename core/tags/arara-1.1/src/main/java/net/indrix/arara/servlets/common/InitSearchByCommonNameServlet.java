package net.indrix.arara.servlets.common;

import java.sql.SQLException;
import java.util.List;

import net.indrix.arara.dao.AbstractDAO;
import net.indrix.arara.dao.CommonNameDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchByCommonNameServlet extends AbstractInitSearchServlet {
    /**
     * Retrieve the DAO to be used
     * 
     * @return
     */
    protected AbstractDAO getDao() {
        return new CommonNameDAO();
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
        return ServletUtil.commonNameDataAsLabelValueBean(dao.retrieve());
    }

    /**
     * The key to put the list of common names in session
     * 
     * @return The key to put the list of common names in session
     */
    protected String getListKeyToSession() {
        return ServletConstants.COMMON_NAME_LIST_KEY;
    }
}
