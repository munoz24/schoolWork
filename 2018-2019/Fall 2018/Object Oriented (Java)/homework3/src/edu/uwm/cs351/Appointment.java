package edu.uwm.cs351;

import java.text.ParseException;

/**
 * A simple class representing an appointment: 
 * a period of time and a description.
 */
public class Appointment implements Comparable<Appointment> {
	private final Period time;
	private final String description;
	
	public Appointment(Period p, String t) {
		time = p;
		description = t;
	}
	
	public Period getTime() { return time; }
	
	public String getDescription() { return description; }

	
	@Override
	public int hashCode() {
		return time.hashCode() ^ description.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Appointment)) return false;
		Appointment other = (Appointment)obj;
		return time.equals(other.time) && description.equals(other.description);
	}

	@Override
	public String toString() {
		return time.toString() + description.replace('\n', '/');
	}
	
	@Override
	public int compareTo(Appointment o) {
		int c = time.getStart().compareTo(o.time.getStart());
		if (c != 0) return c;
		c = time.getLength().compareTo(o.time.getLength());
		if (c != 0) return c;
		return description.compareTo(o.description);
	}
	
	
	/**
	 * Takes a String and turns it into a Appointment
	 * @param String s
	 * @return Appointment 
	 * @exception NullPointerException, NumberFormatException
	 */
	public static Appointment fromString(String s) {
		
		if(s.length() < 26) throw new NumberFormatException("Wrong Format!");
		
		int dot = s.lastIndexOf('.');
		String desc = s.substring(dot+2, s.length());
		
		s = s.substring(0, dot+2);
		
		Appointment current = new Appointment(Period.fromString(s), desc);
		
		
		return current;
	}
	
	
	
}
