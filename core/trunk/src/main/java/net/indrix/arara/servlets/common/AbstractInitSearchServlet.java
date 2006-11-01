package net.indrix.arara.servlets.common;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.AbstractDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.servlets.ServletConstants;

import org.apache.log4j.Logger;

public abstract class AbstractInitSearchServlet extends HttpServlet {
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * init method for InitSearchPhotosByUserServlet servlet
     */
    public void init() {
        logger.debug("Initializing " + this.getClass());
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        List <String>erros = new ArrayList<String>();
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        String action = req.getParameter(ServletConstants.ACTION);
        String pageToShow = req.getParameter(ServletConstants.PAGE_TO_SHOW_KEY);

        HttpSession session = req.getSession();
        logger.debug("Retrieving data...");
        try {
            List list = getListOfData(getDao());
            if ((list != null) && (!list.isEmpty())) {
                logger.debug("Setting data in request");

                if ((list != null) && (!list.isEmpty())) {
                    logger.debug("Data found... putting to session.");
                    session.setAttribute(getListKeyToSession(), list);
                    
                    req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);
                    req.setAttribute(ServletConstants.ACTION, action);
                    req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                    req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
                    logger.debug(this.getClass() + " | " + servletToCall + " | " + action + " | " + pageToShow);
                    
                } else {
                    logger.debug("Data not found...");
                    erros.add(ServletConstants.DATABASE_ERROR);
                    nextPage = ServletConstants.INITIAL_PAGE;
                }
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
            // coloca erros no request para registrar.jsp processar e apresentar
            // mensagem de erro
            req.setAttribute(ServletConstants.ERRORS_KEY, erros);

            // direciona usuário para página de registro novamente
            nextPage = ServletConstants.INITIAL_PAGE;
        }
        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);
    }

    /**
     * Retrieve the final list of data, to be put in session and used by view
     * 
     * @return the final list of data, to be put in session and used by view
     */
    protected abstract List getListOfData(AbstractDAO dao) throws DatabaseDownException, SQLException;

    /**
     * Retrieve the DAO to be used
     * @return
     */
    protected abstract AbstractDAO getDao();
    
    /**
     * The key to put the list of common names in session
     * 
     * @return The key to put the list of common names in session
     */
    protected abstract String getListKeyToSession();

}
