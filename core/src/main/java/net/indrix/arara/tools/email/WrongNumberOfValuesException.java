package net.indrix.tools.email;

/**
 * File:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Angelica Demarchi Munhoz de Oliveira e Silva
 * @version 1.0 
 */

public class WrongNumberOfValuesException extends Exception
{
    private String exception;

    public WrongNumberOfValuesException(String e)
    {
	super(e);
	exception = e;
    }
}
