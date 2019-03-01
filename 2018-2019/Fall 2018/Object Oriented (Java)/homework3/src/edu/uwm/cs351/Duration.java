package edu.uwm.cs351;


/**
 * An amount of time, always positive.
 * To create a duration, scale an existing duration.
 */
public class Duration implements Comparable<Duration> {
	private final long extent; // this must remain private.  Do NOT add a getter!

	// this constructor must remain private:
	private Duration(long milliseconds) {
		extent = milliseconds;
	}

	public static final Duration INSTANTANEOUS = new Duration(0);
	public static final Duration MILLISECOND = new Duration(1);
	public static final Duration SECOND = new Duration(1000);
	public static final Duration MINUTE = new Duration(SECOND.extent * 60);
	public static final Duration HOUR = new Duration(SECOND.extent*3600);
	public static final Duration DAY = new Duration(HOUR.extent * 24);
	public static final Duration YEAR = new Duration(DAY.extent * 365 + HOUR.extent * 6);

	@Override 
	public boolean equals(Object x) {
		if (!(x instanceof Duration)) return false;
		Duration other = (Duration)x;
		return extent == other.extent;
	}

	@Override
	public int hashCode() {
		return new Long(extent).hashCode();
	}

	@Override
	public int compareTo(Duration other) {
		if (extent == other.extent) return 0;
		if (extent < other.extent) return -1;
		else return 1;
	}

	@Override
	public String toString() {
		if (extent < SECOND.extent) return extent + ".0 ms.";
		if (extent < MINUTE.extent) return divide(SECOND) + " s.";
		if (extent < HOUR.extent) return divide(MINUTE) + " min.";
		if (extent < DAY.extent) return divide(HOUR) + " hr.";
		if (extent < YEAR.extent) return divide(DAY) + " days";
		return (((double)extent) / YEAR.extent) + " years";
	}

	// Methods that are not standard or private must have a documentation comment:

	/**
	 * Add two durations together to get a larger duration.
	 * @param d duration being added to this, must not be null
	 * @return new duration equal to the combined duration of this and the argument.
	 * @throws NullPointerException if d is null
	 */
	public Duration add(Duration d) {
		return new Duration(d.extent + extent);
	}

	/**
	 * Return the duration remaining if the argument duration
	 * 'is removed.  The argument must not be longer than this.
	 * @param d duration to remove, must not be null, must not be bigger than this
	 * @return remaining duration after subtracting d
	 * @throws NullPointerException if d is null
	 * @throws ArithmeticException if d is too big
	 */
	public Duration subtract(Duration d) {
		if (d.extent > extent) throw new ArithmeticException("cannot subtract a larger duration");
		return new Duration(extent - d.extent);
	}

	/**
	 * Return the duration by scaling this duration by the given amount.
	 * The result is rounded.
	 * @param d amount to scale by, must not be negative.
	 * @return new scaled duration
	 * @throws IllegalArumentException if scale factor is negative.
	 */
	public Duration scale(double d) {
		if (d < 0) throw new IllegalArgumentException("cannot scale negatively");
		return new Duration((long)Math.round(extent * d));
	}

	/**
	 * Return the scale of this duration against the argument.
	 * @param d duration to divide by, must not be null or instantaneous
	 * @return how many of the argument fit in this duration
	 * @throws NullPointerException if d is null
	 * @throws ArithmeticException if d is instantaneous
	 */
	public double divide(Duration d) {
		if (d.extent == 0) throw new ArithmeticException("divide by zero");
		return ((double)extent)/d.extent;
	}

	/**
	 * Takes a String and turns it into a Duration
	 * @param String s
	 * @return Duration
	 * @exception NullPointerException, NumberFormatException
	 */
	public static Duration fromString(String s) {
		Duration current = null;
		
		if(s.equals(null)) {
			throw new NullPointerException("BRUHHHHH I SAID NO NULL STRINGS");
		}
		
		int space = s.indexOf(" ");
		int strCount = s.length();
		
		if(space < 0 || strCount < 3) throw new NumberFormatException("BRUHHHH THERES NO SPACE");
		
		double num = Double.parseDouble(s.substring(0, space));
		if(num < 0) throw new NumberFormatException("BRUHHHH NO NEGATIVE NUMBERS");
		
		String unit = s.substring(space, strCount);
		
		if(unit.compareTo(" s.") == 0) current = Duration.SECOND.scale(num);
		if(unit.compareTo(" min.") == 0) current = Duration.MINUTE.scale(num);
		if(unit.compareTo(" hr.") == 0) current = Duration.HOUR.scale(num);	
		if(unit.compareTo(" days") == 0) current = Duration.DAY.scale(num);
		if(unit.compareTo(" years") == 0) current = Duration.DAY.scale(num);
		if(unit.compareTo(" ms.") == 0) current = Duration.MILLISECOND.scale(num);
		
		return current;
	}
}
