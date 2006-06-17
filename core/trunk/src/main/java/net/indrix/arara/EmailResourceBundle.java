/*
 * Created on 03/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EmailResourceBundle extends AbstractResourceBundle {

	/**
	 * @see net.indrix.arara.AbstractResourceBundle#getInstance()
	 */
	public static synchronized AbstractResourceBundle getInstance() {
		if (instance == null) {
			instance = new EmailResourceBundle();
		}
		return instance;
	}

	/**
	 * @see net.indrix.arara.AbstractResourceBundle#getBundleToLoad()
	 */
	protected String getBundleToLoad() {
		return "Email";
	}

}
