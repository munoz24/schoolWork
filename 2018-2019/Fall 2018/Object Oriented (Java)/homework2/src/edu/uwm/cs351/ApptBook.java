// This is an assignment for students to complete after reading Chapter 3 of
// "Data Structures and Other Objects Using Java" by Michael Main.

package edu.uwm.cs351;

import junit.framework.TestCase;

/******************************************************************************
 * This class is a homework assignment;
 * A ApptBook ("book" for short) is a sequence of Appointment objects in sorted order.
 * The book can have a special "current element," which is specified and 
 * accessed through four methods that are available in the this class 
 * (start, getCurrent, advance and isCurrent).
 *
 * @note
 *   (1) The capacity of a book can change after it's created, but
 *   the maximum capacity is limited by the amount of free memory on the 
 *   machine. The constructor, add, addAll, clone, 
 *   and catenation methods will result in an
 *   OutOfMemoryError when free memory is exhausted.
 *   <p>
 *   (2) A book's capacity cannot exceed the maximum integer 2,147,483,647
 *   (Integer.MAX_VALUE). Any attempt to create a larger capacity
 *   results in a failure due to an arithmetic overflow. 
 *   
 *   NB: Neither of these conditions require any work for the implementors (students).
 *
 *
 ******************************************************************************/
public class ApptBook implements Cloneable {

	/** Static Constants */
	private static final int INITIAL_CAPACITY = 1;

	/** Fields */
	private Appointment[ ] _data;
	private int _manyItems;
	private int _currentIndex;

	// Invariant of the ApptBook class:
	//   1. The number of elements in the books is in the instance variable 
	//      manyItems.
	//   2. For an empty book (with no elements), we do not care what is 
	//      stored in any of data; for a non-empty book, the elements of the
	//      book are stored in data[0] through data[manyItems-1] and we
	//      don't care what's in the rest of data.
	//   3. None of the elemts are null and they are ordered according to their
	//      natural ordering (Comparable); duplicates *are* allowed.
	//   4. If there is a current element, then it lies in data[currentIndex];
	//      if there is no current element, then currentIndex equals manyItems.

	private static boolean doReport = true; // change only in invariant tests

	private boolean report(String error) {
		if (doReport) {
			System.out.println("Invariant error: " + error);
		}
		return false;
	}

	private boolean wellFormed() {
		// Check the invariant.


		// 1. data array is never null
		// TODO

		if(this._data == null) {
			return report("#1");
		}

		// 2. The data array has at least as many items in it as manyItems
		//    claims the book has
		//TODO
		if(this._manyItems < 0 || this._manyItems > this._data.length) {
			return report("#2-1");
		}
		int check = 0;

		for(int i = 0; i < this._manyItems; ++i) {

			if(this._data[i] == null) {
				if(check <= this._manyItems) {
					return report("#2-2");
				}
			}
			++check;
		}

		// 3. None of the elemts are null and all are in natural order
		for(int i = 0; i < this._manyItems; ++i) {

			if(i == this._manyItems) {
				break;
			}
			if(this._data[i] == null) {
				return report("#3-NULL");
			}
			if(i+1 >= this._manyItems) {
				break;
			}
			if(this._data[i].compareTo(this._data[i+1]) > 0) {
				return report("#3-Out of Order");
			}

		}

		// 4. currentIndex is never negative and never more than the number of
		//    items in the book.
		// TODO	

		if( this._currentIndex < 0 ||  this._currentIndex > this._manyItems) {
			return report("#4");
		}

		// If no problems discovered, return true
		return true;
	}

	// This is only for testing the invariant.  Do not change!
	private ApptBook(boolean testInvariant) { }

	/**
	 * Initialize an empty book with 
	 * an initial capacity of INITIAL_CAPACITY. The {@link #insert(Appointment)} method works
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @param - none
	 * @postcondition
	 *   This book  is empty and has an initial capacity of INITIAL_CAPACITY
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for initial array.
	 **/   
	public ApptBook( )
	{
		// TODO: Implemented by student.

		_data = new Appointment[INITIAL_CAPACITY];
		_manyItems = 0; 
		_currentIndex =0;

		assert wellFormed() : "invariant failed at end of constructor";
	}

