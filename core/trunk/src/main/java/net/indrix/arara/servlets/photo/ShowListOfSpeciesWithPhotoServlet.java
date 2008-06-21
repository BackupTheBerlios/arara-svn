package net.indrix.arara.servlets.photo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.SpecieModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;

@SuppressWarnings("serial")
public class ShowListOfSpeciesWithPhotoServlet extends AbstractServlet {
    private static final String PAGE_TO_SHOW = "/jsp/specie/showSpeciesWithPhoto.jsp";

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        String action = req.getParameter(ServletConstants.ACTION);
        List<String> errors = new ArrayList<String>();

        SpecieModel model = new SpecieModel();
        List list = null;
        
        try {
            if ("ONLY".equals(action)){
                // only species with photo
                logger.debug("Retrieving species with photo...");
                list = model.retrieveSpeciesWithPhoto();
            } else {
                if ("NOT_ONLY".equals(action)){
                    // only species without photo
                    logger.debug("Retrieving species without photo...");
                    list = model.retrieveSpeciesWithoutPhoto();
                } else {
                    // all species, with a flag
                    logger.debug("Retrieving species with flag...");
                    list = model.retrieveAllSpeciesWithPhoto();
                }                
            }
        } catch (DatabaseDownException e) {
            logger.debug("DatabaseDownException.....");
            errors.add(ServletConstants.DATABASE_ERROR);
        } 

        req.setAttribute(ServletConstants.SPECIE_LIST_KEY, list);
        req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, PAGE_TO_SHOW);
        req.setAttribute(ServletConstants.ACTION, action);
        String nextPage = ServletConstants.FRAME_PAGE;

        logger.debug("Dispatching to " + nextPage);
        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);

    }

}
