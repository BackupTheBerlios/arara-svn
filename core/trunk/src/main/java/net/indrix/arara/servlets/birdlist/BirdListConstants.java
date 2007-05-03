package net.indrix.arara.servlets.birdlist;

public interface BirdListConstants {
    public static String BIRD_LIST_ID = "listId";

    public static String BEAN_KEY = "birdListBean";
    public static String USER_LISTS = "userLists";

    public static String NAME = "name";
    public static String LIST_TYPE = "type";
    public static String SELECTED_CITIES = "selectedCities";

    public static String CREATE_SUCCESS_PAGE = "/jsp/birdList/doCreatedSuccess.jsp";
    public static String CREATE_LIST_PAGE = "/jsp/birdList/doCreateBirdList.jsp";
    public static String EDIT_LIST_PAGE = "/jsp/birdList/doEditBirdList.jsp";
    public static String SHOW_LISTs_PAGE = "/jsp/birdList/doShowAllBirdLists.jsp";
    
    public static String CREATE_SERVLET = "/servlet/createBirdList";
    public static String EDIT_SERVLET = "/servlet/editBirdList";
    
}
