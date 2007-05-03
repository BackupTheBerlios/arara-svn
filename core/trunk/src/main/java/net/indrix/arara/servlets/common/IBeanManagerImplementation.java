package net.indrix.arara.servlets.common;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class IBeanManagerImplementation implements IBeanManager{

    /**
     * Logger object to be used by this servlet to log statements
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * The bean this manager manages
     */
    private IBean uploadBean;

    public void setBean(IBean bean) {
        uploadBean = bean;
        
    }

    public IBean getBean() {
        return uploadBean;
    }

    public void setData(Object object, String source) {
    }


    public boolean updateBean(Map data, List<String> errors, boolean validate) {
        return false;
    }
}
