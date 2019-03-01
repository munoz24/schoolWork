package edu.uwm.cs351;

import junit.framework.TestCase;

/******************************************************************************
 * This class is a homework assignment;
 * A ApptBook is a collection of Appointments.
 * The sequence can have a special "current element," which is specified and 
 * accessed through four methods
 * (start, getCurrent, advance and isCurrent).
 * NB: Unlike previous incarnations of this class, it doesn't
 * include duplicates: if the same appointment is added more than once,
 * the new one is ignored.
 *
 ******************************************************************************/
public class ApptBook implements Cloneable
{
	/** 
	 * A data class used for the linked structure for the linked list implementation of ApptBook
	 */
	private static class Node {
		Appointment data;
		Node left, right;
		Node parent;

		public Node(Appointment data) {
			this.data = data;
		}
		public Node(Appointment data, Node parent) {
			this.data = data;
			this.parent = parent;
		}
	}
	// TODO: add parent pointer and 
	// (optionally) constructor taking the parent as well as data


	// The data structure:
	private int manyNodes;
	private Node root;
	private Node cursor;

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
	 * A helper method for checking the invariant:
	 * We check that all the nodes in are in the correct order.
	 * All elements in the left subtree must be less (not equal!) to the
	 * root's element, and all elements in the right subtree must be greater (not equal!).
	 * If we notice something wrong, we return false (and report the problem).
	 * @param n the subtree to check, may be null
	 * @param p the parent that it should have
	 * @param lo the lower bound (if null, there is no lower bound)
	 * @param hi the upper bound (if null, there is no upper bound)
	 */
	private boolean isInProperOrder(Node n, Node p, Appointment lo, Appointment hi) {
		if (n == null) return true;
		if (n.data == null) return report("null data found in tree");
		if (lo != null && lo.compareTo(n.data) >= 0 ||
				hi != null && hi.compareTo(n.data) <= 0) return report("data " + n.data + " is not in range [" + lo + "," + hi + "]");

		if(p != null) {
			if(!p.equals(n.parent)) return report("parent is not correct");
		}
		if(p == null) {
			if(n.parent != null) return report("parent is null but n.parent is not null");
		}

		return isInProperOrder(n.left,n,lo, n.data) && isInProperOrder(n.right,n,n.data, hi);

		// TODO update to check parent pointer including sending
		// correct pointer in recursive call.



	}

	/**
	 * A private helper function to count nodes in a subtree
	 */
	private int countNodes(Node r) {
		if (r == null) return 0;
		return 1 + countNodes(r.left) + countNodes(r.right);
	}

	/**
	 * A private helper function to determine if a node
	 * is located in the tree.
	 * This method should be efficient if the tree is balanced.
	 * @param r subtree to look for node in (may be null)
	 * @param n node to look for, must not be null, nor have null data.
	 * @return whether n is in the subtree rooted by r.
	 */
	private boolean isInSubtree(Node r, Node n) {
		if (r == null) return false;
		if (r == n) return true;
		int c = n.data.compareTo(r.data);
		if (c == 0) return false;
		if (c < 0) return isInSubtree(r.left,n);
		else return isInSubtree(r.right,n);
	}

	/**
	 * Check the invariant. 
	 * Return false if any problem is found.  Returning the result
	 * of {@link #report(String)} will make it easier to debug invariant problems.
	 * @return whether invariant is currently true
	 */
	private boolean wellFormed() {
		// Invariant:
		// 1. tree must not include a cycle (combine with next test)
		// 2. elements in tree must be in order
		// 3. total number of elements must be the same as manyNodes
		// 4. the cursor is null or points to a node in the tree

		// Implementation:
		// We use the private helper methods to do most of the work.

		if (!isInProperOrder(root, null, null, null)) return false;
		if (countNodes(root) != manyNodes) return report("count " + countNodes(root) + " not reflected in manyNodes: " + manyNodes);
		if (cursor != null) {
			if (cursor.data == null) return report("cursor data is null!");
			if (!isInSubtree(root,cursor)) return report("cursor is not in tree!");
		}

		// If no problems found, then return true:
		return true;
	}

	private ApptBook(boolean doNotUse) {} // only for purposes of testing, do not change

