/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.servlets.sound;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.indrix.arara.dao.DatabaseDownException;
import net.indrix.arara.dao.SoundDAO;
import net.indrix.arara.servlets.ServletConstants;
import net.indrix.arara.vo.Sound;

import org.apache.log4j.Logger;

/**
 * @author Jeff
 * 
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GetSoundServlet extends HttpServlet {
	/**
	 * Logger object
	 */
	static Logger logger = Logger.getLogger("net.indrix.aves");

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		logger.debug("GetSoundServlet.doGet called...");
		HttpSession session = req.getSession();
		String specieId = req.getParameter("specieId");

		SoundDAO dao = new SoundDAO();
		try {
			Sound sound = dao
					.retrieveSoundForSpecie(Integer.parseInt(specieId));
			session.setAttribute(ServletConstants.SOUND_KEY, sound);
			logger.debug("Sound retrieved..." + sound);
			logger.debug("writing to outputStream...");
			res.setContentType("application/binary");
			InputStream input = sound.getSound().getSound();
			OutputStream output = res.getOutputStream();
			int size = sound.getSound().getFileSize();
			String s = "" + size;
			byte bytes[] = s.getBytes();
			// write the lenght of the string that contains the size of the
			// sound
			output.write(bytes.length);
			// write the size of the sound file
			output.write(bytes);
            byte buffer[] = new byte[512];   
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
            input.close();
            output.close();
		} catch (DatabaseDownException e) {
			logger.debug("DatabaseDownException.....", e);
		} catch (SQLException e) {
			logger.debug("SQLException.....", e);
		}
	}

}
