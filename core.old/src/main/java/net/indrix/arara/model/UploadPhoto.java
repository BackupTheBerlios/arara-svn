/* 
 * Created on Jun 1, 2005 
 * 
 * To change the template for this generated file go to 
 * Window>Preferences>Java>Code Generation>Code and Comments 
 */
package net.indrix.arara.model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.swing.ImageIcon;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.dao.PhotoIdentificationDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.vo.ImageFile;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.User;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.model.exceptions.ImageProcessingException;

import org.apache.log4j.Logger;

/** 
 * @author wjs085 
 * 
 * To change the template for this generated type comment go to 
 * Window>Preferences>Java>Code Generation>Code and Comments 
 */
public class UploadPhoto extends AbstractUpload {
	/**
	 * The default width value, in case the value from properties could not be read
	 */
	public static final int W = 240;
	/**
	 * the width for the thumbnail
	 */
	private static final int wForSmallPhoto = setSmallWidth(PhotoUtil.getSmallWidth());

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	public void uploadPhoto(Photo photo)
		throws DatabaseDownException, SQLException, ImageProcessingException {

		logger.debug("Calling createSmallPhoto with " + photo);
		createSmallPhoto(photo);

		if (isPhotoValid(photo)) {
			PhotoDAO dao = new PhotoDAO();
			logger.debug("Calling insert in DAO object");
			dao.insert(photo);

			// retrieve data from database, so all data will be loaded           
			SpecieDAO sDao = new SpecieDAO();
			Specie s = sDao.retrieve(photo.getSpecie().getId());
			photo.setSpecie(s);

			// send email to users
			logger.debug("Sending email");
			sendEmail(photo);
		} else {
			throw new ImageProcessingException("Real image or thumbnail is wrong" + photo);
		}
	}

	/**
	 * This method sends an email to users about new photo
	 * 
	 * @param photo The new photo data
	 */
	private void sendEmail(Photo photo) {
		UserModel userModel = new UserModel();
		try {
			logger.debug("UploadPhoto.sendEmail : Retrieving list of users to sent email to");
			List list = userModel.retrieveForEmail();
			if (!list.isEmpty()) {
				Iterator it = list.iterator();
				int count = 0;
				List addresses = new ArrayList();
				while (it.hasNext()) {
					User user = (User) it.next();
					count++;
					addresses.add(user.getEmail());
					if (count >= 10) {
						logger.debug("UploadPhoto.sendEmail : sending email");
						sendEmailToUsers(addresses, photo);
						addresses.clear();
						count = 0;
					}
				}
				if (count > 0) {
					logger.debug("UploadPhoto.sendEmail : sending email");
					sendEmailToUsers(addresses, photo);
				}
			}
		} catch (DatabaseDownException e1) {
			logger.fatal("DatabaseDownException ", e1);
		} catch (SQLException e1) {
			logger.fatal("SQL exception ", e1);
		} catch (UserNotFoundException e1) {
			logger.fatal("No user has been found in database");
		}
	}

