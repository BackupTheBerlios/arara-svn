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

import net.indrix.dao.DatabaseDownException;
import net.indrix.model.PhotoModel;
import net.indrix.vo.Photo;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class RetrievePhotos {

	public static void main(String[] args) throws DatabaseDownException, SQLException {
        
        
        PhotoModel model = new PhotoModel();     
        List l = model.retrievePhotos();
        Iterator it = l.iterator();
        while (it.hasNext()){
            Photo p = (Photo)it.next();
            
            System.out.println(p);
        }
	}
}
