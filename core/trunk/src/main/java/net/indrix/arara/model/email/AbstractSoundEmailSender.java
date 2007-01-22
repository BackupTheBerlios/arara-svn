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
public abstract class AbstractSoundEmailSender extends AbstractEmailSender {
    /**
     * The sound added to database
     */
    protected Sound sound = null;

    /**
     * Saves the given sound in the class attribute
     * 
     * @param sound
     *            The sound just added to the database
     */
    public AbstractSoundEmailSender(Sound sound) {
        this.sound = sound;
    }
}
