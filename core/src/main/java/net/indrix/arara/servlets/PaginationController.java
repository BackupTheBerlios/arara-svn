/*
 * Created on 22/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.servlets;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PaginationController {
    private static Logger logger = Logger.getLogger("net.indrix.aves");

    private int currentIndex;
    private int photosPerPage;
    private List listOfPhotos;
    private List viewOfList;
    
    public PaginationController(int photosPerPage){
        this.photosPerPage = photosPerPage;
    }

    /**
     * This method performs the steps necessary to treat the action
     * 
     * @param action The action from user
     * 
     * @return a new list with the subset of photos
     */
    public List doAction(String action){
        return doAction(action, listOfPhotos);
    }    
    
    /**
     * This method performs the steps necessary to treat the action
     * 
     * @param action The action from user
     * 
     * @return a new list with the subset of photos
     */
    public List doAction(String action, List listOfPhotos){
        this.listOfPhotos = listOfPhotos;
        logger.debug("doAction called with action " + action);
        viewOfList = new ArrayList();
        if (action == null){
            action = "BEGIN";
            reset();
        }
        if (action.equals(ServletConstants.BEGIN)){
            reset();
        } else if (action.equals(ServletConstants.NEXT)) {
            next();
        } else if (action.equals(ServletConstants.PREVIOUS)) {
            previous();
        } else if (action.equals(ServletConstants.FIRST)) {
            reset();
        } else if (action.equals(ServletConstants.LAST)) {
            last();
        }
        
        int end = currentIndex + photosPerPage;
        int numPhotos = end - currentIndex;
        int size = listOfPhotos.size();
        logger.debug("CurrentIndex = " + currentIndex);
        logger.debug("End = " + end);
        logger.debug("List size = " + size);
        for (int i = currentIndex; (i < end) && (i < size); i++){
            logger.debug("Adding photo " + i);
            viewOfList.add(listOfPhotos.get(i));
        }
        return viewOfList;
    }

	/**
     * This method determine if there is a next page to be displayed
     * @return
     */
    public boolean hasNext(){
        boolean next = false;
        int size = listOfPhotos.size();
        if ((currentIndex + photosPerPage) < size){
            next = true;    
        }
        return next;
    }

    /**
     * This method increments the index
     */
    public void next(){
        currentIndex += photosPerPage;
    }
    
    /**
     * This method decrements the index
     */
    public void previous(){
        currentIndex -= photosPerPage;
    }

    /**
     * 
     */
    private void last() {
        int page = (listOfPhotos.size() / photosPerPage);
        logger.debug("Page = " + page);
        if ((page * photosPerPage) == listOfPhotos.size()){
            page--;
        } 
        currentIndex =  page * photosPerPage;
        
    }
    
    /**
     * This method determine if there is a previous page to be displayed
     * @return
     */
    public boolean hasPrevious(){
        boolean previous = false;
        int size = listOfPhotos.size();
        if (currentIndex > 0){
            previous  = true;    
        }
        return previous ;
    }

    /**
     * This method resets the controller, so the first page is the next one to be displayed
     */
    public void reset(){
        currentIndex = 0;
    }
    
	/**
	 * @return
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}

	/**
	 * @return
	 */
	public int getPhotosPerPage() {
		return photosPerPage;
	}

	/**
	 * @param i
	 */
	public void setCurrentIndex(int i) {
		currentIndex = i;
	}

	/**
	 * @param i
	 */
	public void setPhotosPerPage(int i) {
		photosPerPage = i;
	}

	/**
	 * @return
	 */
	public List getListOfPhotos() {
		return listOfPhotos;
	}

	/**
	 * @param list
	 */
	public void setListOfPhotos(List list) {
		listOfPhotos = list;
	}

}
