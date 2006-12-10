package net.indrix.arara.tools.email;

import java.util.List;
import java.util.Vector;

/**
 * File: MessageComposer.java Description: This class is responsible for
 * formating messages.
 * 
 * @author Angelica Demarchi Munhoz de Oliveira e Silva
 * @version 1.0
 */

public class MessageComposer {
	/**
	 * delimiter used in the messages that will be replaced by a value.
	 */
	static final private String delimiter = "_$$$_";

	/**
	 * format the messages that are in the vector of messages replacing the
	 * delimiters by the values in the vector of values. This method returns a
	 * vector with the messages formated.
	 */
	static public Vector formatMessage(Vector messages, Vector values)
			throws WrongNumberOfValuesException {
		Vector returnValue = new Vector(1, 1);
		int numberOfDelimiters = 0;
		boolean delimiterFound;
		String formatedMessage;
		String message;
		String firstPart;
		int endIndex;
		int valueIndex = 0;

		for (int index = 0; index < messages.size(); index++) {
			message = (String) messages.elementAt(index);
			delimiterFound = true;
			firstPart = "";
			formatedMessage = "";
			endIndex = 0;

			while (delimiterFound == true) {
				endIndex = message.indexOf(delimiter);
				if (endIndex != -1) {
					firstPart = message.substring(0, endIndex);
					message = message.substring(endIndex + delimiter.length(),
							message.length());
					if (valueIndex < values.size()) {
						formatedMessage = formatedMessage + firstPart
								+ values.elementAt(valueIndex);
					} else {
						throw new WrongNumberOfValuesException(
								"The number of delimiters in your messages is bigger than the number of the values.");
					}
					numberOfDelimiters++;
					valueIndex++;
				} else {
					if (message.length() > 0) {
						formatedMessage = formatedMessage + message;
					}
					delimiterFound = false;
				}
			}
			returnValue.add(formatedMessage);
		}
		if (numberOfDelimiters != values.size()) {
			throw new WrongNumberOfValuesException(
					"The number of values is bigger than the number of the delimiters in your messages.");
		}
		return returnValue;
	}

	/**
	 * format the message replacing the delimiters by the values in the vector
	 * of values. This method returns the message formated.
	 */
	static public String formatMessage(String message, List values)
			throws WrongNumberOfValuesException {
		int numberOfDelimiters = 0;
		boolean delimiterFound = true;
		String formatedMessage = "";
		String firstPart = "";
		int endIndex = 0;
		int valueIndex = 0;

		while (delimiterFound == true) {
			endIndex = message.indexOf(delimiter);
			if (endIndex != -1) {
				firstPart = message.substring(0, endIndex);
				message = message.substring(endIndex + delimiter.length(),
						message.length());
				if (valueIndex < values.size()) {
					formatedMessage = formatedMessage + firstPart
							+ values.get(valueIndex);
				} else {
					throw new WrongNumberOfValuesException(
							"The number of delimiters in your message is bigger than the number of the values.");
				}
				numberOfDelimiters++;
				valueIndex++;
			} else {
				if (message.length() > 0) {
					formatedMessage = formatedMessage + message;
				}
				delimiterFound = false;
			}
		}

		if (numberOfDelimiters != values.size()) {
			throw new WrongNumberOfValuesException(
					"The number of values is bigger than the number of the delimiters in your message.");
		}
		return formatedMessage;
	}
}
