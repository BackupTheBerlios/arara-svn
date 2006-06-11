/*
 * Created on 07/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

import java.util.Date;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PhotoIdentification {
    private int id;
    private User user;
    private Specie specie;
    private Date date;
    private String comment;
    private Age age;
    private Sex sex;
    private Photo photo;
    
    public PhotoIdentification(){
        specie = new Specie();
        user = null;
        photo = null;
    }
    
	/**
	 * @return
	 */
	public Age getAge() {
		return age;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @return
	 */
	public Date getDate() {
		return date;
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
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * @return
	 */
	public Sex getSex() {
		return sex;
	}

	/**
	 * @return
	 */
	public Specie getSpecie() {
		return specie;
	}

	/**
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param age
	 */
	public void setAge(Age age) {
		this.age = age;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

	/**
	 * @param photo
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	/**
	 * @param sex
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}

	/**
	 * @param specie
	 */
	public void setSpecie(Specie specie) {
		this.specie = specie;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

    public String toString(){
        StringBuffer buffer = new StringBuffer("[");
        buffer.append(id);
        buffer.append(",");
        buffer.append(date);
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
        buffer.append(",");
        buffer.append(comment);
        buffer.append("]");

        return buffer.toString();        
    }
}
