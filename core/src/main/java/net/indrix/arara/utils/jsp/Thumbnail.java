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

    public static int getWidth(int thumbnailTargetWidth, int imageWidth, int imageHeight){
        float rate = ((float)imageWidth)/imageHeight;
        int hForSmallPhoto = (int) (thumbnailTargetWidth / rate);

        if (hForSmallPhoto > thumbnailTargetWidth){
            hForSmallPhoto = thumbnailTargetWidth;
            thumbnailTargetWidth = (int)(hForSmallPhoto * rate); 
        }

        return thumbnailTargetWidth;
    }

    public static int getHeight(int thumbnailTargetWidth, int imageWidth, int imageHeight){
        float rate = ((float)imageWidth)/imageHeight;
        int hForSmallPhoto = (int) (thumbnailTargetWidth / rate);

        if (hForSmallPhoto > thumbnailTargetWidth){
            int temp = thumbnailTargetWidth;
            thumbnailTargetWidth = hForSmallPhoto;
            hForSmallPhoto = temp;
        }

        return hForSmallPhoto;
    }
}
