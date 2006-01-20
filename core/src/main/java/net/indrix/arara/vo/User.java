/*
 * Created on 07/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

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
    private boolean emailOnNewPhoto;
    private boolean emailOnNewSound;
    private boolean addPhoto;
    private boolean addSound;
    private boolean isAdmin;
	
	public User(){
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
     * The setter method for the isAdmin attribute
     * 
     * @param b The new value to the attribute
     */
    public void setAdmin(boolean b) {
        isAdmin = b;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer("[");
        buffer.append(id);
        buffer.append(" - ");
        buffer.append(name);
        buffer.append(" - ");
        buffer.append(login);
        buffer.append(" - ");
        buffer.append(email);
        buffer.append(" - ");
        buffer.append(emailOnNewPhoto);
        buffer.append(" - ");
        buffer.append(addPhoto);    
        return buffer.toString();
    }

    public boolean validar(){
        boolean valid = true;
        if ((name == null || name.trim().length() == 0)
            || (email == null || email.trim().length() == 0)
            || (login == null || login.trim().length() == 0)
            || (password == null || password.trim().length() == 0)) {
            valid = false;
        } 
        return valid;
    }
}
