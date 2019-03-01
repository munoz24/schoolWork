package edu.uwm.cs351.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import junit.framework.TestCase;

// This is a Homework Assignment for CS 351 at UWM

/**
 * A cyclic singly-linked list implementation of the Java Collection interface
 * We use {@link java.util.AbstractCollection} to implement most methods.
 * You should override {@link #clear()} for efficiency, and {@link #add(E)}
 * for functionality.  You will also be required to override the abstract methods
 * {@link #size()} and {@link #iterator()}.  All these methods should be declared
 * "@Override".
 * <p>
 * The data structure is a cyclic singly-linked list with one dummy node.
 * The fields should be:
 * <dl>
 * <dt>tail</dt> The last node in the list.
 * <dt>count</dt> Number of true elements in the collection.
 * <dt>version</dt> Version number (used for fail-fast semantics)
 * </dl>
 * It should be cyclicly linked without any null pointers.
 * The code should have very few special cases.
 * The class should define a private {@link #wellFormed()} method
 * and perform assertion checks in each method.
 * You should use a version stamp to implement <i>fail-fast</i> semantics
 * for the iterator.
 * @param <E>
 */
public class LinkedCollection<E> extends AbstractCollection<E> implements Collection<E> {

	// TODO: Declare private static Node data class
	// TODO: Declare fields

	private static class Node<T>{
		T data;
		Node<T> next;

		public Node(T data, Node<T> next) {
			this.data = data;
			this.next = next;
		}
	}
	
	Node<E> tail;
	int count;
	int version;



	private LinkedCollection(boolean ignored) {} // DO NOT CHANGE THIS

	private boolean report(String s) {
		System.out.println(s);
		return false;
	}

	// The invariant:
	private boolean wellFormed() {
		// check dummy
		if (tail == null) return report("tail is null");
		// check for cycles:
		Node<E> fast = tail.next;
		for (Node<E> p = tail; fast != null && fast.next != null && fast != tail && fast.next != tail; p = p.next) {
			if (p == fast) return report("list is wrongly cyclic");
			fast = fast.next.next;
		}

		// Check that null links aren't encountered
		for(Node<E> p = tail; p.next != tail; p = p.next) {
			if(p.next == null) return report("link was null");
		}

		// Check that count is correct.
		int check = 0;
		for(Node<E> p = tail; p.next != tail; p = p.next) {
			++check;
		}
		if(check != count) return report("count is wrong");	

		// Check that the dummy's data is null.

		if(tail.next.data != null) return report("Dummy is not null");

		// TODO other checks
		return true;
	}

	public LinkedCollection() {
		// TODO: set up fields.
		this.tail = new Node<E>(null, null);
		tail.next = tail;
		this.count = 0;
		this.version = 0;

		assert wellFormed() : "invariant broken at constructor";
	}

	// TODO: Constructor
	// TODO: Methods: add, clear, size, iterator

	/**
	 * Gets the count of elements in the collection
	 * @return count of elements in the collection
	 */
	public int size() {
		return this.count;
	}

	/**
	 * Adds new element to the collection
	 * @return true if element was added
	 */
	public boolean add(E x) {

		Node<E> temp = new Node<E>(x, tail.next);
		tail.next = temp;
		tail = temp;

		++this.version;
		++this.count;

		assert wellFormed() : "invariant broken at end of add";

		return true;
	}

	
	/**
	 * Gets rids of all elements of the collection
	 */
	public void clear(){
		
		Iterator<E> x = this.iterator();
		while(x.hasNext()) {
			x.next();
			x.remove();
		}
		this.count = 0;
		++version;
		
		assert wellFormed() : "invariant broken at clear";
		
	}

	
	
	/**
	 * Creates an iterator for the linked collection
	 * @return iterator
	 */
	@Override
	public Iterator<E> iterator() {
		
		MyIterator x = new MyIterator();
		
		return x;
	}



	// You need a nested (not static) class to implement the iterator:
	private class MyIterator implements Iterator<E> {
		// TODO: declare fields: myVersion, precursor, hasCurrent
		// Normally these should be private, but omit "private" so
		// that TestInvariant can access them.

		int myVersion;
		Node<E> precursor;
		boolean hasCurrent;


		MyIterator(boolean ignored) {} // DO NOT CHANGE THIS

		private boolean wellFormed() {
			// Invariant for recommended fields:
			// 0. The outer invariant holds
			// 1. precursor is not null
			// 2. precursor is tail only if there is no current
			// 3. precursor is in the list
			// NB: Don't check 1,2,3 unless the version matches.

			// 0.
			if (!LinkedCollection.this.wellFormed()) return false;
			
			if (myVersion == version) {
				// TODO: Implement checks 1,2,3
				// loops are OK.
				
				if(precursor == null) report("precursor is null");
				
				if(precursor == tail) {
					if(hasCurrent == false) report("precursor is tail but there is a current");
				}
				boolean check = false;
				for(Node<E> p = tail; p.next != tail; p = p.next) {
					if(precursor == p) {
						check = true;
					}
				}
				if(check != true) report("precursor is not in the list");
			}
			
			return true;
			
			
		}

		public MyIterator() {
			this.precursor = tail;
			this.hasCurrent = false;
			this.myVersion = version;

			assert wellFormed() : "invariant fails in iterator constructor";
		}

		// TODO: Many things
		// Declare constructor and methods.
		// check for invariant in every method
		// check for concurrent modification exception in every method
		// no method should have loops (all constant-time operations).

