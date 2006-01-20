/*
 * Created on 16/01/2006
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package net.indrix.arara.vo;

/**
 * @author Jeff
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class City {

    private int id;
    private State state;
    private String name;
    
    
	/**
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}

    public String toString(){
        StringBuffer buffer = new StringBuffer();
        
        buffer.append(id);
        buffer.append(" - ");
        buffer.append(name);
        buffer.append(" - ");
        buffer.append(state.getAcronym());
        return buffer.toString();
    }
}
