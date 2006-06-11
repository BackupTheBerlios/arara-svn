/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.file;

import java.io.File;
import java.io.InputStream;

import net.indrix.arara.vo.Photo;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoFileManager extends AbstractFileManager {
    private static final String FOLDER = "files\\photos";
    
    private Photo photo;
    
    public PhotoFileManager(Photo photo){
        this.photo = photo;
    }
    
	/**
	 * @return
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	/**
	 * @see net.indrix.arara.model.file.AbstractFileManager#getFilename()
	 */
	public String getFilename() {
		String name = "" + photo.getId() + ".jpg";
		return name;
	}

    /**
     * @see net.indrix.arara.model.file.FileManager#getFolder()
     */
    public String getFolder() {
        int familyId = photo.getSpecie().getFamily().getId();
        int specieId = photo.getSpecie().getId();
        String path = FOLDER + File.separator + familyId + File.separator + specieId;
        return path;
    }

	/**
	 * @see net.indrix.arara.model.file.AbstractFileManager#getInputStream()
	 */
	public InputStream getInputStream() {
		return photo.getRealImage().getImage();
	}
}
