package net.indrix.arara.servlets.birdlist;

public interface BirdListConstants {
    public static final String BIRD_LIST_ID = "listId";

    public static final String BEAN_KEY = "birdListBean";
    public static final String USER_LISTS = "userLists";

    public static final String NAME = "name";
    public static final String LIST_TYPE = "type";
    public static final String SELECTED_CITIES = "selectedCities";

    public static final String CREATE_LIST_PAGE = "/jsp/birdList/doCreateBirdList.jsp";
    public static final String CREATE_SUCCESS_PAGE = "/jsp/birdList/doCreatedSuccess.jsp";

    public static final String EDIT_LIST_PAGE = "/jsp/birdList/doEditBirdList.jsp";
    public static final String SHOW_LISTs_PAGE = "/jsp/birdList/doShowAllBirdLists.jsp";
    
    public static final String CREATE_SERVLET = "/servlet/createBirdList";
    public static final String EDIT_SERVLET = "/servlet/editBirdList";

    public static final String NAME_REQUIRED = "name.required.error";
    
}
