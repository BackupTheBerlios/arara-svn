package net.indrix.arara.servlets.birdlist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.util.LabelValueBean;

import net.indrix.arara.bean.BirdListBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.BirdListModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.BirdList;
import net.indrix.arara.vo.User;

@SuppressWarnings("serial")
public class InitEditBirdListServlet extends AbstractServlet {
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String nextPage = null;
        String pageToShow = null;
        List<String> errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();

        if (user == null) {
            nextPage = userNotLogged(req, res);
        } else {
            // retrieve bird list id
            int id = Integer.parseInt(req.getParameter(BirdListConstants.BIRD_LIST_ID));
            BirdListModel model = new BirdListModel();
            try {
                logger.debug("InitEditBirdListServlet.doPost: retrieving BirdList data...");
                BirdList birdList = model.retrieve(id);
                
                // reset upload data bean
                BirdListBean bean = (BirdListBean) session.getAttribute(BirdListConstants.BEAN_KEY);
                if (bean == null) {
                    logger.debug("Creating BirdListBean...");
                    bean = new BirdListBean(birdList);
                    session.setAttribute(BirdListConstants.BEAN_KEY, bean);
                } 
                bean.setVO(birdList);
                
                logger.debug("Cities information:");
                if (bean.getSelectedCities().isEmpty()){
                    logger.fatal("Lista de cidades vazia...");
                } else {
                    for (LabelValueBean lvb: bean.getSelectedCities()){
                        logger.debug("LVB: " + lvb);
                        
                    }
                }
                               
                // retrieve list of states
                logger.debug("Retrieving list of states...");
                List list = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
                
                bean.setStatesList(list);

                pageToShow = BirdListConstants.CREATE_LIST_PAGE;
                req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
                req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, BirdListConstants.EDIT_SERVLET);
                req.setAttribute(ServletConstants.ACTION, ServletConstants.EDIT_ACTION);                
            } catch (DatabaseDownException e) {
                errors.add(ServletConstants.DATABASE_ERROR);
                pageToShow = ServletConstants.DATABASE_ERROR_PAGE;
            } catch (SQLException e) {
                errors.add(ServletConstants.DATABASE_ERROR);
                pageToShow = ServletConstants.DATABASE_ERROR_PAGE;
            }
            
            nextPage = ServletConstants.FRAME_PAGE;
        }

        if (!errors.isEmpty()) {
            // coloca erros no request para registrar.jsp processar e
            // apresentar mensagem de erro
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
        }
        logger.debug("Dispatching to " + nextPage + " with pageToShow = "
                + pageToShow);
        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);

    }

}
