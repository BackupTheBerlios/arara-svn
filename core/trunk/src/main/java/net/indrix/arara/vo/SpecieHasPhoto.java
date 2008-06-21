package net.indrix.arara.vo;


public class SpecieHasPhoto extends Specie{

    private boolean photo;
    private Specie specie;
    
    public SpecieHasPhoto(){
        photo = false;
    }

    public SpecieHasPhoto(Specie s){
        photo = false;
        specie = s;
    }

    public SpecieHasPhoto(Specie specie , boolean hasPhoto){
        this.photo = hasPhoto;
        this.specie = specie;
    }

    public boolean isPhoto() {
        return photo;
    }

    public void setPhoto(boolean photo) {
        this.photo = photo;
    }

    public Specie getSpecie() {
        return specie;
    }

    public void setSpecie(Specie specie) {
        this.specie = specie;
    }
    
    public int getId() {
        return specie.getId();
    }

    /**
     * @return
     */
    public Family getFamily() {
        return specie.getFamily();
    }

    /**
     * @return
     */
    public String getName() {
        return specie.getName();
    }

    /**
     * @return
     */
    public String getCommonNamesString() {
        return specie.getCommonNamesString();
    }

    /**
     * This method returns the first common name to the specie
     * 
     * @return
     */
    public String getCommonNameString() {
        return specie.getCommonNameString();
    }
}
