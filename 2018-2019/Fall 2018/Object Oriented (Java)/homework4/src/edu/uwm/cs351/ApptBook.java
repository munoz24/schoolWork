// This is an assignment for students to complete after reading Chapter 4 of
// "Data Structures and Other Objects Using Java" by Michael Main.

package edu.uwm.cs351;

import junit.framework.TestCase;


/******************************************************************************
 * This class is a homework assignment;
 * A ApptBook is a collection of Appointments.
 * The sequence can have a special "current element," which is specified and 
 * accessed through four methods
 * (start, getCurrent, advance and isCurrent).
 *
 ******************************************************************************/
public class ApptBook implements Cloneable
{
	// TODO: Declare the private static Node class.
	// It should have a constructor but no methods.
	// The fields of Node should have "default" access (neither public, nor private)
	// and should not start with underscores.

	private static class Node {
		Appointment data;
		Node next;

		public Node(Appointment data, Node next) {
			this.data = data;
			this.next = next;
		}
	}


	// The data structure:
	private int _manyNodes;
	private Node _head;
	private Node _precursor, _cursor;


	private static boolean doReport = true; // used only by invariant checker

	/**
	 * Used to report an error found when checking the invariant.
	 * By providing a string, this will help debugging the class if the invariant should fail.
	 * @param error string to print to report the exact error found
	 * @return false always
	 */
	private boolean report(String error) {
		if (doReport) System.out.println("Invariant error found: " + error);
		return false;
	}

	/**
	 * Check the invariant. 
	 * Return false if any problem is found.  Returning the result
	 * of {@link #report(String)} will make it easier to debug invariant problems.
	 * @return whether invariant is currently true
	 */
	private boolean wellFormed() {
		// Invariant:
		// 1. list must not include a cycle.
		// 2. manyNodes is number of nodes in list
		// 3. precursor is either null or points to a node in the list.
		// 4. if precursor is null, cursor is head, otherwise cursor is precursor.next;
		// 5. The elements (none of which are null) are in non-decreasing order.

		// Implementation:
		// Do multiple checks: each time returning false if a problem is found.

		// We do the first one for you:
		// check that list is not cyclic
		if (_head != null) {
			// This check uses an interesting property described by Guy Steele (CLtL 1987)
			Node fast = _head.next;
			for (Node p = _head; fast != null && fast.next != null; p = p.next) {
				if (p == fast) return report("list is cyclic!");
				fast = fast.next.next;
			}
		}

		// Implement remaining conditions.

		// 2. manyNodes is number of nodes in list
		int count = 0;
		for (Node p = _head; p != null; p = p.next) {
			++count;
		}
		if(count != _manyNodes) return report("2) _manyNodes does not match actual node count");


		// 3. precursor is either null or points to a node in the list.
		if(_precursor != null) {
			boolean check = false;
			for (Node p = _head; p != null; p = p.next) {
				if(p == _precursor) {
					check = true;
				}
			}
			if(check == false) {
				return report("3) _precursor is not a node");
			}
		}

		// 4. if precursor is null, cursor is head, otherwise cursor is precursor.next;

		if(_precursor == null) {
			if(_cursor != _head) return report("4) cursor is not equal to head");
		}
		else {
			if(_precursor.next != _cursor) return report("4) cursor is not precursor.next");
		}


		// 5. The elements (none of which are null) are in non-decreasing order.
		if(_head != null) {
			for (Node p = _head; p.next != null; p = p.next) {

				if(p.data == null || p.next.data == null) return report("5)p.data was null");

				if((p.data.getTime().getStart()).compareTo(p.next.data.getTime().getStart()) == 1) {
					return report("5) Out of Order");
				}
			}
		}



		// If no problems found, then return true:
		return true;
	}

	private ApptBook(boolean doNotUse) {} // only for purposes of testing, do not change

	/**
	 * Create an empty sequence of particles.
	 * @param _manyNodes 
	 * @param - none
	 * @postcondition
	 *   This sequence of particles is empty 
	 **/   
	public ApptBook()
	{
		// TODO: Implemented by student.
		this._manyNodes = 0;
		this._head = null;
		this._precursor = null; 
		this._cursor = null;

		assert wellFormed() : "invariant failed at end of constructor";
	}


