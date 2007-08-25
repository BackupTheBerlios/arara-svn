/*
 * Created on 07/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.identification;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.IdentifyPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.FamilyModel;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.model.SpecieModel;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;
import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AbstractIdentificationServlet extends AbstractServlet {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
	 * This methods handles the data from user, populating a bean object
	 * 
	 * @param data
	 *            The data from user
	 * @param session
	 *            THe user session
	 * @param beanKey
	 *            The key to the bean
	 * 
	 * @return A new <code>IdentifyPhotoBean</code> object
	 */
	protected IdentifyPhotoBean handleBean(HttpServletRequest req,
			HttpSession session, String beanKey, List <String>errors) {
		logger.debug("AbstractIdentificationServlet.handleBean: entering method...");
		IdentifyPhotoBean bean = new IdentifyPhotoBean();
		req.setAttribute(beanKey, bean);
        
		String familyId = req.getParameter(ServletConstants.FAMILY_ID);
		if (familyId == null || familyId.trim().length() == 0) {
			errors.add(UploadPhotoConstants.FAMILY_REQUIRED);
		} else {
            String specieId = req.getParameter(ServletConstants.SPECIE_ID);
            bean.setSelectedAgeId(req.getParameter(ServletConstants.AGE_ID));
            bean.setSelectedSexId(req.getParameter(ServletConstants.SEX_ID));
            String comment = req.getParameter(ServletConstants.COMMENT);

            bean.setSelectedFamilyId(familyId);
            bean.setSelectedSpecieId(specieId);
            bean.setComment(comment);            
        }
		logger.debug("AbstractIdentificationServlet.handleBean: finishing method...");
		return bean;
	}

	/**
	 * This method resets the bean
	 * 
	 * @param bean
	 *            The bean to be reseted
	 */
	protected void resetBean(IdentifyPhotoBean bean) {
		bean.setComment(null);
		bean.setSelectedAgeId(null);
		bean.setSelectedFamilyId(null);
		bean.setSelectedSexId(null);
		bean.setSelectedSpecieId(null);
	}

	/**
	 * This method retrieves the identificatinos for a given photo
	 * 
	 * @param model
	 *            The model object to retrieve the data
	 * @param photo
	 *            The photo to have its identifications retrieved
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	protected void retrieveIdentificationsForPhoto(PhotoModel model, Photo photo)
			throws DatabaseDownException, SQLException {

		logger.info("IdentifyPhotoServlet.retrieveIdentificationsForPhoto: entering method...");
		// now retrieve the comments
		List identifications = model.retrieveIdentificationsForPhoto(photo);
		photo.setIdentifications(identifications);
		if ((identifications == null) || (identifications.isEmpty())) {
			logger.debug("There is no identifications for photo " + photo);
		} else {
			logger.debug(identifications.size() + " identifications found for photo " + photo);
		}
	}

    protected Photo getPhotoFromDatabase(List <String>errors, String photoId) {
        // retrieve from database
        PhotoModel model = new PhotoModel();
        Photo photo = null;
        try {
            photo = model.retrieve(Integer.parseInt(photoId));
            if (photo != null) {
                retrieveIdentificationsForPhoto(model, photo);
            }
            logger.debug("Photo retrieved = " + photo);
        } catch (NumberFormatException e) {
            logger.error("Could not parse photoId " + photoId);
            errors.add(ServletConstants.DATABASE_ERROR);
        } catch (DatabaseDownException e) {
            logger.debug("DatabaseDownException.....");
            errors.add(ServletConstants.DATABASE_ERROR);
        } catch (SQLException e) {
            logger.debug("SQLException.....", e);
            errors.add(ServletConstants.DATABASE_ERROR);
        }
        return photo;
    }

    protected void setListOfFamilies(IdentifyPhotoBean identificationBean) {
        List listOfFamilies;
        logger.debug("Converting list of families to labelValueBean...");
        listOfFamilies = ServletUtil.familyDataAsLabelValueBean(FamilyModel.getFamilyList());
        identificationBean.setFamilyList(listOfFamilies);
    }

    protected void setListOfSpecies(IdentifyPhotoBean identificationBean) {
        List listOfSpecies;
        logger.debug("Converting list of species to labelValueBean...");
        listOfSpecies = ServletUtil.specieDataAsLabelValueBean(SpecieModel.getSpecieList());
        identificationBean.setSpecieList(listOfSpecies);
    }
}