	/**
	 * @param users
	 */
	private void sendEmailToUsers(List addresses, Photo photo) {
		String server = PropertiesManager.getProperty("email.server");
		String subject = PropertiesManager.getProperty("email.newPhoto.subject");
		String body = PropertiesManager.getProperty("email.newPhoto.body");
		String fromAdd = PropertiesManager.getProperty("email.newPhoto.fromAdd");
		String fromText = PropertiesManager.getProperty("email.newPhoto.fromText");
		try {

			// send password to user
			logger.debug("enviando email com os dados:");
			logger.debug(server);
			Iterator it = addresses.iterator();
			while (it.hasNext()) {
				logger.debug((String) it.next());
			}
			logger.debug(subject);
			logger.debug(getMessage(body, photo));
			logger.debug(fromAdd);
			logger.debug(fromText);

			MailClass sender = new MailClass(server);
			logger.debug("Setting to...");
			sender.setToAddresses(addresses);
			logger.debug("Setting subject...");
			sender.setSubject(subject);
			logger.debug("Setting message...");
			sender.setMessageTextBody(getMessage(body, photo));
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
	private String getMessage(String body, Photo photo) {
		String bodyFormatted = "";
		ArrayList list = new ArrayList();
		list.add(photo.getUser().getLogin() + " | " + photo.getUser().getName());
		String family = photo.getSpecie().getFamily().getName();
		list.add(family);
		String specie = photo.getSpecie().getName();
		list.add(specie);
		String url = "http://www.aves.brasil.nom.br/servlet/showOnePhoto?photoId=" + photo.getId();
		list.add(url);

		logger.debug(family + " | " + specie + " | " + url);

		try {
			bodyFormatted = MessageComposer.formatMessage(body, list);
		} catch (WrongNumberOfValuesException e) {
			logger.error("UploadPhoto.getMessage : Exception", e);
		}
		return bodyFormatted;
	}

	/**
	 * This method verifies if the image was successfully uploaded, and that the thumbnail was
	 * successfully created
	 * 
	 * @param photo The photo to be checked
	 * 
	 * @return true if the photo is ok, false otherwise
	 */
	private boolean isPhotoValid(Photo photo) {
		return (photo.getRealImage().getWidth() > 0)
			&& (photo.getRealImage().getHeight() > 0)
			&& (photo.getSmallImage().getWidth() > 0)
			&& (photo.getSmallImage().getHeight() > 0);
	}

	/**
	 * @param i
	 * @return
	 */
	private static int setSmallWidth(int i) {
		if (i == 0) {
			i = W;
		}
		return i;
	}

	/**
	 * This method creates the thumbnail picture for the given photo object
	 * 
	 * @param photo The object with the real image
	 * 
	 * @return the path for the temporary file created
	 * 
	 * @throws ImageProcessingException
	 */
	public static void createSmallPhoto(Photo photo) throws ImageProcessingException {
		String path = null;
		ImageFile realimage = photo.getRealImage();
		ImageFile smallImage = photo.getSmallImage();
		int size = realimage.getImageSize();
		if (size == 0) {
			try {
				size = realimage.getImage().available();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		byte[] bytes = new byte[size];

		try {
			InputStream in = realimage.getImage();
			in.read(bytes);
			in.close();
			ByteArrayInputStream inp = new ByteArrayInputStream(bytes);
			realimage.setImage(inp);
		} catch (IOException e) {
			throw new ImageProcessingException("Error creating byte[] from InputStream");
		}

		logger.debug("Creating ImageIcon");
		ImageIcon icon = new ImageIcon(bytes);
		realimage.setWidth(icon.getIconWidth());
		realimage.setHeight(icon.getIconHeight());
		float r = ((float) realimage.getWidth()) / realimage.getHeight();
		logger.debug("Rate calculated with value = " + r);
		int hForSmallPhoto = (int) (wForSmallPhoto / r);
		smallImage.setWidth(wForSmallPhoto);
		smallImage.setHeight(hForSmallPhoto);

		Image image = icon.getImage();
		path = createFilename();
		logger.debug("Temporary filename created as " + path);
		File file = new File(path);
		RenderedImage thumbnail = null;
		try {
			// create the small image
			thumbnail = createThumbnail(image, wForSmallPhoto, hForSmallPhoto);
			// write image to temp file
			ImageIO.write(thumbnail, "jpg", file);
			try {
				// create the inputStream from the temp file
				File newFile = new File(path);
				InputStream inputStream = new FileInputStream(newFile);
				smallImage.setImage(inputStream);
				smallImage.setImageSize((int) newFile.length());
			} catch (IOException e1) {
				throw new ImageProcessingException("Error creating FileInputStream for smallImage...");
			}
		} catch (IOException e2) {
			throw new ImageProcessingException("Error saving smallImage in a file...");
		}

		logger.debug("Deleting temporary file " + path);
		File f = new File(path);
		f.delete();
	}

	/**
	 * This method creates an thumbnail image for the given image
	 *  
	 * @param image The source image, for which a thumbnail will be created
	 * @param w the new width for the thumbnail
	 * @param h the new height for the thumbnail
	 * 
	 * @return a <code>RenderedImage</code> object for the thumbnail
	 */
	private static RenderedImage createThumbnail(Image image, int w, int h) {
		// Create a buffered image in which to draw
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setRenderingHint(
			RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		// Draw graphics
		g2d.drawImage(image, 0, 0, w, h, null);

		// Graphics context no longer needed so dispose it
		g2d.dispose();

		return bufferedImage;
	}

	/**
	 * @param photo
	 */
	public void uploadPhotoForIdentification(Photo photo)
		throws DatabaseDownException, SQLException, ImageProcessingException {
        logger.debug("UploadPhoto.uploadPhotoForIdentification : entering method");            
		logger.debug("Calling createSmallPhoto with " + photo);
		createSmallPhoto(photo);

		if (isPhotoValid(photo)) {
			PhotoIdentificationDAO dao = new PhotoIdentificationDAO();
			logger.debug("Calling insert in PhotoIdentificationDAO object");
			dao.insert(photo);

			// retrieve data from database, so all data will be loaded           
			//SpecieDAO sDao = new SpecieDAO();
			//Specie s = sDao.retrieve(photo.getSpecie().getId());
			photo.setSpecie(new Specie());

			// send email to users
			//logger.debug("Sending email");
			//sendEmail(photo);
		} else {
			throw new ImageProcessingException("Real image or thumbnail is wrong" + photo);
		}
	}
}
