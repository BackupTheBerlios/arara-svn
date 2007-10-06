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
    static int MAX = 5;
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
    public static int getScaledWidth(int thumbnailTargetWidth, int imageWidth,
            int imageHeight) {
        int w = 0, h = 0, hForSmallPhoto = 0;
        float rate;
        float rate1;
        boolean cool = false;
        float smallerDifference = 1;
        for (int i = thumbnailTargetWidth - MAX; (!cool) && (i < thumbnailTargetWidth + MAX); i++){
            rate = ((float) imageWidth) / imageHeight;       
            float f = (i / (float)rate);
            hForSmallPhoto = (int) Math.round(f);

            rate1 = ((float) i) / hForSmallPhoto;
            
            
            if (smallerDifference > Math.abs(rate - rate1)){
                smallerDifference = Math.abs(rate - rate1);
                w = i;
                h = hForSmallPhoto;
            }
                
        }
        
        return w;
    }
    
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
        int w, h = 0, hForSmallPhoto = 0;
        float rate;
        float rate1;
        boolean cool = false;
        float smallerDifference = 1;
        for (int i = thumbnailTargetWidth - MAX; (!cool) && (i < thumbnailTargetWidth + MAX); i++){
            rate = ((float) imageWidth) / imageHeight;       
            float f = (i / (float)rate);
            hForSmallPhoto = (int) Math.round(f);

            rate1 = ((float) i) / hForSmallPhoto;
            
            
            if (smallerDifference > Math.abs(rate - rate1)){
                smallerDifference = Math.abs(rate - rate1);
                w = i;
                h = hForSmallPhoto;
            }
                
        }
        
		return h;
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
        int s = 600;
        int w = getScaledWidth(s, 800, 533);
        int h = getScaledHeight(s, 800, 533);
        System.out.println(w);
        System.out.println(h);
        System.out.println((float)w/h);
        System.out.println((float)800/533);
    }
}
