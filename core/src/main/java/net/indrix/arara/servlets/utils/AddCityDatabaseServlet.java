/*
 * Created on 16/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.dao.CityDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.State;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AddCityDatabaseServlet extends HttpServlet {
    /**
     * The logger object to log messages
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    private static final String PONTO_VIRGULA = ";";

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        logger.debug("AddCityDatabaseServlet Starting");

        CityDAO cityDao = new CityDAO();
        BufferedReader in = null;
        try {
            String filename = "/home/aves.brasil1/public_html/teste/WEB-INF/classes/net/indrix/servlets/utils/CitiesDatabase.txt";
            in = new BufferedReader(new FileReader(filename));
            if (in == null){
                logger.debug("File not found");
                PrintWriter writer = res.getWriter();
                writer.write("<HTML><body>File not found</body></html>");
                writer.flush();
            } else {
                logger.debug("File found");
                String line = null;
                int index = 0;
                while ((line = in.readLine()) != null) {
                    String tokens[] = line.split(PONTO_VIRGULA);
                    String cityName = tokens[0].trim();
                    String stateAcronym = tokens[1].trim();
                    
                    City city = getCity(cityName, stateAcronym);
                    cityDao.insert(city);
                    logger.debug("Adding city " + city);
                }
                PrintWriter writer = res.getWriter();
                writer.write("<HTML><body>Cities added...</body></html>");
                writer.flush();
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found ", e);
        } catch (IOException e) {
            logger.error("IO Exception", e);
        } catch (DatabaseDownException e) {
            logger.error("DatabaseDownException ", e);
        } 
        if (in != null) {
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        logger.debug("AddCityDatabaseServlet Finished");

    }

	/**
	 * @param city
	 * @param state
	 */
	private City getCity(String cityName, String stateAcronym) {
		State state = StatesModel.getState(stateAcronym);
        City city = new City();
        city.setName(cityName);
        city.setState(state);
		return city;
	}

}
