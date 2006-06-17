/*
 * Created on 02/10/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.utils.PropertiesManager;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SendEmailtest {

	public static void main(String[] args) {
		String server = PropertiesManager.getProperty("email.server");
		String subject = PropertiesManager
				.getProperty("email.newPhoto.subject");
		String body = PropertiesManager.getProperty("email.newPhoto.body");
		String from = PropertiesManager.getProperty("email.newPhoto.from");
		try {

			// send password to user
			MailClass sender = new MailClass(server);
			sender.setToAddress("jefferson@indrix.net");
			sender.setSubject(subject);
			sender.setMessageTextBody(body);
			sender.setFromAddress(from, "Aves Brasil");
			sender.sendMessage(true); // true indicates to emailObject to send
										// the message right now

		} catch (MessageFormatException e) {
			System.out
					.println("exception -> MessageFormatException in sendEmail "
							+ e);
		} catch (AddressException e) {
			System.out.println("exception -> AddressException in sendEmail "
					+ e);
		} catch (NoRecipientException e) {
			System.out
					.println("exception -> NoRecipientException in sendEmail "
							+ e);
		} catch (SendFailedException e) {
			System.out.println("exception -> SendFailedException in sendEmail "
					+ e);
		} catch (Exception e) {
			System.out.println("exception -> in sendEmail " + e);
		}
	}
}
