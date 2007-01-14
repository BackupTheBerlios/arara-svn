package net.indrix.arara.servlets.pagination;

public class PaginationBean {

    private int numberOfPages;
    private int currentPage;
    
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    
    
}
