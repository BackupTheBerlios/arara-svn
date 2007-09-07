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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.email.AbstractPhotoEmailSender;
import net.indrix.arara.model.email.IdentificationPhotoEmailSender;
import net.indrix.arara.model.email.NewSpecieEmailSender;
import net.indrix.arara.model.email.PhotoEmailSender;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.model.file.PhotoFileManager;
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
    private static final int wForSmallPhoto = setSmallWidth(PhotoUtil.getSmallWidth());

    /**
     * This method uploads a photo, meaning that it saves it into database, and stores it into 
     * file system
     * 
     * @param photo The photo to be stored
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     * @throws ImageProcessingException If there is an error with the file
     */
    public void uploadPhoto(Photo photo) throws DatabaseDownException,
            SQLException, ImageProcessingException {

        logger.debug("Calling createSmallPhoto with " + photo);
        createSmallPhoto(photo);

        if (isPhotoValid(photo)) {
            PhotoDAO dao = new PhotoDAO();

            boolean newSpecie;
            if (dao.hasSpecieAPhoto(photo.getSpecie().getId())) {
                newSpecie = false;
            } else {
                newSpecie = true;
            }
            logger.debug("New Specie = " + newSpecie);

            logger.debug("Calling insert in DAO object...");
            dao.insert(photo);

            // now write photos to file system
            logger.debug("Writing photo and thumbnail in file system...");
            PhotoFileManager manager = new PhotoFileManager(photo);
            try {
                manager.writeFile();
                logger.debug("Photo has been written in file system...");
                photo.setRelativePath(manager.getRelativePath());
                photo.setThumbnailRelativePath(manager.getThumbnailRelativePath());
            } catch (FileNotFoundException e) {
                // remove photo from database
                logger.error("Photo " + photo.getId() + " could not be saved into file system.", e);
                dao.delete(photo.getId());
                throw new ImageProcessingException();
            }
            
            // retrieve data from database, so all data will be loaded
            SpecieDAO sDao = new SpecieDAO();
            Specie s = sDao.retrieve(photo.getSpecie().getId());
            photo.setSpecie(s);

            // send email to users
            logger.debug("Sending email");
            AbstractPhotoEmailSender emailSender = new PhotoEmailSender(photo);
            emailSender.sendEmail();
            
            if (newSpecie){
                emailSender = new NewSpecieEmailSender(photo);
                emailSender.sendEmail();
                ((NewSpecieEmailSender)emailSender).sendNewSpecieEmail(photo);
            }
        } else {
            throw new ImageProcessingException(
                    "Real image or thumbnail is wrong" + photo);
        }
    }

    /**
     * This method uploads a photo for identification, meaning that it saves it into database, 
     * and stores it into file system
     * 
     * @param photo The photo to be stored
     * 
     * @throws DatabaseDownException If the database is down
     * @throws SQLException If some SQL Exception occurs
     * @throws ImageProcessingException If there is an error with the file
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

            // now write photos to file system
            logger.debug("Writing photo and thumbnail in file system...");
            PhotoFileManager manager = new PhotoFileManager(photo);
            try {
                manager.writeFile();
                logger.debug("Photo has been written in file system...");
                photo.setRelativePath(manager.getRelativePath());
                photo.setThumbnailRelativePath(manager.getThumbnailRelativePath());
            } catch (FileNotFoundException e) {
                // remove photo from database
                logger.error("Photo " + photo.getId() + " could not be saved into file system.", e);
                dao.delete(photo.getId());
                throw new ImageProcessingException();
                
            }
           
            // send email to users
            logger.debug("Sending emails for identification...");
            IdentificationPhotoEmailSender emailSender = new IdentificationPhotoEmailSender(photo);
            emailSender.sendEmail();
        } else {
            throw new ImageProcessingException("Real image or thumbnail is wrong" + photo);
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
    protected static void createSmallPhoto(Photo photo)
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
        int hForSmallPhoto = Math.round(wForSmallPhoto / r);
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

}
