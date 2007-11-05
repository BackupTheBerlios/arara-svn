package net.indrix.arara.servlets.common;

import java.util.List;
import java.util.Map;

public interface IBeanManager {
    /**
     * This method updates the bean with the data contained in the Map
     * 
     * @param data The data to be uploaded into the bean
     * @param bean The bean to be updated
     * @param errors The list to receive erros in validation
     * @param validate flag to indicate whether or not validation shall occur
     * 
     * @return true if success, false if any validation fails
     */
    public boolean updateBean(Map data, List <String>errors, boolean validate);
    
    /**
     * This method sets the bean to be updated
     * 
     * @param bean The bean to be updated
     */
    public void setBean(IBean bean);

    /**
     * Getter method to the bean to be updated
     */
    public IBean getBean();

}
