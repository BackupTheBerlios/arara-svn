package net.indrix.arara.servlets.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.ListBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InitSearchBySpecieServlet extends RetrieveFamiliesServlet {
    public void init() {
        logger.debug("Initializing InitSearchBySpecieServlet...");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        SpecieDAO dao = new SpecieDAO();
        List list = null;
        try {
            logger.debug("Retrieving list of all species...");
            list = dao.retrieve();
            logger.debug("Converting to labelValueBean...");
            list = ServletUtil.specieDataAsLabelValueBean(list);
            logger.debug("Putting to session...");

            ListBean listBean = (ListBean) session.getAttribute(ServletConstants.FAMILY_LIST_KEY);
            if (listBean == null) {
                listBean = new ListBean();
                session.setAttribute(ServletConstants.FAMILY_LIST_KEY, listBean);
            }
            listBean.setSelectedId(null);
            listBean.setList(list);
            
            listBean = new ListBean();
            listBean.setList(list);
            session.setAttribute(ServletConstants.SPECIE_LIST_KEY, listBean);

        } catch (DatabaseDownException e) {
            logger.error("InitSearchBySpecieServlet.doGet : could not retrieve list of all species",e);
        } catch (SQLException e) {
            logger.error("InitSearchBySpecieServlet.doGet : could not retrieve list of all species",e);
        }

        super.doGet(req, res);
    }
}

