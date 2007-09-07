package net.indrix.arara.tools.email;

import java.util.Vector;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Transport;

import org.apache.log4j.Logger;

/**
 * File: MailSender.java Description: This class extendes the Thread class and
 * is responsible for managing a buffer of messages and send these messages.
 * 
 * @author Angelica Demarchi Munhoz de Oliveira e Silva
 * @version
 */

public class MailSender extends Thread {
	/**
	 * The logger object
	 */
	private static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * The logger object
     */
    private static Logger loggerEmails = Logger.getLogger("net.indrix.emails");

	/**
	 * Singleton object of MailSender.
	 */
	private static MailSender pInstance;

	/**
	 * Messages that will be sent
	 */
	private Vector <Message>messageBuffer;

	/**
	 * indicator of thread's execution's end.
	 */
	private boolean endThread;	

	/**
	 * This is a private constructor. The MailSender class can not be
	 * instantiated by another class because it is a singleton class. To get a
	 * the MailSender object use getInstance() method.
	 */
	private MailSender() {
		messageBuffer = new Vector<Message>(1, 1);
		endThread = false;
		logger.info("MailSender start");
		start();
	}

	/**
	 * Static method that creates an instance of MailSender class and returns
	 * it. If the instance is already created, then the method returns the
	 * instance created. This method avoid the creation of more than one
	 * MailSender (thread) instance.
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
	synchronized public void sendMessage(Message message, boolean sendNow) throws MessagingException{
		logger.info("MainSender.sendMessage: entering method with flag = " + sendNow);
		if (sendNow) {
			try {
				logger.info("MainSender.sendMessage: calling Transport.send...");
                logger.debug(message.getSubject() + " sent to " + message.getAllRecipients());
                
                String recipients = getRecipients(message.getAllRecipients());
                loggerEmails.debug("Sending email " + message.getSubject() + " to " + recipients);
				Transport.send(message);
                loggerEmails.debug("Email sent...");
			} catch (SendFailedException e) {
				logger.debug("MainSender.sendMessage: error to send email : " + e);
                throw e;
			} catch (MessagingException e) {
				logger.debug("MainSender.sendMessage: error to send email : " + e);
                throw e;
			}
		} else {
			logger.debug("MainSender.sendMessage: adding msg to the queue");
			messageBuffer.add(message);
		}
	}

	private String getRecipients(Address[] allRecipients) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < allRecipients.length; i++){
            buffer.append(allRecipients[i].toString());
            buffer.append(";");
        }
        return buffer.toString();
    }

    /**
	 * Verify the buffer of message of each 1 second and if there are messages
	 * on it, these messages are sent and removed from the buffer.
	 */
	public void run() {
		Message message;
		logger.info("MainSender.run: starting run...");
		while (!endThread) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
			}
			for (int i = 0; i < messageBuffer.size(); i++) {
				try {
					logger.debug("MainSender.run: Try to send a msg");
					synchronized (messageBuffer) {
						message = (Message) messageBuffer.firstElement();
						messageBuffer.remove(message);
					}
					// send the message
                    logger.debug(message.getSubject() + " sent to " + getRecipients(message.getAllRecipients()));
                    
                    String recipients = getRecipients(message.getAllRecipients());
                    loggerEmails.debug("Sending email " + message.getSubject() + " to " + recipients);
                    Transport.send(message);
                    loggerEmails.debug("Email sent...");

                    logger.info("MainSender.run: Mail was sent successfully.");
                    
				} catch (MessagingException e) {
					logger.debug("MainSender.run: Error to send email 2 : " + e);
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