	/**
	 * Determine the number of elements in this sequence.
	 * @param - none
	 * @return
	 *   the number of elements in this sequence
	 **/ 
	public int size()
	{
		assert wellFormed() : "invariant wrong at start of size()";
		// TODO: Implemented by student.
		// This method shouldn't modify any fields, hence no assertion at end
		int count = 0;
		for (Node p = this._head; p != null; p = p.next) {
			++count;
		}

		return count;
	}

	/**
	 * Set the current element at the front of this book.
	 * @param - none
	 * @postcondition
	 *   The front element of this book is now the current element (but 
	 *   if this book has no elements at all, then there is no current 
	 *   element).
	 **/  
	public void start( )
	{
		assert wellFormed() : "invariant wrong at start of start()";
		// TODO: Implemented by student.

		this._precursor = null;
		this._cursor = this._head;


		assert wellFormed() : "invariant wrong at end of start()";
	}

	/**
	 * Accessor method to determine whether this sequence has a specified 
	 * current element that can be retrieved with the 
	 * getCurrent method. 
	 * @param - none
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean isCurrent()
	{
		assert wellFormed() : "invariant wrong at start of getCurrent()";
		// TODO: Implemented by student.
		// This method shouldn't modify any fields, hence no assertion at end
		if(this._cursor == null) {
			return false;
		}
		return true;
	}

	/**
	 * Accessor method to get the current element of this sequence. 
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @return
	 *   the current element of this sequence
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   getCurrent may not be called.
	 **/
	public Appointment getCurrent( )
	{
		assert wellFormed() : "invariant wrong at start of getCurrent()";
		// TODO: Implemented by student.
		// This method shouldn't modify any fields, hence no assertion at end

		if(this.isCurrent() == false) {
			throw new IllegalStateException("No current Element");
		}

		return this._cursor.data;

	}

	/**
	 * Move forward, so that the current element will be the next element in
	 * this book.
	 * @param - none
	 * @precondition
	 *   hasCurrent() returns true. 
	 * @postcondition
	 *   If the current element was already the end element of this book 
	 *   (with nothing after it), then there is no longer any current element. 
	 *   Otherwise, the new element is the element immediately after the 
	 *   original current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   advance may not be called.
	 **/
	public void advance( )
	{
		assert wellFormed() : "invariant wrong at start of advance()";
		// TODO: Implemented by student.
		if(this.isCurrent() == false) {
			throw new IllegalStateException();
		}

		this._precursor = this._precursor.next;
		this._cursor = this._cursor.next;

		assert wellFormed() : "invariant wrong at end of advance()";
	}

	/**
	 * Remove the current element from this sequence.
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @postcondition
	 *   The current element has been removed from this sequence.
	 *   The current element is advanced: if there is a following element,
	 *   it is current now, otherwise there is no current element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   removeCurrent may not be called. 
	 **/
	public void removeCurrent( )
	{
		assert wellFormed() : "invariant wrong at start of removeCurrent()";
		// TODO: Implemented by student.
		// See textbook pp.176-78, 181-184
		if(this.isCurrent() == false) {
			throw new IllegalStateException();
		}
		this._precursor.next = this._cursor.next;
		this._cursor = this._cursor.next;

		--this._manyNodes;

		assert wellFormed() : "invariant wrong at end of removeCurrent()";
	}

	/**
	 * Set the current element to the first element that is equal
	 * or greater than the guide.
	 * @param guide element to compare against, must not be null.
	 */
	public void setCurrent(Appointment guide) {
		start();
		while (isCurrent() && getCurrent().compareTo(guide) < 0) {
			advance();
		}
	}

