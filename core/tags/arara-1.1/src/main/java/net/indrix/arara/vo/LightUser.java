package net.indrix.arara.vo;

/**
 * This class represents a User object, with just some basic information
 * 
 * @author Jeff
 *
 */
public class LightUser {

    private int id;

    private String name;

    private String login;

    private String email;

    private String language;

    public LightUser() {
        id = -1;
        name = null;
        login = null;
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
    public String getLanguage() {
        return language;
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
    public void setLanguage(String string) {
        language = string;
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
        return buffer.toString();
    }

    public boolean equals(Object o) {
        boolean equal = true;
        if ((o == null) || !(o instanceof User)) {
            equal = false;
        } else {
            LightUser other = (LightUser) o;
            if (other.id != this.id) {
                equal = false;
            }
        }
        return equal;
    }

}
