/*
 * Created on 10/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.vo;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class State {
    private int id;
    private String acronym;
    private String description;
    
	/**
	 * @return
	 */
	public String getAcronym() {
		return acronym;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param string
	 */
	public void setAcronym(String string) {
		acronym = string;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string) {
		description = string;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(id);
        buffer.append(" | ");
        buffer.append(acronym);
        buffer.append(" | ");
        buffer.append(description);
        buffer.append("]");
        return buffer.toString();
    }
}
