package net.indrix.arara.servlets;

/**
 * HTTP classes
 */
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class SessionControl implements HttpSessionListener {
	/**
	 * The logger for this class
	 */
	private static Logger logger = Logger.getLogger("net.indrix.aves");

    private static Hashtable<String, Boolean>sessionIds = new Hashtable<String, Boolean>();
    
	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent e) {
		HttpSession session = e.getSession();

        String id = session.getId();
        int num = 0;
		ServletContext app = session.getServletContext();
		synchronized (app) {
            if (!sessionIds.contains(id)){
                sessionIds.put(id, new Boolean(true));
            }
            
            num = sessionIds.size();
			app.setAttribute(ServletConstants.USERS_ON_LINE, new Integer(num));
            
            Enumeration<String> l = sessionIds.keys();
            while (l.hasMoreElements()){
                String key = (String)l.nextElement();
                logger.debug(key);
            }
		}

		logger.debug("  ---  Usuarios on-line  >>>  " + num);
	}

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent e) {
		HttpSession session = e.getSession();
		ServletContext app = session.getServletContext();

		logger.info("Session " + session.getId() + " has been destroyed");
        String id = session.getId();

        sessionIds.remove(id);  
        int num = 0;
		synchronized (app) {
            num = sessionIds.size();
            app.setAttribute(ServletConstants.USERS_ON_LINE, new Integer(num));
		}

		logger.debug("  ---  Usuarios on-line  >>>  " + num);
	}
}