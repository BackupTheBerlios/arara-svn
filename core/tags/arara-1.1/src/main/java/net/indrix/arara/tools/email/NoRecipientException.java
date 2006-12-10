package net.indrix.arara.tools.email;

import javax.mail.MessagingException;

/**
 * File: NoRecipientException.java Description: Exception class for lack of
 * recipient in MailClass object
 * 
 * @author Angelica D. Munhoz de O. e Silva
 * @version 1.0
 */

public class NoRecipientException extends MessagingException {

	String exception;

	public NoRecipientException() {
	}

	public NoRecipientException(String e) {
		super(e);
		exception = e;
	}
}
