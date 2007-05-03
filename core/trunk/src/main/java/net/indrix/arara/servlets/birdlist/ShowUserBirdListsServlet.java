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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.BirdListModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

@SuppressWarnings("serial")
public class ShowUserBirdListsServlet extends AbstractServlet {
    
    @Override
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
            int userId = user.getId();
            
            try {
                BirdListModel model = new BirdListModel();
                List birdLists = model.retrieveListsForUser(userId);
                
                req.setAttribute(BirdListConstants.USER_LISTS, birdLists);
                
                nextPage = ServletConstants.FRAME_PAGE;
                pageToShow = BirdListConstants.SHOW_LISTs_PAGE;
                req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);                
            } catch (DatabaseDownException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (!errors.isEmpty()) {
            // coloca erros no request para registrar.jsp processar e
            // apresentar mensagem de erro
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
        }
        logger.debug("Dispatching to " + nextPage + " with pageToShow = " + pageToShow);
        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);

    }
}
