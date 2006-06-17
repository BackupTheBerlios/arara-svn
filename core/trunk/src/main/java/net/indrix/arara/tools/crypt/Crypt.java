package net.indrix.arara.tools.crypt;

public interface Crypt {

	/**
	 * @param password
	 *            String descriptografada.
	 * @return String criptografada.
	 */
	public String encrypt(String clearText);

	/**
	 * @param password
	 *            String criptografada.
	 * @return String descriptografada.
	 */
	public String decrypt(String cipherText);
}
