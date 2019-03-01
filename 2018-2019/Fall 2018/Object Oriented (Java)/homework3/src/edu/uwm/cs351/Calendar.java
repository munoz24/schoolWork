package edu.uwm.cs351;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * The start of a Calendar ADT that could be the
 * basis of a Calendar application.
 */
public class Calendar extends TreeSet<Appointment> {
	/**
	 * Keep Eclipse Happy
	 */
	private static final long serialVersionUID = 1L;

	public Calendar() { }

	/**
	 * Read lines from the scanner and convert each to an appointment
	 * @param s scanner to read from, must not be null
	 * @param errorMessages if a line doesn't read, print it with the
	 * error message.
	 */
	public void doImport(Scanner s, PrintWriter errorMessages) {
		// TODO
		String line;

		if(s == null) throw new NullPointerException("Scanner was null");

		while(s.hasNextLine()) {
			try {
				line = s.nextLine();
				this.add(Appointment.fromString(line));
			}catch(NumberFormatException e) {
				errorMessages.println("Error was : " + e);
			}
		}
	}

	/**
	 * Print all appointments in this calendar to the print writer given.
	 * @param pw output to send to, must not be null
	 */
	public void doExport(PrintWriter pw) {
		// TODO
		if(pw == null) throw new NullPointerException("Print Writer was null");

		Iterator<Appointment> apt = this.iterator();
		
		while(apt.hasNext()) {
			pw.println(apt.next().toString());
		}
	}

	/**
	 * Return an iterator into this calendar starting at the given time.
	 * The iterator's next element (if any) will be the first appointment
	 * that starts on or after this time.
	 * @param t time to start at, must not be null
	 * @return iterator, never null
	 */
	public Iterator<Appointment> starting(Time t) {
		// TODO (see assignment)
		if(t == null) throw new NullPointerException("T was null");
		
		TreeSet<Appointment> tree_set = new TreeSet<Appointment>(); 
		Iterator<Appointment> apt = this.iterator();
		
		Appointment e;
		
		while(apt.hasNext()) {
			e = Appointment.fromString(apt.toString());
			tree_set.add(e);
		}
		
		TreeSet<Appointment> tail_set; 
		
		tail_set = (TreeSet<Appointment>)tree_set.tailSet(Appointment.fromString(apt.toString()));
		
		Iterator<Appointment> current = tail_set.iterator();
		

		return current;
	}

	/**
	 * Remove conflicts from this calendar.
	 * If it find two conflicting appointments, the second one is removed,
	 * and we don't worry about any further conflicts with the second one
	 * (but the non-removed one may still conflict with further appointments).
	 * @return a list of removed appointments.
	 */
	public List<Appointment> removeConflicts() {
		return null; // TODO
	}
}
