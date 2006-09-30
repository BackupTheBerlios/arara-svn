package net.indrix.arara.bean;

import java.util.List;

/**
 * This class keeps a list of LabelValueBean objects and a selected element from the list
 * 
 * @author Jeff
 *
 */
public class ListBean {

    /**
     * The list of LabelValueBean
     */
    private List list;
    /**
     * The id of the selected element from the list
     */
    private String selectedId;
    
    
    public List getList() {
        return list;
    }
    public void setList(List list) {
        this.list = list;
    }
    public String getSelectedId() {
        return selectedId;
    }
    public void setSelectedId(String selectedId) {
        this.selectedId = selectedId;
    }
    
}
