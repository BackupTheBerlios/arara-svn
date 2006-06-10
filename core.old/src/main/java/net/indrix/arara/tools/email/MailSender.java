package net.indrix.arara.tools.email;

import java.util.Vector;
import javax.mail.*;

import org.apache.log4j.Logger;

/**
 * File: MailSender.java
 * Description: This class extendes the Thread class and is
 *              responsible for managing a buffer of messages
 *              and send these messages.
 * @author Angelica Demarchi Munhoz de Oliveira e Silva
 * @version
 */

public class MailSender extends Thread {
	/**
	 * The logger object
	 */
	private static Logger logger = Logger.getLogger("indrix.tools.email.MailSender");

	/**
	 * Singleton object of MailSender.
	 */
	private static MailSender pInstance;

	/**
	 * Messages that will be sent
	 */
	private Vector messageBuffer;

	/**
	 * indicator of thread's execution's end.
	 */
	private boolean endThread;

	/**
	 * This is a private constructor. The MailSender class can not be
	 * instantiated by another class because it is a singleton class.
	 * To get a the MailSender object use getInstance() method.
	 */
	private MailSender() {
		messageBuffer = new Vector(1, 1);
		endThread = false;
		logger.info("MailSender start");
		start();
	}

	/**
	 * Static method that creates an instance of MailSender class and
	 * returns it. If the instance is already created, then the method
	 * returns the instance created. This method avoid the creation of
	 * more than one MailSender (thread) instance.
	 */
	public static MailSender getInstance() {
		if (pInstance == null) {
			pInstance = new MailSender();
		}
		return pInstance;
	}

	/**
	 * Add a new message in the buffer of messages
	 */
	synchronized public void sendMessage(Message m, boolean sendNow) {
		if (sendNow) {
			try {
				Transport.send(m);
				logger.info("Mail was sent successfully.");
			} catch (SendFailedException e) {
				logger.debug("Error to send email : " + e);
                System.out.println(e.getInvalidAddresses());
                System.out.println(e.getValidSentAddresses());
                System.out.println(e.getValidUnsentAddresses());
			} catch (MessagingException e) {
                logger.debug("Error to send email : " + e);
			}
		} else {
			logger.debug("Add msg");
			messageBuffer.add(m);
		}
	}

	/**
	 * Verify the buffer of message of each 1 second and if
	 * there are messages on it, these messages are sent and
	 * removed from the buffer.
	 */
	public void run() {
		Message message;
		logger.info("Start thread");
		while (!endThread) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				logger.debug("Error to send email : " + e);
				e.printStackTrace();
			}
			for (int i = 0; i < messageBuffer.size(); i++) {
				try {
					logger.debug("Try to send a msg");
					message = (Message) messageBuffer.firstElement();
					messageBuffer.remove(message);
					// send the message
					Transport.send(message);
					logger.info("Mail was sent successfully.");
				} catch (MessagingException e) {
					logger.debug("Error to send email 2 : " + e);
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * sinalize to the thread that is time to finish its execution.
	 */
	synchronized public void finalizeMailSender() {
		endThread = true;
		logger.info("MailSender stop");
	}
}