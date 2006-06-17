package net.indrix.arara.tools.email;

import javax.mail.MessagingException;

/**
 * File: MessageFormatException.java Description: Exception class for message
 * format
 * 
 * @author Angelica D. Munhoz de O. e Silva
 * @version 1.0
 */

public class MessageFormatException extends MessagingException {
	String exception;

	public MessageFormatException(String e) {
		super(e);
		exception = e;
	}

}
