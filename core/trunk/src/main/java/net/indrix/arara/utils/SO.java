/*
 * Created on 19/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.utils;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SO {
	public static final int WIN = 0;

	public static final int LINUX = 1;

	public static int getSO() {
		int so = WIN;
		String os = System.getProperty("os.name");
		if (os.toLowerCase().indexOf("win") < 0) {
			so = LINUX;
		}
		return so;
	}
}
