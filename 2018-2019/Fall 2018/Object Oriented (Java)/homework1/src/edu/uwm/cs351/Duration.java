package edu.uwm.cs351;
// Consulted concepts with David Ruiz
// Got advice from David Ruiz

import java.util.Objects;

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
	
	// TODO: For all constants, have a line:
	// public static final Duration CONSTANT = new Duration(...);
	
	public static final Duration INSTANTANEOUS = new Duration(0L);
	public static final Duration MILLISECOND = new Duration(1L);
	public static final Duration SECOND = new Duration(1000L);
	public static final Duration MINUTE = new Duration(60000L);
	public static final Duration HOUR = new Duration(3600000L);
	public static final Duration DAY = new Duration(86400000L);
	public static final Duration YEAR = new Duration(31557600000L);
	
	
	// If you are overriding a method from a super class, always
	// annotate it "@Override" as here, overriding Object#equals(Object)
	@Override 
	public boolean equals(Object x) {
		if(!(x instanceof Duration)){
			return false;
		}
		Duration d = (Duration)x;
		
		return this.extent == d.extent;
	}
	
	@Override
	public int hashCode() {
		int code = Objects.hash(extent);
		return code; // TODO: Do something efficient.  Do NOT create a String.
	}
	
	// If you are overriding a method from an interface, then Java 5
	// says you CANNOT use Override, but anything later says you MAY.
	// Please always do unless you write a javadoc comment. 
	@Override
	public int compareTo(Duration other) {
		if(this.extent < other.extent) {
			return -1;
		}
		else if(this.extent == other.extent) {
			return 0;
		}
		else {
			return 1;
		}
		
		// TODO: Remember what compareTo is supposed to return.
		
	}
	
	@Override
	public String toString() {
		
		if(this.extent == 0) {
			return (double)this.extent + " ms."; //Instantaneous
		}
		else if(this.extent > 0 && this.extent < 1000) {
			return (double)this.extent + " ms."; //Milliseconds
		}
		else if(this.extent >= 1000 && this.extent < 60000) {
			return (double)this.extent/1000 + " s."; //Seconds
		}
		else if(this.extent >= 60000 && this.extent < 3600000) {
			return (double)this.extent / 60000 + " min."; //Minutes
		}
		else if(this.extent >= 3600000 && this.extent < 86400000) {
			return (double)this.extent / 3600000 + " hr.";//Hours
		}
		else if(this.extent >= 86400000 && this.extent < 31540000000L) {
			return (double)this.extent / 86400000 + " days";//Days
		}
		else if(this.extent >= 31557600000L) {
			return (double)this.extent / 31557600000.0 + " years";//years
		}
		
		return null; // TODO
	}
	
	// Methods that are not standard or private must have a documentation comment:
	
	/**
	 * Add two durations together to get a larger duration.
	 * @param d duration being added to this, must not be null
	 * @return new duration equal to the combined duration of this and the argument.
	 * @throws NullPointerException if d is null
	 */
	public Duration add(Duration d) {		
		if( d == null) {
			throw new NullPointerException("Duration cannot be null");
		}
		// not sure how to add durations
		//creating new Duration
		Duration Added = new Duration(this.extent + d.extent/*here should go the addition*/);
		
		return Added /* here should go "Added"*/; // TODO
	}
	
	// TODO: three other public methods: subtract, scale & divide
	// Don't forget to write documentation comments.

	
	/**
	 * Subtract two durations together to get a smaller duration.
	 * @param d duration being subtracted to this, must not be null
	 * @return that when added to the argument will be equal to this
	 * @throws ArithmeticException if d is greater than this
	 */
	public Duration subtract(Duration d){
		if(d.extent > this.extent) {
			throw new ArithmeticException("Arithmetic Error");
		}
		Duration Subtracted = new Duration(this.extent - d.extent);
		return Subtracted;
	}
	/**
	 * Multiply two durations together to get a larger duration.
	 * @param x double being multiplied to this, must not be null
	 * @return new duration that is the argument times bigger than this duratiom
	 */
	public Duration scale(double x) {
		
		if(this.extent == 1 && x >= 0.5 && x < 1) {
			return MILLISECOND;
		}
		else if(this.extent == 1 && x >= 0 && x < 0.5) {
			return INSTANTANEOUS;
		}
		else {	
			return new Duration((long)(x *this.extent));
		}
	}
	
	/**
	 * Divide two durations together to get a smaller duration.
	 * @param d duration being divided to this, must not be null
	 * @return the relative size of the one duration to another as a double (number)
	 * @throws ArithmeticException if d is equal to 0
	 */
	public double divide(Duration d) {
		if(d.extent == 0) {
			throw new ArithmeticException("Arithmetic Error");
		}
		
		return (double)this.extent / (double)d.extent;
	}
	
	
}
