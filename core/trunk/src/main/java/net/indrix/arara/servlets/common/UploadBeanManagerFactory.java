package net.indrix.arara.servlets.common;

import javax.servlet.http.HttpServletRequest;

import net.indrix.arara.bean.BirdListBean;
import net.indrix.arara.bean.UploadPhotoBean;
import net.indrix.arara.bean.UploadSoundBean;
import net.indrix.arara.servlets.UploadConstants;
import net.indrix.arara.servlets.birdlist.BirdListConstants;

import org.apache.log4j.Logger;

public class UploadBeanManagerFactory {
    public static final String PHOTO = "PHOTO";
    public static final String PHOTO_FOR_IDENTIFICATION = "PHOTO_FOR_IDENTIFICATION";
    public static final String SOUND = "SOUND";
    public static final String BIRDLIST = "BIRDLIST";
    
    private static UploadBeanManagerFactory instance;
    
    private UploadBeanManagerFactory(){
        
    }

    public synchronized static UploadBeanManagerFactory getInstance(){
        if (instance == null){
            instance = new UploadBeanManagerFactory();
        }
        return instance;
    }
    
    /**
     * Logger object to be used by this servlet to log statements
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    public IBeanManager createBean(String source, String action, HttpServletRequest req){
        IBean bean = null;
        IBeanManager manager = null;
        
        String beanKey = null;

        if (PHOTO.equals(source)) {
            if (action == null || UploadConstants.UPLOAD_ACTION.equals(action)) {
                beanKey = UploadConstants.UPLOAD_PHOTO_BEAN;
            } else if (UploadConstants.EDIT_ACTION.equals(action)) {
                beanKey = UploadConstants.UPLOAD_PHOTO_BEAN;
            }
            logger.debug("creating an UploadPhotoBean...");
            bean = new UploadPhotoBean();

            logger.debug("creating an PhotoBeanManager...");
            manager = new PhotoBeanManager();
        } else if (PHOTO_FOR_IDENTIFICATION.equals(source)) {
            beanKey = UploadConstants.UPLOAD_PHOTO_BEAN;

            logger.debug("creating an UploadPhotoBean...");
            bean = new UploadPhotoBean();

            logger.debug("creating an PhotoForIdentificationBeanManager...");
            manager = new PhotoForIdentificationBeanManager();
        } else if (SOUND.equals(source)) {
            if (UploadConstants.UPLOAD_ACTION.equals(action)) {
                logger.debug("uploading sound");
                beanKey = UploadConstants.UPLOAD_SOUND_BEAN;
            }

            logger.debug("creating an UploadSoundBean...");
            bean = new UploadSoundBean();

            logger.debug("creating an SoundBeanManager...");
            manager = new SoundBeanManager();
        } else if (BIRDLIST.equals(source)) {
            beanKey = BirdListConstants.BEAN_KEY;

            logger.debug("creating an BirdListBean...");
            bean = new BirdListBean();

            logger.debug("creating an BirdListBeanManager...");
            manager = new BirdListBeanManager();
        }       
        if (manager != null){
            logger.debug("setting bean in manager...");
            manager.setBean(bean);           
        }
        
        req.setAttribute(beanKey, bean);

        return manager;
    }
}
