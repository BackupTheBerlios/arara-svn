/*
 * Created on 02/05/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.mains;

import java.sql.SQLException;

import net.indrix.dao.DatabaseDownException;
import net.indrix.dao.UserDAO;
import net.indrix.vo.User;

/**
 * @author Jefferson
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestConnection {

	public static void main(String[] args) {
		UserDAO userDao = new UserDAO();
		try {
			User user = userDao.retrieve("jeff");
			System.out.println("User = " + user);
		} catch (DatabaseDownException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
