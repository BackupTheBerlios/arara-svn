/*
 * Created on 23/08/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.photo;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.servlets.pagination.PaginationController;
import net.indrix.arara.servlets.pagination.PhotoByCommonNamePaginationController;
import net.indrix.arara.servlets.pagination.PhotoByFamilyPaginationController;
import net.indrix.arara.servlets.pagination.PhotoBySpeciePaginationController;
import net.indrix.arara.servlets.pagination.PhotoByUserPaginationController;
import net.indrix.arara.servlets.pagination.PhotoPaginationController;
import net.indrix.arara.utils.PropertiesManager;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AbstractSearchPhotosServlet extends HttpServlet {
	public static final int PAGINATION_FOR_ALL_PHOTOS = 0;

	public static final int PAGINATION_FOR_ALL_FAMILY = 1;

	public static final int PAGINATION_FOR_ALL_SPECIE = 2;

	public static final int PAGINATION_FOR_ALL_COMMON_NAME = 3;

	public static final int PAGINATION_FOR_ALL_USER = 4;

	protected static Logger logger = Logger.getLogger("net.indrix.aves");

	protected static Logger loggerActions = Logger
			.getLogger("net.indrix.actions");

	private static String PHOTOS_BY_PAGE_KEY = "photos.per.page";

	private static int PHOTOS_PER_PAGE;
	static {
		PHOTOS_PER_PAGE = Integer.parseInt(PropertiesManager
				.getProperty(PHOTOS_BY_PAGE_KEY));
	}

	protected PaginationController getPaginationController(HttpSession session,
			boolean identification, int target) {

		// the key is the key string plus the target
		String key = ServletConstants.PHOTO_PAGINATION_CONTROLLER_KEY + target
				+ identification;
		logger
				.debug("AbstractSearchPhotosServlet.getPaginationController: retrieving controller with key "
						+ key);
		PaginationController c = (PaginationController) session
				.getAttribute(key);
		if (c == null) {
			switch (target) {
			case PAGINATION_FOR_ALL_PHOTOS:
				c = new PhotoPaginationController(PHOTOS_PER_PAGE,
						identification);
				break;
			case PAGINATION_FOR_ALL_FAMILY:
				c = new PhotoByFamilyPaginationController(PHOTOS_PER_PAGE);
				break;
			case PAGINATION_FOR_ALL_SPECIE:
				c = new PhotoBySpeciePaginationController(PHOTOS_PER_PAGE);
				break;
			case PAGINATION_FOR_ALL_COMMON_NAME:
				c = new PhotoByCommonNamePaginationController(PHOTOS_PER_PAGE);
				break;
			case PAGINATION_FOR_ALL_USER:
				c = new PhotoByUserPaginationController(PHOTOS_PER_PAGE);
				break;
			}
			logger.debug("PaginationController just created");
		} else {
			logger.debug("PaginationController retrieved from session");
		}
		session.setAttribute(key, c);
		session.setAttribute(ServletConstants.PHOTO_PAGINATION_CONTROLLER_KEY,
				c);
		return c;
	}
}
