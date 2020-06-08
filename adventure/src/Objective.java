
/**
 * @author Alex Cohen
 */
public abstract class Objective {
	private boolean complete_ = false; // Is the objective complete or not
	private int points_; // The number of points a player get for completing the
	                     // objective

	public void setComplete ( boolean complete ) {
		complete_ = complete;
	}

	public boolean isComplete () {
		return complete_;
	}

	public void setPoints ( int points ) {
		points_ = points;
	}

	public int getPoints () {
		return points_;
	}
}
