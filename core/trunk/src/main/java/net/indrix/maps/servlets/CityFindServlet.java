package net.indrix.maps.servlets;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import net.indrix.hibernate.HibernateUtil;

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

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.vo.User;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

import net.indrix.maps.dao.CityDescription;

/**
 * Servlet implementation class for Servlet: CityFind
 *
 */
 public class CityFindServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.maps");

    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");
     
     
     /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CityFindServlet() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        HttpSession session = request.getSession();
        String cityName = request.getParameter("city");
        String nextPage = "/maps/maps.jsp";
        try {
            Session DatabaseSession = HibernateUtil.getSessionFactory().openSession();
            logger.debug("Try to Find " + cityName);
/*            
           CityDescription theCityDescription = (CityDescription) DatabaseSession.load(CityDescription.class, cityName);
            CityDescription theCityDescription = (CityDescription) DatabaseSession
            .createQuery("select name from city_description where name = :cityName")
            .setParameter("cityName", cityName)
            .uniqueResult();
*/
           
            List list = DatabaseSession.createCriteria(CityDescription.class)
            .add( Expression.eq("name", cityName) )
            .list();

            if (list.size()==0) throw new IllegalArgumentException("No city for the given name: " + cityName);
            CityDescription theCityDescription = (CityDescription) list.get(0);
            logger.debug("Found " + theCityDescription.getName() + " " + theCityDescription.getCoordinates());            
            
            DatabaseSession.close();
            
            request.setAttribute(ServletConstants.CITY_NAME, theCityDescription.getName());            
            request.setAttribute(ServletConstants.CITY_COORDS, theCityDescription.getCoordinates());
            request.setAttribute(ServletConstants.CITY_INFO, theCityDescription.getDescription());            


            dispatcher = context.getRequestDispatcher(nextPage);
            logger.debug("Dispatching to " + nextPage);
            dispatcher.forward(request, response);

        } catch (Exception e) {
            logger.debug("MapException.....", e);
        }                       
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
 }
 
