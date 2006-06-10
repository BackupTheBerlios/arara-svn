/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

import java.io.InputStream;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundFile {
    private InputStream sound;
    private int fileSize;
    private String filename;

	/**
	 * @return
	 */
	public InputStream getSound() {
		return sound;
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
	public int getFileSize() {
		return fileSize;
	}

    /**
     * @param stream
     */
    public void setSound(InputStream stream) {
        sound = stream;
    }

	/**
	 * @param string
	 */
	public void setFilename(String string) {
		filename = string;
	}

	/**
	 * @param i
	 */
	public void setFileSize(int i) {
		fileSize = i;
	}

}
