/*
 * Created on 19/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrievePhotosForUser {

	public static void main(String[] args) throws DatabaseDownException,
			SQLException, IOException {
		int id = Integer.parseInt(JOptionPane.showInputDialog(null, "User ID"));

		PhotoDAO fDao = new PhotoDAO();
		List list = fDao.retrieveIDsForUser(id);
		if (list.isEmpty()) {
			System.out.println("No photos for specie id " + id);
		} else {
			Iterator it = list.iterator();
			int index = 0;
			while (it.hasNext()) {
                Integer i = (Integer)it.next();
                Photo photo = fDao.retrieve(i);
				InputStream in = photo.getRealImage().getImage();

				File ff = new File("d:\\newImage" + index++ + ".jpg");
				FileOutputStream output = new FileOutputStream(ff);

				int b;
				while ((b = in.read()) != -1) {
					output.write(b);
				}
				output.close();
				in.close();

				System.out.println(photo);
			}
		}
	}
}
