/*
 * Created on 20/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.indrix.arara.dao.CommonNameDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieCommonNameDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.CommonName;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Specie;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class LoadDataServlet extends HttpServlet {
    /**
     * The logger object to log messages
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    private static final String PONTO_VIRGULA = ";";
    private static final String BRANCO = " ";
    private static final String EMPTY = "";
    private static final String EMPTY_LINE = "; ; ; ;";
    
    private FamilyDAO fDao = new FamilyDAO();
    private SpecieDAO sDao = new SpecieDAO();
    private CommonNameDAO cnDao = new CommonNameDAO();
    private SpecieCommonNameDAO scnDao = new SpecieCommonNameDAO();
    
    private Map families = new HashMap();
    private Map species = new HashMap();
    private Map commonNames = new HashMap();
    private Family currentFamily;
    private Specie currentSpecie;
    
    /**
     * This method parses a file with format
     * Familia; ; ; ;
     * ; ;genero;especie;nome comum
     * ; ; ; ;
     * 
     * This method updates the maps families, species and commonNames.
     * 
     */
    public void doParse(){
        
        BufferedReader in = null; 
        try {
            String filename = PropertiesManager.getProperty("data.to.load");
            in = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line = in.readLine()) != null) {
                String tokens[] = line.split(PONTO_VIRGULA);
                if ((tokens.length > 0) && (!emptyLine(line))){
                    String familyName = tokens[0];
                    Family f = getFamily(familyName);
                    if (tokens.length == 1){
                        //logger.debug(line);
                        System.exit(1);
                    }
                    String subFamilyName = tokens[1];
                    updateFamily(f, subFamilyName);
                    handleSpecie(tokens);                                      
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (in != null){
            try {
                in.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * @param string
     * @param string2
     */
    private void handleSpecie(String tokens[]) {
        String string = tokens[2];
        String string2 = tokens[3];
        String string3 = null;
        if (tokens.length == 5){
            string3 = tokens[4];
        }
        if (!BRANCO.equals(string)){
            String name = string + BRANCO + string2;
            Specie s = new Specie();
            s.setName(name);
            s.setFamily(currentFamily);
            species.put(name, s);
            
            // common name
            if (string3 != null && !BRANCO.equals(string3) && !EMPTY.equals(string3)){
                CommonName c = (CommonName)commonNames.get(string3);
                if (c == null){
                    c = new CommonName();
                    c.setName(string3);
                    commonNames.put(string3, c);
                }
                
                s.addPopularName(c);
            }
            
            currentSpecie = s;
            //logger.debug(currentSpecie);            
        }
        
    }

    /**
     * @param line
     * @return
     */
    private boolean emptyLine(String line) {
        return EMPTY_LINE.equals(line);
    }
    
    /**
     * @param subFamilyName
     */
    private void updateFamily(Family f, String subFamilyName) {
        if (!BRANCO.equals(subFamilyName)){
            if (f.getSubFamilyName() == null){
                removeFamily(f);
                f.setSubFamilyName(subFamilyName);
                addFamily(f);
            } else {
                Family newF = new Family();
                newF.setName(f.getName());
                newF.setSubFamilyName(subFamilyName);
                addFamily(newF);
                currentFamily = newF;
            }
        }       
    }

    /**
     * @param f
     */
    private void removeFamily(Family f) {
        families.remove(f.toString());
        
    }

    private void addFamily(Family f) {
        families.put(f.toString(), f);
    }
    
    /**
     * @param familyName
     * @return
     */    
    private Family getFamily(String familyName) {
        Family f = null;
        if (EMPTY.equals(familyName)){
            f = currentFamily;
        } else { 
            f = (Family)families.get(familyName);
            if (f == null){
                f = new Family();
                f.setName(familyName);
                
                addFamily(f);
            }
            currentFamily = f;
        }
        return f;
    }


    /**
     * 
     */
    private void doCreateDatabase() {
        // add families
        logger.debug("Adding families");
        Iterator it = families.keySet().iterator();
        while (it.hasNext()){
            String name = (String)it.next();
            Family f = (Family)families.get(name);
            try {
                fDao.insert(f);
                logger.debug("Inserting family " + f);
                sleep();
            } catch (DatabaseDownException e) {
                logger.debug("ERROR DatabaseDownException adding " + f);
                e.printStackTrace();
            } catch (SQLException e) {
                logger.debug("ERROR SQLException adding " + f);
                e.printStackTrace();
            }
            
        }

        logger.debug("Adding Common Names");
        it = commonNames.keySet().iterator();
        while (it.hasNext()){
            String name = (String)it.next();
            CommonName c = (CommonName)commonNames.get(name);
            try {
                if (c.getName() != null){
                    cnDao.insert(c);                
                    logger.debug("Inserting common name " + c);
                    sleep();
                }
            } catch (DatabaseDownException e) {
                logger.debug("ERROR DatabaseDownException adding " + c);
                e.printStackTrace();
            } catch (SQLException e) {
                logger.debug("ERROR SQLException adding " + c);
                e.printStackTrace();
            }
            
        }

        logger.debug("Adding species");
        it = species.keySet().iterator();
        while (it.hasNext()){
            String name = (String)it.next();
            Specie s = (Specie)species.get(name);
            try {
                sDao.insert(s);
                logger.debug("Inserting specie " + s);
                sleep();
            } catch (DatabaseDownException e) {
                logger.debug("ERROR DatabaseDownException adding " + s);
                e.printStackTrace();
            } catch (SQLException e) {
                logger.debug("ERROR SQLException adding " + s);
                e.printStackTrace();
            }
            
        }
        
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doParse();
        doCreateDatabase();
        logger.debug("Finished");        
        
    }  
}