	/**
	 * Add a new element to this book, in order (in the last position possible).
	 * The current element (if any) is not affected.
	 * @param element
	 *   the new element that is being added, must not be null
	 * @postcondition
	 *   A new copy of the element has been added to this book. The current
	 *   element (whether or not is exists) is not changed.
	 * @exception IllegalArgumentExcetion
	 *   indicates the parameter is null
	 **/
	public void insert(Appointment element)
	{
		assert wellFormed() : "invariant failed at start of insert";
		// TODO: Implemented by student.

		if(element == null) {
			throw new IllegalArgumentException("element, at insert, is null");
		}

		Node currentE = null;
		if(this._cursor != null) {
			currentE = this._cursor;
		}


		++this._manyNodes;
		if(this._head == null) {
			this._head = new Node(element, null);
		}else {

			Node lag = null;

			for(Node p = this._head; p != null; p = p.next) {

				if(p.data.getTime().getStart().compareTo(element.getTime().getStart()) == 1){
					lag.next = new Node(element, lag.next);
				}
				lag = p;
			}

		}


		if(this._cursor != null) {
			for(Node p = this._head; p != null; p = p.next) {
				if(p.data.getTime().getStart().compareTo(currentE.data.getTime().getStart()) == 0) {
					this._cursor = p;
					break;
				}
			}
		}



		assert wellFormed() : "invariant failed at end of insert";
	}

	/**
	 * Place all the appointments of another book (which may be the
	 * same book as this!) into this book in order.
	 * The elements are probably not going to be placed in a single block.
	 * @param addend
	 *   a book whose contents will be placed into this book
	 * @precondition
	 *   The parameter, addend, is not null. 
	 * @postcondition
	 *   The elements from addend have been placed into
	 *   this book. The current el;ement (if any) is
	 *   unchanged.
	 * @exception NullPointerException
	 *   Indicates that addend is null. 
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory to increase the size of this book.
	 **/
	public void insertAll(ApptBook addend)
	{
		assert wellFormed() : "invariant failed at start of insertAll";
		// TODO: Implemented by student.
		// Watch out for the this==addend case!
		// Cloning the addend is an easy way to avoid problems.
		ApptBook addend2 = addend.clone();






		assert wellFormed() : "invariant failed at end of insertAll";
		assert addend.wellFormed() : "invariant of addend broken in insertAll";
	}


