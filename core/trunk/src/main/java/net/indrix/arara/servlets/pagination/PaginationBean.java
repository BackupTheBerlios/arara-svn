package net.indrix.arara.servlets.pagination;

public class PaginationBean {

    private int numberOfPages;
    private int currentPage;
    private int initialPageForIndex;
    private int finalPageForIndex;
    public int getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getFinalPageForIndex() {
        return finalPageForIndex;
    }
    public void setFinalPageForIndex(int finalPageForIndex) {
        this.finalPageForIndex = finalPageForIndex;
    }
    public int getInitialPageForIndex() {
        return initialPageForIndex;
    }
    public void setInitialPageForIndex(int initialPageForIndex) {
        this.initialPageForIndex = initialPageForIndex;
    }
    public int getNumberOfPages() {
        return numberOfPages;
    }
    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
    
    
    
}
