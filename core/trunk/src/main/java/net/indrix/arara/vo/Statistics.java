/*
 * Created on 09/11/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Statistics {

	private int numberOfPhotos;

	private int numberOfFamilies;

	private int numberOfSpecies;

	private int numberOfUsers;

	/**
	 * @return
	 */
	public int getNumberOfFamilies() {
		return numberOfFamilies;
	}

	/**
	 * @return
	 */
	public int getNumberOfPhotos() {
		return numberOfPhotos;
	}

	/**
	 * @param i
	 */
	public void setNumberOfFamilies(int i) {
		numberOfFamilies = i;
	}

	/**
	 * @param i
	 */
	public void setNumberOfPhotos(int i) {
		numberOfPhotos = i;
	}

	/**
	 * @return
	 */
	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	/**
	 * @param i
	 */
	public void setNumberOfUsers(int i) {
		numberOfUsers = i;
	}

	/**
	 * @return
	 */
	public int getNumberOfSpecies() {
		return numberOfSpecies;
	}

	/**
	 * @param i
	 */
	public void setNumberOfSpecies(int i) {
		numberOfSpecies = i;
	}

}
