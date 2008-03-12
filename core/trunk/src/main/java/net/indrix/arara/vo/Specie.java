/*
 * Especie.java
 *
 * Created on 6 de Fevereiro de 2005, 00:35
 */

package net.indrix.arara.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Jefferson_Angelica
 */
public class Specie {
	private int id;

	private String name;

    private String englishName;
    
	private Family family;

	private List commonNames;

	private boolean soundAvailable;

	private List sounds;

	/** Creates a new instance of Specie */
	public Specie() {
		id = -1;
		name = null;
		family = new Family();
		commonNames = new ArrayList();
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
	public Family getFamily() {
		return family;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public List getPopularNames() {
		return commonNames;
	}

	/**
	 * @return
	 */
	public boolean isSoundAvailable() {
		return soundAvailable;
	}

	/**
	 * @return
	 */
	public List getSounds() {
		return sounds;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param list
	 */
	public void setPopularNames(List list) {
		commonNames = list;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

	/**
	 * @param family
	 */
	public void setFamily(Family family) {
		this.family = family;
	}

	/**
	 * @param b
	 */
	public void setSoundAvailable(boolean b) {
		soundAvailable = b;
	}

	/**
	 * @param list
	 */
	public void setSounds(List list) {
		sounds = list;
	}

	public String toString() {
		return "[" + id + " - " + name + " - " + englishName + "] , " + family + getCommonNamesString();
	}

	/**
	 * @return
	 */
	public String getCommonNamesString() {
		StringBuffer buffer = new StringBuffer("[");
		Iterator it = commonNames.iterator();
		while (it.hasNext()) {
			CommonName common = (CommonName) it.next();
            if (common != null){
                buffer.append(common.getName());
                buffer.append(", ");                
            }
		}
		if (buffer.length() > 1) {
			buffer.replace(buffer.length() - 2, buffer.length() - 1, "]");
		} else {
		    buffer.append(" ]");
        }
		return buffer.toString();
	}

	/**
	 * This method returns the first common name to the specie
	 * 
	 * @return
	 */
	public String getCommonNameString() {
		String name = "";
		if ((commonNames != null) && (!commonNames.isEmpty())) {
			CommonName common = (CommonName) commonNames.get(0);
			if (common != null) {
				name = common.getName();
			}
		}
		return name;
	}

	public void addPopularName(CommonName common) {
		commonNames.add(common);
	}

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }
}
