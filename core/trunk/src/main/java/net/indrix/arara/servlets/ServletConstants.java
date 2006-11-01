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
	public static final String ID = "id";

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

	public static final String ACTION = "action";

	public static final String BEGIN = "BEGIN";

	public static final String NEXT = "NEXT";

	public static final String PREVIOUS = "PREVIOUS";

	public static final String FIRST = "FIRST";

	public static final String LAST = "LAST";

	public static final String NEXT_RESOURCE_AFTER_LOGIN = "nextResource";

	// KEYS
	public static final String PHOTO_PAGINATION_CONTROLLER_KEY = "photoPaginationController";

	public static final String SOUND_PAGINATION_CONTROLLER_KEY = "soundPaginationController";

	public static final String AGE_KEY = "ageList";

	public static final String SEX_KEY = "sexList";

	public static final String STATES_KEY = "stateList";

	public static final String CITIES_KEY = "citiesList";

	public static final String IDENTIFICATION_KEY = "identification";

	public static final String VIEW_MODE_KEY = "viewMode";

	public static final String SOUND_KEY = "sound";

	public static final String USER_KEY = "user";

	public static final String ERRORS_KEY = "erros";

	public static final String MESSAGES_KEY = "messages";
    
    public static final String NEXT_PAGE_KEY = "nextPage";
    
    public static final String PAGE_TO_SHOW_KEY = "pageToShow";
    
    public static final String SERVLET_TO_CALL_KEY = "servletToCall";

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

	public static final String USERS_ON_LINE = "usersOnLine";

	// Pages
	public static final String INITIAL_PAGE = "/index.jsp";

    public static final String FRAME_PAGE = "/frame.jsp";

	public static final String REGISTER_PAGE = "/jsp/user/register.jsp";

	public static final String REGISTERED_PAGE = "/jsp/user/registered.jsp";

	public static final String UPDATE_PAGE = "/jsp/user/updateUser.jsp";

	public static final String UPDATED_PAGE = "/jsp/user/updatedUser.jsp";

	public static final String LOGIN_PAGE = "/jsp/user/login.jsp";

	public static final String SEND_PASSWORD_PAGE = "/jsp/user/forgotPassword.jsp";

	public static final String PASSWORD_SENT_PAGE = "/jsp/user/passwordSent.jsp";

	public static final String CHANGE_PASSWORD_PAGE = "/jsp/user/changePassword.jsp";

	public static final String PASSWORD_CHANGED_PAGE = "/jsp/user/passwordChanged.jsp";

	public static final String LOGOUT_PAGE = "/jsp/user/logout.jsp";

	public static final String DELETED_PAGE = "/jsp/photo/search/deleted.jsp";

	public static final String LIST_PAGE = "/jsp/user/listUsers.jsp";

	public static final String NON_LOGGED_PAGE = "/jsp/user/userNotLogged.jsp";

	public static final String ERROR_LOGIN = "/loginerrado.html";

	public static final String ERROR_LOGOUT = "/jsp/user/logoutError.jsp";

	public static final String DATABASE_ERROR_PAGE = "/databaseError.jsp";

	// PHOTOS
	public static final String UPLOAD_PAGE = "/jsp/photo/upload/uploadPhoto.jsp";

	public static final String UPLOAD_SUCCESS_PAGE = "/jsp/photo/upload/uploadSuccess.jsp";

	public static final String UPLOAD_IDENTIFICATION_PAGE = "/jsp/photo/upload/uploadPhotoIdentify.jsp";

	public static final String EDIT_PAGE = "/jsp/photo/upload/editPhoto.jsp";

    public static final String COMMENT_PAGE = "/jsp/photo/comment/commentPhoto.jsp";

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
	public static final String LOGIN_ALREADY_EXIST = "login.already.exists";

	public static final String PASSWORD_MISMATCH = "password.mismatch";

	public static final String FIELDS_REQUIRED = "fields.required";

	public static final String INVALID_PASSWORD = "invalid.password";

	public static final String INVALID_USER = "user.not.registered";

	public static final String USER_NOT_LOGGED = "user.not.logged";

	public static final String USER_NOT_FOUND = "user.not.found";

	public static final String SELECT_FAMILY_ERROR = "select.family.error";
    
    public static final String SELECT_VALUE_ERROR = "select.value.error";
    
	public static final String PASSWORD_FORMAT = "password.format.error";

	public static final String PHOTO_ALREADY_IDENTIFIED = "photo.already.identified";

	public static final String END_OF_IDENTIFICATION_MSG = "end.of.identification.msg";

	public static final String SESSION_EXPIRED_DURING_PHOTO_NAVIGATION = "session.expired.during.photo.navigation";

	public static final String DATABASE_ERROR = "database.error";
    
    public static final String CITY_COORDS = "city.coords";    
    
    public static final String CITY_NAME = "city.name";    
    
    public static final String CITY_INFO = "city.info";    

}
