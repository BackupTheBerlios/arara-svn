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
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.model.email.SoundEmailSender;
import net.indrix.arara.model.exceptions.SoundProcessingException;
import net.indrix.arara.model.file.SoundFileManager;
import net.indrix.arara.servlets.exceptions.InvalidFileException;
import net.indrix.arara.utils.FileUtils;
import net.indrix.arara.vo.Sound;
import net.indrix.arara.vo.Specie;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UploadSound extends AbstractUpload {
	private static final String validExtensions[] = { "mp3", "MP3" };

	/**
	 * @param sound
	 * @throws InvalidFileException 
	 */
	public void uploadSound(Sound sound) throws DatabaseDownException,
			SQLException, SoundProcessingException, InvalidFileException {
		String filename = sound.getSound().getFilename();
		if (!FileUtils.checkExtension(filename, validExtensions)) {
			throw new InvalidFileException();
		}

		SoundDAO dao = new SoundDAO();
		dao.insert(sound);

		try {
			SoundFileManager manager = new SoundFileManager(sound);
			manager.writeFile();
            
            // retrieve data from database, so all data will be loaded
            SpecieDAO sDao = new SpecieDAO();
            Specie s = sDao.retrieve(sound.getSpecie().getId());
            sound.setSpecie(s);
            
            // send email to users
            logger.debug("Sending email");
            SoundEmailSender emailSender = new SoundEmailSender(sound);
            emailSender.sendEmail();
            
		} catch (FileNotFoundException e) {
			dao.delete(sound.getId());
			throw new SoundProcessingException("Could not write sound file...");
		}
	}
}
