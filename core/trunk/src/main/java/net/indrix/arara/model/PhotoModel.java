/*
 * Created on 25/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.CommentsDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.dao.PhotoIdentificationDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.exceptions.PhotoAlreadyIdentifiedException;
import net.indrix.arara.model.file.PhotoFileManager;
import net.indrix.arara.model.file.SoundFileManager;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Comment;
import net.indrix.arara.vo.ImageFile;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.PhotoIdentification;
import net.indrix.arara.vo.Sound;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoModel extends MediaModel {

    public PhotoModel() {
        super();
        dao = new PhotoDAO();
    }

    /**
     * The DAO object to be used to retrieve data of photo for identification
     * from database
     */
    PhotoIdentificationDAO daoIdentification = new PhotoIdentificationDAO();

    SoundModel model = new SoundModel();

    /**
     * This method updates a photo into database
     * 
     * @param photo
     *            The photo to be updated
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public void update(Photo photo) throws DatabaseDownException, SQLException {
        int oldId = photo.getId();
        // retrieve old photo, so that the current path can be retrieved and photo can be copied
        // in the file system
        Photo oldPhoto = retrieve(oldId);

        logger.debug("Photo " + photo);
        dao.update(photo);
        SpecieDAO specieDao = new SpecieDAO();
        logger.debug("Retrieving new specie object for id " + photo.getSpecie().getId());
        Specie specie = specieDao.retrieve(photo.getSpecie().getId());
        logger.debug("Specie retrieved " + specie);
        photo.setSpecie(specie);
        

        // retrieve current path for the photo
        String currentPath = oldPhoto.getRelativePath();
        String currentPathForThumbnail = oldPhoto.getThumbnailRelativePath();
        
        // copy the photo from old path to the new one
        PhotoFileManager manager = new PhotoFileManager(photo);
        manager.updatePhotoAndMoveInFileSystem(currentPath, currentPathForThumbnail);
        updatePhotoLink(photo);
    }

    /**
     * This method deletes a photo given by the photoId
     * 
     * @param photoId
     *            The id of the photo to be deleted
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public void delete(int photoId) throws DatabaseDownException, SQLException {
        logger.debug("PhotoId " + photoId);
        dao.delete(photoId);
    }

    /**
     * This method retrieves a photo given by the photoId
     * 
     * @param photoId
     *            The id of the photo to be retrieved
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public Photo retrieve(int photoId) throws DatabaseDownException,
            SQLException {
        logger.debug("PhotoId " + photoId);
        Photo photo = ((PhotoDAO) dao).retrieve(photoId);

        updatePhotoLink(photo);
        return photo;
    }

    /**
     * This method retrieves a photo given by the photoId. Only the thumbnail
     * image is retrieved
     * 
     * @param photoId
     *            The id of the photo to be retrieved
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public Photo retrieveThumbnail(int photoId) throws DatabaseDownException,
            SQLException {
        logger.debug("PhotoId " + photoId);
        Photo photo = ((PhotoDAO) dao).retrieveThumbnail(photoId);

        updatePhotoLink(photo);
        
        if (photo.isSoundAvailable()) {
            model.updateSoundLink(photo.getSound());
        }

        return photo;
    }

    /**
     * This method retrieves the id of all photos from database
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrievePhotoIDs() throws DatabaseDownException, SQLException {
        logger.debug("Retrieving photo ids...");
        List list = dao.retrieveIDs();
        logger.debug("Photo ids retrieved.");
        return list;
    }

    /**
     * This method retrieves the id of all photos from database for Slide Show
     * 
     * @return An ArrayList object with Integer objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrievePhotoIDsForSlideShow() throws DatabaseDownException, SQLException {
        logger.debug("Retrieving photo ids...");
        List list = ((PhotoDAO) dao).retrieveIDsForSlideShow();
        logger.debug("Photo ids retrieved.");
        return list;
    }
    
    /**
     * This method retrieves a <code>List</code> object with
     * <code>Integer</code> objects, of photos more recently added to database
     * 
     * @return a <code>List</code> object with <code>Photo</code> objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrievePhotoIDsForRecentPhotos() throws DatabaseDownException,
            SQLException {
        logger.debug("Retrieving photo ids for more recent photos...");
        List list = ((PhotoDAO) dao).retrieveIDsForRecentPhotos();
        logger.debug("Photo ids retrieved.");
        return list;
    }

    /**
     * This method retrieves all comments for the given photo
     * 
     * @param photoId
     *            The id of the photo
     * 
     * @return An ArrayList object with Photo objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveCommentsForPhoto(Photo photo)
            throws DatabaseDownException, SQLException {
        logger.debug("PhotoId " + photo.getId());
        CommentsDAO dao = new CommentsDAO();
        List list = dao.retrieveComments(photo);
        return list;
    }

    /**
     * This method inserts comments to a photo
     * 
     * @param photo
     *            the photo that received a comment
     * @param comment
     *            the comment from user
     */
    public void insertComment(Photo photo, User user, String comment)
            throws DatabaseDownException, SQLException {
        Comment c = new Comment();
        c.setComment(comment);
        c.setPhoto(photo);
        c.setUser(user);
        c.setDate(new Date());
        logger.debug("Inserting comment to photo " + photo);
        logger.debug("Inserting comment on" + c.getDate());
        CommentsDAO dao = new CommentsDAO();
        dao.insertComment(c);

        String login = photo.getUser().getLogin();
        logger.debug("Notifying user " + login + " about new comment.");
        notifyPhotoAuthor(c, photo, user);
    }

    /**
     * This method retrieves all photos from database
     * 
     * @return An ArrayList object with Photo objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrievePhotosForIdentification() throws DatabaseDownException,
            SQLException {
        logger.debug("Entering method...");
        List list = daoIdentification.retrieveIDs();
        return list;
    }

    /**
     * THis method adds an identification to a photo into database
     * 
     * @param identification
     *            The identification done by a user
     * 
     * @return true if the identification has been closed, false otherwise
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     * @throws PhotoAlreadyIdentifiedException
     *             If the photo has been identified already
     * 
     */
    public boolean identifyPhoto(PhotoIdentification identification,
            boolean finishIdentification) throws DatabaseDownException,
            SQLException, PhotoAlreadyIdentifiedException {
        logger.debug("Entering method...");

        boolean finishedIdentification = false;

        Photo photo = identification.getPhoto();
        if (daoIdentification.isPhotoForIdentification(photo.getId())) {
            logger.debug("Identification : " + identification);
            daoIdentification.insert(identification);

            // verifying if the phot author has finished the identification
            User photoAuthor = photo.getUser();
            User photoIdentifier = identification.getUser();
            if ((photoAuthor.getId() == photoIdentifier.getId())
                    || finishIdentification) {

                PhotoFileManager manager = new PhotoFileManager(photo);
                
                // retrieve current path for the photo
                String currentPath = photo.getRelativePath();
                String currentPathForThumbnail = photo.getThumbnailRelativePath();
                
                // photo author has identified photo or admin has finish
                // identification Set the specie to it
                logger.debug("Setting specie, age and sex to photo.");
                photo.setSpecie(identification.getSpecie());
                photo.setAge(identification.getAge());
                photo.setSex(identification.getSex());
                logger.debug("Photo author has identified his/her photo "
                        + photo);
                logger.debug("Updating photo into database...");
                dao.update(photo);
                
                // move the photo from old path to the new one
                manager.updatePhotoAndMoveInFileSystem(currentPath, currentPathForThumbnail);
                updatePhotoLink(photo);
                
                // send an email to everyone that identified the photo that the
                // identification has
                // finished
                sendFinalEmailToIdentifiers(photoAuthor, identification, finishIdentification);

                finishedIdentification = true;
            } else {
                // send an email to everyone that identified the photo that the
                // a new photo
                // identification has been done
                sendEmailToIdentifiers(photoIdentifier, identification);
                // send an email to the photo author that a new identification
                // for his/her photo has
                // been done
                sendEmailToPhotoAuthor(photoAuthor, identification);
            }
        } else {
            // photo author has already finished identification for photo
            throw new PhotoAlreadyIdentifiedException("Photo " + photo.getId());
        }

        return finishedIdentification;
    }

    /**
     * This method retrieves all identifications for the given photo
     * 
     * @param photo
     *            The photo to have its identifications retrieved
     * 
     * @return An ArrayList object with Comment objects
     * 
     * @throws DatabaseDownException
     *             If the database is down
     * @throws SQLException
     *             If some SQL Exception occurs
     */
    public List retrieveIdentificationsForPhoto(Photo photo)
            throws DatabaseDownException, SQLException {
        logger.debug("PhotoId " + photo.getId());
        PhotoIdentificationDAO dao = new PhotoIdentificationDAO();
        List list = dao.retrieveIdentifications(photo);
        return list;
    }

    /**
     * This method updates the photo link, to the given photo
     * 
     * @param photo The Photo object to be updated
     */
    public void updatePhotoLink(Photo photo) {
        // set the filename, with the full path, to the sound file
        PhotoFileManager manager = new PhotoFileManager(photo);
        photo.setRelativePath(manager.getRelativePath());
        photo.setThumbnailRelativePath(manager.getThumbnailRelativePath());
        
    }
    
    /**
     * This method notifies the photo author about a new comment added to
     * his/her photo
     * 
     * @param photo
     *            The photo that received a comment
     */
    private void notifyPhotoAuthor(Comment c, Photo photo, User user) {
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = null;
        String body = null;
        String fromText = null;
        try {
            logger.debug("Sending email...");

            MailClass sender = new MailClass(server);

            // retrieve addresses to send photo
            CommentsDAO dao = new CommentsDAO();
            List<User> l = dao.retrieveUsersWithCommentsForPhoto(photo.getId(),
                    user.getId());
            Iterator dIt = l.iterator();
            while (dIt.hasNext()) {
                User u = (User) dIt.next();
                logger.debug("User = " + u);
            }

            if (!l.contains(photo.getUser())) {
                l.add(photo.getUser());
            }

            Iterator it = l.iterator();
            while (it.hasNext()) {
                User u = (User) it.next();

                Locale locale = new Locale(u.getLanguage());
                EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
                        .getInstance();

                subject = bundle.getString("email.newComment.subject", locale);
                body = bundle.getString("email.newComment.body", locale);
                fromText = bundle
                        .getString("email.newComment.fromText", locale);

                sender.setMessageTextBody(getMessage(u, body, c, photo));
                sender.setSubject(subject);
                sender.setFromAddress(fromAdd, fromText);

                sender.setToAddress(u.getEmail());
                sender.sendMessage(false);
                // false indicates to emailObject to not send the message right
                // now
            }
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail "
                    + e);
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
        }
    }

    /**
     * Format the message to be sent to the photo author about a new
     * identification that has been done to his/her photo
     * 
     * @param user
     *            The photo author
     * @param body
     *            The body text template to be formatted
     * @param comment
     *            The comment added to the photo
     * @param photo
     *            The photo identification
     * 
     * @return A formatted message to be sent to the photo author about a new
     *         identification that has been done to his/her photo
     */
    private String getMessage(User user, String body, Comment comment,
            Photo photo) {
        String bodyFormatted = "";
        ArrayList<String> list = new ArrayList<String>();
        String url = "http://www.aves.brasil.nom.br/servlet/showOnePhoto?photoId="
                + photo.getId();
        list.add(user.getName());
        list.add(comment.getComment());
        list.add(url);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("Exception", e);
        }
        return bodyFormatted;
    }

    /**
     * This method sends an email to the author of a photo about a new
     * identification that has been done to his/her photo
     * 
     * @param photoAuthor
     *            The author of a photo for identification
     * @param photo
     *            The photo under identification
     */
    private void sendEmailToPhotoAuthor(User photoAuthor,
            PhotoIdentification photoIdentification) {
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = null;
        String body = null;
        String fromText = null;
        try {
            MailClass sender = new MailClass(server);

            Locale locale = new Locale(photoAuthor.getLanguage());
            EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
                    .getInstance();

            subject = bundle.getString(
                    "email.toAuthor.newPhotoIdentification.subject", locale);
            body = bundle.getString(
                    "email.toAuthor.newPhotoIdentification.body", locale);
            fromText = bundle.getString(
                    "email.toAuthor.newPhotoIdentification.fromText", locale);

            sender.setMessageTextBody(getMessage(photoAuthor, body,
                    photoIdentification));
            sender.setSubject(subject);
            sender.setFromAddress(fromAdd, fromText);

            logger.debug("Sending to... " + photoAuthor.getEmail());
            sender.setToAddress(photoAuthor.getEmail());
            sender.sendMessage(false);
            // false indicates to emailObject to not send the message right now
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail "
                    + e);
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
        }
    }

    /**
     * Formats the message to be sent to the photo author about a new
     * identification that has been done to his/her photo
     * 
     * @param user
     *            The photo author
     * @param body
     *            The body text template to be formatted
     * @param photo
     *            The photo identification
     * 
     * @return A formatted message to be sent to the photo author about a new
     *         identification that has been done to his/her photo
     */
    private String getMessage(User user, String body,
            PhotoIdentification photoIdentification) {
        String bodyFormatted = "";
        ArrayList<String> list = new ArrayList<String>();
        String url = "http://www.aves.brasil.nom.br/servlet/initIdentification?photoId="
                + photoIdentification.getPhoto().getId();
        list.add(user.getName());
        list.add(photoIdentification.getUser().getName());
        list.add(url);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("Exception", e);
        }
        return bodyFormatted;
    }

    /**
     * This method sends an email to everyone that has already identified a
     * photo, about a new identification done
     * 
     * @param photoIdentifier
     *            The new identifier user
     * @param identification
     *            The new identification
     */
    private void sendEmailToIdentifiers(User photoIdentifier,
            PhotoIdentification photoIdentification) {
        logger.debug("PhotoModel.sendEmailToIdentifiers: entering method...");
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = null;
        String body = null;
        String fromText = null;
        try {
            List listOfUsers = daoIdentification
                    .retrieveUsersForIdentification(photoIdentification
                            .getPhoto().getId());

            MailClass sender = new MailClass(server);

            Iterator it = listOfUsers.iterator();

            EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
                    .getInstance();

            Map<String, Locale> map = new HashMap<String, Locale>();
            while (it.hasNext()) {
                User user = (User) it.next();

                Locale locale = (Locale) map.get(user.getLanguage());
                if (locale == null) {
                    locale = new Locale(user.getLanguage());
                    map.put(user.getLanguage(), locale);
                }
                subject = bundle.getString(
                        "email.identification.to.photo.subject", locale);
                body = bundle.getString("email.identification.to.photo.body",
                        locale);
                fromText = bundle.getString(
                        "email.identification.to.photo.fromText", locale);

                if (!user.equals(photoIdentifier)) {
                    sender.setMessageTextBody(getMessage(user, body,
                            photoIdentification));
                    sender.setSubject(subject);
                    sender.setFromAddress(fromAdd, fromText);

                    logger.debug("Sending to... " + user.getName() + " | "
                            + user.getEmail());
                    sender.setToAddress(user.getEmail());
                    sender.sendMessage(false);
                    // false indicates to emailObject to not send the message
                    // right now
                }
            }
        } catch (DatabaseDownException e1) {
            logger.fatal("DatabaseDownException ", e1);
        } catch (SQLException e1) {
            logger.fatal("SQL exception ", e1);
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail "
                    + e);
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
            e.printStackTrace();
        }
    }

    /**
     * This method sends an email to everyone that has already identified a
     * photo, about a new identification done
     * 
     * @param photoAuthor
     *            The new photo author
     * @param identification
     *            The new identification
     */
    private void sendFinalEmailToIdentifiers(User photoAuthor,
            PhotoIdentification photoIdentification, boolean finishIdentification) {
        logger.debug("Entering method...");
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = null;
        String body = null;
        String fromText = null;
        try {
            List listOfUsers = daoIdentification
                    .retrieveUsersForIdentification(photoIdentification
                            .getPhoto().getId());

            MailClass sender = new MailClass(server);

            Iterator it = listOfUsers.iterator();

            EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle
                    .getInstance();

            Map<String, Locale> map = new HashMap<String, Locale>();
            while (it.hasNext()) {
                User user = (User) it.next();
                Locale locale = (Locale) map.get(user.getLanguage());
                if (locale == null) {
                    locale = new Locale(user.getLanguage());
                    map.put(user.getLanguage(), locale);
                }
                subject = bundle.getString(
                        "email.close.identification.to.photo.subject", locale);
                body = bundle.getString(
                        "email.close.identification.to.photo.body", locale);
                fromText = bundle.getString(
                        "email.close.identification.to.photo.fromText", locale);

                
                if (finishIdentification || !user.equals(photoAuthor)) {
                    sender.setMessageTextBody(getMessage(user, body,
                            photoIdentification));
                    sender.setSubject(subject);
                    sender.setFromAddress(fromAdd, fromText);

                    logger.debug("Sending to... " + user.getName() + " | "
                            + user.getEmail());
                    sender.setToAddress(user.getEmail());
                    sender.sendMessage(false);
                    // false indicates to emailObject to not send the message
                    // right now
                }
            }
        } catch (DatabaseDownException e1) {
            logger.fatal("DatabaseDownException ", e1);
        } catch (SQLException e1) {
            logger.fatal("SQL exception ", e1);
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail "
                    + e);
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
            e.printStackTrace();
        }
    }

}
