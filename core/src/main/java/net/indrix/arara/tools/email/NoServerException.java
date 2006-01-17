package net.indrix.tools.email;

/**
 * File: MessageFormatException.java
 * Description: Exception class throwed when there is there is no server
 * or server is invalid
 *
 * @author   Angelica D. Munhoz de O. e Silva
 * @version  1.0
 */

public class NoServerException extends NullPointerException
{

  String exception;

  public NoServerException()
  {
  }

  public NoServerException(String e)
  {
    super(e);
    exception = e;
  }
}

