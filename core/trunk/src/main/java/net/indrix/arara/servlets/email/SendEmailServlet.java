package net.indrix.arara.servlets.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.model.email.UserEmailSender;
import net.indrix.arara.model.email.exceptions.SendEmailException;
import net.indrix.arara.servlets.AbstractServlet;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.User;

public class SendEmailServlet extends AbstractServlet {

    public void init(){
        logger.info("Initializing SendEmailServlet servlet...");
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String login = req.getParameter("loginTo");
        String subject = req.getParameter("subject");
        String body = req.getParameter("body");

        logger.debug("SendEmailServlet.doPost: data received " + login + ", " + subject + ", " + body);
        
        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();

        String nextPage = ServletConstants.FRAME_PAGE;
        String pageToShow = null;

        List <String>errors = new ArrayList<String>();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ServletConstants.USER_KEY);
        if (user == null) {
            logger.debug("errors is not null.");
            errors.add(ServletConstants.USER_NOT_LOGGED);
            nextPage = ServletConstants.LOGIN_PAGE;
        } else {
            // send email to user
            UserEmailSender sender = new UserEmailSender(login, subject, body, user);
            String messageKey = null;
            try {
                sender.sendEmailNow();
                logger.debug("SendEmailServlet.doPost: email sent...");
                loggerActions.info("User " + login + " has sent a message to user " + user.getLogin());
                messageKey = "send.email.success";
            } catch (SendEmailException e) {
                logger.error("SendEmailServlet.doPost: email NOT sent...");
                messageKey = "send.email.failure";
            }
            pageToShow = ServletConstants.SHOW_MESSAGE_PAGE;
            
            req.setAttribute(ServletConstants.NEXT_PAGE_KEY, nextPage);
            req.setAttribute(ServletConstants.PAGE_TO_SHOW_KEY, pageToShow);
            req.setAttribute(ServletConstants.SEND_EMAIL_MESSAGE_KEY, messageKey);
        }
        
        if (!errors.isEmpty()) {
            req.setAttribute(ServletConstants.ERRORS_KEY, errors);
            req.setAttribute(ServletConstants.USER_KEY, user);
        }

        dispatcher = context.getRequestDispatcher(nextPage);
        dispatcher.forward(req, res);
    }

}
