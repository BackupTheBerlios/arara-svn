package net.indrix.arara.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Util {
    public static void copyFile(File in, File out) throws IOException {
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
}
