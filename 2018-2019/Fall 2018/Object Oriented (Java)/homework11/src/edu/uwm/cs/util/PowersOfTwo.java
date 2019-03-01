package edu.uwm.cs.util;

public class PowersOfTwo {

	/**
	 * Return true if the parameter is a power of two.
	 * @param x integer
	 * @return whether x is a power of 2
	 */
	public static boolean contains(int x){
		if (x <= 0) return false;
		return (x & (x-1)) == 0;
	}
	
	/**
	 * Return the closest power of two bigger than the argument.
	 * @param x
	 * @return smallest power of two larger than the argument
	 */
	public static int next(int x) {
		if (x <= 0) return 1;
		int sh = x >> 1;
		while (sh != 0) {
			x |= sh;
			sh >>= 1;
		}
		return x + 1;
	}
}
