/*
 * Created on 11/09/2005
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
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.dao.CommonNameDAO;
import net.indrix.dao.DatabaseDownException;
import net.indrix.dao.SpecieCommonNameDAO;
import net.indrix.dao.SpecieDAO;
import net.indrix.vo.CommonName;
import net.indrix.vo.Specie;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AddCommonNameServlet extends HttpServlet {
	/**
	 * The logger object to log messages
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	private static final String PONTO_VIRGULA = ";";

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
        logger.debug("AddCommonNameServlet Starting");

		SpecieDAO specieDao = new SpecieDAO();
		CommonNameDAO commonDao = new CommonNameDAO();
		SpecieCommonNameDAO scDao = new SpecieCommonNameDAO();
		BufferedReader in = null;
		try {
			String filename = "D:\\development\\Tomcat5.0\\webapps\\aves\\WEB-INF\\classes\\net\\indrix\\servlets\\utils\\CommonName.txt";
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
                    String specieName = tokens[0];
                    String commonName = tokens[1];
                    Specie s = specieDao.retrieve(specieName);
                    if (s == null) {
                        index++;
                        logger.debug(index + " " + line + "  NAO ENCONTRADO  ");
                    } else {
                        CommonName common = new CommonName();
                        common.setName(commonName);
                        commonDao.insert(common);
                        logger.debug("CommonName inserted " + common);
                        s.addPopularName(common);
                        scDao.insert(s);
                        logger.debug("CommonName added to specie " + s);
                    }
                }
                PrintWriter writer = res.getWriter();
                writer.write("<HTML><body>Common name added...</body></html>");
                writer.flush();
            }
		} catch (FileNotFoundException e) {
			logger.error("File not found ", e);
		} catch (IOException e) {
            logger.error("IO Exception", e);
		} catch (DatabaseDownException e) {
            logger.error("DatabaseDownException ", e);
		} catch (SQLException e) {
            logger.error("SQLException ", e);
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
        logger.debug("AddCommonNameServlet Finished");

	}
}
