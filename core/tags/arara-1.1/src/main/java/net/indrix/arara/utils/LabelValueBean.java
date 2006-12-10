package net.indrix.arara.utils;

public class LabelValueBean {

	// ----------------------------------------------------------- Constructors

	/**
	 * Construct a new LabelValueBean with the specified values.
	 * 
	 * @param label
	 *            The label to be displayed to the user
	 * @param value
	 *            The value to be returned to the server
	 */
	public LabelValueBean(String label, String value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * Construct a new LabelValueBean with the specified values, as int.
	 * 
	 * @param label
	 *            The label to be displayed to the user
	 * @param value
	 *            The value to be returned to the server
	 */
	public LabelValueBean(String label, int value) {
		this.label = label;
		this.value = Integer.toString(value);
	}

	// ------------------------------------------------------------- Properties

	/**
	 * The label to be displayed to the user.
	 */
	protected String label = null;

	public String getLabel() {
		return (this.label);
	}

	/**
	 * The value to be returned to the server.
	 */
	protected String value = null;

	public String getValue() {
		return (this.value);
	}

	// --------------------------------------------------------- Public Methods

	/**
	 * Return a string representation of this object.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer("LabelValueBean[");
		sb.append(this.label);
		sb.append(", ");
		sb.append(this.value);
		sb.append("]");
		return (sb.toString());
	}

}
