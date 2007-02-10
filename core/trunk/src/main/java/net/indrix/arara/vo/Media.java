/* 
 * Created on 10/02/2007 
 * 
 */
package net.indrix.arara.vo;

import java.util.Date;

/**
 * @author Jefferson
 * 
 */
public class Media {

    private int id;
    private Specie specie;
    private Date postDate;
    private User user;
    private Age age;
    private Sex sex;
    private String location;
    private City city;
    private String comment;
    private String relativePath;

    public Media() {
        super();
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
     * @return
     */
    public Date getPostDate() {
    	return postDate;
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
     * Getter method for the comment attribute
     * 
     * @return The comment attribute
     */
    public String getComment() {
        return comment;
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
    public String getRelativePath() {
    	return relativePath;
    }

    /**
     * @return
     */
    public String getRelativePathAsLink() {
    	String path = relativePath.replace('\\', '/');
    	return path;
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

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    

    /**
     * @param string
     */
    public void setRelativePath(String string) {
    	relativePath = string;
    }

}