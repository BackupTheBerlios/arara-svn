package net.indrix.arara.servlets.photo;

@SuppressWarnings("serial")
public class SearchPhotosWithMoreCommentsOfWeekServlet extends SearchPhotosServlet {

    protected int getPaginationConstant() {
        return PAGINATION_FOR_MORE_COMMENTS_OF_WEEK;
    } 

    protected String getServletToCall() {
        return "/servlet/searchPhotosWithMoreCommentsOfWeek";
    }  
}