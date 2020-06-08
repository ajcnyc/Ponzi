import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Cohen
 */
public class Bag {
	private int capacity_;
	private HashSet items_;

	public Bag ( int capacity ) {
		capacity_ = capacity;
		items_ = new HashSet();
	}

	public boolean add ( Item item ) {
		if ( items_.size() - 1 < capacity_ ) {
			items_.add(item);
			return true;
		}
		return false;
	}

	public boolean remove ( Item item ) {
		if ( !items_.isEmpty() ) {
			items_.remove(item);
			return true;
		}
		return false;
	}

	public HashSet getItems () {
		return items_;
	}
}