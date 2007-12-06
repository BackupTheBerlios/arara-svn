/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.email;

import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractPhotoEmailSender extends AbstractEmailSender {
    
    protected static final int PHOTO_WIDTH = 240;
    
	/**
	 * The photo added to database
	 */
	protected Photo photo = null;

	/**
	 * Saves the given photo in the class attribute
	 * 
	 * @param photo
	 *            The photo just added to the database
	 */
	public AbstractPhotoEmailSender(Photo photo) {
		this.photo = photo;
	}
}
