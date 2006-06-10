/*
 * Created on 12/06/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

/**
 * This class is contains the width value for the thumbnail. The InitServlet is responsible
 * for setting the value
 * 
 * @author Jeff
 */
public class PhotoUtil {

    private static int smallWidth;
    
	/**
	 * @return
	 */
	public static int getSmallWidth() {
		return smallWidth;
	}

	/**
	 * @param i
	 */
	public static void setSmallWidth(int i) {
		smallWidth = i;
	}

}
