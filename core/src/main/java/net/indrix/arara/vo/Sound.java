/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.vo;

import java.util.Date;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Sound {

    private int id;
    private Specie specie;
    private SoundFile sound;
    private Date postDate;
    private User user;
    private Age age;
    private Sex sex;
    private String location;
    private String city;
    private State state;
    private String comment;
            
    public Sound(){
        id = -1;
        sound = new SoundFile();
        age = new Age();
        sex = new Sex();
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
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @return
	 */
	public SoundFile getSound() {
		return sound;
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
     * @return
     */
    public Age getAge() {
        return age;
    }

    /**
     * @return
     */
    public Sex getSex() {
        return sex;
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
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

	/**
	 * @param date
	 */
	public void setPostDate(Date date) {
		postDate = date;
	}

	/**
	 * @param stream
	 */
	public void setSound(SoundFile s) {
		sound = s;
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

	/**
	 * @param age
	 */
	public void setAge(Age age) {
		this.age = age;
	}

	/**
	 * @param sex
	 */
	public void setSex(Sex sex) {
		this.sex = sex;
	}
    
    /**
     * Setter method for the comment attribute
     * 
     * @param string The new value to the attribute
     */
    public void setComment(String string) {
        comment = string;
    }
    
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(id);
        buffer.append(",");

        if (specie != null) {
            buffer.append(specie.getId());
            buffer.append(",");
            buffer.append(specie.getName());
            buffer.append(",");

            if (specie.getFamily() != null) {
                buffer.append(specie.getFamily().getName());
                buffer.append(",");
            }
            
            buffer.append(specie.getCommonNamesString());
            buffer.append(",");
        }

        if (age != null){
            buffer.append(age.getId() + " | " + age.getAge());
            buffer.append(",");           
        }
       
        if (sex != null){
            buffer.append(sex.getId() + " | " + sex.getSex());
            buffer.append(",");           
        }

        buffer.append("]");

        return buffer.toString();
    }
        
	/**
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @return
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param string
	 */
	public void setCity(String string) {
		city = string;
	}

	/**
	 * @param string
	 */
	public void setLocation(String string) {
		location = string;
	}

	/**
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}

}
