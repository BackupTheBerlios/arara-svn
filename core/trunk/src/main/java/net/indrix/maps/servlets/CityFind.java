package net.indrix.maps.servlets;
import org.hibernate.Session;
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

/**
 * Servlet implementation class for Servlet: CityFind
 *
 */
 public class CityFind extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.maps");

    protected static Logger loggerActions = Logger.getLogger("net.indrix.actions");
     
     
     /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public CityFind() {
		super();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}  	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        HttpSession session = request.getSession();
        try {

        } catch (ServletException e) {
            logger.debug("ServletException.....");
        } catch (IOException e) {
            logger.debug("IOException.....");
        } catch (Exception e) {
            logger.debug("MapException.....", e);
        }   	  	    
	}
 }
 
 /*
package events;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);
        session.getTransaction().commit();
        HibernateUtil.getSessionFactory().close();        
    }

}

 
 */