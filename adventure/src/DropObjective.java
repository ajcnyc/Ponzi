
/**
 * @author Alex Cohen
 */
public class DropObjective extends Objective {
	private Item item_;
	private Room room_;
	private Player player_;
	private String message_;

	public DropObjective ( Item item, Room room, int points, Player player ) {
		item_ = item;
		room_ = room;
		super.setPoints(points);
		player_ = player;
	}

	public DropObjective ( Item item, Room room, int points, Player player,
	                       String message ) {
		item_ = item;
		room_ = room;
		super.setPoints(points);
		player_ = player;
		message_ = message;
	}

	public String getMessage () {
		return message_;
	}

	public void setMessage ( String message ) {
		message_ = message;
	}

	public Room getRoom () {
		return room_;
	}

	public Item getItem () {
		return item_;
	}

	public void updateComplete ( Player player, Room room ) {
		boolean itemContained = false;

		// System.out.println("hello update");
		for ( Item i : room.getItems() ) {
			// System.out.println("A "+i.getName());
			// System.out.println("b "+item_.getName());

			if ( i.getName().trim().equals(item_.getName().trim()) ) {
				itemContained = true;
				break;
			}
		}
		System.out.println("itemContained: " + itemContained);
		if ( itemContained == true && super.isComplete() == false ) {
			player.incrementScore(super.getPoints());
			super.setComplete(true);
		} else {
			super.setComplete(false);
			player.incrementScore(-1 * super.getPoints());
		}
	}
}
