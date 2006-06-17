package net.indrix.arara.mains;

import java.io.InputStream;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SoundDAO;
import net.indrix.arara.vo.Sound;

/*
 * Created on 11/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TestSound {

	public static void main(String[] args) throws NumberFormatException,
			DatabaseDownException, SQLException {
		String specieId = JOptionPane.showInputDialog(null, "Specie Id");
		SoundDAO dao = new SoundDAO();
		Sound sound = dao.retrieveSoundForSpecie(Integer.parseInt(specieId));
		System.out.println("Sound retrieved..." + sound);
		InputStream input = sound.getSound().getSound();

		try {
			Player player = new Player(input);
			player.play();
		} catch (JavaLayerException e) {
			e.printStackTrace();
		}

	}
}
