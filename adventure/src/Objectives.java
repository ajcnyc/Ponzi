import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Cohen
 */
public class Objectives {
	private List<DropObjective> dropObjectives_;
	private List<VisitObjective> visitObjectives_;
	private List<GoalObjective> goalObjectives_;
	private int maxScore_;

	/**
	 * @param dropObjectives
	 * @param visitObjectives
	 * @param goalObjectives
	 */
	public Objectives () {
		dropObjectives_ = new ArrayList<DropObjective>();
		visitObjectives_ = new ArrayList<VisitObjective>();
		goalObjectives_ = new ArrayList<GoalObjective>();
	}

	private void initializeMaxScore () {
		if ( this.getDropObjectives() != null ) {
			for ( Objective o : this.getDropObjectives() ) {
				if ( o != null ) {
					maxScore_ = maxScore_ + o.getPoints();
				}
			}
		}
		if ( this.getVisitObjectives() != null ) {
			for ( Objective o : this.getVisitObjectives() ) {
				if ( o != null ) {
					maxScore_ = maxScore_ + o.getPoints();
				}
			}
		}
		if ( this.getGoalObjectives() != null ) {
			for ( Objective o : this.getGoalObjectives() ) {
				if ( o != null ) {
					maxScore_ = maxScore_ + o.getPoints();
				}
			}
		}
	}

	public int getMaxScore () {
		return maxScore_;
	}

	/**
	 * @return the dropObjectives
	 */
	public List<DropObjective> getDropObjectives () {
		return dropObjectives_;
	}

	/**
	 * @param dropObjectives
	 *          the dropObjectives to set
	 */
	public void setDropObjectives ( List<DropObjective> dropObjectives ) {
		dropObjectives_ = dropObjectives;
	}

	/**
	 * @return the visitObjectives
	 */
	public List<VisitObjective> getVisitObjectives () {
		return visitObjectives_;
	}

	/**
	 * @param visitObjectives
	 *          the visitObjectives to set
	 */
	public void setVisitObjectives ( List<VisitObjective> visitObjectives ) {
		visitObjectives_ = visitObjectives;
	}

	/**
	 * @return the goalObjectives
	 */
	public List<GoalObjective> getGoalObjectives () {
		return goalObjectives_;
	}

	/**
	 * @param goalObjectives
	 *          the goalObjectives to set
	 */
	public void setGoalObjectives ( List<GoalObjective> goalObjectives ) {
		goalObjectives_ = goalObjectives;
	}

}
