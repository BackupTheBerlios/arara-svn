/*
 * Created on 19/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model;

import net.indrix.arara.tools.crypt.VigenereCipherImpl;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Cryptography {
	/**
	 * Logger object to be used by this class
	 */
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	/**
	 * Cipher object to be used in encryption/decryption
	 */
	protected static VigenereCipherImpl c;

	static {
		try {
			c = new VigenereCipherImpl();

			String key = "\\]^_`a|}~!\"%&'(";
			String msg = "\\]@l&x@ndr&_M&10_8r@g@.";
			c.setKey(key);
		} catch (Exception e) {
			logger.error("Erro crypt " + e);
		}
	}

	/**
	 * This method encrypts the password
	 * 
	 * @param password
	 *            the password, without any encryption
	 * 
	 * @return The encrypted password
	 */
	public static String cryptPassword(String password) {
		try {
			return c.encrypt(password);
		} catch (Exception e) {
			logger.error("Error Password : " + e);
			return "";
		}
	}

	/**
	 * This method decrypts the password
	 * 
	 * @param password
	 *            The encrypted password
	 * 
	 * @return The non encrypted password
	 */
	public static String decryptPassword(String password) {
		try {
			return c.decrypt(password);
		} catch (Exception e) {
			logger.error("Error Password : " + e);
			return "";
		}
	}

}
