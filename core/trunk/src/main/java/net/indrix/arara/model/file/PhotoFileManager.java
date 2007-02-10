/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoFileManager extends AbstractFileManager {
    public static final String THUMBNAIL = "_thumbnail";

    public static final String PHOTO_FOLDER = ROOT_FOLDER + File.separator + "photos";

    private static final String EXTENSION = ".jpg";

    /**
     * The photo to be saved
     */	private Photo photo;

	public PhotoFileManager(Photo photo) {
		this.photo = photo;
	}

	/**
	 * @see net.indrix.arara.model.file.AbstractFileManager#getFilename()
	 */
	public String getFilename() {
		String name = "" + photo.getId() + EXTENSION;
		return name;
	}

    /**
     * @see net.indrix.arara.model.file.AbstractFileManager#getFilename()
     */
    private String getThumbnailFilename() {
        String name = "" + photo.getId() + THUMBNAIL + EXTENSION;
        return name;
    }    

    /**
     * This method retrieves the filename, with the relative path, to the file
     * in the filesystem
     * 
     * @return the filename, with the relative path, to the file in the
     *         filesystem
     */
    public String getThumbnailRelativePath() {
        return getFolder() + File.separator + getThumbnailFilename();
    }
    
	/**
	 * @see net.indrix.arara.model.file.FileManager#getFolder()
	 */
	public String getFolder() {
		int familyId = photo.getSpecie().getFamily().getId();
		int specieId = photo.getSpecie().getId();
		String path = PHOTO_FOLDER + File.separator + familyId + File.separator
				+ specieId;
		return path;
	}

    /**
     * This method writes the file, given by input, to the filesystem
     * 
     * @param input
     *            The InputStream with the file contents
     */
    public void writeFile() throws FileNotFoundException {
        logger.debug("PhotoFileManager.writeSoundToFilesystem: entering method...");
        
        super.writeFile();
        
        InputStream input = photo.getSmallImage().getImage();
        String filename = getRootPath() + File.separator + getFolder() + File.separator + getThumbnailFilename();
        
        writeFile(input, filename);
    }
    
	/**
	 * @see net.indrix.arara.model.file.AbstractFileManager#getInputStream()
	 */
	public InputStream getInputStream() {
		return photo.getRealImage().getImage();
	}

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
