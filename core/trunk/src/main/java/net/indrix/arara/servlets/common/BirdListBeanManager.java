package net.indrix.arara.servlets.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.indrix.arara.bean.BirdListBean;
import net.indrix.arara.bean.UploadBean;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.CityModel;
import net.indrix.arara.vo.City;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.birdlist.BirdListConstants;
import net.indrix.arara.servlets.photo.upload.UploadPhotoConstants;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.apache.struts.util.LabelValueBean;

public class BirdListBeanManager extends IBeanManagerImplementation{
    public static final String CITY_TYPE = "City";
    /**
     * @param data
     * @param bean
     */
    public boolean updateBean(Map data, List <String>errors, boolean validate) {
        boolean status = false;

        logger.debug("BirdListBeanManager.updateBean: updating data...");
        
        String stateId = (String) data.get(ServletConstants.STATE_ID);
        
        if (validate) {
            if ((stateId == null) || (stateId.trim().equals(""))) {
                errors.add(UploadPhotoConstants.STATE_REQUIRED);
            }
        }
        
        BirdListBean bean = (BirdListBean)getBean();
        bean.setName((String) data.get(BirdListConstants.NAME));
        bean.setSelectedType((String) data.get(BirdListConstants.LIST_TYPE));
        bean.setSelectedStateId(stateId);       
        bean.setLocation((String) data.get(ServletConstants.LOCATION));
        bean.setComment((String) data.get(ServletConstants.COMMENT));       
        String[] selectedCities = getSelectedCities(data); 
        bean.setSelectedCities(getSelectedCities(selectedCities));

        if (validate) {
            if ((stateId == null) || (stateId.trim().equals(""))) {
                errors.add(UploadPhotoConstants.STATE_REQUIRED);
            }
            if (selectedCities == null || selectedCities.length == 0){
                errors.add(UploadPhotoConstants.CITY_REQUIRED);                
            }
        }
        logger.debug("BirdListBeanManager.updateBean: bean updated " + bean);
        return status;
    }

    /**
     * This method retrieves the selected cities from the map. If it was just one, this method
     * creates a new String[] and return the city selected
     * 
     * @param data The map with the user data
     * 
     * @return A String[], either the one in the map, or a new one if the map contains only one 
     * String object 
     */
    private String[] getSelectedCities(Map data) {
        String[] array;
        if (data.get(BirdListConstants.SELECTED_CITIES) instanceof String[]){
            array = (String[])data.get(BirdListConstants.SELECTED_CITIES);
        } else {
            array = new String[1];
            array[0] = (String)data.get(BirdListConstants.SELECTED_CITIES);
        }
        return array ;
    }

    private List<LabelValueBean> getSelectedCities(String cities[]) {
        ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();
        CityModel model = new CityModel();
        if (cities != null){
            logger.debug("Creating list of beans... Size = " + cities.length);
            for (int i = 0; i < cities.length; i++){
                LabelValueBean bean = new LabelValueBean();
                try {
                    City city = model.getCity(Integer.parseInt(cities[i]));
                    bean.setLabel(city.getState().getAcronym() + " - " + city.getName());
                    bean.setValue(cities[i]);
                    list.add(bean);
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (DatabaseDownException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }            
        } else {
            logger.debug("No cities selected...");           
        }
        return list;
    }

    /**
     * This method allows the manager to set an object in the correct bean. The source specifies the 
     * data type, such as a City.
     * 
     * @param object The object to be updated into the bean
     * @param source The type of the data
     */
    public void setData(Object object, String source){
        BirdListBean bean = (BirdListBean)getBean();
        bean.setCurrentStateCities((List)object);
        
    }
}
