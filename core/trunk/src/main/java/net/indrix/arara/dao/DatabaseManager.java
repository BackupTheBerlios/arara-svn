package net.indrix.arara.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.utils.SO;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: worldcup
 * </p>
 * <p>
 * Description: Game e Bet
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Indrix.NET
 * </p>
 * 
 * @author Jefferson Rodrigues and Rafael Cuba
 * @version 0.1
 */

public class DatabaseManager {

	private static String site;

	private static String database;

	private static String user;

	private static String password;

	/**
	 * Logger object to be used by this class
	 */
	protected static final Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * Database Manager
	 */
	public static DataSource datasource = null;

	private static boolean initialized = false;
	/**
	 * creates the cipher object, and loads the database driver
	 */
	static {
		logger.debug("Reading DB driver class...");
		try {
            
            String classToUse = getClassToUse();
            logger.info("Driver to use:" + classToUse);
            Class.forName(classToUse);
            logger.info("Driver loaded");

            Context context = new InitialContext();
            Context lautx = (Context) context.lookup("java:comp/env");
            datasource = (DataSource)lautx.lookup("jdbc/avesbrasil1");                
		} catch (Exception e) {
			logger.error("Erro reading driver class " + e);
		}
	}

	/**
	 * This method returns a Connection from the pool
	 * 
	 * @return a <code>Connection</code> object
	 * 
	 * @throws DatabaseDownException
	 *             If no connection was retrieved by the
	 *             <code>DriverManager</code>
	 */
	public static Connection getConnection() throws DatabaseDownException {
		Connection c = null;

		try {
			c = retrieveConnection();
		} catch (Exception e) {
			logger.error("Error retrieving connection " + e);
			throw new DatabaseDownException();
		}
		return c;
	}

	/**
	 * Retrieve the connection to the database
	 * 
	 * @return
	 */
	private static Connection retrieveConnection() throws NamingException,
			SQLException {
		Connection c = null;

		if (SO.getSO() == SO.WIN) {
			// if (eTeste()) {
			 c = DriverManager.getConnection("jdbc:mysql://localhost/passaros?autoReconnect=true",
			 "jeff",
			 "jeff");
			// } else {
            /*
            logger.info("Conection by Context");            
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource) envContext.lookup("jdbc/aves");
			c = ds.getConnection();
            */
			// }
		} else {
            /*
			if (!initialized) {
				logger.error("DatabaseManager.retrieveConnection : initializing...");
				initialize();
				initialized = true;
			}
            logger.info("Conection direct");            
			String url = "jdbc:mysql://" + site + "/" + database
					+ "?autoReconnect=true";
			c = DriverManager.getConnection(url, user, password);
            */
            
            try {
                c = datasource.getConnection();
            } catch (SQLException sqle) {
                logger.fatal("Could not retrieve a conection", sqle);
                c = null;
            } 
        }
        return c;

	}

	/**
	 * 
	 */
	private static void initialize() {
		site = PropertiesManager.getProperty("site");
		database = PropertiesManager.getProperty("database");
		user = PropertiesManager.getProperty("user");
		password = PropertiesManager.getProperty("password");
		logger.debug("Properties loaded ");
	}

	/**
	 * Retrieve what driver classname shall be used, depending on the OS
	 * 
	 * @return the driver classname shall be used, depending on the OS
	 */
	private static String getClassToUse() {
		String classToUse = null;
		String os = System.getProperty("os.name");
		if (SO.getSO() == SO.WIN) {
			classToUse = "com.mysql.jdbc.Driver";
		} else {
			classToUse = "org.gjt.mm.mysql.Driver";
		}
		return classToUse;
	}

}