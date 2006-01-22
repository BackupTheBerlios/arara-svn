/*
 * Created on 12/10/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets.utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;

public class ExemploMail extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {

		PrintWriter out = res.getWriter();
		res.setContentType("text/html");
		try {

			sendOne(req);
			out.println("E-mail 1 enviado");
            
			sendTwo(req);
            out.println("E-mail 2 enviado");
		} catch (MessagingException e) {
			out.println("Email nao pode ser enviado! " + e.getMessage());
		}
	}

	private void sendTwo(HttpServletRequest req)
		throws NoRecipientException, AddressException, MessageFormatException, SendFailedException {
		MailClass m = new MailClass("smtp2.locaweb.com.br");
		m.setToAddress(req.getParameter("to"));
		m.setFromAddress("teste@dominio.com.br", "Teste");
		m.setSubject("teste de envio de e-mails");
		m.setMessageTextBody("este eh um teste de envio");
		m.sendMessage(true);
	}

	private void sendOne(HttpServletRequest req) throws MessagingException, AddressException {
		String to = req.getParameter("to");
		
		String from = "teste@dominio.com.br";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp2.locaweb.com.br");
		Session session = Session.getInstance(props, null);
		
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(from));
		Address toAddress = new InternetAddress(to);
		message.addRecipient(Message.RecipientType.TO, toAddress);
		
		message.setSubject("teste de envio de e-mails");
		
		message.setContent("este eh um teste de envio", "text/plain");
		
		Transport.send(message);
	}
}
