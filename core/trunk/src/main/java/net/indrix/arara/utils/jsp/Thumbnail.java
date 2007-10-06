/*
 * Created on 19/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.utils.jsp;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Thumbnail {

	/**
	 * This method retrieves the scaled height for the given width
	 * 
	 * @param thumbnailTargetWidth
	 *            The target width
	 * @param imageWidth
	 *            The photo width
	 * @param imageHeight
	 *            The photo heigth
	 * 
	 * @return The new scaled height of the photo
	 */
	public static int getScaledHeight(int thumbnailTargetWidth, int imageWidth,
			int imageHeight) {

		float rate = ((float) imageWidth) / imageHeight;       
        float f = (thumbnailTargetWidth / (float)rate);
        int hForSmallPhoto = (int) Math.round(f);

		return hForSmallPhoto;
	}

	/**
	 * This method retrieves the thumbnail width for the given width
	 * 
	 * @param thumbnailTargetWidth
	 *            The target width
	 * @param imageWidth
	 *            The photo width
	 * @param imageHeight
	 *            The photo heigth
	 * 
	 * @return The new scaled height of the photo
	 */
	public static int getWidth(int thumbnailTargetWidth, int imageWidth,
			int imageHeight) {
		float rate = ((float) imageWidth) / imageHeight;
		int hForSmallPhoto = (int) (thumbnailTargetWidth / rate);

		if (hForSmallPhoto > thumbnailTargetWidth) {
			hForSmallPhoto = thumbnailTargetWidth;
			thumbnailTargetWidth = (int) (hForSmallPhoto * rate);
		}

		return thumbnailTargetWidth;
	}

	/**
	 * This method retrieves the thumbnail height for the given width
	 * 
	 * @param thumbnailTargetWidth
	 *            The target width
	 * @param imageWidth
	 *            The photo width
	 * @param imageHeight
	 *            The photo heigth
	 * 
	 * @return The new scaled height of the photo
	 */
	public static int getHeight(int thumbnailTargetWidth, int imageWidth,
			int imageHeight) {
		float rate = ((float) imageWidth) / imageHeight;
        float f = (thumbnailTargetWidth / (float)rate);
		int hForSmallPhoto = (int) Math.round(f);

		if (hForSmallPhoto > thumbnailTargetWidth) {
			int temp = thumbnailTargetWidth;
			thumbnailTargetWidth = hForSmallPhoto;
			hForSmallPhoto = temp;
		}

		return hForSmallPhoto;
	}
    
    public static void main(String args[]){
        int h = getHeight(600, 800, 533);
        System.out.println(h);
    }
}
