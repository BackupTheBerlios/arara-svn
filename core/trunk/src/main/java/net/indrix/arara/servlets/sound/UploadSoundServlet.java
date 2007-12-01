/*
 * Created on 03/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.model.FamilyModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.model.StatesModel;
import net.indrix.arara.model.UploadSound;
import net.indrix.arara.model.exceptions.SoundProcessingException;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.ServletUtil;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.common.SoundBeanManager;
import net.indrix.arara.servlets.common.UploadBeanManagerFactory;
import net.indrix.arara.servlets.exceptions.InvalidFileException;
import net.indrix.arara.vo.Sound;
import net.indrix.arara.vo.User;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("serial")
public class UploadSoundServlet extends AbstractUploadServlet {
	private static final int MAX_SOUND_SIZE = 500000;

	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void init() {
		logger.debug("Initializing UploadSoundServlet...");
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		RequestDispatcher dispatcher = null;
		ServletContext context = this.getServletContext();
		String nextPage = null;
		List<String> errors = new ArrayList<String>();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        UploadSoundBean soundBean = null;
        if (user == null) {
			logger.debug("errors is not null.");
			errors.add(ServletConstants.USER_NOT_LOGGED);
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
			nextPage = ServletConstants.LOGIN_PAGE;
		} else {
			Map data = null;
			try {
				data = parseMultiPartFormData(req);
                

                String dataToBeUploaded = UploadBeanManagerFactory.SOUND;
                String action = UploadConstants.UPLOAD_ACTION;

                logger.debug("Updating sound bean...");
                SoundBeanManager manager = getBeanManager(dataToBeUploaded, action, req);
                manager.updateBean(data, errors, true);
                soundBean = (UploadSoundBean)manager.getBean();
                req.setAttribute(UploadConstants.UPLOAD_SOUND_BEAN, soundBean);

                if (errors.isEmpty()) {        
					logger.debug("bean updated " + soundBean);
					try {
                        AbstractServlet.updateCitiesListForState(soundBean);
						Sound sound = createSound(soundBean, user);
						// set upload data to session
						session.setAttribute(UploadSoundConstants.LAST_UPLOAD_BEAN, soundBean);
						if (sound.getSound().getFileSize() > MAX_SOUND_SIZE) {
							logger.debug("photo with size = " + sound.getSound().getFileSize());
							errors.add(UploadSoundConstants.INVALID_FILE_SIZE);
						} else {
							logger.debug("Calling addSoundToDatabase " + sound);
							addSoundToDatabase(sound);
							logger.debug("Sound added to database");
							// next page
							nextPage = ServletConstants.UPLOAD_SOUND_SUCCESS_PAGE;

                            logInfo(user, req);
						}
					} catch (InvalidFileException e) {
						logger.debug("InvalidFileException.....", e);
						errors.add(UploadSoundConstants.INVALID_FILE);
                        updateSoundBean(soundBean);                                    
					} catch (SQLException e) {
						logger.debug("SQLException.....", e);
						errors.add(ServletConstants.DATABASE_ERROR);
                        updateSoundBean(soundBean);                                    
					} catch (SoundProcessingException e) {
						logger.debug("SoundProcessingException .....", e);
						errors.add(UploadSoundConstants.ERROR_PROCESSING_SOUND_FILE);
                        updateSoundBean(soundBean);                                    
					} 
				} else {
                    logger.debug("Error updating sound bean...");
                    updateSoundBean(soundBean);                    
                }
			} catch (ServletException e) {
				logger.debug("ServletException.....", e);
			} catch (IOException e) {
				logger.debug("IOException.....", e);
			} catch (FileUploadException e) {
				logger.debug("FileUploadException.....", e);
				errors.add(UploadConstants.INVALID_FILE);
			} catch (DatabaseDownException e) {
                logger.debug("DatabaseDownException.....");
                errors.add(ServletConstants.DATABASE_ERROR);
            }
			if (!errors.isEmpty()) {
				nextPage = ServletConstants.UPLOAD_SOUND_PAGE;
			}
		}

		if (!errors.isEmpty()) {
			// put errors in request
			req.setAttribute(ServletConstants.ERRORS_KEY, errors);
		}

		dispatcher = context.getRequestDispatcher(nextPage);
		logger.debug("Dispatching to " + nextPage);
		dispatcher.forward(req, res);

	}

    private void updateSoundBean(UploadSoundBean soundBean) throws DatabaseDownException {
        // 2. now, update the lists of family and states
        // put family list on bean
        List familyList = ServletUtil.familyDataAsLabelValueBean(FamilyModel.getFamilyList());
        soundBean.setFamilyList(familyList);

        // put states on bean
        List statesList = ServletUtil.statesDataAsLabelValueBean(StatesModel.getStates());
        soundBean.setStatesList(statesList);

        // 3. retrieve list of species for the selected family
        AbstractServlet.updateSpeciesListForFamily(soundBean);
        AbstractServlet.updateCitiesListForState(soundBean);
    }

	private void logInfo(User user, HttpServletRequest req) {
        String userStr = "User " + user.getLogin();
        
        loggerActions.info(userStr + " from IP " + req.getRemoteAddr() + " has uploaded one sound.");
    }

    /**
	 * @param photo
     * @throws InvalidFileException 
	 */
	private void addSoundToDatabase(Sound sound) throws DatabaseDownException, SQLException, SoundProcessingException, InvalidFileException {
		UploadSound model = new UploadSound();
		model.uploadSound(sound);
	}

	/**
	 * @param data
	 * @return
	 */
	private Sound createSound(UploadSoundBean bean, User user) throws InvalidFileException {
		Sound sound = new Sound();
		sound.setUser(user);
		sound.setSpecie(createSpecie(bean.getSelectedSpecieId(), bean.getSelectedFamilyId()));
		sound.getSound().setFileSize(Integer.parseInt(bean.getFileSize()));
		sound.getSound().setFilename(bean.getFilename());
		sound.setAge(AgeModel.getAge(Integer.parseInt(bean.getSelectedAgeId())));
		sound.setSex(SexModel.getSex(Integer.parseInt(bean.getSelectedSexId())));
		sound.setLocation(bean.getLocation());
		sound.setCity(CityModel.getCity(bean.getCitiesList(), bean.getSelectedCityId()));
		sound.setComment(bean.getComment());
		try {
			sound.getSound().setSound(bean.getFileItem().getInputStream());
		} catch (IOException e) {
			sound.getSound().setSound(null);
			throw new InvalidFileException();
		}
		return sound;
	}
}
