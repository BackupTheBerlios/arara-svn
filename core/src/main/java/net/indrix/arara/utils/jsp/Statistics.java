/*
 * Created on 09/11/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.utils.jsp;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.StatisticsModel;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Statistics {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    public static int getNumberOfPhotos(){
        int n = 0;
        try {
			net.indrix.vo.Statistics s = StatisticsModel.retrieveStatistics();
            n = s.getNumberOfPhotos();
		} catch (DatabaseDownException e) {
            logger.error("Statistics.getNumberOfPhotos: DatabaseDownException ", e);
		} catch (SQLException e) {
            logger.error("Statistics.getNumberOfPhotos: SQLException ", e);
		}
        return n;
    }

    public static int getNumberOfFamilies(){
        int n = 0;
        try {
            net.indrix.vo.Statistics s = StatisticsModel.retrieveStatistics();
            n = s.getNumberOfFamilies();
        } catch (DatabaseDownException e) {
            logger.error("Statistics.getNumberOfFamilies: DatabaseDownException ", e);
        } catch (SQLException e) {
            logger.error("Statistics.getNumberOfFamilies: SQLException ", e);
        }
        return n;
    }

    public static int getNumberOfSpecies(){
        int n = 0;
        try {
            net.indrix.vo.Statistics s = StatisticsModel.retrieveStatistics();
            n = s.getNumberOfSpecies();
        } catch (DatabaseDownException e) {
            logger.error("Statistics.getNumberOfFamilies: DatabaseDownException ", e);
        } catch (SQLException e) {
            logger.error("Statistics.getNumberOfFamilies: SQLException ", e);
        }
        return n;
    }

    public static int getNumberOfUsers(){
        // set n as 30 as a default value
        int n = 30;
        try {
            net.indrix.vo.Statistics s = StatisticsModel.retrieveStatistics();
            n = s.getNumberOfUsers();
        } catch (DatabaseDownException e) {
            logger.error("Statistics.getNumberOfPhotos: DatabaseDownException ", e);
        } catch (SQLException e) {
            logger.error("Statistics.getNumberOfPhotos: SQLException ", e);
        }
        return n;
    }
    
}
