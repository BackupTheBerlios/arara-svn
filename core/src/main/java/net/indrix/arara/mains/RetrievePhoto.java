/*
 * Created on 08/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.mains;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.PhotoModel;
import net.indrix.vo.Photo;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrievePhoto {

	public static void main(String[] args) throws DatabaseDownException, SQLException, IOException {
		int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Photo ID"));
		
		PhotoModel model = new PhotoModel();		
		Photo newPhoto = model.retrieve(id);
		InputStream in = newPhoto.getRealImage().getImage();
		
		saveImage(in, "d:\\aves\\temp\\newImage.jpg");
		in.close();
        
        in = newPhoto.getSmallImage().getImage();
        saveImage(in, "d:\\aves\\temp\\newSmallImage.jpg");
		
		System.out.println(newPhoto);
	}

	private static void saveImage(InputStream in, String file) throws FileNotFoundException, IOException {
		File ff = new File(file);
		FileOutputStream output = new FileOutputStream(ff);
		
		int b;
		while ((b = in.read()) != -1){
			output.write(b);
		}
		output.close();
	}
}
