package edu.uwm.cs351;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

/**
 * A point in time.
 */
public class Time implements Comparable<Time> {
	// TODO: data structure for Time (very simple)
	// The solution also has a private constructor,
	// which is very useful.
	private final long extent;
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("zz G yyyy/MM/dd HH:mm:ss");
	
	private Time(long t) {
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		extent = t;
	}
	
	/**
	 * Create a time for now.
	 */
	public Time() {
		// TODO (see homework)
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		extent = System.currentTimeMillis();
	}
	
	/**
	 * Create a time according to the time parameter.
	 * @param c calendar object representing a time, must not be null
	 * @return 
	 * @return 
	 */
	public Time(Calendar c) {
		// TODO
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		extent = c.getTimeInMillis();
	}
	
	// Override/implement methods standard for immutable classes.
	
	public boolean equals(Object x) {
		if(!(x instanceof Time)){
			return false;
		}
		Time d = (Time)x;
		
		return this.extent == d.extent;
	}
	
	
	public int hashCode() {
		int code = Objects.hash(extent);
		
		return code;
	}
	
	
	
	public int compareTo(Time t) {
		if(this.extent < t.extent) {
			return -1;
		}
		else if(this.extent == t.extent) {
			return 0;
		}
		else {
			return 1;
		}
		
	}	
	public String toString() {
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String strDate = dateFormat.format(this.extent);
	
		String AD = strDate;
	
		return AD;
	}
	
	
	
	
	/**
	 * Return the difference between two time points.
	 * The order doesn't matter --- the difference is always a
	 * (positive) Duration.
	 * @param t time point to compute difference with
	 * @return duration between two time points.
	 */
	public Duration difference(Time t) {
		
		double n = t.extent - this.extent;
		if(n < 0) {
			n = n*(-1);
		}
		
		return Duration.MILLISECOND.scale(n); // TODO 
	}

	/**
	 * Return the time point after a particular duration.
	 * If the point advances too far into the future,
	 * more than a hundred million years from now, this
	 * method may malfunction.
	 * @param d duration to advance, must not be null
	 * @return new time after given duration
	 */
	public Time add(Duration d) {
		
		return new Time(this.extent + (long)d.divide(Duration.MILLISECOND)); // TODO 
	}
	
	/**
	 * Return the time point before a particular duration.
	 * If a point regresses too far into the past,
	 * more than a hundred million years from now,
	 * this method may malfunction.
	 * @param d duration to regress, must not be null
	 * @return new time before this one by the given duration.
	 */
	public Time subtract(Duration d) {
		
		return new Time(this.extent - (long)d.divide(Duration.MILLISECOND)); // TODO 
	}
}
