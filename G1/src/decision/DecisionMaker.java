package decision;

import java.util.HashMap;

/**
 * An interface used to make a decision
 * @author Pagano Lucas
 */
public interface DecisionMaker {

	/**
	 * Make a decision
	 * @return  HashMap linking an UserID to a ChoiceID
	 */
	public HashMap<Double, Double> makeDecision();

}
