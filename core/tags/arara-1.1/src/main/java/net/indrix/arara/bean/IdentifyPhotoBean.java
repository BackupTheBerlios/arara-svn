/*
 * Created on 07/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.bean;

import java.util.List;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class IdentifyPhotoBean {
	protected List familyList;

	protected String selectedFamilyId;

	protected List specieList;

	protected String selectedSpecieId;

	protected String selectedAgeId;

	protected String selectedSexId;

	protected String comment;

	/**
	 * @return
	 */
	public List getFamilyList() {
		return familyList;
	}

	/**
	 * @return
	 */
	public String getSelectedFamilyId() {
		return selectedFamilyId;
	}

	/**
	 * @return
	 */
	public String getSelectedSpecieId() {
		return selectedSpecieId;
	}

	/**
	 * @return
	 */
	public List getSpecieList() {
		return specieList;
	}

	/**
	 * @return
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param list
	 */
	public void setFamilyList(List list) {
		familyList = list;
	}

	/**
	 * @param string
	 */
	public void setSelectedFamilyId(String string) {
		selectedFamilyId = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedSpecieId(String string) {
		selectedSpecieId = string;
	}

	/**
	 * @param list
	 */
	public void setSpecieList(List list) {
		specieList = list;
	}

	/**
	 * @param string
	 */
	public void setComment(String string) {
		comment = string;
	}

	/**
	 * @return
	 */
	public String getSelectedAgeId() {
		return selectedAgeId;
	}

	/**
	 * @return
	 */
	public String getSelectedSexId() {
		return selectedSexId;
	}

	/**
	 * @param string
	 */
	public void setSelectedAgeId(String string) {
		selectedAgeId = string;
	}

	/**
	 * @param string
	 */
	public void setSelectedSexId(String string) {
		selectedSexId = string;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		buffer.append(selectedFamilyId);
		buffer.append(",");
		buffer.append(selectedSpecieId);
		buffer.append("]");
		return buffer.toString();
	}

}
