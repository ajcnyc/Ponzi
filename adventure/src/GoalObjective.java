
/**
 * @author Alex Cohen
 */
public class GoalObjective extends Objective {
	private Room room_;
	private Player player_;
	private String message_;

	public GoalObjective ( Room room, int points, Player player ) {
		room_ = room;
		super.setPoints(points);
		player_ = player;
	}

	public GoalObjective ( Room room, int points, Player player,
	                       String message ) {
		room_ = room;
		super.setPoints(points);
		player_ = player;
		message_ = message;
	}

	/**
	 * Checks if the given room is the goal room
	 * 
	 * @param currentRoom
	 *          the room to check
	 * @return true if the rooms match, false if they do not
	 */
	public boolean checkRoom ( Room currentRoom ) {
		if ( currentRoom.equals(room_) ) {
			return true;
		}
		return false;
	}

	public String getMessage () {
		return message_;
	}

	public void setMessage ( String message ) {
		message_ = message;
	}
}
