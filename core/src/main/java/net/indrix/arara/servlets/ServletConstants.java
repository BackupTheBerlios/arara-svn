/*
 * Created on 07/11/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets;

/**
 * @author Jefferson_Angelica
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ServletConstants {
	/**
     * constants containing the names of the attributes submmited by the GUI.
     */
    public static final String FAMILY_ID = "familyId";
    public static final String SPECIE_ID = "specieId";
    public static final String AGE_ID = "ageId";
    public static final String SEX_ID = "sexId";  
    public static final String CITY_ID = "cityId";
    public static final String STATE_ID = "stateId";
    public static final String USER_ID = "userId";
    public static final String COMMON_NAME_ID = "commonNameId";
    public static final String CAMERA = "camera";
    public static final String LENS = "lens";
    public static final String FILM = "film";
    public static final String LOCATION = "location";
    public static final String DATE = "date";
    public static final String COMMENT = "comment";
    public static final String PHOTO_ID = "photoId";

	public static final String SERVLET_TO_CALL = "servletToCall";
    
	public static final String ACTION = "action";
    public static final String BEGIN = "BEGIN";
    public static final String NEXT = "NEXT";
    public static final String PREVIOUS = "PREVIOUS";
    public static final String FIRST = "FIRST";
    public static final String LAST = "LAST";
    
    
    public static final String PAGINATION_CONTROLLER_KEY = "paginationController";
    
    // KEYS
    public static final String AGE_KEY = "ageList";
    public static final String SEX_KEY = "sexList";
    public static final String STATES_KEY = "stateList";
    public static final String CITIES_KEY = "citiesList";

    public static final String IDENTIFICATION_KEY = "identification";
    
    public static final String SOUND_KEY = "sound";
    
	public static final String USER_KEY = "user";
	public static final String ERRORS_KEY = "erros";
    public static final String MESSAGES_KEY = "messages";
    
	public static final String FAMILY_LIST_KEY = "familyList";
	public static final String SPECIE_LIST_KEY = "specieList";
    public static final String COMMON_NAME_LIST_KEY = "commonNameList";
    public static final String USER_LIST_KEY = "userList";
    
    public static final String SELECTED_FAMILY_KEY = "selectedFamilyId";
    public static final String SELECTED_SPECIE_KEY = "selectedSpecieId";

	public static final String USERS_LIST = "listaUsuarios";
	public static final String PHOTOS_LIST = "listOfPhotos";
    public static final String SOUNDS_LIST = "listOfSounds";
    public static final String CURRENT_PHOTO = "currentPhoto";
    
    // Pages
    public static final String INITIAL_PAGE = "/index.jsp";
	public static final String REGISTER_PAGE = "/jsp/user/register.jsp";
	public static final String REGISTERED_PAGE = "/jsp/user/registered.jsp";
	public static final String LOGIN_PAGE = "/jsp/user/login.jsp";
    public static final String CHANGE_PASSWORD_PAGE = "/jsp/user/changePassword.jsp";
    public static final String PASSWORD_CHANGED_PAGE = "/jsp/user/passwordChanged.jsp";
	public static final String LOGOUT_PAGE = "/jsp/user/logout.jsp";
    public static final String DELETED_PAGE = "/jsp/photo/search/deleted.jsp";
	public static final String LIST_PAGE = "/jsp/user/listUsers.jsp";
	public static final String NON_LOGGED_PAGE = "/jsp/user/userNotLogged.jsp";
	public static final String ERROR_LOGIN = "/loginerrado.html";
	public static final String ERROR_LOGOUT = "/jsp/user/logoutError.jsp";
	public static final String DATABASE_ERROR_PAGE ="/databaseError.jsp";

    // PHOTOS
    public static final String UPLOAD_PAGE = "/jsp/photo/upload/uploadPhoto.jsp";
    public static final String UPLOAD_SUCCESS_PAGE = "/jsp/photo/upload/uploadSuccess.jsp";
    public static final String UPLOAD_IDENTIFICATION_PAGE = "/jsp/photo/upload/uploadPhotoIdentify.jsp";
    public static final String UPLOAD_IDENTIFICATION_SUCCESS_PAGE = "/jsp/photo/upload/uploadIdentifySuccess.jsp";
    public static final String EDIT_PAGE = "/jsp/photo/upload/editPhoto.jsp";
    public static final String EDIT_SUCCESS_PAGE = "/jsp/photo/upload/editSuccess.jsp";

    public static final String ALL_PHOTOS_PAGE = "/jsp/photo/search/showAllPhotos.jsp";
    public static final String PHOTO_BY_FAMILY_PAGE = "/jsp/photo/search/showPhotosByFamily.jsp";
    public static final String PHOTO_BY_SPECIE_PAGE = "/jsp/photo/search/showPhotosBySpecie.jsp";
    public static final String PHOTO_BY_COMMON_NAME_PAGE = "/jsp/photo/search/showPhotosByCommonName.jsp";
    public static final String PHOTO_BY_USER_PAGE = "/jsp/photo/search/showPhotosByUser.jsp";
    public static final String ONE_PHOTO_PAGE = "/jsp/photo/search/showOnePhoto.jsp";
    public static final String ONE_PHOTO_PAGE_ERROR = "/jsp/photo/search/showOnePhotoError.jsp";

    // SOUNDS
    public static final String ALL_SOUNDS_PAGE = "/jsp/sound/display/showAllSounds.jsp";
    public static final String UPLOAD_SOUND_PAGE = "/jsp/sound/upload/uploadSound.jsp";
    public static final String UPLOAD_SOUND_SUCCESS_PAGE = "/jsp/sound/upload/uploadSoundSuccess.jsp";
    
    // Messages
	public static final String LOGIN_ALREADY_EXIST = "Login já existe. Escolha outro";
	public static final String PASSWORD_MISMATCH = "A segunda senha não bate com a primeira";
	public static final String FIELDS_REQUIRED = "Todos os campos são obrigatórios";
	public static final String DATABASE_ERROR = "Erro no banco de dados";
	public static final String INVALID_PASSWORD = "Senha inválida.";
	public static final String INVALID_USER = "User não registrado.";
    public static final String USER_NOT_LOGGED = "Sua sessão expirou ou você não está logado ainda.";
    public static final String SELECT_FAMILY_ERROR = "Selecione uma família.";
	
}
