/* 
 * Created on 07/02/2005 
 * 
 * To change the template for this generated file go to 
 * Window>Preferences>Java>Code Generation>Code and Comments 
 */
package net.indrix.arara.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jefferson
 * 
 */
@SuppressWarnings("serial")
public class Photo extends Media implements Serializable {
	private Date date;
    
	private String camera;

	private String lens;

	private String film;

	private String fstop;
    
    private String shutterSpeed;
    
    private String iso;
    
    private String zoom;
    
    private boolean flash;
    
    private ImageFile realImage;

	private ImageFile smallImage;

	private List comments;

	private List identifications;

	private Sound sound;
    
    private String thumbnailRelativePath;

	public Photo() {
		realImage = new ImageFile();
		smallImage = new ImageFile();
	}

	/**
	 * Getter method for the specie attribute
	 * 
	 * @return The value of the attribute
	 */
	public String getCamera() {
		return camera;
	}

	/**
	 * Getter method for the date attribute
	 * 
	 * @return The value of the attribute
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Getter method for the date attribute
	 * 
	 * @return The value of the attribute
	 */
	public String getFilm() {
		return film;
	}

	/**
	 * Getter method for the lens attribute
	 * 
	 * @return The value of the attribute
	 */
	public String getLens() {
		return lens;
	}

	/**
	 * Getter method for the smallImage attribute, as a <code>ImageFile</code>
	 * object
	 * 
	 * @return the smallImage attribute, as a <code>ImageFile</code> object
	 */
	public ImageFile getSmallImage() {
		return smallImage;
	}

	/**
	 * Getter method for the realImage attribute, as a <code>ImageFile</code>
	 * object
	 * 
	 * @return the realImage attribute, as a <code>ImageFile</code> object
	 */
	public ImageFile getRealImage() {
		return realImage;
	}

	/**
	 * Getter method for the comments attribute
	 * 
	 * @return The list of comments
	 */
	public List getComments() {
		return comments;
	}

    /**
     * Getter method for the identifications attribute, as a <code>List</code>
     * object
     * 
     * @return the identifications attribute, as a <code>List</code> object
     */
	public List getIdentifications() {
		return identifications;
	}


    /**
     * Getter method for the thumbnailRelativePath attribute, as a <code>thumbnailRelativePath</code>
     * object
     * 
     * @return the thumbnailRelativePath attribute, as a <code>thumbnailRelativePath</code> object
     */
    public String getThumbnailRelativePath() {
        return thumbnailRelativePath;
    }
    
    /**
     * This method returns the thumbnailRelativePath attribute value, as a link
     * 
     * @return the thumbnailRelativePath attribute value, as a link, as a 
     * <code>thumbnailRelativePath</code> object
     */
    public String getThumbnailRelativePathAsLink() {
        String link = "ERROR";
        if (thumbnailRelativePath != null){
            link = thumbnailRelativePath.replace('\\', '/'); 
        }
        return link;
    }
   
	/**
	 * Setter method for the camera attribute
	 * 
	 * @param string
	 *            The new value to the attribute
	 */
	public void setCamera(String string) {
		camera = string;
	}

	/**
	 * Setter method for the date attribute
	 * 
	 * @param date
	 *            The new value to the attribute
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Setter method for the film attribute
	 * 
	 * @param string
	 *            The new value to the attribute
	 */
	public void setFilm(String string) {
		film = string;
	}

	/**
	 * Setter method for the lens attribute
	 * 
	 * @param string
	 *            The new value to the attribute
	 */
	public void setLens(String string) {
		lens = string;
	}


	/**
	 * Setter method for the smallImage attribute
	 * 
	 * @param image
	 *            The new value to the attribute
	 */
	public void setSmallImage(ImageFile image) {
		smallImage = image;
	}

	/**
	 * Setter method for the realImage attribute
	 * 
	 * @param image
	 *            The new value to the attribute
	 */
	public void setRealImage(ImageFile image) {
		realImage = image;
	}

	/**
	 * Setter method for the comments attribute
	 * 
	 * @param list
	 *            The new list of comments
	 */
	public void setComments(List list) {
		comments = list;
	}

	/**
	 * @param list
	 */
	public void setIdentifications(List list) {
		identifications = list;
	}

    /**
     * @return
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Setter method for the sound attribute
     * 
     * @param Sound
     *            The new value to the attribute
     */
    public void setSound(Sound sound) {
        this.sound = sound;
    }

    /**
     * Setter method for the thumbnailRelativePath attribute
     * 
     * @param String
     *            The new value to the attribute
     */
    public void setThumbnailRelativePath(String thumbnailRelativePath) {
        this.thumbnailRelativePath = thumbnailRelativePath;
    }
    
	/**
	 * Verify if for this photo, there is a sound associated, that is, a sound
	 * for the same sex and age
	 * 
	 * @return true if there is a sound, false otherwise
	 */
	public boolean isSoundAvailable() {
		boolean is = false;
		if (getSpecie().isSoundAvailable()) {
			int ageId = getAge().getId();
			int sexId = getSex().getId();

			List sounds = getSpecie().getSounds();
			Iterator it = sounds.iterator();
			while (it.hasNext() && (!is)) {
				Sound sound = (Sound) it.next();
				if ((sound.getAge().getId() == ageId)
						&& (sound.getSex().getId() == sexId)) {
					is = true;
					setSound(sound);
				}
			}
		}

		return is;
	}

    public boolean isFlash() {
        return flash;
    }

    public void setFlash(boolean flash) {
        this.flash = flash;
    }

    public String getFstop() {
        return fstop;
    }

    public void setFstop(String stop) {
        fstop = stop;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public String getZoom() {
        return zoom;
    }

    public void setZoom(String zoom) {
        this.zoom = zoom;
    }

    /**
     * the overwritten toString method, with a string representing the photo
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("[");
        buffer.append(getId());
        buffer.append(",");
        buffer.append(date);
        buffer.append(",");
        buffer.append(getPostDate());
        buffer.append(",");

        User user = getUser();
        if (user != null) {
            buffer.append(user.getLogin());
            buffer.append(" (");
            buffer.append(user.getEmail());
            buffer.append("),");
        }

        Specie specie = getSpecie();
        if (specie != null) {
            buffer.append(specie.getId());
            buffer.append(",");
            buffer.append(specie.getName());
            buffer.append(",");

            if (specie.getFamily() != null) {
                buffer.append(specie.getFamily().getId());
                buffer.append(",");
                buffer.append(specie.getFamily().getName());
                buffer.append(",");
            }

            buffer.append(specie.getCommonNamesString());
            buffer.append(",");
        }

        buffer.append(camera);
        buffer.append(",");
        buffer.append(lens);
        buffer.append(",");
        buffer.append(film);
        buffer.append(",");
        buffer.append(fstop);
        buffer.append(",");
        buffer.append(shutterSpeed);
        buffer.append(",");
        buffer.append(iso);
        buffer.append(",");
        buffer.append(zoom);
        buffer.append(",");
        buffer.append(flash);
        buffer.append(",");
        buffer.append(getLocation());
        buffer.append(",");
        buffer.append(getCity());
        buffer.append("]");

        return buffer.toString();
    }

}
