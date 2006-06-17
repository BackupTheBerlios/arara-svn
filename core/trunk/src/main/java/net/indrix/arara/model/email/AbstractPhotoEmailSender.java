/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.email;

import net.indrix.arara.vo.Photo;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractPhotoEmailSender implements Runnable {
	/**
	 * The photo added to database
	 */
	protected Photo photo = null;

	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * Saves the given photo in the class attribute
	 * 
	 * @param photo
	 *            The photo just added to the database
	 */
	public AbstractPhotoEmailSender(Photo photo) {
		this.photo = photo;
	}

	/**
	 * This method sends an email to users about new photo
	 * 
	 * @param photo
	 *            The new photo data
	 */
	abstract public void run();

	/**
	 * This method sends an email to users about new photo
	 * 
	 * @param photo
	 *            The new photo data
	 */
	public void sendEmail() {
		logger
				.info("AbstractPhotoEmailSender.sendEmail: starting thread to send emails...");
		Thread t = new Thread(this);
		this.run();
	}

}
