package util;

import java.util.Random;

/**
 * Class to generate new id
 * @author nathan
 */
public class RandomId {
	
	private Random random;;

	public RandomId(long seed) {
		super();
		this.random = new Random(seed);
	}

	/**
	 * Generate a new id on 64 bits
	 * @return a random double
	 */
	public double generateNewId() {
		return random.nextDouble();
	}	
}
