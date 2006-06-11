/*
 * Created on 20/05/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.model.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.indrix.arara.utils.PropertiesManager;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class AbstractFileManager {
    /**
     * Logger object to be used by this class
     */
    protected static Logger logger = Logger.getLogger("net.indrix.aves");

    /**
     * The key to the string containing the path where the files will be stored
     */    
    private static final String FILES_PATH = "files.path";
    
    /**
     * The key to the string containing the link to access the files
     */    
    private static final String FILES_LINK = "files.link";

    /**
     * This method writes the file, given by input, to the filesystem
     * 
     * @param input The InputStream with the file contents
     */
    public void writeFile() throws FileNotFoundException{
        logger.debug("UploadSound.writeSoundToFilesystem: entering method...");
        String path = getRootPath() + File.separator + getFolder();
        
        File dir = new File(path);
        logger.debug("UploadSound.writeSoundToFilesystem: creating dir " + dir);
        dir.mkdirs();

        String filename = getFullFilename();
        logger.debug("UploadSound.writeSoundToFilesystem: creating file " + filename);
        FileOutputStream output = new FileOutputStream(new File(filename));
        InputStream input = getInputStream();
        byte buffer[] = new byte[512];
        try {
            logger.debug("UploadSound.writeSoundToFilesystem: writing file...");
            while (input.read(buffer) != -1){
                output.write(buffer);
            }
            output.close();
            input.close();
        } catch (IOException e) {
            logger.debug("UploadSound.writeSoundToFilesystem: IOException: ", e);
            throw new FileNotFoundException();          
        }
        logger.debug("UploadSound.writeSoundToFilesystem: finishing method...");
         
    }

    /**
     * This method retrieves the root path to all types of files
     * 
     * @return the root path to all types of files
     */
	protected String getRootPath() {
		return PropertiesManager.getProperty(FILES_PATH);
	}
  
    /**
     * This method retrieves the root link to sound files
     * 
     * @return the root link to all types of files
     */
    public static String getRootLink() {
        return PropertiesManager.getProperty(FILES_LINK);
    }
  
    /**
     * This method returns the folder that will contain the file
     * 
     * @return A string with the folder that will contain the file
     */
    public abstract String getFolder();

    /**
     * This method returns the filename of the file to be saved
     * 
     * @return A string with the filename of the file to be saved
     */
    public abstract String getFilename();

    /**
     * This method returns the input stream to the file to be saved
     * 
     * @return the input stream to the file to be saved
     */
    public abstract InputStream getInputStream();
    
    /**
     * This method retrieves the filename, with the relative path, to the file in the filesystem
     * 
     * @return the filename, with the relative path, to the file in the filesystem
     */
    public String getRelativePath() {
        return getFolder() + File.separator + getFilename();
    }

    /**
     * This method retrieves the filename, with the relative path, to the file in the filesystem
     * 
     * @return the filename, with the relative path, to the file in the filesystem
     */
    public String getFullFilename() {
        return getRootPath() + File.separator + getFolder() + File.separator + getFilename();
    }
}
