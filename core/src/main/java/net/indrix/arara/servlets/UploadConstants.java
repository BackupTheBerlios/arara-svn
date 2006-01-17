/*
 * Created on 08/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UploadConstants {
	/**
	 * constants containing the names of the attributes submmited by the GUI.
	 */
	public static final String FILE_URL = "FileURL";
	public static final String FILE_NAME = "fileName";
	public static final String FILE_SIZE = "fileSize";
	public static final String FILE_ITEM = "fileItem";
 
    public static final String UPLOAD_PHOTO_BEAN = "uploadPhotoBean";
    public static final String UPLOAD_SOUND_BEAN = "uploadSoundBean";

    // Errors
    public static final String FILE_REQUIRED = "Arquivo � campo obrigat�rio.";
    public static final String INVALID_FILE = "Arquivo inv�lido.";
    public static final String INVALID_FILE_SIZE = "Tamanho de arquivo inv�lido. M�ximo permitido � ";

    // Messages
    public static final String UPLOAD_SUCCESS = "Arquivo enviado com sucesso";
}
