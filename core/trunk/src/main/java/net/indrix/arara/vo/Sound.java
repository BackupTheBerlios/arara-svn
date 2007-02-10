/*
 * Created on 08/12/2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;


/**
 * @author Jefferson
 * 
 */
public class Sound extends Media {

	private SoundFile sound;

	public Sound() {
		sound = new SoundFile();
	}

	/**
	 * @return
	 */
	public SoundFile getSound() {
		return sound;
	}

	/**
	 * @param stream
	 */
	public void setSound(SoundFile s) {
		sound = s;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getId());
		buffer.append(",");

        Specie specie = getSpecie();
		if (specie != null) {
			buffer.append(specie.getId());
			buffer.append(",");
			buffer.append(specie.getName());
			buffer.append(",");

			if (specie.getFamily() != null) {
				buffer.append(specie.getFamily().getName());
				buffer.append(",");
			}

			buffer.append(specie.getCommonNamesString());
			buffer.append(",");
		}

        Age age = getAge();
		if (age != null) {
			buffer.append(age.getId() + " | " + age.getAge());
			buffer.append(",");
		}

        Sex sex = getSex();
		if (sex != null) {
			buffer.append(sex.getId() + " | " + sex.getSex());
			buffer.append(",");
		}

		buffer.append("]");

		return buffer.toString();
	}
}