	/**
	 * Initialize an empty book with a specified initial capacity.
	 * The {@link #insert(Appointment)} method works
	 * efficiently (without needing more memory) until this capacity is reached.
	 * @param initialCapacity
	 *   the initial capacity of this book, must not be negative
	 * @exception IllegalArgumentException
	 *   Indicates that name, bpm or initialCapacity are invalid
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for an array with this many elements.
	 *   new Appointment[initialCapacity].
	 **/   
	public ApptBook(int initialCapacity)
	{
		// TODO: Implemented by student.
		if(initialCapacity < 0) {
			throw new IllegalArgumentException("initialCapacity is negative");
		}
		if(initialCapacity > Integer.MAX_VALUE) {
			throw new OutOfMemoryError("initialCapacity is too big");
		}

		_data = new Appointment[initialCapacity];
		_manyItems = initialCapacity; 
		_currentIndex = initialCapacity;

		assert wellFormed() : "invariant failed at end of constructor";
	}

	/**
	 * Determine the number of elements in this book.
	 * @param - none
	 * @return
	 *   the number of elements in this book
	 **/ 
	public int size( )
	{
		if(this._data[0] == null) {
			return 0;
		}

		assert wellFormed() : "invariant failed at start of size";
		// TODO: Implemented by student.

		return this._manyItems;

	}

