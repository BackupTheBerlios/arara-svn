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
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class InitSearchBySpecieServlet extends RetrieveFamiliesServlet {
    public void init() {
        logger.debug("Initializing InitSearchBySpecieServlet...");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        SpecieDAO dao = new SpecieDAO();
        try {
            logger.debug("Retrieving list of all families...");
            FamilyDAO familyDao = new FamilyDAO();
            List familyList = ServletUtil.familyDataAsLabelValueBean(familyDao.retrieve());
            ListBean listBean = new ListBean();
            listBean.setSelectedId(null);
            listBean.setList(familyList);
            session.setAttribute(ServletConstants.FAMILY_LIST_KEY, listBean);
            
            logger.debug("Retrieving list of all species...");
            List speciesList = ServletUtil.specieDataAsLabelValueBean(dao.retrieve());
            logger.debug("Putting to session...");
            listBean = new ListBean();
            listBean.setList(speciesList);
            session.setAttribute(ServletConstants.SPECIE_LIST_KEY, listBean);

        } catch (DatabaseDownException e) {
            logger.error("InitSearchBySpecieServlet.doGet : could not retrieve list of all species",e);
        } catch (SQLException e) {
            logger.error("InitSearchBySpecieServlet.doGet : could not retrieve list of all species",e);
        }

        super.doGet(req, res);
    }
}

