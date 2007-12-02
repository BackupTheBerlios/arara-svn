package net.indrix.arara.tools.email;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

/**
 * File: MailClass.java
 * Description: This class is responsible for building a message with a
 *              specific subject and body and send this message to the
 *              specified addresses, using the server specified.
 * @author   Angelica D. Munhoz de O. e Silva
 * @version  1.0
 */

public class MailClass {
	//----------------------------------------------------
	// Attributes

	/**
	 * The logger object
	 */
	private static Logger logger = Logger.getLogger("net.indrix.aves");

	private Session session;
	private Message message;
	private Address fromAddress;
	private Vector toAddresses;
	private Vector ccAddresses;
	private Vector bccAddresses;
	private Vector replyToAddresses;
	private String subject;
	private Object body;
	private String server;
	private boolean sessionFailed;

	//----------------------------------------------------
	// Constructors

	/**
	 * Constructor
	 */
	public MailClass(String s) throws NoServerException {
		server = s;
		if (server != null) {
			sessionFailed = false;
			// set the session
			setSession();
			// build the message object
			message = new MimeMessage(session);
		} else {
			throw new NoServerException("There is no server specified");
		}
	}

	// ---------------------------------------------------
	// PRIVATE methods
	// ---------------------------------------------------

	/**
	 * set the session used to send the message
	 */
	private void setSession() {
		try {
			Properties properties = new Properties();
			logger.debug("Email Server " + server);
			properties.put("mail.smtp.host", server);
			session = Session.getInstance(properties, null);
		} catch (Exception e) {
			sessionFailed = true;
			logger.debug("Error in session : " + e);
		}
	}

	// ---------------------------------------------------
	// PUBLIC methods
	// ---------------------------------------------------

	// ---------------------------------------------------
	// SET methods

	/**
	 * set the address that sends the message
	 */
	public void setFromAddress(String fromAddr, String fromName)
		throws NoRecipientException, AddressException {
		try {
			message.setFrom(new InternetAddress(fromAddr, fromName));
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's 'FROM' field.");
		} catch (UnsupportedEncodingException e) {
			throw new NoRecipientException("Problem with the message's 'FROM' field.");
		}

	}

	/**
	 * set the addresses that have their recipients as "TO"
	 */
	public void setToAddresses(List toAddr) throws NoRecipientException, AddressException {
		try {

			// clear the 'TO' recipients
			message.setRecipients(Message.RecipientType.TO, (Address[]) null);

            // save the recipients
            if (toAddresses == null){
                toAddresses = new Vector();
            }
			toAddresses.addAll(toAddr);
            
            // add to addresses to message
            Iterator it = toAddr.iterator();
            while (it.hasNext()){
                String to = (String)it.next();           
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			}

		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's recipients set as 'TO'.");
		}
	}

	/**
	 * set the address that has its recipient as "TO"
	 */
	public void setToAddress(String toAddr) throws NoRecipientException, AddressException {
		try {
			// clear the 'TO' recipients
			message.setRecipients(Message.RecipientType.TO, null);

			toAddresses = new Vector(1, 1);
			Address addr = new InternetAddress(toAddr);
			message.setRecipient(Message.RecipientType.TO, addr);
			toAddresses.add(addr);
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's recipient set as 'TO'.");
		}
	}

	/**
	 * set the addresses that have their recipients as "CC"
	 */
	public void setCCAddresses(Vector ccAddr) throws NoRecipientException, AddressException {
		Address addr;
		try {
			// clear the 'CC' recipients
			message.setRecipients(Message.RecipientType.CC, (Address[]) null);

			ccAddresses = new Vector(1, 1);
			for (int i = 0; i < ccAddr.size(); i++) {
				addr = new InternetAddress((String) ccAddr.elementAt(i));
				message.addRecipient(Message.RecipientType.CC, addr);
				ccAddresses.add(addr);
			}
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's recipient set as 'CC'.");
		}

	}

	/**
	 * set the address that has its recipient as "CC"
	 */
	public void setCCAddress(String ccAddr) throws NoRecipientException, AddressException {
		try {
			// clear the 'CC' recipients
			message.setRecipients(Message.RecipientType.CC, (Address[]) null);

			ccAddresses = new Vector(1, 1);
			Address addr = new InternetAddress(ccAddr);
			message.addRecipient(Message.RecipientType.CC, addr);
			ccAddresses.add(addr);
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's recipient set as 'CC'.");
		}

	}

	/**
	 * set the addresses that have their recipients as "BCC"
	 */
	public void setBCCAddresses(Vector bccAddr) throws NoRecipientException, AddressException {
		Address addr;
		try {
			// clear the 'BCC' recipients
			message.setRecipients(Message.RecipientType.BCC, (Address[]) null);

			bccAddresses = new Vector(1, 1);
			for (int i = 0; i < bccAddr.size(); i++) {
				addr = new InternetAddress((String) bccAddr.elementAt(i));
				message.addRecipient(Message.RecipientType.BCC, addr);
				bccAddresses.add(addr);
			}
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's recipient set as 'BCC'.");
		}
	}

