package net.indrix.arara.servlets;

/**
 * HTTP classes
 */
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

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent e) {
		HttpSession session = e.getSession();
		logger.info("Session " + session.getId() + " has been created");

		ServletContext app = session.getServletContext();
		NumberOfUsers numUsers = null;
		synchronized (app) {
			/* inc the number of on-line users */
			numUsers = (NumberOfUsers) app
					.getAttribute(ServletConstants.USERS_ON_LINE);
			if (numUsers == null) {
				numUsers = new NumberOfUsers();
				app.setAttribute(ServletConstants.USERS_ON_LINE, numUsers);
			}
			numUsers.inc();
		}

		logger.debug("  ---  Usuarios on-line  >>>  " + numUsers.getNumber());
	}

	/**
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent e) {
		HttpSession session = e.getSession();
		ServletContext app = session.getServletContext();

		logger.info("Session " + session.getId() + " has been destroyed");

		NumberOfUsers numUsers = null;
		synchronized (app) {
			/* inc the number of on-line users */
			numUsers = (NumberOfUsers) app
					.getAttribute(ServletConstants.USERS_ON_LINE);
			if (numUsers == null) {
				numUsers = new NumberOfUsers();
				app.setAttribute(ServletConstants.USERS_ON_LINE, numUsers);
			}
			numUsers.dec();
		}

		logger.debug("  ---  Usuarios on-line  >>>  " + numUsers.getNumber());
	}

	class NumberOfUsers {
		private int number = 0;

		public void inc() {
			number++;
		}

		public void dec() {
			number--;
		}

		public int getNumber() {
			return number;
		}

		public String toString() {
			return "" + number;
		}
	}
}