		/**
		 * Gets the precursor
		 * @return Node<E>
		 * @throws ConcurrentModificationException
		 */
		private Node<E> getCursor() {
			if(myVersion != version) throw new ConcurrentModificationException();
			assert wellFormed() : "invariant broken at getCursor";
			
			if (hasCurrent) return precursor.next;
			else return precursor;
			
		}
		
		
		/**
		 * Checks to see if there is a next element
		 * @return true
		 * @throws ConcurrentModificationException
		 */
		@Override
		public boolean hasNext() {
			if(myVersion != version) throw new ConcurrentModificationException();
			
			assert wellFormed() : "invariant broken at hasNext";
			
			if(precursor.next != tail) return true;
			
			return false;
		}

		
		
		/**
		 * Gets the next element in the collection
		 * @return element
		 * @throws ConcurrentModificationException
		 */
		@Override
		public E next() {
			if(myVersion != version) throw new ConcurrentModificationException();
			assert wellFormed() : "invariant broken at next";
			
			if(hasNext() == false) throw new NoSuchElementException();
			
			precursor = precursor.next;
			hasCurrent = true;
			
			return precursor.data;
		}

		
		
		/**
		 * removes the element returned by next()
		 * @throws IllegalStateException, ConcurrentModificationException
		 */
		@Override
		public void remove() {
			if(hasCurrent == false || hasNext() == false) throw new IllegalStateException();
			if(myVersion != version) throw new ConcurrentModificationException();

			
			Node<E> temp = getCursor();
			temp = temp.next;
			
			if(temp.data == next()) {
				temp.data = null;
			}
		}



	}

	public static class TestInvariant extends TestCase {
		protected LinkedCollection<String> self;
		protected LinkedCollection<String>.MyIterator iterator;
		protected Node<String> dummy;
		protected Node<String> n1, n2, n3;

		@Override
		protected void setUp() {
			self = new LinkedCollection<String>(false);
			iterator = self.new MyIterator(false);
			dummy = new Node<String>(null,null);
			dummy.next = dummy;
			n1 = new Node<String>("hello",null);
			n2 = new Node<String>("world",null);
			n3 = new Node<String>("bye",null);
		}

		public void testNullDummy() {
			assertFalse("null dummy",self.wellFormed());
		}

		public void testDummyNull() {
			self.tail = new Node<String>("bad",null);
			assertFalse("tail links null",self.wellFormed());
			self.tail.next = self.tail;
			assertFalse("dummy node has data",self.wellFormed());
			self.tail.data = null;
			assertTrue("empty is OK",self.wellFormed());
		}

		public void testNull() {
			self.tail = n2;
			assertFalse("null next pointer in list",self.wellFormed());
			n2.next = dummy;
			self.count = 1;
			assertFalse("wrong cyclic",self.wellFormed());
			dummy.next = n1; 
			self.count = 2;
			assertFalse("null next pointer in list",self.wellFormed());
			n1.next = n2;
			assertTrue("good list of length 2",self.wellFormed());
		}

		public void testCycles() {
			self.tail = n3;
			dummy.next = n1; n1.next = n2; n2.next = n1; n3.next = dummy;
			self.count = 3;
			assertFalse("cycle not with dummy",self.wellFormed());
			n2.next = n3; n3.next = n1;
			assertFalse("another dummy with data",self.wellFormed());
			n3.next = dummy;
			assertTrue("good list of length 3",self.wellFormed());
		}

		public void testCountOff() {
			self.tail = dummy;
			self.count = 1;
			assertFalse("dummy doesn't count",self.wellFormed());
			self.count = 0;
			dummy.next = n1;
			n1.next = dummy;
			self.tail = n1;
			assertFalse("n1 does count",self.wellFormed());
			self.count = 1;
			assertTrue("good one element list",self.wellFormed());
			n1.next = n2; n2.next = n3; n3.next = dummy;
			self.tail = n3;
			assertFalse("count two off",self.wellFormed());
			++self.count;
			assertFalse("count one off",self.wellFormed());
			++self.count;
			assertTrue("count just right",self.wellFormed());
			++self.count;
			assertFalse("count too big",self.wellFormed());
		}

		public void testEmptyIterator() {
			self.tail = dummy;
			assertFalse("null cursor",iterator.wellFormed());
			++self.version;
			assertTrue("version bad",iterator.wellFormed());
			iterator.precursor = dummy;
			++iterator.myVersion;
			assertTrue("cursor OK",iterator.wellFormed());
			n3.next = dummy;
			iterator.precursor = n3;
			iterator.hasCurrent = true;
			assertFalse("cannot remove dummy",iterator.wellFormed());
		}

		public void testIterator() {
			self.tail = n2;
			self.version += 456;
			dummy.next = n1; n1.next = n2; n3.next = n3; n2.next = dummy;
			self.count = 2;
			assertTrue(self.wellFormed());
			assertTrue(iterator.wellFormed());
			iterator.myVersion = 456;
			iterator.precursor = n1;
			assertTrue(iterator.wellFormed());
			iterator.precursor = dummy; iterator.hasCurrent = true;
			assertTrue(iterator.wellFormed());
			iterator.precursor = n3;
			assertFalse("precursor lost",iterator.wellFormed());
			++iterator.myVersion;
			assertTrue(iterator.wellFormed());
			iterator.myVersion = self.version;
			iterator.precursor = n2;
			assertFalse("cannot have current if current would be dummy.",iterator.wellFormed());
			iterator.hasCurrent = false;
			assertTrue(iterator.wellFormed());
		}

		public void testThroughIterator() {
			self.tail = n2;
			dummy.next = n1; n1.next = n2;
			iterator.precursor = n1;
			assertFalse("outer wrong",iterator.wellFormed());
		}
	}

}
