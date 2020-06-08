
/**
 * @author Alex Cohen
 */
public class Item {
	private String name_;
	private String description_;

	public Item ( String name ) {
		name_ = name;
	}

	/**
	 * @return the name
	 */
	public String getName () {
		return name_;
	}

	/**
	 * @return the description
	 */
	public String getDescription () {
		return description_;
	}

	public void setDescription ( String description ) {
		description_ = description;
	}

}
