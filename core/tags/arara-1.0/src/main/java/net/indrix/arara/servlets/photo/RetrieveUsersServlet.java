/*
 * Created on 28/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.UserDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.utils.LabelValueBean;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class RetrieveUsersServlet extends HttpServlet{
    static Logger logger = Logger.getLogger("net.indrix.aves");

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        List erros = new ArrayList();
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = null;
        HttpSession session = req.getSession();
        logger.debug("Retrieving data...");
        UserDAO dao = new UserDAO();
        try {
            List list = dataAsLabelValueBean(dao.retrieve());
            if ((list != null) && (!list.isEmpty())){              
                nextPage = getNextPage();
                session.setAttribute(ServletConstants.USER_LIST_KEY, list);
            } else {
                logger.debug("Data not found...");
                erros.add(ServletConstants.DATABASE_ERROR);
                nextPage = ServletConstants.INITIAL_PAGE;
            }
        } catch (DatabaseDownException e) {
            erros.add(ServletConstants.DATABASE_ERROR);
            nextPage = ServletConstants.INITIAL_PAGE;
        } catch (SQLException e) {
            erros.add(ServletConstants.DATABASE_ERROR);
            nextPage = ServletConstants.INITIAL_PAGE;
        }

        if (!erros.isEmpty()) {
            // coloca erros no request para registrar.jsp processar e apresentar mensagem de erro
            req.setAttribute(ServletConstants.ERRORS_KEY, erros);

            // direciona usuário para página de registro novamente
            nextPage = ServletConstants.INITIAL_PAGE;
        }
        dispatcher = context.getRequestDispatcher(nextPage);        
        dispatcher.forward(req, res);
    }

    /**
     * @return
     */
    abstract protected String getNextPage();

    /**
     * @param list
     * @return
     */
    private List dataAsLabelValueBean(List list) {
        List newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            User user = (User)it.next();
            LabelValueBean bean = new LabelValueBean(user.getName(), user.getId());
            newList.add(bean);
            logger.debug("adding bean to list " + bean);
        }
        return newList;
    }

}
