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
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Photo implements Serializable {
	private int id;

	private User user;

	private Specie specie;

	private Date date;
    
    private Date postDate;

	private String location;

	private City city;

	private String camera;

	private String lens;

	private String film;

	private ImageFile realImage;

	private ImageFile smallImage;

	private String comment;

	private List comments;

	private List identifications;

	private Age age;

	private Sex sex;

	private Sound sound;

	public Photo() {
		realImage = new ImageFile();
		smallImage = new ImageFile();
		id = -1;
		age = new Age();
		sex = new Sex();
		city = new City();
	}

	/**
	 * Getter method for the id attribute
	 * 
	 * @return The id attribute
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method for the specie attribute
	 * 
	 * @return The value of the attribute
	 */
	public Specie getSpecie() {
		return specie;
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
	 * Getter method for the location attribute
	 * 
	 * @return The value of the attribute
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Getter method for the user attribute, as a <code>User</code> object
	 * 
	 * @return the user attribute, as a <code>User</code> object
	 */
	public User getUser() {
		return user;
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
	 * Getter method for the comment attribute
	 * 
	 * @return The comment attribute
	 */
	public String getComment() {
		return comment;
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
	 * Getter method for the age attribute, as a <code>Age</code> object
	 * 
	 * @return the age attribute, as a <code>Age</code> object
	 */
	public Age getAge() {
		return age;
	}

	/**
	 * Getter method for the sex attribute, as a <code>Sex</code> object
	 * 
	 * @return the sex attribute, as a <code>Sex</code> object
	 */
	public Sex getSex() {
		return sex;
	}

	/**
	 * @return
	 */
	public City getCity() {
		return city;
	}

	/**
	 * @return
	 */
	public List getIdentifications() {
		return identifications;
	}

	/**
	 * Setter method for the id attribute
	 * 
	 * @param i
	 *            The new value to the attribute
	 */
	public void setId(int i) {
		id = i;
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
	 * Setter method for the location attribute
	 * 
	 * @param string
	 *            The new value to the attribute
	 */
	public void setLocation(String string) {
		location = string;
	}

	/**
	 * Setter method for the user attribute
	 * 
	 * @param user
	 *            The new value to the attribute
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Setter method for the specie attribute
	 * 
	 * @param specie
	 *            The new value to the attribute
	 */
	public void setSpecie(Specie specie) {
		this.specie = specie;
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
	 * Setter method for the comment attribute
	 * 
	 * @param string
	 *            The new value to the attribute
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * Setter method for the age attribute
	 * 
	 * @param age
	 *            The new value to the attribute
	 */
	public void setAge(Age age) {
		this.age = age;
	}

	/**
	 * Setter method for the sex attribute
	 * 
	 * @param sex
	 *            The new value to the attribute
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	/**
	 * @param city
	 */
	public void setCity(City city) {
		if (city == null) {
			city = new City();
		}
		this.city = city;
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
     * @param sound
     */
    public void setSound(Sound sound) {
        this.sound = sound;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    
	/**
	 * Verify if for this photo, there is a sound associated, that is, a sound
	 * for the same sex and age
	 * 
	 * @return true if there is a sound, false otherwise
	 */
	public boolean isSoundAvailable() {
		boolean is = false;
		if (specie.isSoundAvailable()) {
			System.out.println("Specie isSoundAvailable = true");
			int ageId = age.getId();
			int sexId = sex.getId();

			List sounds = specie.getSounds();
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

	/**
	 * the overwritten toString method, with a string representing the photo
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		buffer.append(id);
		buffer.append(",");
		buffer.append(date);
        buffer.append(",");
        buffer.append(postDate);
		buffer.append(",");

		if (user != null) {
			buffer.append(user.getLogin());
			buffer.append(" (");
			buffer.append(user.getEmail());
			buffer.append("),");
		}

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
		buffer.append(location);
		buffer.append(",");
		buffer.append(city);
		buffer.append("]");

		return buffer.toString();
	}
}
