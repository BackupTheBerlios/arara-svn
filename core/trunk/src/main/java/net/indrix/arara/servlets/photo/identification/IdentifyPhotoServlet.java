/*
 * Created on 07/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo.identification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.bean.IdentifyPhotoBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.AgeModel;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.model.SexModel;
import net.indrix.arara.model.exceptions.NumberOfPhotosPerSpecieException;
import net.indrix.arara.model.exceptions.PhotoAlreadyIdentifiedException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.PhotoIdentification;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

/**
 * This class controls the identification of one photo by a user. The
 * identification can be a final one in one of two cases:
 * 
 * 1. if the user is the photo owner 2. if the user is an admin and it checked
 * the checkbox for finishing identification
 * 
 * @author Jeff
 */
public class IdentifyPhotoServlet extends AbstractIdentificationServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * The constant for telling the page that we're in view mode
     */
    private static final String VIEW_MODE = "viewMode";

    /**
     * The constant for telling the page that we're in identification mode
     */
    private static final String IDENTIFICATION_MODE = "identificationMode";

    /**
     * The value of the checkbox for finishing identification when the user
     * selects it
     */
    private static final String ON = "on";

    public void init() {
        logger.debug("Initializing IdentifyPhotoServlet ...");
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        logger.info("Entering method....");
        List<String> errors = new ArrayList<String>();
        List<String> messages = new ArrayList<String>();
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        HttpSession session = req.getSession();
        String nextPage = null;

        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        if (user == null) {
            logger.debug("errors is not null.");
            errors.add(ServletConstants.USER_NOT_LOGGED);
            // put errors in request
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            nextPage = ServletConstants.LOGIN_PAGE;
        } else {
            boolean finish = false;
            if (user.isAdmin() || user.isPhotoIdModerator()) {
                String value = req.getParameter(ServletConstants.FINISH_IDENTIFICATION_KEY);
                finish = (ON.equals(value)) ? true : false;
                logger.info("User is admin, retrieve finish identification: " + finish);
            } else {
                finish = false;
            }
            // retrieve photo under identification
            String photoId = req.getParameter("photoId");
            Photo photo = (Photo) getPhotoFromDatabase(errors, photoId);
            req.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
            logger.debug("Photo under identification = " + photo);

            String beanKey = IdentificationPhotoConstants.IDENTIFICATION_PHOTO_BEAN;
            IdentifyPhotoBean bean = handleBean(req, session, beanKey, errors);

            if (errors.isEmpty()) {
                logger.debug("identification done: " + bean);

                PhotoIdentification photoIdentification = createObject(bean, user, photo);
                if (photoIdentification != null) {
                    PhotoModel model = new PhotoModel();
                    try {

                        if (model.identifyPhoto(photoIdentification, finish)) {
                            req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "false");
                            req.setAttribute(ServletConstants.VIEW_MODE_KEY, VIEW_MODE);

                            messages.add(ServletConstants.END_OF_IDENTIFICATION_MSG);
                            req.setAttribute(ServletConstants.MESSAGES_KEY, messages);
                        } else {
                            // retrieve all identifications already done for the
                            // given photo
                            retrieveIdentificationsForPhoto(model, photo);
                            req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
                            req.setAttribute(ServletConstants.VIEW_MODE_KEY, IDENTIFICATION_MODE);
                        }

                        // reset the bean object
                        resetBean(bean);
                    } catch (PhotoAlreadyIdentifiedException e) {
                        logger.debug("Exception", e);
                        errors.add(ServletConstants.PHOTO_ALREADY_IDENTIFIED);

                        // retrieve the photo from database, with updated data
                        try {
                            photo = model.retrieve(photoIdentification.getPhoto().getId());
                            session.setAttribute(ServletConstants.CURRENT_PHOTO, photo);
                        } catch (DatabaseDownException e1) {
                            logger.debug("DatabaseDownException.....");
                            errors.add(ServletConstants.DATABASE_ERROR);
                        } catch (SQLException e1) {
                            logger.debug("SQLException.....", e);
                            errors.add(ServletConstants.DATABASE_ERROR);
                        }
                        req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "false");
                    } catch (DatabaseDownException e) {
                        logger.debug("DatabaseDownException.....");
                        errors.add(ServletConstants.DATABASE_ERROR);
                    } catch (SQLException e) {
                        logger.debug("SQLException.....", e);
                        errors.add(ServletConstants.DATABASE_ERROR);
                    } catch (NumberOfPhotosPerSpecieException e) {
                        logger.debug("NumberOfPhotosPerSpecieException.....", e);
                        errors.add(ServletConstants.MAX_NUM_PHOTOS_FOR_SPECIE);
                        
                        req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
                        req.setAttribute(ServletConstants.VIEW_MODE_KEY, IDENTIFICATION_MODE);
                        
                    }
                } else {
                    logger.debug("IdentifyPhotoServlet.doPost : COULD NOT create object...");
                    req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
                }
            } else {
                req.setAttribute(ServletConstants.IDENTIFICATION_KEY, "true");
                req.setAttribute(ServletConstants.VIEW_MODE_KEY, IDENTIFICATION_MODE);
            }

            nextPage = ServletConstants.FRAME_PAGE;
            String pageToShow = "/jsp/photo/search/doShowOnePhoto.jsp";
            req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
            req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);

            if (!errors.isEmpty()) {
                // coloca erros no request para registrar.jsp processar e
                // apresentar mensagem de erro
                req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            }
        }
        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);
    }

    /**
     * This method creates the VO with the identification data
     * 
     * @param bean
     *            The bean with data from user
     * @param user
     *            The user identifying the photo
     * @param photo
     *            The photo being identified
     * 
     * @return A new <code>PhotoIdentification</code> object
     */
    private PhotoIdentification createObject(IdentifyPhotoBean bean, User user,
            Photo photo) {
        PhotoIdentification object = new PhotoIdentification();
        object.setPhoto(photo);
        object.setUser(user);

        int ageId = Integer.parseInt(bean.getSelectedAgeId());
        object.setAge(AgeModel.getAge(ageId));
        int sexId = Integer.parseInt(bean.getSelectedSexId());
        object.setSex(SexModel.getSex(sexId));
        object.setComment(bean.getComment());

        Specie specie = getSpecie(bean.getSelectedSpecieId());
        object.setSpecie(specie);
        return object;
    }

    /**
     * This method creates the specie object (with default values), and set the
     * id into it.
     * 
     * @param string
     *            The id as string
     * 
     * @return A new Specie object, only with the id set
     */
    private Specie getSpecie(String string) {
        if (string == null || string.trim().length() == 0) {
            return null;
        }
        int id = Integer.parseInt(string);

        SpecieDAO dao = new SpecieDAO();
        Specie specie = null;
        try {
            specie = dao.retrieve(id);
        } catch (DatabaseDownException e) {
            logger.fatal("Could not retrieve Specie object for id " + id);
        } catch (SQLException e) {
            logger.fatal("Could not retrieve Specie object for id " + id);
        } finally {
            if (specie == null) {
                specie = new Specie();
                specie.setId(id);
            }
        }
        return specie;
    }

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
        IdentifyPhotoBean bean = super.handleBean(req, session, beanKey, errors);
        
        setListOfFamilies(bean);
        setListOfSpecies(bean);
        return bean;
    }    
}
