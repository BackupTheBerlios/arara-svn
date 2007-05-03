package net.indrix.arara.servlets.birdlist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.BirdListBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.BirdListModel;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.common.IBean;
import net.indrix.arara.servlets.common.IBeanManager;
import net.indrix.arara.servlets.common.UploadBeanManagerFactory;
import net.indrix.arara.vo.BirdList;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.User;

import org.apache.struts.util.LabelValueBean;

/**
 * This class creates the bird list, with its basic information. It does not create the species
 * data
 * 
 * @author Jeff
 */
@SuppressWarnings("serial")
public class CreateBirdListServlet extends AbstractServlet {
    
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
            Map data = null;
            data = parseFormData(req);

            UploadBeanManagerFactory factory = UploadBeanManagerFactory.getInstance();
            IBeanManager manager = factory.createBean(UploadBeanManagerFactory.BIRDLIST, null, session);
            manager.updateBean(data, errors, false);

            BirdList birdList = new BirdList();
            try {
                updateBirdList(birdList, manager.getBean(), user);
                
                BirdListModel model = new BirdListModel();
                model.insert(birdList);
                
                nextPage = ServletConstants.FRAME_PAGE;
                pageToShow = BirdListConstants.CREATE_SUCCESS_PAGE;
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

    private void updateBirdList(BirdList birdList, IBean bean, User user)
            throws DatabaseDownException, SQLException {
        BirdListBean bBean = (BirdListBean)bean;
        
        updateCities(birdList, bBean); 
        birdList.setComment(bBean.getComment());
        birdList.setLocation(bBean.getLocation());
        birdList.setName(bBean.getName());
        birdList.setType(bBean.getSelectedType().equals("public") ? 1 : 2);
        birdList.setUser(user);
        logger.debug("CreateBirdListServlet.updateBirdList: birdList updated " + birdList);
    }

    private void updateCities(BirdList birdList, BirdListBean bBean) throws DatabaseDownException, SQLException {
        List selectedCities = bBean.getSelectedCities();
        Iterator it = selectedCities.iterator();
        while (it.hasNext()){
            LabelValueBean lvBean = (LabelValueBean)it.next();
            int id = Integer.parseInt(lvBean.getValue());
            CityModel model = new CityModel();
            try {
                City city = model.getCity(id);
                birdList.addCity(city);
            } catch (DatabaseDownException e) {
                logger.debug("Could not retrieve city id " + id);
                throw e;
            } catch (SQLException e) {
                logger.debug("Could not retrieve city id " + id);
                throw e;
            }
        }
    }
}