package net.indrix.arara.model.email;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.mail.SendFailedException;
import javax.mail.internet.AddressException;

import net.indrix.arara.EmailResourceBundle;
import net.indrix.arara.dao.CommentsDAO;
import net.indrix.arara.model.file.AbstractFileManager;
import net.indrix.arara.tools.email.MailClass;
import net.indrix.arara.tools.email.MessageComposer;
import net.indrix.arara.tools.email.MessageFormatException;
import net.indrix.arara.tools.email.NoRecipientException;
import net.indrix.arara.tools.email.WrongNumberOfValuesException;
import net.indrix.arara.utils.PropertiesManager;
import net.indrix.arara.utils.jsp.Thumbnail;
import net.indrix.arara.vo.Comment;
import net.indrix.arara.vo.Photo;
import net.indrix.arara.vo.User;

public class CommentEmailSender extends AbstractPhotoEmailSender {

    private Comment comment;
    private User user;

    public CommentEmailSender(Comment c, Photo photo, User user) {
        super(photo);
        this.comment = c;
        this.user = user;
    }

    @Override
    public void run() {
        notifyPhotoAuthor();
    }

    /**
     * This method notifies the photo author about a new comment added to
     * his/her photo
     * 
     */
    private void notifyPhotoAuthor() {
        String server = PropertiesManager.getProperty("email.server");
        String fromAdd = PropertiesManager.getProperty("email.from");
        String subject = null;
        String body = null;
        String fromText = null;
        try {
            logger.debug("Sending email...");

            // retrieve addresses to send photo
            CommentsDAO dao = new CommentsDAO();
            List<User> l = dao.retrieveUsersWithCommentsForPhoto(photo.getId(), user.getId());
//            Iterator dIt = l.iterator();
//            while (dIt.hasNext()) {
//                User u = (User) dIt.next();
//                logger.debug("User = " + u);
//            }

            User photoUser = photo.getUser(); 
            if (photoUser != null && !l.contains(photoUser) && !photoUser.equals(user)) {
                logger.debug("Adding user to list = " + photo.getUser());
                l.add(photo.getUser());
            }

//            dIt = l.iterator();            
//            while (dIt.hasNext()) {
//                User u = (User) dIt.next();
//                logger.debug("User = " + u);
//            }
            
            Iterator it = l.iterator();
            while (it.hasNext()) {
                User u = (User) it.next();

                Locale locale = new Locale(u.getLanguage());
                EmailResourceBundle bundle = (EmailResourceBundle) EmailResourceBundle.getInstance();

                subject = bundle.getString("email.newComment.subject", locale);
                body = bundle.getString("email.newComment.body", locale);
                fromText = bundle.getString("email.newComment.fromText", locale);

                MailClass sender = new MailClass(server);
                sender.setMessageTextHTML(getMessage(u, body, comment, photo));
                sender.setSubject(subject);
                sender.setFromAddress(fromAdd, fromText);

                sender.setToAddress(u.getEmail());

                logger.debug("Sending email " + subject + " to " + u.getLogin() + "-" + u.getEmail());
                sender.sendMessage(false);
                
                // false indicates to emailObject to not send the message right now
            }
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
     * Format the message to be sent to the photo author about a new
     * identification that has been done to his/her photo
     * 
     * @param user
     *            The photo author
     * @param body
     *            The body text template to be formatted
     * @param comment
     *            The comment added to the photo
     * @param photo
     *            The photo identification
     * 
     * @return A formatted message to be sent to the photo author about a new
     *         identification that has been done to his/her photo
     */
    private String getMessage(User user, String body, Comment comment, Photo photo) {
        /*
         * 
         * Oi _$$$_!<br>
         * Um novo comentário foi adicionado a uma foto!<br><br>
         * 
         * Autor do comentário:_$$$_<br>
         * Comentário:_$$$_<br>
         * Autor da foto:_$$$_<br><br>
         * 
         * Veja a foto <a href=_$$$_>aqui</a> <br><br> 
         * <img src=\"_$$$_\" width=\"_$$$_\" height=\"_$$$_"\" align=\"bottom\" border=\"0\"/>
         * 
         * 
         */
        
        String bodyFormatted = "";
        ArrayList<String> list = new ArrayList<String>();
        String url = "http://www.aves.brasil.nom.br/servlet/showOnePhoto?photoId=" + photo.getId();
        
        String link = AbstractFileManager.getRootLink();        
        link += photo.getThumbnailRelativePathAsLink();

        int w = Thumbnail.getWidth(PHOTO_WIDTH, photo.getSmallImage().getWidth(), photo.getSmallImage().getHeight());
        int h = Thumbnail.getHeight(PHOTO_WIDTH, photo.getSmallImage().getWidth(), photo.getSmallImage().getHeight());
        
        list.add(user.getName());
        list.add(comment.getUser().getName());
        list.add(comment.getComment());
        list.add(photo.getUser().getLogin() + " | " + photo.getUser().getName());
        list.add(url);
        list.add(link);
        list.add(""+w);
        list.add(""+h);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
        } catch (WrongNumberOfValuesException e) {
            logger.error("Exception", e);
        }
        return bodyFormatted;
    }
    
}
