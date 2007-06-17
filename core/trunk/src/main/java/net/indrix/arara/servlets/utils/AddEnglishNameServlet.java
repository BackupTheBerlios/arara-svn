package net.indrix.arara.servlets.utils;

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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Specie;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class AddEnglishNameServlet extends HttpServlet {
    /**
     * The logger object to log messages
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    private static final String PONTO_VIRGULA = ";";

    private SpecieDAO sDao = new SpecieDAO();

    /**
     * This method parses a file with format Familia; ; ; ; ;
     * ;genero;especie;nome comum ; ; ; ;
     * 
     * This method updates the maps families, species and commonNames.
     * 
     */
    public void doParse() {

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        BufferedReader in = null;
        
        PrintWriter out = res.getWriter();
        
        try {
            String filename = PropertiesManager.getProperty("english.name.file");
            in = new BufferedReader(new FileReader(filename));
            String line = null;
            int count = 0;
            while ((line = in.readLine()) != null) {
                String tokens[] = line.split(PONTO_VIRGULA);
                if (tokens.length > 0) {
                    String commonName = tokens[0];
                    Specie s = sDao.retrieveForCommonName(commonName.trim());
                    if (s == null){
                        out.write("COULD NOT FIND " + commonName + "\n");
                    } else {
                        String englishName = tokens[1];
                        if (englishName.trim().length() != 0){
                            s.setEnglishName(englishName);
                            sDao.insertEnglishName(s);
                            count++;
                            //out.write("adding " + commonName + " | " + englishName + "\n");
                        } else {
                            out.write(commonName + " HAS EMPTY ENGLISH NAME \n");                       
                        }
                    }
                }
            }
            out.write("\n");
            out.write("Total: " + count);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DatabaseDownException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        if (in != null) {
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        logger.debug("Finished");

    }
}
