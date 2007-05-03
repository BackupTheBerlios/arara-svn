package net.indrix.arara.vo;

import java.util.ArrayList;
import java.util.List;

public class BirdList {
    public static final int PUBLIC_LIST = 1;
    public static final int PRIVATE_LIST = 2;
    
    public static final String PUBLIC_LIST_STR = "public";
    public static final String PRIVATE_LIST_STR = "private";

    private int id;
    private String name;
    private User user;
    private String location;
    private List<City> cities = new ArrayList<City>();
    private int type;
    private String comment;
    
    private List<BirdListElement> species;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void addCity(City city){
        cities.add(city);
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BirdListElement> getSpecies() {
        return species;
    }

    public void setSpecies(List<BirdListElement> species) {
        this.species = species;
    }

    public int getType() {
        return type;
    }

    public String getTypeAsString() {
        return type == PUBLIC_LIST ? PUBLIC_LIST_STR : PRIVATE_LIST_STR;
    }

    public void setType(int type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ ");
        buffer.append(name);
        buffer.append(" | ");
        buffer.append(type);
        buffer.append(" | ");
        buffer.append(location);
        buffer.append(" ]");
        
        return buffer.toString();
    }
}
