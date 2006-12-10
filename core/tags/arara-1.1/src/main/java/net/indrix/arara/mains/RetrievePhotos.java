/*
 * Created on 17/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.PhotoModel;
import net.indrix.arara.vo.Photo;

/**
 * @author Jefferson
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrievePhotos {

	public static void main(String[] args) throws DatabaseDownException,
			SQLException {

		PhotoModel model = new PhotoModel();
		List l = model.retrievePhotoIDs();
		Iterator it = l.iterator();
		while (it.hasNext()) {
            Integer i = (Integer)it.next();
            Photo p = model.retrieve(i);

			System.out.println(p);
		}
	}
}
