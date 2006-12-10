/*
 * Created on 09/11/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.utils.jsp;

import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.model.StatisticsModel;

import org.apache.log4j.Logger;

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

	public static int getNumberOfPhotos() {
		int n = 0;
		try {
			net.indrix.arara.vo.Statistics s = StatisticsModel
					.retrieveStatistics();
			n = s.getNumberOfPhotos();
		} catch (DatabaseDownException e) {
			logger.error(
					"Statistics.getNumberOfPhotos: DatabaseDownException ", e);
		} catch (SQLException e) {
			logger.error("Statistics.getNumberOfPhotos: SQLException ", e);
		}
		return n;
	}

	public static int getNumberOfFamilies() {
		int n = 0;
		try {
			net.indrix.arara.vo.Statistics s = StatisticsModel
					.retrieveStatistics();
			n = s.getNumberOfFamilies();
		} catch (DatabaseDownException e) {
			logger
					.error(
							"Statistics.getNumberOfFamilies: DatabaseDownException ",
							e);
		} catch (SQLException e) {
			logger.error("Statistics.getNumberOfFamilies: SQLException ", e);
		}
		return n;
	}

	public static int getNumberOfSpecies() {
		int n = 0;
		try {
			net.indrix.arara.vo.Statistics s = StatisticsModel
					.retrieveStatistics();
			n = s.getNumberOfSpecies();
		} catch (DatabaseDownException e) {
			logger
					.error(
							"Statistics.getNumberOfFamilies: DatabaseDownException ",
							e);
		} catch (SQLException e) {
			logger.error("Statistics.getNumberOfFamilies: SQLException ", e);
		}
		return n;
	}

	public static int getNumberOfUsers() {
		// set n as 30 as a default value
		int n = 30;
		try {
			net.indrix.arara.vo.Statistics s = StatisticsModel
					.retrieveStatistics();
			n = s.getNumberOfUsers();
		} catch (DatabaseDownException e) {
			logger.error(
					"Statistics.getNumberOfPhotos: DatabaseDownException ", e);
		} catch (SQLException e) {
			logger.error("Statistics.getNumberOfPhotos: SQLException ", e);
		}
		return n;
	}

    public static String getSlideShow(String contextPath) {
        // ; is the separator
        String imgUrls="";
        try {
            PhotoModel model = new PhotoModel();
            List listOfPhotos = null;
            
            // listOfPhotos = model.retrievePhotoIDs();
            listOfPhotos = model.retrievePhotoIDsForSlideShow();
            
            Collections.shuffle(listOfPhotos);
            int maxSize = 10;
            if (maxSize > listOfPhotos.size()){
                maxSize = listOfPhotos.size();
            }
            Iterator i = (listOfPhotos.subList(0, maxSize)).iterator();
            String id = ((Integer) (i.next())).toString();            
            imgUrls = contextPath + "/servlet/getThumbnail?photoId=" + id;
            while (i.hasNext()) {
                id = ((Integer) (i.next())).toString();
                imgUrls = imgUrls + ";" + contextPath + "/servlet/getThumbnail?photoId=" + id;
            }            
        } catch (DatabaseDownException e) {
            logger.error(
                    "Statistics.getSlideShow: DatabaseDownException ", e);
        } catch (SQLException e) {
            logger.error("Statistics.getSlideShow: SQLException ", e);
        }
        return imgUrls;
    }
    
}
