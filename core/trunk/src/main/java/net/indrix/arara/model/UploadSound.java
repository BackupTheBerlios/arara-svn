/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SoundDAO;
import net.indrix.arara.model.email.SoundEmailSender;
import net.indrix.arara.model.exceptions.SoundProcessingException;
import net.indrix.arara.model.file.SoundFileManager;
import net.indrix.arara.vo.Sound;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UploadSound extends AbstractUpload {
	private static final String validExtensions[] = { "mp3" };

	/**
	 * @param sound
	 */
	public void uploadSound(Sound sound) throws DatabaseDownException,
			SQLException, SoundProcessingException {
		String filename = sound.getSound().getFilename();
		if (!checkExtension(filename)) {
			throw new SoundProcessingException("Invalid file extension");
		}

		SoundDAO dao = new SoundDAO();
		dao.insert(sound);

		try {
			SoundFileManager manager = new SoundFileManager(sound);
			manager.writeFile();
            
            // send email to users
            logger.debug("Sending email");
            SoundEmailSender emailSender = new SoundEmailSender(sound);
            emailSender.sendEmail();
            
		} catch (FileNotFoundException e) {
			dao.delete(sound.getId());
			throw new SoundProcessingException("Could not write sound file...");
		}
	}

	/**
	 * This method verifies if the given file is valid
	 * 
	 * @param filename
	 *            The file uploaded by user
	 * 
	 * @return true if file extension is valid, false otherwise
	 */
	private boolean checkExtension(String filename) {
		boolean valid = true;
		int index = filename.lastIndexOf(".");
		if (index > 0) {
			String extension = filename.substring(index + 1);

			for (int i = 0; i < (validExtensions.length) && (valid); i++) {
				if (!validExtensions[i].equals(extension)) {
					valid = false;
				}
			}
		} else {
			valid = false;
		}
		return valid;
	}
}
