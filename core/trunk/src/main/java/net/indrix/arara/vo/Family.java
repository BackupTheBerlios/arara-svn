/*
 * Created on 06/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Family {
    private int id;
    private String name;
    private String subFamilyName;

    public Family() {
        id = -1;
        name = null;
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
    public String getName() {
        return name;
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
    public void setName(String string) {
        name = string;
    }

    /**
     * @return
     */
    public String getSubFamilyName() {
        return subFamilyName;
    }

    /**
     * @param string
     */
    public void setSubFamilyName(String string) {
        subFamilyName = string;
    }
    
    public String getNameToShow(){
        String s = name;
        if (subFamilyName != null){
            s += " - " + subFamilyName;
        }
        return s;
    }

    public String toString() {
        return "[" + id + " - " + name + " - " + subFamilyName  + "]";
    }
}
