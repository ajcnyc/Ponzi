import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Cohen
 */
public class Player {
	private String name_;
	private int score_;
	private Bag briefcase_;
	private Room location_;
	private ArrayList<Room> visitedRooms_;

	public Player ( String name, int score, Bag briefcase, Room location ) {
		name_ = name;
		score_ = score;
		briefcase_ = briefcase;
		location_ = location;
		visitedRooms_ = new ArrayList<Room>();
	}

	public Room getLocation () {
		return location_;
	}

	public void move ( String direction ) {
		location_ = location_.getDirectionMap().get(direction);
	}

	public boolean take ( Item item ) {
		return briefcase_.add(item);
	}

	public void drop ( Item item ) {
		briefcase_.remove(item);
		location_.addItem(item);
	}

	public void look () {

	}

	public void look ( Item object ) {

	}

	public HashSet<Item> inventory () {
		return briefcase_.getItems();
	}

	public int getscore () {
		return score_;
	}

	public void incrementScore ( int increment ) {
		score_ = score_ + increment;
	}

	public void quit () {

	}

}
