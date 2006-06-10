/*
 * Created on 07/07/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;

import net.indrix.arara.model.UploadPhoto;
import net.indrix.arara.model.exceptions.ImageProcessingException;
import net.indrix.arara.vo.ImageFile;
import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CreateSmallPhotoTest extends UploadPhoto{

    public void doIt(String file) throws FileNotFoundException, ImageProcessingException{
        File f = new File(file);
        long size = f.length();
        Photo photo = new Photo();
        
        photo.setRealImage(getImageFile(file, size));
        createSmallPhoto(photo);
    }
	/**
	 * @param file
	 * @return
	 */
	private ImageFile getImageFile(String file, long size) throws FileNotFoundException {
		ImageFile image = new ImageFile();
        image.setImage(new FileInputStream(file));
        image.setImageSize((int)size);
       
		return image;
	}
	public static void main(String[] args) throws FileNotFoundException, ImageProcessingException {
        String filename = JOptionPane.showInputDialog(null, "File");
        CreateSmallPhotoTest test = new CreateSmallPhotoTest();
        test.doIt(filename);
	}
}
