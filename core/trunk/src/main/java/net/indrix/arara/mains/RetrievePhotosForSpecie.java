/*
 * Created on 08/02/2005
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

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.PhotoDAO;
import net.indrix.arara.vo.Photo;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrievePhotosForSpecie {

	public static void main(String[] args) throws DatabaseDownException,
			SQLException, IOException {
		int id = Integer.parseInt(args[0]);

		PhotoDAO fDao = new PhotoDAO();
		List list = fDao.retrieveForSpecie(id);
		if (list.isEmpty()) {
			System.out.println("No photos for specie id " + id);
		} else {
			Iterator it = list.iterator();
			int index = 0;
			while (it.hasNext()) {
				Photo photo = (Photo) it.next();
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
