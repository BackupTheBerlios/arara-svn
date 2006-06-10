/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.bean;


/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UploadPhotoBean extends UploadBean {
    private String camera;
    private String film;
    private String lens;
    private String date;

    /**
     * @return
     */
    public String getCamera() {
        return camera;
    }

    /**
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * @return
     */
    public String getFilm() {
        return film;
    }

    /**
     * @return
     */
    public String getLens() {
        return lens;
    }

    /**
     * @param string
     */
    public void setCamera(String string) {
        camera = string;
    }

    /**
     * @param string
     */
    public void setDate(String string) {
        date = string;
    }

    /**
     * @param string
     */
    public void setFilm(String string) {
        film = string;
    }

    /**
     * @param string
     */
    public void setLens(String string) {
        lens = string;
    }

    public String toString(){
        StringBuffer buffer = new StringBuffer(super.toString());
        buffer.append("[");
        buffer.append(camera);
        buffer.append(",");
        buffer.append(lens);
        buffer.append(",");
        buffer.append(film);
        buffer.append(",");
        buffer.append(location);
        buffer.append(",");
        buffer.append(date);
        buffer.append("]");
        return buffer.toString();
    }
}
