package net.indrix.arara.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import net.indrix.arara.servlets.common.IBean;
import net.indrix.arara.vo.BirdList;
import net.indrix.arara.vo.BirdListElement;
import net.indrix.arara.vo.City;
import net.indrix.arara.vo.User;

public class BirdListBean implements IBean{

    private String id;
    private String name;
    private User user;
    private String location;
    private List<LabelValueBean> selectedCities;
    private List<City>currentStateCities;
    private String type;
    private String selectedType;
    private String comment;
    protected List statesList;
    protected String selectedStateId;
    
    private List<BirdListElement> species;
    
    /**
     * Default constructor
     *
     */
    public BirdListBean(){
    }

    /**
     * Creates a bean from a VO
     * 
     * @param vo The object with the bird list data
     */
    public BirdListBean(BirdList vo){
        setVO(vo);
    }

    public void setVO(BirdList vo) {
        id = String.valueOf(vo.getId());
        name = vo.getName();
        user = vo.getUser();
        location = vo.getLocation();
        type = String.valueOf(vo.getType());
        comment = vo.getComment();
        List<City> cities = vo.getCities();
        LabelValueBean bean = null;
        selectedCities = new ArrayList<LabelValueBean>();
        for (City city : cities){
            bean = new LabelValueBean();
            bean.setLabel(city.getState().getAcronym() + " - " + city.getName());
            bean.setValue(String.valueOf(city.getId()));
            selectedCities.add(bean);
        }
    }
    
    public void reset(){
        id = null;
        name = null;
        user = null;
        location = null;
        selectedCities = null;
        type = null;
        comment = null;
        statesList = null;
        selectedStateId = null;
        currentStateCities = null;
        
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
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<LabelValueBean> getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(List<LabelValueBean> selectedCities) {
        this.selectedCities = selectedCities;
    }

    public String getSelectedStateId() {
        return selectedStateId;
    }

    public void setSelectedStateId(String selectedStateId) {
        this.selectedStateId = selectedStateId;
    }

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public List<BirdListElement> getSpecies() {
        return species;
    }

    public void setSpecies(List<BirdListElement> species) {
        this.species = species;
    }

    public List getStatesList() {
        return statesList;
    }

    public void setStatesList(List statesList) {
        this.statesList = statesList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<City> getCurrentStateCities() {
        return currentStateCities;
    }

    public void setCurrentStateCities(List<City> currentStateCities) {
        this.currentStateCities = currentStateCities;
    }
}
