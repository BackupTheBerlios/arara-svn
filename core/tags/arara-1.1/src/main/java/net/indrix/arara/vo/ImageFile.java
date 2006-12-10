/*
 * Created on 12/06/2005
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
public class ImageFile {
	private InputStream image;

	private int width;

	private int height;

	private int imageSize;

	/**
	 * @return
	 */
	public InputStream getImage() {
		return image;
	}

	/**
	 * @param stream
	 */
	public void setImage(InputStream stream) {
		image = stream;
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param i
	 */
	public void setHeight(int i) {
		height = i;
	}

	/**
	 * @param i
	 */
	public void setWidth(int i) {
		width = i;
	}

	/**
	 * @return
	 */
	public int getImageSize() {
		return imageSize;
	}

	/**
	 * @param l
	 */
	public void setImageSize(int l) {
		imageSize = l;
	}

	public float getRate() {
		return ((float) width) / height;
	}

}