	/**
	 * Create an empty sequence of appointments.
	 * @param - none
	 * @postcondition
	 *   This appointment book is empty 
	 **/   
	public ApptBook( )
	{
		manyNodes = 0;
		root = cursor = null;
		assert wellFormed() : "invariant failed at end of constructor";
	}


	/**
	 * Determine the number of elements in this sequence.
	 * @param - none
	 * @return
	 *   the number of elements in this sequence
	 **/ 
	public int size( )
	{
		assert wellFormed() : "invariant wrong at start of size()";
		return manyNodes;
		// This method doesn't modify any fields, hence no assertion at end
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
		cursor = root;
		if (cursor != null) {
			while (cursor.left != null) {
				cursor = cursor.left;
			}
		}
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
	public boolean isCurrent( )
	{
		assert wellFormed() : "invariant wrong at start of getCurrent()";
		return cursor != null;
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
		if (!isCurrent()) throw new IllegalStateException("no current");
		return cursor.data;
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
		if(!isCurrent()) throw new IllegalStateException("no cursor");

		assert wellFormed() : "invariant wrong at start of advance()";
		// TODO: Implemented by student (use parent pointer)

		if(cursor.right != null) {
			cursor = cursor.right;
			while(cursor.left != null) {
				cursor = cursor.left;
			}
		} else {
			Node ahead = cursor.parent;

			while(ahead != null && cursor == ahead.right){
				cursor = ahead;
				ahead = ahead.parent;
			}
			cursor = ahead;
		}



		assert wellFormed() : "invariant wrong at end of advance()";
	}

	/**
	 * Helper method for a child to take the place of a node.
	 * @param node being replaced, must not be null
	 * @param child child subtree, may be null
	 */
	private void yieldPlaceToChild(Node node, Node child) {
		// TODO: helpful in removeCurrent


		if(node == root) {
			if(node.right == null && node.left == null) {
				root = null;
				return;
			}
			if(node.right != null) {
				root = node.right;
				root.right = null;
			}
			if(node.left != null) {
				root = node.left;
				root.left = null;
			}
			root.parent = null;
			return;
		}
		
		if(node.left == null) {
			node.parent.left = null;
		}
		if(node.right == null) {
			node.parent.right = null;
		}
		if(node.left != null) {
			
			node.left = null;
			node = child;
			
			return;

		}
		if(node.right != null) {
			node.right = null;
			node = child;
			return;
		}

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
		if(!isCurrent()) throw new IllegalStateException("No current");

		assert wellFormed() : "invariant wrong at start of removeCurrent()";
		//throw new UnsupportedOperationException("removeCurrent not implemented for Homework #8");
		// TODO: Implement this method.
		// Use parent & advance to avoid loops
		// Use yieldPlaceToChild to avoid "if"'s.  
		// One "if" remains in our solution and no loops.
		// TODO: assert wellFormed at end

		Node now = cursor;
		advance();


		//no children
		if(now.left == null && now.right == null) {

			yieldPlaceToChild(now, cursor);
		}

		//left child
		if(now.left != null && now.right == null) {

			yieldPlaceToChild(now, now.left);
		}
		//right child
		if(now.right != null && now.left == null) {

			yieldPlaceToChild(now, cursor);
		}


		--manyNodes;


		assert wellFormed() : "invariant wrong at end of removeCurrent()";

	}

	/**
	 * Set the current element to the first element that is equal
	 * or greater than the guide. 
	 * Unlike past assignments, this method must be efficient!
	 * @param guide element to compare against, must not be null.
	 */
	public void setCurrent(Appointment guide) {
		assert wellFormed() : "invariant broken at start of setCurrent";
		Node n = root;
		Node pre = null;
		int c;
		while (n != null && (c = guide.compareTo(n.data)) != 0) {
			if (c < 0) { 
				pre = n; n = n.left; 
			} else n = n.right;
		}
		if (n == null) cursor = pre;
		else cursor = n;
		assert wellFormed() : "invariant broken at end of setCurrent";
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
		// TODO: Update to handle parent pointers.
		if (element == null) throw new IllegalArgumentException("cannot insert null!");

		Node p = root, lag = null;
		int c = 0;
		while (p != null && (c = p.data.compareTo(element)) != 0) {
			lag = p;
			if (c < 0) p = p.right;
			else p = p.left;
		}


		if (p == null) {

			p = new Node(element);
			if (lag == null) root = p;
			else if (c < 0) {
				lag.right = p;
				p.parent = lag;
			}
			else {
				lag.left = p;
				p.parent = lag;
			}
			++manyNodes;
		}

		assert wellFormed() : "invariant failed at end of insert";
	}

	/**
	 * Insert all the appointments from the subtree of the node given
	 * into this appointment book.  The argument must NOT be in this book
	 * or else nontermination could happen! 
	 */
	private void insertAllHelper(Node p) {
		if (p == null) return;
		insert(p.data);
		insertAllHelper(p.left);
		insertAllHelper(p.right);
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
		insertAllHelper(addend.root);
		assert wellFormed() : "invariant failed at end of insertAll";
		assert addend.wellFormed() : "invariant of addend broken in insertAll";
	}


	// TODO: rewrite the following private helper method to handle parent pointers
	/**
	 * Clone the given subtree by creating a new subtree.
	 * If the subtree being cloned is the same node as the parameter old
	 * cursor, then set this cursor to the copy just created.
	 * @param n subtree to copy, may be null
	 * @param oldCursor old cursor, may be null
	 * @return copy of subtree
	 */
	private Node cloneSubtree(Node n, Node oldCursor) {
		if (n == null) return null;
		Node l = cloneSubtree(n.left,oldCursor);
		Node r = cloneSubtree(n.right,oldCursor);
		Node copy = new Node(n.data);
		copy.parent = n;
		copy.left = l;
		copy.right = r;
		if(r != null)
			r.parent = copy;
		if(l != null)
			l.parent = copy;
		if (n == oldCursor) cursor = copy;

		if(root == copy) copy.parent = null;

		return copy;
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

		// Now we do the hard work of cloning the tree.
		result.root = result.cloneSubtree(root, cursor);
		// TODO: Change to use modified helper method

		assert wellFormed() : "invariant wrong at end of clone()";
		assert result.wellFormed() : "invariant wrong for result of clone()";
		return result;
	}

	public static class TestInvariantChecker extends TestCase {
		ApptBook self = new ApptBook(false);
		Time now = new Time();
		Appointment h1 = new Appointment(new Period(now,Duration.HOUR),"1: think");
		Appointment h2 = new Appointment(new Period(now,Duration.DAY),"2: current");
		Appointment h3 = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat");
		Appointment h4 = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");
		Appointment h5 = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow");
		Appointment h6 = new Appointment(new Period(now.add(Duration.YEAR),Duration.HOUR),"6: next year");
		Appointment h7 = new Appointment(new Period(now.add(Duration.YEAR),Duration.DAY),"7: next year for a day");
		Appointment h8 = new Appointment(new Period(now.add(Duration.YEAR.add(Duration.DAY)),Duration.HOUR),"8: a year and a day later");
		Appointment h9 = new Appointment(new Period(now.add(Duration.YEAR.scale(2)),Duration.HOUR),"9: two years later");

		Appointment h1x = new Appointment(new Period(now,Duration.HOUR),"1: think (x)");
		Appointment h2x = new Appointment(new Period(now,Duration.DAY),"2: current (x)");
		Appointment h3x = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat (x)");
		Appointment h4x = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep (x)");
		Appointment h5x = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow (x)");
		Appointment h6x = new Appointment(new Period(now.add(Duration.YEAR),Duration.HOUR),"6: next year (x)");
		Appointment h7x = new Appointment(new Period(now.add(Duration.YEAR),Duration.DAY),"7: next year for a day (x)");
		Appointment h8x = new Appointment(new Period(now.add(Duration.YEAR.add(Duration.DAY)),Duration.HOUR),"8: a year and a day later (x)");

		@Override
		protected void setUp() {
			self = new ApptBook(false);
			doReport = false;
		}

		protected Node copyNode(Node p) {
			Node result = new Node(p.data);
			result.parent = p.parent;
			result.left = p.left;
			result.right = p.right;
			return result;
		}


		/// test Ix: is in order tests. 

		public void testI0() {
			assertEquals("null tree",true,self.isInProperOrder(null, null, null, null));
			assertEquals("null tree",true,self.isInProperOrder(null, null, null, h5));
			assertEquals("null tree",true,self.isInProperOrder(null, null, h3, null));
			assertEquals("null tree",true,self.isInProperOrder(null, null, h3, h5));
		}

		public void testI1() {
			Node a1 = new Node(h1);
			Node b2 = new Node(h2);
			assertEquals("one node tree",true,self.isInProperOrder(a1, null, null, null));
			assertEquals("one node tree",true,self.isInProperOrder(a1, null, null, h2));
			assertEquals("one node tree out of range",false,self.isInProperOrder(a1, null, h1, null));
			assertEquals("one node tree out of range",false,self.isInProperOrder(b2, null, null, h2));
			assertEquals("one node tree in range",true,self.isInProperOrder(b2, null, h1, null));
			assertEquals("one node tree in range",true,self.isInProperOrder(b2, null, h1, h3));
		}

		public void testI2() {
			Node a1 = new Node(h1);
			Node a2 = new Node(h1);
			Node b2 = new Node(h2);
			a1.left = a2;
			a2.parent = a1;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			b2.left = a2;
			assertEquals("malformed tree",false, self.isInProperOrder(b2, null, null, null));
			a2.parent = b2;
			assertEquals("OK tree (ba)",true,self.isInProperOrder(b2, null, null, null));
		}

		public void testI3() {
			Node a1 = new Node(h1);
			Node a2 = new Node(h1);
			Node a3 = new Node(h1);
			Node b2 = new Node(h2);
			a1.right = a2;
			a2.parent = a1;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			a1.right = b2;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			b2.parent = a1;
			assertEquals("OK tree (ab)",true,self.isInProperOrder(a1, null, null, null));
			a1.left=a3;
			a3.parent = a1;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			a1.left = a1.right = null;
			assertEquals("good tree",true, self.isInProperOrder(a1, null, null, null));
		}

		public void testI4() {
			Node a1 = new Node(h1);
			Node b2 = new Node(h2);
			Node c3 = new Node(h3);

			b2.left = a1;
			a1.parent = b2;
			b2.right = c3;
			c3.parent = b2;
			assertEquals("OK tree (bac)",true,self.isInProperOrder(b2, null, null, null));
			assertEquals("OK tree (bac) in range",true,self.isInProperOrder(b2, null, null, h3x));
			assertEquals("tree (bac) not in hi range",false,self.isInProperOrder(b2, null, null, h3));
			assertEquals("tree (bac) not in lo range",false,self.isInProperOrder(b2, null, h1, null));
		}

		public void testI5() {
			Node a1 = new Node(h1);
			Node a2 = new Node(h1);
			Node a3 = new Node(h1);

			a1.left = a2;
			a2.parent = a1;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			a1.left=null;
			a1.right = a2;
			a2.parent = a1;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			a1.left=a3;
			a3.parent = a1;
			assertEquals("malformed tree",false, self.isInProperOrder(a1, null, null, null));
			a1.left = a1.right = null;
			assertEquals("good tree",true, self.isInProperOrder(a1, null, null, null));
		}


		public void testI6() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);

			c.left = b; b.parent = c;
			b.left = a; a.parent = b;
			c.right = e; e.parent = c;
			e.left = d; d.parent = e;

			e.data = null;
			assertEquals("null data in tree",false, self.isInProperOrder(c, null, null, null));
			e.data = h5;
			assertEquals("good tree",true, self.isInProperOrder(c, null, null, null));

			e.left=f; f.parent = e;
			f.left=d; d.parent = f;
			assertEquals("malformed tree",false, self.isInProperOrder(c, null, null, null));
			e.left = null;
			e.right=f;
			assertEquals("malformed tree",false, self.isInProperOrder(c, null, null, null));			
			f.left = null;
			assertEquals("good tree",true, self.isInProperOrder(c, null, null, null));
			e.left=d;
			assertEquals("malformed tree",false, self.isInProperOrder(c, null, null, null));
			d.parent = e;
			assertEquals("good tree",true, self.isInProperOrder(c, null, null, null));

			Node aa = new Node(h1x);
			a.left=aa;
			aa.parent = a;
			assertEquals("malformed tree",false, self.isInProperOrder(c, null, null, null));
			a.left=null;
			a.right=aa;
			assertEquals("good tree",true, self.isInProperOrder(c, null, null, null));
		}