	/**
	 * Add a new element to this book, in order.
	 * If the new element would take this book beyond its current capacity,
	 * then the capacity is increased before adding the new element.
	 * The current element (if any) is not affected.
	 * @param element
	 *   the new element that is being added, must not be null
	 * @postcondition
	 *   A new copy of the element has been added to this book. The current
	 *   element (whether or not is exists) is not changed.
	 * @exception IllegalArgumentException
	 *   indicates the parameter is null
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for increasing the book's capacity.
	 **/
	public void insert(Appointment element)
	{
		assert wellFormed() : "invariant failed at start of insert";
		// TODO: Implemented by student.		

		if(element == null) {
			throw new IllegalArgumentException("element, at insert, is null");
		}

		if(this._currentIndex == this._manyItems) {
			++this._currentIndex;
		}
		++_manyItems;

		Appointment currentE = this._data[0];

		if(this._currentIndex != this._manyItems) {
			currentE = this._data[this._currentIndex];
		}


		Appointment copyE = element;
		this.ensureCapacity(this._manyItems + 1);

		if(this._manyItems <= 1 || this._data[this._manyItems] == null) {
			this._data[this._manyItems-1] = copyE;

		}


		for(int i=this._manyItems; i > 0; --i) {

			if(this._data[i] instanceof Appointment) {

				if(this._data[i].compareTo(this._data[i-1]) < 1) {
					Appointment temp = this._data[i];

					this._data[i] = this._data[i-1];
					this._data[i-1] = temp;
				}

			}
		}

		if(this._currentIndex != this._manyItems) {
			for(int i = 0; i < this._manyItems; ++i) {
				if(this._data[i] == currentE) {
					this._currentIndex = i;
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
		ApptBook cloneAddend = addend.clone();
		ApptBook cloneThis = this.clone();

		for(int i = 0; i < cloneAddend._manyItems; ++i) {
			if(cloneAddend._data[i] == null) {
				throw new NullPointerException("addended._data[i] is null");
			}
		}

		this.ensureCapacity(cloneThis._manyItems + cloneAddend._manyItems +1);

		int i = 0, j = 0, k = 0;

		while (i < cloneThis._manyItems && j < cloneAddend._manyItems) {
			if (cloneThis._data[i].compareTo(cloneAddend._data[j]) > 0) {
				this._data[k] = cloneThis._data[i];
				i++;
			} else {
				this._data[k] = cloneAddend._data[j];
				j++;
			}
			k++;
		}

		if (i < cloneThis._manyItems) {
			for(;i < this._manyItems; ++i ) {
				this._data[k] = cloneThis._data[i];
				k++;
			}

		}

		if(j < cloneAddend._manyItems) {
			for(;j < this._manyItems; ++j ) {
				this._data[k] = cloneAddend._data[j];
				k++;
			}
		}

		this._manyItems = this._data.length -1;
		Appointment temp;

		for (int p = 0; p < this._manyItems; p++) {

			for (int o = 0; o < this._manyItems; o++) {

				if (this._data[p].compareTo(this._data[o]) < 1) {
					temp = this._data[p];
					this._data[p] = this._data[o];
					this._data[o] = temp;
				}
			}
		}


		assert wellFormed() : "invariant failed at end of insertAll";
		assert addend.wellFormed() : "invariant of addend broken in insertAll";
	}

	/**
	 * Change the current capacity of this book as needed so that
	 * the capacity is at least as big as the parameter.
	 * This code must work correctly and efficiently if the minimum
	 * capacity is (1) smaller or equal to the current capacity (do nothing)
	 * (2) at most double the current capacity (double the capacity)
	 * or (3) more than double the current capacity (new capacity is the
	 * minimum passed).
	 * @param minimumCapacity
	 *   the new capacity for this book
	 * @postcondition
	 *   This book's capacity has been changed to at least minimumCapacity.
	 *   If the capacity was already at or greater than minimumCapacity,
	 *   then the capacity is left unchanged.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for: new array of minimumCapacity elements.
	 **/
	private void ensureCapacity(int minimumCapacity)
	{
		// TODO: Implemented by student.
		// NB: do not check invariant

		if(minimumCapacity >= Integer.MAX_VALUE) {
			throw new OutOfMemoryError("minimumCapacity is too large");
		}

		if (this._data.length >= minimumCapacity) return;


		Appointment[] temp = new Appointment[minimumCapacity];

		for(int i = 0; i < this._manyItems;++i) {

			temp[i] = this._data[i];

		}
		this._data = temp;
		return;
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
		assert wellFormed() : "invariant failed at start of start";
		// TODO: Implemented by student.

		if(this._data[0] != null) {
			this._currentIndex = 0;
		}

		assert wellFormed() : "invariant failed at end of start";
	}

	/**
	 * Accessor method to determine whether this book has a specified 
	 * current element that can be retrieved with the 
	 * getCurrent method. 
	 * @param - none
	 * @return
	 *   true (there is a current element) or false (there is no current element at the moment)
	 **/
	public boolean isCurrent( )
	{
		assert wellFormed() : "invariant failed at start of isCurrent";
		// TODO: Implemented by student.

		if(this._currentIndex == this._manyItems) {
			return false;
		}

		return true;
	}

	/**
	 * Accessor method to get the current element of this book. 
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @return
	 *   the current element of this book
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   getCurrent may not be called.
	 **/
	public Appointment getCurrent( )
	{
		assert wellFormed() : "invariant failed at start of getCurrent";
		// TODO: Implemented by student.
		// Don't change "this" object!

		if(this.isCurrent() == false) {
			if(this._data[this._currentIndex] == null) {
				throw new IllegalStateException("No current Element");
			}
			return this._data[this._currentIndex+1];
		}

		return this._data[this._currentIndex];
	}


	/**
	 * Move forward, so that the current element will be the next element in
	 * this book.
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true. 
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
		assert wellFormed() : "invariant failed at start of advance";
		// TODO: Implemented by student.
		if(this.isCurrent() == false) {
			throw new IllegalStateException();
		}
		if(this._data[this._currentIndex] != null)
		{
			++this._currentIndex;
		}
		assert wellFormed() : "invariant failed at end of advance";
	}

	/**
	 * Remove the current element from this book.
	 * @param - none
	 * @precondition
	 *   isCurrent() returns true.
	 * @postcondition
	 *   The current element has been removed from this book, and the 
	 *   following element (if there is one) is now the new current element. 
	 *   If there was no following element, then there is now no current 
	 *   element.
	 * @exception IllegalStateException
	 *   Indicates that there is no current element, so 
	 *   removeCurrent may not be called. 
	 **/
	public void removeCurrent( )
	{
		assert wellFormed() : "invariant failed at start of removeCurrent";
		// TODO: Implemented by student

		if(this._data[this._currentIndex] == null) {
			throw new IllegalStateException("No current element");
		}

		int p =0;
		if(this.isCurrent() == true) {
			for(int i = this._currentIndex; i < this._manyItems; ++i) {
				if(this._manyItems == this._currentIndex) {
					this._currentIndex = this._manyItems;
					++p;
				}
				this._data[i] = this._data[i+1];
			}
			--this._manyItems;
			if(p > 0) {
				--this._currentIndex;
			}
		}

		assert wellFormed() : "invariant failed at end of removeCurrent";
	}

	/**
	 * Set the current element to the first element that is equal
	 * or greater than the guide.
	 * @param guide element to compare against, must not be null.
	 */
	public void setCurrent(Appointment guide) {
		// TODO: can be done entirely with other methods.
		// (Binary would be much faster for a large book.
		// but don't worry about efficiency yet.)

		if(guide == null) {
			return;
		}

		for(int i = 0; i < this._manyItems; ++i) {
			if(this._data[i].compareTo(guide) >= 0) {
				this._currentIndex = i;
				return;
			}
		}

		this._currentIndex = this._manyItems;


	}

	/**
	 * Generate a copy of this book.
	 * @param - none
	 * @return
	 *   The return value is a copy of this book. Subsequent changes to the
	 *   copy will not affect the original, nor vice versa.
	 * @exception OutOfMemoryError
	 *   Indicates insufficient memory for creating the clone.
	 **/ 
	public ApptBook clone( ) { 
		assert wellFormed() : "invariant failed at start of clone";
		ApptBook answer;

		try
		{
			answer = (ApptBook) super.clone( );
		}
		catch (CloneNotSupportedException e)
		{  // This exception should not occur. But if it does, it would probably
			// indicate a programming error that made super.clone unavailable.
			// The most common error would be forgetting the "Implements Cloneable"
			// clause at the start of this class.
			throw new RuntimeException
			("This class does not implement Cloneable");
		}

		// all that is needed is to clone the data array.
		// (Exercise: Why is this needed?)
		answer._data = _data.clone( );

		assert wellFormed() : "invariant failed at end of clone";
		assert answer.wellFormed() : "invariant on answer failed at end of clone";
		return answer;
	}

	// don't change this nested class:
	public static class TestInvariantChecker extends TestCase {
		Time now = new Time();
		Appointment e1 = new Appointment(new Period(now,Duration.HOUR),"1: think");
		Appointment e2 = new Appointment(new Period(now,Duration.DAY),"2: current");
		Appointment e3 = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat");
		Appointment e4 = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");
		Appointment e5 = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow");
		ApptBook hs;

		protected void setUp() {
			hs = new ApptBook(false);
			ApptBook.doReport = false;
		}

		public void test0() {
			assertFalse(hs.wellFormed());
		}

		public void test1() {
			hs._data = new Appointment[0];
			hs._manyItems = -1;
			assertFalse(hs.wellFormed());
			hs._manyItems = 1;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._manyItems = 0;
			assertTrue(hs.wellFormed());
		}

		public void test2() {
			hs._data = new Appointment[1];
			hs._manyItems = 1;
			assertFalse(hs.wellFormed());
			hs._manyItems = 2;
			assertFalse(hs.wellFormed());
			hs._data[0] = e1;

			doReport = true;
			hs._manyItems = 0;
			assertTrue(hs.wellFormed());
			hs._manyItems = 1;
			hs._data[0] = e1;
			assertTrue(hs.wellFormed());
		}

		public void test3() {
			hs._data = new Appointment[3];
			hs._manyItems = 2;
			hs._data[0] = e2;
			hs._data[1] = e1;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._data[0] = e1;
			assertTrue(hs.wellFormed());
			hs._data[1] = e2;
			assertTrue(hs.wellFormed());
		}

		public void test4() {
			hs._data = new Appointment[10];
			hs._manyItems = 3;
			hs._data[0] = e2;
			hs._data[1] = e4;
			hs._data[2] = e3;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._data[2] = e5;
			assertTrue(hs.wellFormed());
			hs._data[0] = e4;
			assertTrue(hs.wellFormed());
			hs._data[1] = e5;
			assertTrue(hs.wellFormed());
		}

		public void test5() {
			hs._data = new Appointment[10];
			hs._manyItems = 4;
			hs._data[0] = e1;
			hs._data[1] = e3;
			hs._data[2] = e2;
			hs._data[3] = e5;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._data[2] = e4;
			assertTrue(hs.wellFormed());
			hs._data[2] = e5;
			assertTrue(hs.wellFormed());
			hs._data[2] = e3;
			assertTrue(hs.wellFormed());
		}

		public void test6() {
			hs._data = new Appointment[3];
			hs._manyItems = -2;
			assertFalse(hs.wellFormed());
			hs._manyItems = -1;
			assertFalse(hs.wellFormed());
			hs._manyItems = 1;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._manyItems = 0;
			assertTrue(hs.wellFormed());
		}

		public void test7() {
			hs._data = new Appointment[3];
			hs._data[0] = e1;
			hs._data[1] = e2;
			hs._data[2] = e4;
			hs._manyItems = 4;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._manyItems = 3;
			assertTrue(hs.wellFormed());
		}

		public void test8() {
			hs._data = new Appointment[3];
			hs._data[0] = e2;
			hs._data[1] = e3;
			hs._data[2] = e1;
			hs._manyItems = 2;
			hs._currentIndex = -1;
			assertFalse(hs.wellFormed());
			hs._currentIndex = 3;
			assertFalse(hs.wellFormed());

			doReport = true;
			hs._currentIndex = 0;
			assertTrue(hs.wellFormed());
			hs._currentIndex = 1;
			assertTrue(hs.wellFormed());
			hs._currentIndex = 2;
			assertTrue(hs.wellFormed());
		}
	}
}

