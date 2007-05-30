package net.indrix.arara.servlets.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.model.UserModel;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.vo.User;

import org.apache.log4j.Logger;

@SuppressWarnings("serial")
public class ConfirmInactiveUsers extends HttpServlet {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    public void init() {
        logger.debug("Initializing ConfirmInactiveUsers...");
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        logger.debug("ConfirmInactiveUsers.doPost called...");

        RequestDispatcher dispatcher = null;
        ServletContext context = this.getServletContext();
        
        UserModel userModel = new UserModel();
        try {
            logger.debug("Retrieving users...");
            List<User> users = userModel.retrieveInactiveUsers();
            Iterator<User> it = users.iterator();
            String response = "";
            while (it.hasNext()){
                User user = it.next();
                logger.debug("Processing user " + user);
                response += "Processing user " + user + "\n";
                sendConfirmationEmailToUser(user);
            }
            
            PrintWriter w = res.getWriter();
            w.write(response);
            w.flush();
            w.close();
        } catch (DatabaseDownException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        dispatcher = context.getRequestDispatcher(ServletConstants.REGISTER_PAGE);
        dispatcher.forward(req, res);
    }

    private void sendConfirmationEmailToUser(User user) {
        logger.debug("UserModel.sendConfirmationEmailToUser: enviando email para usuário...");
        
        String link = "http://www.aves.brasil.nom.br/servlet/confirmRegistration?id=" + generateKey(user);
        
        EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle.getInstance();
        Locale l = new Locale(user.getLanguage());
        
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String fromText = bundle.getString("email.general.fromText", l);

        String subject = bundle.getString("email.newUserConfirmation.subject", l);
        try {
            String body = getPtMessage(user);
            body += "\n\n\n\n\n--------------------------------------------------\n\n\n\n";
            body += getEnMessage(user);
            
            // send password to user
            logger.debug("Enviando email de confirmação para usuário, com os dados:");
            logger.debug(server + " | " + fromAdd + " | " + subject);
            logger.debug(getConfirmationMessage(body, link));

            MailClass sender = new MailClass(server);
            logger.debug("Setting to...");
            sender.setToAddress(user.getEmail());
            logger.debug("Setting subject...");
            sender.setSubject(subject);
            logger.debug("Setting message...");
            sender.setMessageTextBody(getConfirmationMessage(body, link));
            logger.debug("Setting from...");
            sender.setFromAddress(fromAdd, fromText);
            logger.debug("Sending message...");
            sender.sendMessage(true);
            // true indicates to emailObject to send the message right now
        } catch (MessageFormatException e) {
            logger.error("exception -> MessageFormatException in sendEmail " + e);
        } catch (AddressException e) {
            logger.error("exception -> AddressException in sendEmail " + e);
        } catch (NoRecipientException e) {
            logger.error("exception -> NoRecipientException in sendEmail " + e);
        } catch (SendFailedException e) {
            logger.error("exception -> SendFailedException in sendEmail " + e);
        } catch (Exception e) {
            logger.error("exception -> in sendEmail " + e);
        }
    }

    /**
     * @param body
     * @return
     */
    private String getConfirmationMessage(String body, String link) {
        /*
        email.newUserConfirmation.hello=Olá _$$$_!
        email.newUserConfirmation.message=Foi efetuado o cadastro no site Aves do Brasil com seu email. 
        email.newUserConfirmation.link=Para confirmar o registro, clique <a href="http://www.aves.brasil.nom.br/servlets/confirmUser?id=_$$$_">aqui</a>.
        email.newUserConfirmation.thanks=\n\nObrigado pelo seu interesse no site Aves do Brasil.
        */
        
        String bodyFormatted = "";
        ArrayList <String>list = new ArrayList<String>();
        list.add(link);
        list.add(link);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("UserModel.getMessage : Exception", e);
        }
        return bodyFormatted;
    }

    /**
     * This method generates the key to be appended to the email sent to user
     * Algorithm is:
     * (33 + user.id + year)!(month+day)$-(77+user.id)$=user.login
     * @param user The user data
     * 
     * @return a key that will be sent do user inside the email body, as link
     */
    private String generateKey(User user) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(user.getRegisteredOn());
        
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        
        String key = (33 + user.getId() + year) + "!" + (month + day) + "$-" + (77+user.getId()) + "$=" + user.getLogin();
        logger.debug("UserModel.generateKey: key generated: " + key);
        return key;
    }

    private String getPtMessage(User user){
        String msg = "Olá " + user.getName() + "\n";
        msg += "Você está recebendo este email porque está cadastrado no site Aves do Brasil.\n";
        msg += "Alguns usuários reclamaram de estar recebendo emails sem ter se registrado no site.\n";
        msg += "O que pode ter acontecido nestes casos foi que alguém usou o email de outra pessoa, de maneira indevida.\n\n";
        
        msg += "Para resolver este problema, foi implementado no site o mecanismo de confirmação de registro.\n";
        msg += "Deste modo, novos usuários terão que confirmar o registro através do clique em um link que estará em email enviado, logo após o cadastro.\n\n";
        
        msg += "Como você já estava registrado, é preciso que confirme seu interesse pelo site clicando no link abaixo.\n";
        
        msg += "_$$$_ \n\n\n";
        
        msg += "Caso você não tenha interesse pelo site, basta desconsiderar este email.\n\n";        
        msg += "Atenciosamente,\nEquipe do site Aves do Brasil!";
        
        return msg;
    }

    private String getEnMessage(User user){
        String msg = "Hello " + user.getName() + "\n";
        msg += "You're receiving this email because you're registered in Aves do Brasil web site.\n";
        msg += "There have been some users complaning about receiving emails without permission.\n";
        msg += "Probably someone else has used the user's email without his/her permission.\n\n";
        
        msg += "In order to solve this situation, it has been implemented in the website the confirmation mechanism.\n";
        msg += "Therefore, new users have to confirm the registration by clicking in a link sent in email.\n\n";
        
        msg += "As your email address was already registered, you need to confirm your registration by clicking in the link below.\n";
        
        msg += "_$$$_ \n\n\n";
        
        msg += "If you don't want to stay registered in the Aves do Brasil website, please ignore this email.\n\n";        
        msg += "Best regards,\nAves do Brasil team.";
        
        return msg;
    }
}
