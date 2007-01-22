package net.indrix.arara.model.email;

import org.apache.log4j.Logger;

public abstract class AbstractEmailSender implements Runnable {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

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
        logger.info("AbstractEmailSender.sendEmail: starting thread to send emails...");
        Thread t = new Thread(this);
        t.run();
    }    
}
