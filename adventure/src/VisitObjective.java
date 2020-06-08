
/**
 * @author Alex Cohen
 */
public class VisitObjective extends Objective {
	private Room room_;
	private Player player_;

	public VisitObjective ( Room room, int points, Player player ) {
		room_ = room;
		super.setPoints(points);
		player_ = player;
	}

	public void updateComplete ( Player player ) {
		if ( player.getLocation().getName().trim()
		    .equals(room_.getName().trim()) ) {}
		if ( super.isComplete() == false && (player.getLocation().getName().trim())
		    .equals(room_.getName().trim()) ) {
			super.setComplete(true);
			player.incrementScore(super.getPoints());
		}

	}
}
