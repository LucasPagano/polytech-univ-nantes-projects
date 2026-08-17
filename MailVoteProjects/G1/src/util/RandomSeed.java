package util;

import java.util.Random;

/**
 * Class used to create the random seeds for the run
 * @author nathan
 */
public class RandomSeed {
	private static Random random = new Random(42); 

	/**
	 * Generate a new seed
	 * @return a random long
	 */
	public static long generateNewSeed() {
		return random.nextLong();
	}	
}