		public void testI7() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);

			a.right = b; b.parent = a;
			b.right = c; c.parent = b;
			c.right = d; d.parent = c;
			d.right = e; e.parent = d;
			e.left=f; f.parent = e;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			e.left=null;
			a.left=f;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			a.left=b;
			a.right=null;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			b.right=null;
			a.left=null;
			a.right=c;
			c.left=b;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			c.parent = a;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			b.parent = c;
			assertEquals("good tree",true, self.isInProperOrder(a, null, null, null));
		}

		public void testI8() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);
			Node g = new Node(h7);
			d.left = b; b.parent = d;
			d.right = f; f.parent = d;
			b.left = a; a.parent = b;
			b.right = c; c.parent = b;
			f.left = e; e.parent = f;
			f.right = g; g.parent = f;
			assertEquals("good tree",true, self.isInProperOrder(d, null, null, null));	

			Node p = new Node(h8);
			p.left = d; d.parent = p;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			d.parent = d;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			d.parent = f;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			d.parent = g;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			d.parent = a;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			d.parent = null;

			a.parent = d;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			a.parent = b;

			b.parent = null;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			b.parent = d;

			c.parent = null;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			c.parent = b;

			e.parent = e;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			e.parent = f;

			f.parent = p;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			f.parent = d;

			g.parent = p;
			assertEquals("malformed tree",false, self.isInProperOrder(a, null, null, null));
			g.parent = f;

			assertEquals("good tree",true, self.isInProperOrder(d, null, null, null));	
		}

		public void testI9() {
			Node n1 = new Node(h1);
			Node n2 = new Node(h2);
			Node n3 = new Node(h3);
			Node n4 = new Node(h4);
			n2.left = n1; n1.parent = n2;
			n2.right = n3; n3.parent = n2;
			n1.right = n2; n2.parent = n1;
			assertEquals("cyclic tree",false,self.isInProperOrder(n2, null, null, null));
			n1.right = null;
			n3.left = n2; n2.parent = n3;
			assertEquals("cyclic tree",false,self.isInProperOrder(n2, null, null, null));
			n3.left = null; n2.parent = null;
			n3.right = n3; 
			assertEquals("cyclic tree",false,self.isInProperOrder(n2, null, null, null));
			n3.right = n4; n4.parent = n3;
			assertEquals("acyclic tree",true,self.isInProperOrder(n2, null, null, null));
			n4.left = n3; n3.parent = n4;
			assertEquals("cyclic tree",false,self.isInProperOrder(n2, null, null, null));
		}


		/// testNx: testing countNodes

		public void testN1() {
			Node a = new Node(h1);
			assertEquals(1,self.countNodes(a));
		}

		public void testN2() {
			Node a1 = new Node(h1);
			Node a2 = new Node(h2);
			Node a3 = new Node(h3);
			a1.left =a2;
			a2.right = a3;
			assertEquals(3,self.countNodes(a1));
		}

		public void testN3() {
			Node a1 = new Node(h1);
			Node a2 = new Node(h2);
			Node a3 = new Node(h3);
			a1.left = a1.right = a2; a2.left = a2.right = a3;
			assertEquals(7,self.countNodes(a1));
		}


		/// testSx: tests for isInSubtree

		public void testS0() {
			Node n1 = new Node(h1);
			assertFalse(self.isInSubtree(null, n1));
		}

		public void testS1() {
			Node n1 = new Node(h1);
			assertTrue(self.isInSubtree(n1, n1));
		}

		public void testS2() {
			Node n2 = new Node(h2);
			Node n2a = new Node(h2);
			assertFalse(self.isInSubtree(n2, n2a));
		}

		public void testS3() {
			Node n2 = new Node(h2);
			Node n2a = new Node(h2);
			n2.left = n2.right = n2a; // Do not look further!
			assertFalse(self.isInSubtree(n2, n2a));
		}

		public void testS4() {
			Node n1 = new Node(h1);
			Node n2 = new Node(h2);
			Node n3 = new Node(h3);
			n2.left = n1;
			n2.right = n3;
			assertTrue(self.isInSubtree(n2, n1));
			assertTrue(self.isInSubtree(n2, n2));
			assertTrue(self.isInSubtree(n2, n3));
		}

		public void testS5() {
			Node n1 = new Node(h1);
			Node n2 = new Node(h2);
			Node n3 = new Node(h3);
			n2.left = n1;
			n2.right = n3;
			assertFalse(self.isInSubtree(n2, new Node(h1)));
			assertFalse(self.isInSubtree(n2, new Node(h2)));
			assertFalse(self.isInSubtree(n2, new Node(h3)));
		}

		public void testS6() {
			Node n1 = new Node(h1);
			Node n2 = new Node(h2);
			Node n3 = new Node(h3);
			n2.left = n1;
			n2.right = n3;
			Node n1a = new Node(h1);
			Node n2a = new Node(h2);
			Node n2b = new Node(h2);
			Node n3a = new Node(h3);
			n1.left = n1a;
			n1.right = n2a;
			n3.left = n2b;
			n3.right = n3a;
			// don't keep on looking!
			assertFalse(self.isInSubtree(n2, n1a));
			assertFalse(self.isInSubtree(n2, n2a));
			assertFalse(self.isInSubtree(n2, n2b));
			assertFalse(self.isInSubtree(n2, n3a));
		}

		public void testS7() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);

			a.right = d;
			b.right = c;
			d.left = b;
			d.right = e;
			e.right = f;

			assertTrue(self.isInSubtree(a, a));
			assertTrue(self.isInSubtree(a, b));
			assertTrue(self.isInSubtree(a, c));
			assertTrue(self.isInSubtree(a, d));
			assertTrue(self.isInSubtree(a, e));
			assertTrue(self.isInSubtree(a, f));
		}

		public void testS8() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);

			a.right = b;
			d.left = c;
			b.left = d;
			b.right = e;
			e.right = f;

			assertTrue(self.isInSubtree(a, a));
			assertTrue(self.isInSubtree(a, b));
			assertFalse(self.isInSubtree(a, c)); // lost in tree
			assertFalse(self.isInSubtree(a, d)); // lost in tree
			assertTrue(self.isInSubtree(a, e));
			assertTrue(self.isInSubtree(a, f));
		}


		/// textWx: Tests for wellFormed

		public void testW0() {
			self.manyNodes = 1;
			assertFalse(self.wellFormed());
			self.manyNodes = 0;
			doReport = true;
			assertTrue(self.wellFormed());
		}

		public void testW1() {
			self.root = new Node(h1);
			assertFalse(self.wellFormed());
			self.manyNodes = 1;
			assertTrue(self.wellFormed());
			self.manyNodes = 2;
			assertFalse(self.wellFormed());
			self.manyNodes = 1;
			self.root.data = null;
			assertFalse(self.wellFormed());
		}

		public void testW2() {
			Node a1 = new Node(h1);
			Node a2 = new Node(h1);
			Node b = new Node(h2);
			self.manyNodes = 2;
			assertFalse(self.wellFormed());
			self.root = a1;
			assertFalse(self.wellFormed());
			a1.right = a2; a2.parent = a1;
			assertEquals(false, self.wellFormed());
			a1.right = a1; a1.parent = a1;
			assertEquals(false, self.wellFormed());
			a1.right = b; a1.parent = null; b.parent = a1;
			assertEquals(true, self.wellFormed());

			b.left = a1; a1.parent = b;
			assertFalse(self.wellFormed());
			self.root = b; b.parent = null;
			assertFalse(self.wellFormed());
			a1.right = null;
			assertTrue(self.wellFormed());

			b.right = b;
			assertFalse(self.wellFormed());
		}

		public void testW3() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			c.left = a; a.parent = c;
			c.right = b; b.parent = c;
			self.root = c;

			self.manyNodes = 3;			
			assertEquals(false, self.wellFormed());
			self.manyNodes = 1;
			assertFalse(self.wellFormed());
		}

		public void testW4() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);
			Node g = new Node(h7);
			Node h = new Node(h8);
			Node i = new Node(h9);

			self.root = e;
			e.left = c; c.parent = e;
			c.right = d; d.parent = c;
			c.left = a; a.parent = c;
			a.right = b; b.parent = a;
			e.right = h; h.parent = e;
			h.left = g; g.parent = h;
			g.left = f; f.parent = g;
			h.right = i; i.parent = h;
			self.manyNodes = 9;
			//you may want to draw a picture
			assertEquals(true, self.wellFormed());

			self.manyNodes = 10;
			assertFalse("incorrect count", self.wellFormed());

			a.left = new Node(h1x); a.left.parent = a;
			assertFalse(self.wellFormed());
			a.left = null;

			b.left = new Node(h1); b.left.parent = b;
			assertFalse(self.wellFormed());
			b.left = null;
			b.right = new Node(h3x); b.right.parent = b;
			assertFalse(self.wellFormed());
			b.right = null;

			--self.manyNodes;
			assertTrue(self.wellFormed());
			++self.manyNodes;

			d.left = new Node(h2x); d.left.parent = d;
			assertFalse(self.wellFormed());
			d.left = null;
			d.right = new Node(h5x); d.right.parent = d;
			assertFalse(self.wellFormed());
			d.right = null;

			f.left = new Node(h4x); f.left.parent = f;
			assertFalse(self.wellFormed());
			f.left = null;
			f.right = new Node(h7x); f.right.parent = f;
			assertFalse(self.wellFormed());
			f.right = null;

			g.right = new Node(h8x); g.right.parent = g;
			assertFalse(self.wellFormed());
			g.right = null;

			--self.manyNodes;
			assertTrue(self.wellFormed());
			++self.manyNodes;

			i.left = new Node(h7x); i.left.parent = i;
			assertFalse(self.wellFormed());
			i.left = null;
			i.right = new Node(h8x); i.right.parent = i;
			assertFalse(self.wellFormed());
			i.right = null;

			--self.manyNodes;
			assertTrue(self.wellFormed());			
		}

		public void testW5() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);
			Node g = new Node(h7);
			Node h = new Node(h8);
			Node i = new Node(h9);

			self.root = e;
			e.left = c; c.parent = e;
			c.right = d; d.parent = c;
			c.left = a; a.parent = c;
			a.right = b; b.parent = a;
			e.right = h; h.parent = e;
			h.left = g; g.parent = h;
			g.left = f; f.parent = g;
			h.right = i; i.parent = h;
			self.manyNodes = 9;
			//you may want to draw a picture
			assertEquals(true, self.wellFormed());

			self.cursor = a;
			assertEquals(true, self.wellFormed());

			self.cursor = b;
			assertEquals(true, self.wellFormed());

			self.cursor = c;
			assertEquals(true, self.wellFormed());

			self.cursor = d;
			assertEquals(true, self.wellFormed());

			self.cursor = e;
			assertEquals(true, self.wellFormed());

			self.cursor = f;
			assertEquals(true, self.wellFormed());

			self.cursor = g;
			assertEquals(true, self.wellFormed());

			self.cursor = h;
			assertEquals(true, self.wellFormed());

			self.cursor = i;
			assertEquals(true, self.wellFormed());
		}

		public void testW6() {
			Node a = new Node(h1);
			Node b = new Node(h2);
			Node c = new Node(h3);
			Node d = new Node(h4);
			Node e = new Node(h5);
			Node f = new Node(h6);
			Node g = new Node(h7);
			Node h = new Node(h8);
			Node i = new Node(h9);

			self.root = e;
			e.left = c; c.parent = e;
			c.right = d; d.parent = c;
			c.left = a; a.parent = c;
			a.right = b; b.parent = a;
			e.right = h; h.parent = e;
			h.left = g; g.parent = h;
			g.left = f; f.parent = g;
			h.right = i; i.parent = h;
			self.manyNodes = 9;
			//you may want to draw a picture
			assertEquals(true, self.wellFormed());

			self.cursor = copyNode(a); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(b); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(c); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(d); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(e); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(f); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(g); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(h); 
			assertEquals(false, self.wellFormed());
			self.cursor = copyNode(i); 
			assertEquals(false, self.wellFormed());

			self.cursor = null;
			assertEquals(true, self.wellFormed());
		}
	}
}
