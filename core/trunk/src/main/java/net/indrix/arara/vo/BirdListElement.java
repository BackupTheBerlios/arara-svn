package net.indrix.arara.vo;

public class BirdListElement {

    private int id;
    private Specie specie;
    private Photo photo;
    private Sound sound;
    private BirdListElementStatus status;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Photo getPhoto() {
        return photo;
    }
    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
    public Sound getSound() {
        return sound;
    }
    public void setSound(Sound sound) {
        this.sound = sound;
    }
    public Specie getSpecie() {
        return specie;
    }
    public void setSpecie(Specie specie) {
        this.specie = specie;
    }
    public BirdListElementStatus getStatus() {
        return status;
    }
    public void setStatus(BirdListElementStatus status) {
        this.status = status;
    }
    
    
}
