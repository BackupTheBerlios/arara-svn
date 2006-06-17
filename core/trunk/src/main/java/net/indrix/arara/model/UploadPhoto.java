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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.email.IdentificationPhotoEmailSender;
import net.indrix.arara.model.email.PhotoEmailSender;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.vo.ImageFile;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.Specie;

/**
 * @author wjs085
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UploadPhoto extends AbstractUpload {
	/**
	 * The default width value, in case the value from properties could not be
	 * read
	 */
	public static final int W = 240;

	/**
	 * the width for the thumbnail
	 */
	private static final int wForSmallPhoto = setSmallWidth(PhotoUtil
			.getSmallWidth());

	public void uploadPhoto(Photo photo) throws DatabaseDownException,
			SQLException, ImageProcessingException {

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
			PhotoEmailSender emailSender = new PhotoEmailSender(photo);
			emailSender.sendEmail();
		} else {
			throw new ImageProcessingException(
					"Real image or thumbnail is wrong" + photo);
		}
	}

	/**
	 * This method verifies if the image was successfully uploaded, and that the
	 * thumbnail was successfully created
	 * 
	 * @param photo
	 *            The photo to be checked
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
	 * @param photo
	 *            The object with the real image
	 * 
	 * @return the path for the temporary file created
	 * 
	 * @throws ImageProcessingException
	 */
	public static void createSmallPhoto(Photo photo)
			throws ImageProcessingException {
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
			throw new ImageProcessingException(
					"Error creating byte[] from InputStream");
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
				throw new ImageProcessingException(
						"Error creating FileInputStream for smallImage...");
			}
		} catch (IOException e2) {
			throw new ImageProcessingException(
					"Error saving smallImage in a file...");
		}

		logger.debug("Deleting temporary file " + path);
		File f = new File(path);
		f.delete();
	}

	/**
	 * This method creates an thumbnail image for the given image
	 * 
	 * @param image
	 *            The source image, for which a thumbnail will be created
	 * @param w
	 *            the new width for the thumbnail
	 * @param h
	 *            the new height for the thumbnail
	 * 
	 * @return a <code>RenderedImage</code> object for the thumbnail
	 */
	private static RenderedImage createThumbnail(Image image, int w, int h) {
		// Create a buffered image in which to draw
		BufferedImage bufferedImage = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
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
			throws DatabaseDownException, SQLException,
			ImageProcessingException {
		logger
				.debug("UploadPhoto.uploadPhotoForIdentification : entering method");
		logger.debug("Calling createSmallPhoto with " + photo);
		createSmallPhoto(photo);

		if (isPhotoValid(photo)) {
			PhotoDAO dao = new PhotoDAO();
			logger.debug("Calling insert in PhotoIdentificationDAO object");
			dao.insert(photo);

			// retrieve data from database, so all data will be loaded
			// SpecieDAO sDao = new SpecieDAO();
			// Specie s = sDao.retrieve(photo.getSpecie().getId());
			photo.setSpecie(new Specie());

			// send email to users
			logger.debug("Sending emails for identification...");
			IdentificationPhotoEmailSender emailSender = new IdentificationPhotoEmailSender(
					photo);
			emailSender.sendEmail();
		} else {
			throw new ImageProcessingException(
					"Real image or thumbnail is wrong" + photo);
		}
	}
}