	/**
	 * Generate a copy of this sequence.
	 * @param - none
	 * @return
	 *   The return value is a copy of this sequence. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 *   Whatever was current in the original object is now current in the clone.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public ApptBook clone( )
	{  	 
		assert wellFormed() : "invariant wrong at start of clone()";

		ApptBook result;

		try
		{
			result = (ApptBook) super.clone( );
		}
		catch (CloneNotSupportedException e)
		{  
			// This exception should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}

		// TODO: Implemented by student.
		// Now do the hard work of cloning the list.
		// See pp. 220-222, 235 (3rd edition: 193-197, 228)
		// Setting precursor correctly is tricky.

		assert wellFormed() : "invariant wrong at end of clone()";
		assert result.wellFormed() : "invariant wrong for result of clone()";
		return result;
	}


	public static class TestInvariantChecker extends TestCase {
		ApptBook hs = new ApptBook(false);
		Time now = new Time();
		Appointment h1 = new Appointment(new Period(now,Duration.HOUR),"1: think");
		Appointment h2 = new Appointment(new Period(now,Duration.DAY),"2: current");
		Appointment h3 = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat");
		Appointment h4 = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");
		Appointment h5 = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow");

		@Override
		protected void setUp() {
			hs = new ApptBook(false);
			doReport = false;
		}

		public void test0() {
			hs._manyNodes = 1;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._manyNodes = 0;
			assertTrue(hs.wellFormed()); // it appears only element was removed
		}

		public void test1() {
			hs._head = new Node(h1,null);
			hs._cursor = hs._head;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 2;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._manyNodes = 1;
			assertTrue(hs.wellFormed());
		}

		public void test2() {
			hs._cursor = hs._head = new Node(h2,null);
			hs._manyNodes = 1;
			hs._precursor = new Node(h2,null);
			assertFalse(hs.wellFormed());
			hs._precursor = new Node(h1,hs._head);
			hs._cursor = hs._head;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._precursor = null;
			assertTrue(hs.wellFormed());
			hs._precursor = hs._head;
			hs._cursor = hs._precursor.next;
			assertTrue(hs.wellFormed());
		}

		public void test3() {
			hs._cursor = hs._head = new Node(h1,null);
			hs._head.next = hs._head;
			hs._manyNodes = 1;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 2;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 3;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 0;
			assertFalse(hs.wellFormed());
			hs._manyNodes = -1;
			assertFalse(hs.wellFormed());
		}

		public void test4() {
			hs._head = new Node(h3,null);
			hs._head = new Node(null,hs._head);
			hs._manyNodes = 1;
			hs._cursor = hs._head;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 2;
			assertFalse(hs.wellFormed());
			hs._head.data = h4;
			assertFalse(hs.wellFormed());
			hs._head.next.data = null;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._head.next.data = h4;
			assertTrue(hs.wellFormed());
		}

		public void test5() {
			hs._head = new Node(h5,null);
			hs._head = new Node(h4,hs._head);
			hs._head = new Node(h3,hs._head);
			hs._head = new Node(h2,hs._head);
			hs._head = new Node(h1,hs._head);
			hs._cursor = hs._head;

			hs._manyNodes = 1;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 2;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 3;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 4;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 0;
			assertFalse(hs.wellFormed());
			hs._manyNodes = -1;
			assertFalse(hs.wellFormed());
			hs._manyNodes = 5;

			hs._cursor = hs._cursor.next;
			assertFalse(hs.wellFormed());
			hs._cursor = hs._cursor.next;
			hs._precursor = hs._head;
			assertFalse(hs.wellFormed());
			hs._cursor = hs._cursor.next;
			hs._precursor = hs._precursor.next;
			assertFalse(hs.wellFormed());
			hs._cursor = hs._cursor.next;
			hs._precursor = hs._precursor.next;
			assertFalse(hs.wellFormed());
			hs._cursor = hs._cursor.next;
			hs._precursor = hs._precursor.next;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._precursor = hs._precursor.next;
			assertTrue(hs.wellFormed());
		}

		public void test6() {
			Node n1,n2,n3,n4,n5;
			hs._head = n5 = new Node(h5,null);
			hs._head = n4 = new Node(h4,hs._head);
			hs._head = n3 = new Node(h3,hs._head);
			hs._head = n2 = new Node(h2,hs._head);
			hs._head = n1 = new Node(h1,hs._head);
			hs._manyNodes = 5;

			hs._precursor = new Node(h1,null);
			assertFalse(hs.wellFormed());
			hs._precursor = new Node(h1,n1);
			hs._cursor = n1;
			assertFalse(hs.wellFormed());
			hs._precursor = new Node(h1,n2);
			hs._cursor = n2;
			assertFalse(hs.wellFormed());
			hs._precursor = new Node(h2,n3);
			hs._cursor = n3;
			assertFalse(hs.wellFormed());
			hs._precursor = new Node(h3,n4);
			hs._cursor = hs._precursor;
			assertFalse(hs.wellFormed());
			hs._precursor = new Node(h4,n5);
			hs._cursor = n5;
			assertFalse(hs.wellFormed());

			hs._precursor = n4;

			n1.data = h3;
			assertFalse(hs.wellFormed());
			n2.data = h4;
			assertFalse(hs.wellFormed());
			n3.data = h5;
			assertFalse(hs.wellFormed());
			n4.data = null;
			assertFalse(hs.wellFormed());

			doReport = true;
			n4.data = h5;
			assertTrue(hs.wellFormed());
		}

		public void test7() {
			Node n1,n2,n3,n4,n5;
			hs._head = n5 = new Node(h5,null);
			hs._head = n4 = new Node(h4,hs._head);
			hs._head = n3 = new Node(h3,hs._head);
			hs._head = n2 = new Node(h2,hs._head);
			hs._head = n1 = new Node(h1,hs._head);
			hs._manyNodes = 5;

			hs._precursor = n1;
			hs._cursor = n3;
			assertFalse(hs.wellFormed());
			hs._cursor = n4;
			assertFalse(hs.wellFormed());
			hs._cursor = n5;
			assertFalse(hs.wellFormed());
			hs._cursor = null;
			assertFalse(hs.wellFormed());

			hs._precursor = n3;
			hs._cursor = n1;
			assertFalse(hs.wellFormed());
			hs._cursor = n2;
			assertFalse(hs.wellFormed());
			hs._cursor = n5;
			assertFalse(hs.wellFormed());
			hs._cursor = null;
			assertFalse(hs.wellFormed());
		}

		public void test8() {
			Node n2 = new Node(h1,null);
			Node n1 = new Node(h1,n2);
			hs._manyNodes = 2;
			hs._head = n1;

			hs._precursor = null;
			hs._cursor = n2;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._cursor = n1;
			assertTrue(hs.wellFormed());
		}
	}
}

