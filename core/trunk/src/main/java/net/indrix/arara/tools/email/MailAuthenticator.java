/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.tools.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MailAuthenticator extends Authenticator {

	public PasswordAuthentication getPasswordAuthentication() {
		String username, password;

		username = "webmaster@aves.brasil.nom.br";
		password = "aves11brasil22";

		return new PasswordAuthentication(username, password);
	}
}