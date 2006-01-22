/*
 * Created on 07/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

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
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import net.indrix.dao.DatabaseDownException;
import net.indrix.dao.SpecieDAO;
import net.indrix.dao.UserDAO;
import net.indrix.model.UploadPhoto;
import net.indrix.model.exceptions.ImageProcessingException;
import net.indrix.vo.Photo;
import net.indrix.vo.Specie;
import net.indrix.vo.User;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class InsertPhoto {
    private static final int WIDTH_FOR_SMALL_IMAGE = 160;
    private static final String TEMP_PATH = "d:\\aves\\temp\\";

	public static void main(String[] args) throws IOException, DatabaseDownException, SQLException, ImageProcessingException {
		SpecieDAO sDao = new SpecieDAO();
		Specie specie = sDao.retrieve(Integer.parseInt(JOptionPane.showInputDialog(null, "Specie Id")));
		
		UserDAO uDao = new UserDAO();
		User user = uDao.retrieve("jeff");
		
		Date date = new Date(2005, 01, 06);
		File f = new File(JOptionPane.showInputDialog(null, "filename"));
		FileInputStream input = new FileInputStream(f);
		int size = (int)f.length();
		Photo photo = new Photo();
		photo.setUser(user);
		photo.setSpecie(specie);		
		photo.setDate(date);
		photo.setCamera("Canon EOS");
		photo.setFilm("Kodak");
		photo.getRealImage().setImage(input);
		photo.getRealImage().setImageSize(size);
        
        //String tempFilename = createSmallPhoto(photo);
        
		UploadPhoto upload = new UploadPhoto();
		upload.uploadPhoto(photo);
		input.close();
		System.out.println(photo.getId());

        //deleteTempFile(tempFilename);        		
	}

    private static String createSmallPhoto(Photo photo) throws ImageProcessingException {
        String path = null;
        byte[] bytes = new byte[photo.getRealImage().getImageSize()];

        try {
            InputStream in = photo.getRealImage().getImage();
            in.read(bytes);
            in.close();
            ByteArrayInputStream inp = new ByteArrayInputStream(bytes);
            photo.getRealImage().setImage(inp);
        } catch (IOException e) {
            throw new ImageProcessingException("Error creating byte[] from InputStream");
        }

        ImageIcon icon = new ImageIcon(bytes);
        photo.getRealImage().setWidth(icon.getIconWidth());
        photo.getRealImage().setHeight(icon.getIconHeight());
        float r = ((float) photo.getRealImage().getWidth()) / photo.getRealImage().getHeight();
        int hForSmallPhoto = (int) (WIDTH_FOR_SMALL_IMAGE / r);
        photo.getSmallImage().setWidth(WIDTH_FOR_SMALL_IMAGE);
        photo.getSmallImage().setHeight(hForSmallPhoto);
        
        Image image = icon.getImage();
        path = createFilename();
        File file = new File(path);
        RenderedImage smallImage = null;
        try {
            smallImage = createThumbnail(image, WIDTH_FOR_SMALL_IMAGE, hForSmallPhoto);
            ImageIO.write(smallImage, "jpg", file);
            try {
                File newFile = new File(path);
                InputStream inputStream = new FileInputStream(newFile);
                photo.getSmallImage().setImage(inputStream);
                photo.getSmallImage().setImageSize((int) newFile.length());
            } catch (IOException e1) {
                throw new ImageProcessingException("Error creating FileInputStream for smallImage...");
            }
        } catch (IOException e2) {
            throw new ImageProcessingException("Error saving smallImage in a file...");
        }
        return path;
    }

    /**
     * @return
     */
    private static String createFilename() {
        double d = Math.random();
        String filename = "file" + d + ".jpg";
        return TEMP_PATH + filename;
    }

    /**
     * @param tempFilename
     */
    private static void deleteTempFile(String tempFilename) {
        File f = new File(tempFilename);
        f.delete();
    }
    
    /**
     * 
     * @param image
     * @param w
     * @param h
     * @return
     */
    public static RenderedImage createThumbnail(Image image, int w, int h) {
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
}
