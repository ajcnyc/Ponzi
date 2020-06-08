import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Cohen
 */
public class Room {
	private Map<String,Room> directionMap_ = new HashMap<String,Room>();
	private String name_;
	private String initialDescription_ = null;
	private String longDescription_;
	private HashSet<Item> items_ = new HashSet<Item>(); 
	private int visits_ = 0;

	public Room () {

	}

	public Room ( String name ) {
		name_ = name;
		items_ = new HashSet<Item>();
		visits_ = 0;
	}

	public Room ( String name, String initialDescription ) {
		name_ = name;
		items_ = new HashSet<Item>();
		visits_ = 0;
		initialDescription_ = initialDescription;
	}

	public void incrementVisits () {
		visits_++;
	}

	public int getVisits () {
		return visits_;
	}

	/**
	 * @param directionMap
	 *          the directionMap to set
	 */
	public void setDirectionMap ( Map<String,Room> directionMap ) {
		directionMap_ = directionMap;
	}

	public void addDirection ( String direction, Room room ) {
		directionMap_.put(direction,room);
	}

	/**
	 * @param name
	 *          the name to set
	 */
	public void setName ( String name ) {
		name_ = name;
	}

	/**
	 * @param initialDescription
	 *          the longDescription to set
	 */
	public void setInitialDescription ( String initialDescription ) {
		initialDescription_ = initialDescription;
	}

	/**
	 * @param longDescription
	 *          the longDescription to set
	 */
	public void setLongDescription ( String longDescription ) {
		longDescription_ = longDescription;
	}

	/**
	 * @param items
	 *          the items to set
	 */
	public void setItems ( HashSet<Item> items ) {
		items_ = items;
	}

	public void addItem ( Item item ) {
		items_.add(item);
	}

	/**
	 * @return the directionMap
	 */
	public Map<String,Room> getDirectionMap () {
		return directionMap_;
	}

	/**
	 * @return the name
	 */
	public String getName () {
		return name_;
	}

	/**
	 * @return the shortDescription
	 */
	public String getInitialDescription () {
		return initialDescription_;
	}

	/**
	 * @return the longDescription
	 */
	public String getLongDescription () {
		return longDescription_;
	}

	/**
	 * @return the items
	 */
	public HashSet<Item> getItems () {
		return items_;
	}

}
