/*
 * Created on 12/10/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.mains;

import java.util.ArrayList;

import net.indrix.tools.email.MessageComposer;
import net.indrix.tools.email.WrongNumberOfValuesException;
import net.indrix.utils.PropertiesManager;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TesteMessageFormatter {

	public static void main(String[] args) {
        
        String body = PropertiesManager.getProperty("email.newPhoto.body");
        System.out.println("Body = " + body);
        String bodyFormatted = "";
        ArrayList list = new ArrayList();
        list.add("Familia");
        list.add("Especie");
        list.add("http://www.aves.brasil.nom.br/servlet/showOnePhoto?photoId=" + 123);

        try {
            bodyFormatted = MessageComposer.formatMessage(body, list);
            System.out.println(bodyFormatted);
        } catch (WrongNumberOfValuesException e) {
            System.out.println("Exception");
        }
        System.exit(1);        
	}
}
