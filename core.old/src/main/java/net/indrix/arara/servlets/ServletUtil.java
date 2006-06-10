/*
 * Created on 02/09/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.utils.LabelValueBean;
import net.indrix.arara.vo.CommonName;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Specie;
import net.indrix.arara.vo.State;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ServletUtil {
    static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * @param list
     * @return
     */
    public static List familyDataAsLabelValueBean(List list) {
        List newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            Family f = (Family)it.next();
            String label = f.getName();
            if (f.getSubFamilyName() != null){
                label += " - " + f.getSubFamilyName();
            }
            LabelValueBean bean = new LabelValueBean(label, f.getId());
            newList.add(bean);           
        }
        newList.add(0, new LabelValueBean("", ""));
        return newList;
    }

    /**
     * @param list
     * @return
     */
    public static List specieDataAsLabelValueBean(List list) {
        List newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            Specie s = (Specie)it.next();
            LabelValueBean bean = new LabelValueBean(s.getName(), s.getId());
            newList.add(bean);
        }
        newList.add(0, new LabelValueBean("", ""));
        return newList;
    }

    /**
     * @param list
     * @return
     */
    public static List commonNameDataAsLabelValueBean(List list) {
        List newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            CommonName c = (CommonName)it.next();
            String label = c.getName();
            LabelValueBean bean = new LabelValueBean(label, c.getId());
            newList.add(bean);
        }
        newList.add(0, new LabelValueBean("", ""));
        return newList;
    }

    /**
     * @param list
     * @return
     */
    public static List statesDataAsLabelValueBean(List list) {
        List newList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()){
            State state = (State)it.next();
            String label = state.getAcronym();
            LabelValueBean bean = new LabelValueBean(label, state.getId());
            newList.add(bean);
        }
        newList.add(0, new LabelValueBean("", ""));
        return newList;
    }
}
