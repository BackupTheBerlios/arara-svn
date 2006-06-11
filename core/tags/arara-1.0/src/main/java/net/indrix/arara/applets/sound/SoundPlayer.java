/*
 * Created on 11/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.applets.sound;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.PlayerApplet;

import com.oreilly.servlet.HttpMessage;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoundPlayer extends PlayerApplet {
	public static final String SPECIE_ID_PARAMETER = "specieId";
    byte bytes[];
	private int specieId;
	ByteArrayInputStream buffer;
	private AudioDevice device = null;

	public void init() {
		// retrieve specieId
		String specieId = getParameter(SPECIE_ID_PARAMETER);
		this.specieId = Integer.parseInt(specieId);

        // reads the sound from servlet, and keep it as a byte array in memory.
		retrieveSoundFromServlet();

		Button play = new Button("Tocar");
		play.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					play(getInputStream(), getAudioDevice());
				} catch (JavaLayerException e1) {
					e1.printStackTrace();
				}
			}
		});
        Button stop = new Button("Parar");
        stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        
        this.setBackground(new Color(166, 210, 210));
        
        Panel buttons = new Panel();
        buttons.setLayout(new FlowLayout());
        buttons.add(play);
        buttons.add(stop);

        this.setLayout(new BorderLayout());
        this.add(buttons);

	}

    /**
     * Creates and returns a new ByteArrayInputStream
     * 
     * @return a new ByteArrayInputStream
     */
	private InputStream getInputStream() {
		return new ByteArrayInputStream(bytes);

	}

	public void start() {
	}

	private void retrieveSoundFromServlet() {
		try {
			// Construct a URL referring to the servlet
			URL url =
				new URL(
					getCodeBase(),
					"/servlet/getSound?specieId=" + specieId);
			// Create a com.oreilly.servlet.HttpMessage to communicate with that URL
			HttpMessage msg = new HttpMessage(url);
			// Send a GET message to the servlet, with no query string
			// Get the response as an InputStream
			InputStream inputStream = msg.sendGetMessage();
			if (inputStream == null) {
				System.out.println("input null...");
			}
            // read the lenght of the string that contains the size of the sound
			byte size = (byte) inputStream.read();
            
            // read the bytes of the string that represents the size of the sound
			byte string[] = new byte[size];
			inputStream.read(string, 0, size);
			String s = new String(string);
			int lenght = Integer.parseInt(s);

            // read the bytes
			bytes = new byte[lenght];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
				&& (numRead = inputStream.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
		} catch (Exception e) {
			// If there was a problem, print to System.out
			// (typically the Java console) and return null
			e.printStackTrace();
		}
	}

}
