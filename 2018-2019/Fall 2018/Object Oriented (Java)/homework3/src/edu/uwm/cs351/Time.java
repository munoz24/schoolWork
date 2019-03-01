package edu.uwm.cs351;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A point in time.
 */
public class Time implements Comparable<Time> {
	private final long point;

	private Time(long milliseconds) {
		point = milliseconds;
	}

	/**
	 * Create a time for now.
	 */
	public Time() {
		this(System.currentTimeMillis());
	}

	/**
	 * Create a time according to the time parameter.
	 * @param c calendar object representing a time, must not be null
	 */
	public Time(Calendar c) {
		point = c.getTimeInMillis();
	}

	@Override
	public boolean equals(Object x) {
		if (!(x instanceof Time)) return false;
		Time other = (Time)x;
		return point == other.point;
	}

	@Override
	public int hashCode() {
		return new Long(point).hashCode();
	}

	@Override
	public int compareTo(Time other) {
		return new Long(point).compareTo(other.point);
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("z G yyyy/MM/dd HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(new Date(point));
	}

	/**
	 * Return the difference between two time points.
	 * The order doesn't matter --- the difference is always a
	 * (positive) Duration.
	 * @param t time point to compute difference with
	 * @return duration between two time points.
	 */
	public Duration difference(Time t) {
		return Duration.MILLISECOND.scale(Math.abs(t.point - point));
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
		return new Time((long)d.divide(Duration.MILLISECOND) + point);
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
		return new Time((long)(point - d.divide(Duration.MILLISECOND)));
	}



	/**
	 * Takes a String and turns it into a Time
	 * @param String s
	 * @return Time
	 * @throws ParseException 
	 * @exception NullPointerException, NumberFormatException
	 */

	public static Time fromString(String s){
		if(s.equals(null)) throw new NullPointerException("Cant be null");
		
		int slashCount = 0;
		int dotCount = 0;
		int i = 0;
		for(i = 0; i < s.length(); ++i) {
			if(s.charAt(i) == '/') ++slashCount;
			if(s.charAt(i) == ':') ++dotCount;
		}

		
		if(slashCount != 2 || dotCount != 2 || i != 26) throw new NumberFormatException("Incorrect Format");

		
		SimpleDateFormat format = new SimpleDateFormat("z G yyyy/MM/dd HH:mm:ss");
		Date date = format.parse(s,new ParsePosition(0));
		Time current = new Time(date.getTime());
		return current;


	}

}
