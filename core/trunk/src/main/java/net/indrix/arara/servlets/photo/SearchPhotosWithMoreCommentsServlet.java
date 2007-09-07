package net.indrix.arara.servlets.photo;

@SuppressWarnings("serial")
public class SearchPhotosWithMoreCommentsServlet extends SearchPhotosServlet {
    protected int getPaginationConstant() {
        return PAGINATION_FOR_MORE_COMMENTS;
    } 

    protected String getServletToCall() {
        return "/servlet/searchPhotosWithMoreComments";
    }  
}