	/**
	 * set the address that has its recipient as "BCC"
	 */
	public void setBCCAddress(String bccAddr) throws NoRecipientException, AddressException {
		try {
			// clear the 'BCC' recipients
			message.setRecipients(Message.RecipientType.BCC, (Address[]) null);

			bccAddresses = new Vector(1, 1);
			Address addr = new InternetAddress(bccAddr);
			message.addRecipient(Message.RecipientType.BCC, addr);
			bccAddresses.add(addr);
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's recipient set as 'BCC'.");
		}
	}

	/**
	 * set the 'REPLY TO' field of the message to the specified addresses
	 */
	public void setReplyToAddresses(Vector replyAddr)
		throws NoRecipientException, AddressException {
		Address addr;
		Address[] rAddr = (Address[]) Array.newInstance(Address.class, replyAddr.size());
		try {
			// clear the 'REPLY TO' fields
			message.setReplyTo(null);

			replyToAddresses = new Vector(1, 1);
			for (int i = 0; i < replyAddr.size(); i++) {
				addr = new InternetAddress((String) replyAddr.elementAt(i));
				rAddr[i] = addr;
				replyToAddresses.add(addr);
			}
			message.setReplyTo(rAddr);
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's REPLY TO field.");
		}
	}

	/**
	 * set the 'REPLY TO' field of the message to the specified address
	 */
	public void setReplyToAddress(String replyAddr) throws NoRecipientException, AddressException {
		try {
			// clear the 'REPLY TO' fields
			message.setReplyTo(null);

			replyToAddresses = new Vector(1, 1);
			Address[] rAddr = (Address[]) Array.newInstance(Address.class, 1);
			Address addr = new InternetAddress(replyAddr);
			replyToAddresses.add(addr);
			rAddr[0] = addr;
			message.setReplyTo(rAddr);
		} catch (AddressException e) {
			throw e;
		} catch (MessagingException e) {
			throw new NoRecipientException("Problem with the message's REPLY TO field.");
		}
	}

	/**
	 * set the subject of the message
	 */
	public void setSubject(String s) throws MessageFormatException {
		subject = s;
		try {
			message.setSubject(subject);
		} catch (MessagingException e) {
			throw new MessageFormatException("There is some problem with the subject of the message.");
		}
	}

	/**
	 * set the body as text of the message
	 */
	public void setMessageTextBody(Object b) throws MessageFormatException {
		body = b;
		try {
			message.setText((String) b);
			message.setContent(body, "text/plain");
		} catch (MessagingException e) {
			throw new MessageFormatException("There is some problem with the body(text) of the message.");
		}
	}

	/**
	 * set the body as HTML of the message
	 */
	public void setMessageTextHTML(Object b) throws MessageFormatException {
		body = b;
		try {
			//message.setContent(body, "html/plain");
            
			// Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart();

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText((String)body);

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(body, "text/html");

            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);
		} catch (MessagingException e) {
			throw new MessageFormatException("There is some problem with the body(HTML) of the message.");
		}
	}

	/**
	 * set the server mail used in the application
	 */
	public void setServer(String s) {
		server = s;
	}

	// ---------------------------------------------------
	// GET methods

	/**
	 * get the 'FROM' field of the message
	 */
	public Address getFromAddress() {
		return fromAddress;
	}

	/**
	 * get the addresses in the 'TO' recipient.
	 */
	public Vector getToAddresses() {
		return toAddresses;
	}

	/**
	 * get the addresses in the 'CC' recipient.
	 */
	public Vector getCCAddresses() {
		return ccAddresses;
	}

	/**
	 * get the addresses in the 'BCC' recipient.
	 */
	public Vector getBCCAddresses() {
		return bccAddresses;
	}

	/**
	 * get the 'REPLY TO' addresses of the message.
	 */
	public Vector getReplyToAddresses() {
		return replyToAddresses;
	}

	/**
	 * get the subject of the message
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * get the body of the message
	 */
	public Object getBody() {
		return body;
	}

	/**
	 * get the mail server
	 */
	public String getServer() {
		return server;
	}

	// ------------------------------------------
	// Other operations

	/**
	 * send the message to the specified addresses
	 *
	 * @param now Indicates if the message has to be sent right now or it can
	 *        be queued for later send
	 *
	 */
	public void sendMessage(boolean now) throws NoRecipientException, SendFailedException {
		if (sessionFailed == false) {
			try {
				Address[] addrList = message.getAllRecipients();
				if (addrList != null) {
					MailSender mSender = MailSender.getInstance();
					mSender.sendMessage(message, now);
					logger.info("Mail sent successfully!");
				} else {
					logger.debug("NoRecipientException!");
					throw new NoRecipientException("No message recipient.");
				}
			} catch (MessagingException e) {
				logger.debug("MessagingException!");
				throw new SendFailedException("Can not send the message.");
			}
		} else {
			throw new SendFailedException("Can not send the message.");
		}
	}
}
