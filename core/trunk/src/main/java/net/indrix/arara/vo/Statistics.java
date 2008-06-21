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
    private int numberOfSounds;
	private int numberOfFamiliesWithPhoto;
    private int numberOfFamiliesWithSound;
    private int numberOfSpecies;
    private int numberOfSpeciesWithPhoto;
	private int numberOfSpeciesWithSound;
	private int numberOfUsers;
    private int numberOfUsersWithPhoto;
    private int numberOfUsersWithSound;
    
    public int getNumberOfFamiliesWithPhoto() {
        return numberOfFamiliesWithPhoto;
    }
    public void setNumberOfFamiliesWithPhoto(int numberOfFamiliesWithPhoto) {
        this.numberOfFamiliesWithPhoto = numberOfFamiliesWithPhoto;
    }
    public int getNumberOfFamiliesWithSound() {
        return numberOfFamiliesWithSound;
    }
    public void setNumberOfFamiliesWithSound(int numberOfFamiliesWithSound) {
        this.numberOfFamiliesWithSound = numberOfFamiliesWithSound;
    }
    public int getNumberOfPhotos() {
        return numberOfPhotos;
    }
    public void setNumberOfPhotos(int numberOfPhotos) {
        this.numberOfPhotos = numberOfPhotos;
    }
    public int getNumberOfSounds() {
        return numberOfSounds;
    }
    public void setNumberOfSounds(int numberOfSounds) {
        this.numberOfSounds = numberOfSounds;
    }

    public int getNumberOfSpecies() {
        return numberOfSpecies;
    }
    public void setNumberOfSpecies(int numberOfSpecies) {
        this.numberOfSpecies = numberOfSpecies;
    }

    public int getNumberOfSpeciesWithPhoto() {
        return numberOfSpeciesWithPhoto;
    }
    public void setNumberOfSpeciesWithPhoto(int numberOfSpeciesWithPhoto) {
        this.numberOfSpeciesWithPhoto = numberOfSpeciesWithPhoto;
    }

    public int getNumberOfSpeciesWithSound() {
        return numberOfSpeciesWithSound;
    }
    public void setNumberOfSpeciesWithSound(int numberOfSpeciesWithSound) {
        this.numberOfSpeciesWithSound = numberOfSpeciesWithSound;
    }
    public int getNumberOfUsers() {
        return numberOfUsers;
    }
    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
    public int getNumberOfUsersWithPhoto() {
        return numberOfUsersWithPhoto;
    }
    public void setNumberOfUsersWithPhoto(int numberOfUsersWithPhoto) {
        this.numberOfUsersWithPhoto = numberOfUsersWithPhoto;
    }
    public int getNumberOfUsersWithSound() {
        return numberOfUsersWithSound;
    }
    public void setNumberOfUsersWithSound(int numberOfUsersWithSound) {
        this.numberOfUsersWithSound = numberOfUsersWithSound;
    }

}
