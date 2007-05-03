package net.indrix.arara.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

public class Util {
    /**
     * Logger object
     */
    static Logger logger = Logger.getLogger("net.indrix.aves");

    public static void copyFile(File in, File out) throws IOException {
        logger.debug("Copying file from " + in.getAbsolutePath() + " to " + out.getAbsolutePath());
        
        int index = out.getAbsolutePath().lastIndexOf(File.separator);
        String outputPath = out.getAbsolutePath().substring(0, index);
        File outputPathAsFile = new File(outputPath);
        outputPathAsFile.mkdirs();
        
        FileChannel sourceChannel = new FileInputStream(in).getChannel();
        FileChannel destinationChannel = new FileOutputStream(out).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }
    
    public static void moveFile(File in, File out) throws IOException{
        copyFile(in, out);
        in.delete();
    }

    public static void deleteFile(File file) throws IOException{
        logger.debug("Deleting file " + file);
        file.delete();
    }
}
