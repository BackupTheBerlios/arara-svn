/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.file;

import java.io.File;
import java.io.InputStream;

import net.indrix.arara.vo.Sound;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundFileManager extends AbstractFileManager {
    public static final String SOUND_FOLDER = ROOT_FOLDER + File.separator + "sounds";

	private static final String EXTENSION = ".mp3";

	/**
	 * The sound to be saved
	 */
	private Sound sound;

	/**
	 * 
	 * @param sound
	 */
	public SoundFileManager(Sound sound) {
		this.sound = sound;
	}

	/**
	 * @see net.indrix.arara.model.file.FileManager#getFolder()
	 */
	public String getFolder() {
		int familyId = sound.getSpecie().getFamily().getId();
		int specieId = sound.getSpecie().getId();
		String path = SOUND_FOLDER + File.separator + familyId + File.separator
				+ specieId;
		return path;
	}

	/**
	 * @see net.indrix.arara.model.file.AbstractFileManager#getFilename()
	 */
	public String getFilename() {
		String name = "" + sound.getId() + EXTENSION;
		return name;
	}

	/**
	 * @see net.indrix.arara.model.file.AbstractFileManager#getInputStream()
	 */
	public InputStream getInputStream() {
		return sound.getSound().getSound();
	}
}
