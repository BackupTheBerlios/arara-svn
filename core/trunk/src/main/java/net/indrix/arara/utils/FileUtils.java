package net.indrix.arara.utils;

public class FileUtils {
    /**
     * This method verifies if the given file is valid
     * 
     * @param filename
     *            The file uploaded by user
     * 
     * @return true if file extension is valid, false otherwise
     */
    public static boolean checkExtension(String filename, String validExtensions[]) {
        boolean valid = false;
        if (filename != null){
            int index = filename.lastIndexOf(".");
            if (index > 0) {
                String extension = filename.substring(index + 1);

                for (int i = 0; i < (validExtensions.length) && (!valid); i++) {
                    if (validExtensions[i].equals(extension)) {
                        valid = true;
                    }
                }
            } else {
                valid = false;
            }
        }
        return valid;
    }
}
