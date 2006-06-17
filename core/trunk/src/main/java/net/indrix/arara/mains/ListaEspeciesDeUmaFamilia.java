/*
 * Created on 06/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.FamilyDAO;
import net.indrix.arara.dao.SpecieDAO;
import net.indrix.arara.vo.Family;
import net.indrix.arara.vo.Specie;

/**
 * @author Jefferson_Angelica
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ListaEspeciesDeUmaFamilia {
	public static void main(String args[]) throws DatabaseDownException,
			SQLException {
		if (args.length == 0) {
			System.out.println("ERRO NOS PARAMETROS");
			System.exit(0);
		}
		final String familia = args[0];
		System.out.println("Data for " + familia);
		FamilyDAO fDao = new FamilyDAO();
		Family f = fDao.retrieve(familia);

		SpecieDAO sDao = new SpecieDAO();
		List l = sDao.retrieveForFamily(f);
		Iterator it = l.iterator();
		while (it.hasNext()) {
			Specie s = (Specie) it.next();
			System.out.println(s);
		}
	}
}
