/*
 * Created on 06/02/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.mains;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import net.indrix.arara.dao.CommonNameDAO;
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
public class LoadData {
	private static final String PONTO_VIRGULA = ";";

	public static void loadData() throws DatabaseDownException, SQLException {
		FamilyDAO familyDao = new FamilyDAO();
		SpecieDAO specieDao = new SpecieDAO();
		CommonNameDAO commonNameDao = new CommonNameDAO();
		BufferedReader in = null;
		try {
			String filename = JOptionPane.showInputDialog(null, "file");
			in = new BufferedReader(new FileReader(filename));
			String line = null;
			while ((line = in.readLine()) != null) {
				String tokens[] = line.split(PONTO_VIRGULA);
				String specieName = tokens[0];
				String familyName = tokens[1];

				Family family = getFamily(familyName, familyDao);
				Specie specie = new Specie();
				specie.setFamily(family);
				specie.setName(specieName);

				specieDao.insert(specie);
				System.out.println("Inserting specie " + specie);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("Finished");
	}

	/**
	 * @param family
	 * @param familyDao
	 * @return
	 */
	private static Family getFamily(String family, FamilyDAO familyDao) {
		Family f = null;
		try {
			f = familyDao.retrieve(family);
			if (f == null) {
				// family not found... create it
				f = new Family();
				f.setName(family);
				familyDao.insert(f);
				System.out.println("Inserting family " + f);
			}
		} catch (DatabaseDownException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return f;
	}

	public static void main(String args[]) throws DatabaseDownException,
			SQLException {
		loadData();
		System.exit(1);
	}

}
