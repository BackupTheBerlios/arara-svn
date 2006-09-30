/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.email;

import net.indrix.arara.vo.Sound;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractSoundEmailSender implements Runnable {
    /**
     * The sound added to database
     */
    protected Sound sound = null;

    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * Saves the given sound in the class attribute
     * 
     * @param sound
     *            The sound just added to the database
     */
    public AbstractSoundEmailSender(Sound sound) {
        this.sound = sound;
    }

    /**
     * This method sends an email to users about new sound
     * 
     * @param sound
     *            The new sound data
     */
    abstract public void run();

    /**
     * This method sends an email to users about new sound
     * 
     * @param sound
     *            The new sound data
     */
    public void sendEmail() {
        logger
                .info("AbstractSoundEmailSender.sendEmail: starting thread to send emails...");
        Thread t = new Thread(this);
        t.run();
    }

}
