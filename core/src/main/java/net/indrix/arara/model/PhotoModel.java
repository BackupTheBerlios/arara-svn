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
import java.util.Iterator;
import java.util.List;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.dao.CommentsDAO;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.dao.PhotoIdentificationDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.Comment;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoModel {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * This method updates a photo into database
	 * 
	 * @param photo The photo to be updated
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public void update(Photo photo) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.update | photo " + photo);
		PhotoDAO dao = new PhotoDAO();
		dao.update(photo);
		SpecieDAO specieDao = new SpecieDAO();
		logger.debug("Retrieving new specie object for id " + photo.getSpecie().getId());
		Specie specie = specieDao.retrieve(photo.getSpecie().getId());
		logger.debug("Specie retrieved " + specie);
		photo.setSpecie(specie);
	}
	/**
	 * This method deletes a photo given by the photoId
	 * 
	 * @param photoId The id of the photo to be deleted
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public void delete(int photoId) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.delete | photoId " + photoId);
		PhotoDAO dao = new PhotoDAO();
		dao.delete(photoId);
	}

	/**
	 * This method deletes a photo given by the photoId
	 * 
	 * @param photoId The id of the photo to be deleted
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public Photo retrieve(int photoId) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.retrieve | photoId " + photoId);
		PhotoDAO dao = new PhotoDAO();
		Photo photo = dao.retrieve(photoId);
		return photo;
	}

    /**
     * @param i
     * @return
     */
    public Photo retrieveForIdentification(int photoId) throws DatabaseDownException, SQLException {
        logger.debug("PhotoModel.retrieveForIdentification | photoId " + photoId);
        PhotoIdentificationDAO dao = new PhotoIdentificationDAO();
        Photo photo = dao.retrieve(photoId);
        return photo;
    }

	/**
	 * This method retrieves all photos from database
	 * 
	 * @return An ArrayList object with Photo objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrievePhotos() throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.retrievePhotos");
		PhotoDAO dao = new PhotoDAO();
		List list = dao.retrieve();
		return list;
	}

    /**
     * This method retrieves all photos from database
     * 
     * @return An ArrayList object with Photo objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrievePhotosForIdentification() throws DatabaseDownException, SQLException {
        logger.debug("PhotoModel.retrievePhotosForIdentification");
        PhotoIdentificationDAO dao = new PhotoIdentificationDAO();
        List list = dao.retrieve();
        return list;
    }

	/**
	 * This method retrieves all photos for the given family id
	 *  
	 * @param familyId The id of the family
	 * 
	 * @return An ArrayList object with Photo objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrievePhotosForFamily(int familyId) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.retrievePhotosForFamily | familyId " + familyId);
		PhotoDAO dao = new PhotoDAO();
		List list = dao.retrieveForFamily(familyId);
		return list;
	}

	/**
	 * This method retrieves all photos for the given specie id
	 * 
	 * @param specieId The id of the specie
	 * 
	 * @return An ArrayList object with Photo objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrievePhotosForSpecie(int specieId) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.retrievePhotosForSpecie | specieId " + specieId);
		PhotoDAO dao = new PhotoDAO();
		List list = dao.retrieveForSpecie(specieId);
		return list;
	}

    /**
     * This method retrieves all photos for the given common name id
     * 
     * @param commonNameId The id of the common name
     * 
     * @return An ArrayList object with Photo objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrievePhotosForCommonName(int commonNameId ) throws DatabaseDownException, SQLException {
        logger.debug("PhotoModel.retrievePhotosForCommonName | commonNameId " + commonNameId);
        PhotoDAO dao = new PhotoDAO();
        List list = dao.retrieveForCommonName(commonNameId);
        return list;
    }

	/**
	 * This method retrieves all photos for the given user id
	 * 
	 * @param userId  The id of the user
	 * 
	 * @return An ArrayList object with Photo objects
	 * 
	 * @throws DatabaseDownException If the database is down
	 * @throws SQLException If some SQL Exception occurs
	 */
	public List retrievePhotosForUser(int userId) throws DatabaseDownException, SQLException {
		logger.debug("PhotoModel.retrievePhotosForUser | userId " + userId);
		PhotoDAO dao = new PhotoDAO();
		List list = dao.retrieveForUser(userId);
		return list;
	}

    /**
     * This method retrieves all comments for the given photo
     * 
     * @param photoId  The id of the photo
     * 
     * @return An ArrayList object with Photo objects
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     */
    public List retrieveCommentsForPhoto(Photo photo) throws DatabaseDownException, SQLException {
        logger.debug("PhotoModel.retrieveCommentsForPhoto | photoId " + photo.getId());
        CommentsDAO dao = new CommentsDAO();
        List list = dao.retrieveComments(photo);
        return list;
    }

	/**
	 * This method inserts comments to a photo
	 * @param photo the photo that received a comment
	 * @param comment the comment from user
	 */
	public void insertComment(Photo photo, User user, String comment)
		throws DatabaseDownException, SQLException {
		Comment c = new Comment();
		c.setComment(comment);
		c.setPhoto(photo);
        c.setUser(user);
		c.setDate(new Date());
        logger.debug("PhotoModel.insertComment : inserting comment to photo " + photo);
        logger.debug("PhotoModel.insertComment : inserting comment on" + c.getDate());
		CommentsDAO dao = new CommentsDAO();
		dao.insertComment(c);
        
        logger.debug("Notifying user " + photo.getUser().getLogin() + " about new comment.");
        notifyPhotoAuthor(c, photo, user);
	}
	/**
	 * @param photo
	 */
	private void notifyPhotoAuthor(Comment c, Photo photo, User user) {
        String server = PropertiesManager.getProperty("email.server");
        String subject = PropertiesManager.getProperty("email.newComment.subject");
        String body = PropertiesManager.getProperty("email.newComment.body");
        String fromAdd = PropertiesManager.getProperty("email.newComment.fromAdd");
        String fromText = PropertiesManager.getProperty("email.newComment.fromText");
        try {

            // send password to user
            logger.debug("enviando email com os dados:");
            logger.debug(server);
            logger.debug(subject);
            logger.debug(getMessage(body, c, photo));
            logger.debug(fromAdd);
            logger.debug(fromText);
            
            // retrieve addresses to send photo
            CommentsDAO dao = new CommentsDAO();
            List l = dao.retrieveUsersForPhoto(photo.getId(), user.getId());
            if (!l.contains(photo.getUser().getEmail())){
                l.add(photo.getUser().getEmail());
            }
            Iterator it = l.iterator();
            while (it.hasNext()){
                logger.debug((String)it.next());                
            }
            
            MailClass sender = new MailClass(server);
            String to = photo.getUser().getEmail();
            logger.debug("Sending to... ");
            sender.setToAddresses(l);
            logger.debug("Setting subject...");
            sender.setSubject(subject);
            logger.debug("Setting message...");
            sender.setMessageTextBody(getMessage(body, c, photo));
            logger.debug("Setting from...");
            sender.setFromAddress(fromAdd, fromText);
            logger.debug("Sending message...");
            sender.sendMessage(true);
            // true indicates to emailObject to send the message right now
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail " + e);
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
     * @param body
     * @return
     */
    private String getMessage(String body, Comment c, Photo photo) {
        String bodyFormatted = "";
        ArrayList list = new ArrayList();
        String url = "http://www.aves.brasil.nom.br/servlet/showOnePhoto?photoId=" + photo.getId(); 
        list.add(c.getComment());
        list.add(url);
        
        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("UploadPhoto.getMessage : Exception", e);
        }
        return bodyFormatted;
    }
}
