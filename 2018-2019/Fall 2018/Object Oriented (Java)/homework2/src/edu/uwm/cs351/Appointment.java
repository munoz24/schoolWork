package edu.uwm.cs351;

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
		return time.toString() + description;
	}

	@Override
	public int compareTo(Appointment o) {
		int c = time.getStart().compareTo(o.time.getStart());
		if (c != 0) return c;
		c = time.getLength().compareTo(o.time.getLength());
		if (c != 0) return c;
		return description.compareTo(o.description);
	}
	
	

}
