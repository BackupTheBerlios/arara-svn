/*
 * Created on 07/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

import java.util.Date;

import net.indrix.arara.model.Cryptography;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class User {
	private int id;
	private String name;
	private String login;
	private String password;
	private String email;
	private String language;
	private boolean emailOnNewPhoto;
	private boolean emailOnNewIdPhoto;
	private boolean emailOnNewSound;
    private boolean emailOnNewComment;
	private boolean addPhoto;
	private boolean addSound;
	private boolean isAdmin;
    private boolean photoIdModerator;
    private boolean photoEditModerator;
    private boolean active;    
    private Date registeredOn;

	public User() {
		id = -1;
		name = null;
		login = null;
		password = null;
		email = null;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return
	 */
	public boolean isAddPhoto() {
		return addPhoto;
	}

	/**
	 * @return
	 */
	public boolean isAddSound() {
		return addSound;
	}

	/**
	 * @return
	 */
	public boolean isEmailOnNewPhoto() {
		return emailOnNewPhoto;
	}

	/**
	 * @return
	 */
	public boolean isEmailOnNewSound() {
		return emailOnNewSound;
	}

    /**
     * @return
     */
    public boolean isEmailOnNewComment() {
        return emailOnNewComment;
    }

	/**
	 * Getter method for the isAdmin attribute
	 * 
	 * @return The isAdmin attribute
	 */
	public boolean isAdmin() {
		return isAdmin;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

	/**
	 * @param string
	 */
	public void setLogin(String string) {
		login = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @param string
	 */
	public void setLanguage(String string) {
		language = string;
	}

	/**
	 * @param b
	 */
	public void setAddPhoto(boolean b) {
		addPhoto = b;
	}

	/**
	 * @param b
	 */
	public void setAddSound(boolean b) {
		addSound = b;
	}

	/**
	 * @param b
	 */
	public void setEmailOnNewPhoto(boolean b) {
		emailOnNewPhoto = b;
	}

	/**
	 * @param b
	 */
	public void setEmailOnNewSound(boolean b) {
		emailOnNewSound = b;
	}

    /**
     * @param b
     */
    public void setEmailOnNewComment(boolean b) {
        emailOnNewComment = b;
    }

	/**
	 * The setter method for the isAdmin attribute
	 * 
	 * @param b
	 *            The new value to the attribute
	 */
	public void setAdmin(boolean b) {
		isAdmin = b;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		buffer.append(id);
		buffer.append(" - ");
		buffer.append(name);
		buffer.append(" - ");
		buffer.append(login);
		buffer.append(" - ");
		buffer.append(email);
		buffer.append(" - ");
		buffer.append(language);
		buffer.append(" - ");
		buffer.append(emailOnNewPhoto);
		buffer.append(" - ");
		buffer.append(addPhoto);
		return buffer.toString();
	}

	public boolean equals(Object o) {
		boolean equal = true;
		if ((o == null) || !(o instanceof User)) {
			equal = false;
		} else {
			User other = (User) o;
			if (other.id != this.id) {
				equal = false;
			}
		}
		return equal;
	}

	public boolean validar() {
		boolean valid = true;
		if ((name == null || name.trim().length() == 0)
				|| (email == null || email.trim().length() == 0)
				|| (login == null || login.trim().length() == 0)
				|| (password == null || password.trim().length() == 0)) {
			valid = false;
		} else {
            if (login.contains(" ")){
                valid = false;
            } else {
                if (isPasswordValid(password)) {
                    String newPass = Cryptography.cryptPassword(password);
                    if (newPass == "") {
                        valid = false;
                    }
                } else {
                    valid = false;
                }                           
            }
        }


		return valid;
	}

	/**
	 * This method verifies if the password contains a character different than
	 * digit or letter
	 * 
	 * @param p
	 *            The password string to be verified
	 * 
	 * @return true if the pass is valid, false otherwise
	 */
	private boolean isPasswordValid(String p) {
		boolean valid = true;
		for (int i = 0; i < p.length() && valid; i++) {
			if (!Character.isLetterOrDigit(p.charAt(i))) {
				valid = false;
			}
		}

		return valid;
	}

	/**
	 * @return
	 */
	public boolean isEmailOnNewIdPhoto() {
		return emailOnNewIdPhoto;
	}

	/**
	 * @param b
	 */
	public void setEmailOnNewIdPhoto(boolean b) {
		emailOnNewIdPhoto = b;
	}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(Date registeredOn) {
        this.registeredOn = registeredOn;
    }

    public boolean isPhotoIdModerator() {
        return photoIdModerator;
    }

    public void setPhotoIdModerator(boolean photoModerator) {
        this.photoIdModerator = photoModerator;
    }

    public boolean isPhotoEditModerator() {
        return photoEditModerator;
    }

    public void setPhotoEditModerator(boolean photoModerator) {
        this.photoEditModerator = photoModerator;
    }
}
