package net.indrix.arara.tools.crypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import net.indrix.arara.dao.DatabaseManager;

/**
 * <p>Title: worldcup</p>
 * <p>Description: Game e Bet</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Indrix.NET</p>
 * @author Jefferson Rodrigues and Rafael Cuba
 * @version 0.1
 */

public class Change {

  public Change() {

        VigenereCipherImpl c;
        VigenereCipherImplOld cOld;

      c = new VigenereCipherImpl();
      String key = "\\]^_`a|}~!\"%&'(";
      String msg = "\\]@l&x@ndr&_M&10_8r@g@.";
      c.setKey(key);
      cOld = new VigenereCipherImplOld();
      cOld.setKey(key);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatementUpdate = null;
        try {
            connection = DatabaseManager.getConnection();
            preparedStatement = connection.prepareStatement("select login,password from user where position > 20");

            ResultSet result = preparedStatement.executeQuery();
            preparedStatementUpdate = connection.prepareStatement("update user set password = ? where login = ?");
            while (result.next()){
               preparedStatement.setString(1,cOld.decrypt(result.getString(1)));
               preparedStatement.setString(2,c.encrypt(result.getString(1)));
               preparedStatement.executeUpdate();
            }

        }
        catch (Exception e){

        }
  }
}