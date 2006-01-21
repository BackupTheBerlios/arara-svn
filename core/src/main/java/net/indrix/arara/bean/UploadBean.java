/*
 * Created on 08/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.bean;

import java.util.List;

import org.apache.commons.fileupload.FileItem;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UploadBean {

    protected List familyList;
    protected String selectedFamilyId;
    protected List specieList;
    protected String selectedSpecieId;
    protected String filename;
    protected String fileSize;
    protected FileItem fileItem;    
    protected String selectedAgeId;
    protected String selectedSexId;
    protected String location;
    protected String cityId;
    protected String stateId;
    protected String selectedCityId;
    protected String selectedStateId;
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
	public String getFilename() {
		return filename;
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
    public FileItem getFileItem() {
        return fileItem;
    }

    /**
     * @return
     */
    public String getFileSize() {
        return fileSize;
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
	public void setFilename(String string) {
		filename = string;
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
     * @param i
     */
    public void setSelectedAgeId(String i) {
        selectedAgeId = i;
    }

    /**
     * @param i
     */
    public void setSelectedSexId(String i) {
        selectedSexId = i;
    }

	/**
	 * @param item
	 */
	public void setFileItem(FileItem item) {
		fileItem = item;
	}

	/**
	 * @param string
	 */
	public void setFileSize(String string) {
		fileSize = string;
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
    public String getCityId() {
        return cityId;
    }

    /**
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return
     */
    public String getStateId() {
        return stateId;
    }

    /**
     * @param string
     */
    public void setCityId(String string) {
        cityId = string;
    }

    /**
     * @param string
     */
    public void setLocation(String string) {
        location = string;
    }

    /**
     * @param string
     */
    public void setStateId(String string) {
        stateId = string;
    }

    /**
     * @return
     */
    public String getSelectedCityId() {
        return selectedCityId;
    }

    /**
     * @return
     */
    public String getSelectedStateId() {
        return selectedStateId;
    }

    /**
     * @param string
     */
    public void setSelectedCityId(String string) {
        selectedCityId = string;
    }

    /**
     * @param string
     */
    public void setSelectedStateId(String string) {
        selectedStateId = string;
    }


    public String toString(){
        StringBuffer buffer = new StringBuffer("[");
        buffer.append(selectedFamilyId);
        buffer.append(",");
        buffer.append(selectedSpecieId);
        buffer.append(",");
        buffer.append(filename);
        buffer.append(",");
        buffer.append(fileSize);
        buffer.append("]");
        return buffer.toString();
    }    
}
