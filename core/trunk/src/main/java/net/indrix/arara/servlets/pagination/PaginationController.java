/*
 * Created on 22/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.pagination;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.exceptions.InvalidControllerException;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class PaginationController {
	protected static Logger logger = Logger.getLogger("net.indrix.aves");

    private static final int PAGES_INTERVAL = 10;
    
    /**
     * Flag to control wheter the media is for identification or not
     */
    private boolean identification;
    
    /**
     * the current index media
     */
	protected int currentIndex;

    /**
     * The number of data per page
     */
	protected int dataPerPage;

    /**
     * The list of data (media) to be displayed
     */
	protected List listOfData;

    /**
     * The list that will handle the objects for just one page
     */
	protected List <Object>viewOfList;

    /**
     * The id, when necessary, to retrieve data from database
     */
    protected int id;

    /**
     * The page number, when necessary, to go
     */
    protected int pageToGo;
    
    /**
     * The text, when necessary, to retrieve data from database
     */
    protected String text;

    protected PaginationBean paginationBean; 
    
    /**
     * Creates a new PaginationController object, with the given number of elements per page, and
     * with the flag identification
     * 
     * @param dataPerPage The amount of data per page
     * @param identification The flag for identification
     */
	public PaginationController(int dataPerPage, boolean identification) {
		this.dataPerPage = dataPerPage;
        this.identification = identification;
        paginationBean = new PaginationBean();
	}

	/**
	 * This method performs the steps necessary to treat the action
	 * 
	 * @param action The action from user
	 * 
	 * @return a new list with the subset of photos
	 */
	public List doAction(String action) throws InvalidControllerException {
		logger.debug("PaginationController.doAction called with action " + action);

		viewOfList = new ArrayList<Object>();
		if (action == null || action.trim().length() == 0) {
			action = "BEGIN";
		}

		if (action.equals(ServletConstants.BEGIN)) {
			// if null, that means that the session has expired. Start search
			// again
			reset();

			try {
				listOfData = retrieveAllData();
			} catch (DatabaseDownException e) {
				logger.debug("PaginationController.doAction: DatabaseDownException when retrieving all data",e);
				viewOfList = null;
			} catch (SQLException e) {
				logger.debug("PaginationController.doAction: SQLException when retrieving all data",e);
				viewOfList = null;
			}
		} else if (action.equals(ServletConstants.NEXT)) {
			next();
		} else if (action.equals(ServletConstants.PREVIOUS)) {
			previous();
		} else if (action.equals(ServletConstants.FIRST)) {
			reset();
		} else if (action.equals(ServletConstants.LAST)) {
			last();
		} else if (action.equals(ServletConstants.GO_TO_PAGE)){
		    goToPage();
        }

		try {
			retrieveDataForPage();
		} catch (DatabaseDownException e) {
			logger.debug("PaginationController.doAction: DatabaseDownException when retrieving page data",e);
			viewOfList = null;
		} catch (SQLException e) {
			logger.debug("PaginationController.doAction: SQLException when retrieving page data",e);
			viewOfList = null;
		}
		return viewOfList;
	}

    /**
	 * Retrieve the data for the current page
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	abstract protected void retrieveDataForPage() throws DatabaseDownException,
			SQLException;

	/**
	 * Retrieve the all data to be paginated
	 * 
	 * @return A list with all data
	 * 
	 * @throws DatabaseDownException
	 *             If the database is down
	 * @throws SQLException
	 *             If some SQL Exception occurs
	 */
	abstract protected List retrieveAllData() throws DatabaseDownException,
			SQLException;

	/**
	 * This method determine if there is a next page to be displayed
	 * 
	 * @return
	 */
	public boolean hasNext() {
		boolean next = false;
		int size = listOfData.size();
		if ((currentIndex + dataPerPage) < size) {
			next = true;
		}
		return next;
	}

	/**
	 * This method moves to the next page
	 */
	public void next() throws InvalidControllerException {
		if (listOfData == null) {
			throw new InvalidControllerException();
		} else {
			currentIndex += dataPerPage;
		}
	}

	/**
	 * This method moves to the previous page
	 */
	public void previous() throws InvalidControllerException {
		if (listOfData == null) {
			throw new InvalidControllerException();
		} else {
			currentIndex -= dataPerPage;
		}
	}

    /**
     * This method moves to the last page
     */
	private void last() throws InvalidControllerException {
		if (listOfData == null) {
			throw new InvalidControllerException();
		} else {
			int page = (listOfData.size() / dataPerPage);
			logger.debug("Page = " + page);
			if ((page * dataPerPage) == listOfData.size()) {
				page--;
			}
			currentIndex = page * dataPerPage;
		}
	}

    /**
     * This method moves to the specified page
     *  
     * @param stringPage The page number, as String
     * 
     * @throws InvalidControllerException 
     */
    private void goToPage() throws InvalidControllerException {
        int currentPage = (currentIndex / dataPerPage) + 1;
        logger.debug("Going from " + currentPage + " to page " + pageToGo);
        if (pageToGo < currentPage){
            for (int i = 0; i < currentPage - pageToGo; i++){
                logger.debug("calling previous method...");
                previous();
            }
        } else {
            for (int i = 0; i < pageToGo - currentPage; i++){
                logger.debug("calling next method...");
                next();
            }           
        }
    }

	/**
	 * This method determine if there is a previous page to be displayed
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		boolean previous = false;
		if (currentIndex > 0) {
			previous = true;
		}
		return previous;
	}

	/**
	 * This method resets the controller, so the first page is the next one to
	 * be displayed
	 */
	public void reset() {
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
	public int getDataPerPage() {
		return dataPerPage;
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
	public void setDataPerPage(int i) {
		dataPerPage = i;
	}

	/**
	 * @return
	 */
	public List getListOfData() {
		return listOfData;
	}

	/**
	 * This method returns true if the list is already set, false otherwise
	 * 
	 * @return true if the list is already set, false otherwise
	 */
	public boolean isListSet() {
		return listOfData != null;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIdentification() {
        return identification;
    }

    public void setIdentification(boolean identification) {
        this.identification = identification;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    /**
     * Returns the amount of data retrieved
     * 
     * @return the amount of data retrieved
     */
    public PaginationBean getPaginationBean(){
        int numberOfPages = 0;
        if (listOfData != null){            
            if ((listOfData.size() % dataPerPage) == 0){
                numberOfPages = (listOfData.size() / dataPerPage);
            } else {
                numberOfPages = (listOfData.size() / dataPerPage)+1;
            }
            int currentPage = (currentIndex / dataPerPage) + 1;
            int initialPage = 0;
            if ((currentPage - (PAGES_INTERVAL/2)) > 0){
                initialPage = currentPage - (PAGES_INTERVAL/2);
            } else {
                initialPage = 1;
            }
            
            int finalPage = 0;
            if ((initialPage + PAGES_INTERVAL - 1) < numberOfPages){
                finalPage = initialPage + PAGES_INTERVAL - 1;
            } else {
                finalPage = numberOfPages;
            }
            
            paginationBean.setNumberOfPages(numberOfPages);
            paginationBean.setCurrentPage(currentPage);
            paginationBean.setInitialPageForIndex(initialPage);
            paginationBean.setFinalPageForIndex(finalPage);
        }
        return paginationBean;
    }

    public void setPageToGo(int pageToGo) {
        this.pageToGo = pageToGo;
    }
}
