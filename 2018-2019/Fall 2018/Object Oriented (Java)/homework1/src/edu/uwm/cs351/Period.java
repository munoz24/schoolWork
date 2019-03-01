package edu.uwm.cs351;

import java.util.Objects;
// went to https://stackoverflow.com/questions/11597386/objects-hash-vs-objects-hashcode-clarification-needed
// read about Objects.hash() and saw i needed to import 
// java.util.Objects


/**
 * A time period within historical time.
 */
public class Period {
	// TODO: Choose fields
	private final Duration length;
	private final Time start;
	private final Time stop;
	
	/**
	 * Construct a period given the start time and length.
	 * @param s start time, must not be null
	 * @param l length, must not be null
	 */
	public Period(Time s, Duration l) {
		// TODO
		
		length = l;
		start = s;
		stop = s.add(l);
		
	}
	
	/**
	 * Construct a period given the start and end time
	 * @param from start time, must not be null
	 * @param to end time, must not be null or before the start time
	 */
	public Period(Time from, Time to) {
		// TODO
		start = from;
		stop = to;
		length = to.difference(from);
		
	}
	
	/**
	 * Construct a period given the length and end time.
	 * @param len length of the period, must not be null
	 * @param end end time of the period.
	 */
	public Period(Duration len, Time end) {
		// TODO
		
		length = len;
		stop = end;
		start = end.subtract(len);
		
	}
	
	/**
	 * Return the start time of the period.
	 * @return beginning time
	 */
	public Time getStart() {
		return this.start; // TODO
	}
	
	/**
	 * Return the stop time of the period.
	 * @return end time
	 */
	public Time getStop() {
		return this.stop; // TODO
	}
	
	/**
	 * Return the length of the period.
	 * @return the amount of time in this period
	 */
	public Duration getLength() {
		return this.length; // TODO
	}
	
	@Override
	public boolean equals(Object x) {
		if(!(x instanceof Period)){
			return false;
		}
		Period d = (Period)x;
		
		return this.toString().equals(d.toString());
	}
	
	@Override
	public int hashCode() {
	
		return Objects.hash(start, stop, length); // TODO
	}
	
	@Override
	public String toString() {
		
		String one = "[" + this.start.toString() + "; "; 
		String two = this.length.toString() + "]";
		
		return one + two; // TODO
	}
	
	/**
	 * Return whether this period overlaps with the parameter.
	 * If one appointment starts where the other ends, they do not overlap.
	 * @param p period to compare to, must not be null
	 * @return whether this period overlaps the parameter
	 */
	public boolean overlap(Period p) {
		

		return (this.start == p.stop || this.stop == p.start) || (this.start == p.start || this.stop == p.stop); // TODO
	} 
}
