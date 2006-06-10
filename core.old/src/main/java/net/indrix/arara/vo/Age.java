/*
 * Created on 08/12/2005
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
public class Age {
    private int id;
    private String age;
    
    public Age(){
        id = -1;
    }
    
	/**
	 * @return
	 */
	public String getAge() {
		return age;
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
	public void setAge(String string) {
		age = string;
	}

	/**
	 * @param i
	 */
	public void setId(int i) {
		id = i;
	}

    public String toString(){
        return id + ", " + age;
    }
}
