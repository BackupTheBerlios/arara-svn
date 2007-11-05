package net.indrix.arara.servlets.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.FamilyModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.vo.User;

import org.apache.commons.fileupload.FileUploadException;

@SuppressWarnings("serial")
public class MediaFilterServlet extends AbstractServlet {
    public void init() {
        logger.debug("Initializing PhotoFilterServlet...");

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        logger.debug("Entering doGet method...");
        List<String> errors = new ArrayList<String>();
        RequestDispatcher dispatcher = null;
        String nextPage = req.getParameter(ServletConstants.NEXT_PAGE_KEY);
        String pageToShow = req.getParameter(ServletConstants.PAGE_TO_SHOW_KEY);
        String servletToCall = req.getParameter(ServletConstants.SERVLET_TO_CALL_KEY);
        String action = req.getParameter(ServletConstants.ACTION);

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        if (user == null) {
            logger.debug("RetrieveFamiliesServlet.doGet : user is NOT logged...");
            // The method userNotLogged takes care of user not logged trying to
            // access options for logged users
            nextPage = userNotLogged(req, res);
        } else {
            Map data = null;
            try {
                data = parseMultiPartFormData(req);
                String dataToBeUploaded = req.getParameter("data");
                logger.debug("Calling factory with " + dataToBeUploaded);

                UploadBeanManagerFactory factory = UploadBeanManagerFactory.getInstance();
                IBeanManager manager = factory.createBean(dataToBeUploaded,action, req);
                if (manager != null) {
                    // 1. update the bean, with data from user
                    manager.updateBean(data, errors, false);

                    UploadBean uploadBean = (UploadBean)manager.getBean();

                    // 2. now, update the lists of family and states
                    // put family list on bean
                    List familyList = ServletUtil.familyDataAsLabelValueBean(FamilyModel.getFamilyList());
                    uploadBean.setFamilyList(familyList);

                    // put states on bean
                    List statesList = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
                    uploadBean.setStatesList(statesList);

                    // 3. retrieve list of species for the selected family
                    AbstractServlet.updateSpeciesListForFamily(uploadBean);
                    AbstractServlet.updateCitiesListForState(uploadBean);

                    // upload photo id
                    String photoId = (String)data.get(ServletConstants.PHOTO_ID);
                    req.setAttribute(ServletConstants.PHOTO_ID, photoId);

                    req.setAttribute(ServletConstants.SERVLET_TO_CALL_KEY, servletToCall);
                    req.setAttribute(ServletConstants.ACTION, action);
                    req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
                    req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);

                }
            } catch (FileUploadException e) {
                errors.add(ServletConstants.DATABASE_ERROR);
                nextPage = ServletConstants.INITIAL_PAGE;
            } catch (DatabaseDownException e) {
                errors.add(ServletConstants.DATABASE_ERROR);
                nextPage = ServletConstants.INITIAL_PAGE;
            }
        }

        logger.debug("Forwarding to page " + nextPage);
        ServletContext context = this.getServletContext();
        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);
    }

}

