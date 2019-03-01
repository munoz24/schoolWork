package edu.uwm.cs351.util;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import junit.framework.TestCase;

public class TreeMap<K,V>  extends AbstractMap<K,V> {

	// Here is the data structure to use.

	private static class Node<K,V> extends DefaultEntry<K,V> {
		Node<K,V> left, right;
		Node<K,V> parent;
		Node(K k, V v) {
			super(k,v);
			parent = left = right = null;
		}
	}

	private Comparator<K> comparator;
	private Node<K,V> dummy;
	private int numItems = 0;
	private int version = 0;


	/// Invariant checks:

	private boolean showReports = true;

	private boolean report(String message) {
		if(showReports) System.err.println("Invariant error: " + message);
		return false;
	}

	private int reportNeg(String error) {
		report(error);
		return -1;
	}

	/**
	 * Return the number of nodes in the subtree rooted at the given node if keys in 
	 * this subtree are never null and are correctly sorted and are all in the range 
	 * between the lower and upper (both exclusive).
	 * If either bound is null, then that means that there is no limit at this side.
	 * @param node root of subtree to examine
	 * @param p parent of subtree to examine
	 * @param lower value that all nodes must be greater than.  If null, then
	 * there is no lower bound.
	 * @param upper value that all nodes must be less than. If null,
	 * then there is no upper bound.
	 * @return number of nodes in the subtree if the subtree is fine.  If -1 is 
	 * returned, there is a problem, which has already been reported.
	 */
	private int checkInRange(Node<K,V> node, Node<K, V> p, K lower, K upper) {
		if (node == null) return 0;
		if (node.parent != p) return reportNeg(String.format("%s is not parent of %s", p, node));
		if (node.key == null) return reportNeg(String.format("%s has null key", node));
		if (lower != null && comparator.compare(lower, node.key) >= 0) 
			return reportNeg(String.format("%s is less than %s", node, lower));
		if (upper != null && comparator.compare(node.key, upper) >= 0) 
			return reportNeg(String.format("%s is greater than %s", node, upper));
		int left = checkInRange(node.left,node,lower, node.key);
		if(left < 0) return -1;
		int right = checkInRange(node.right,node,node.key, upper);
		if(right < 0) return -1;
		return left+right+1;
	}

	/**
	 * Check the invariant, printing a message if not satisfied.
	 * @return whether invariant is correct
	 */
	private boolean wellFormed() {
		// TODO:
		// 1. check that comparator is not null
		// 2. check that dummy is not null
		// 3. check that dummy's right subtree and parent are null
		// 4. check that all (non-dummy) nodes are in range
		// 5. check that all nodes have correct parents
		// 6. check that number of items matches number of (non-dummy) nodes
		// "checkInRange" will help with 4,5,6. 

		if(comparator == null) report("comparator is null");
		if(dummy == null) report("dummy is null");

		//if(dummy.right != null || dummy.parent != null) report("dummy.right or dummy.parent is null");

		if(checkInRange(dummy, null, null, null) == -1) report("checkInRange returned -1");

		if(checkInRange(dummy, null, null, null) != numItems) report("numItems is incorrect"); 


		return true;
	}


	/// constructors

	private static class myComparator<K> implements Comparator<K>{

		@Override
		public int compare(K k1, K k2) {
			int c = k2.toString().compareTo(k1.toString());

			if(c == 0) return 0;
			else if(c < 0) return -1;

			return 1;
		}
	}




	private TreeMap(boolean ignored) {showReports = ignored;} // do not change this.

	@SuppressWarnings({ "unchecked" })
	public TreeMap() {
		// TODO: create an anonymous comparator that does the right thing, i.e.,
		// assume the key type is comparable, then call the compareTo method on it.  
		// You will need to use an unchecked cast.
		// Make sure to call the other constructor here ("this(...)")
		// so that the dummy is set up correctly.
		
		this(new myComparator<K>());
		
		assert wellFormed() : "invariant broken after constructor()";
	}



	public TreeMap(Comparator<K> c) {
		// TODO
		// Create the dummy node.

		comparator = c;
		dummy = new Node<K, V>(null, null);

		assert wellFormed() : "invariant broken after constructor(Comparator)";
	}

	@SuppressWarnings("unchecked")
	private K asKey(Object x) {
		if (dummy.left == null || x == null) return null;
		try {
			comparator.compare(dummy.left.key,(K)x);
			comparator.compare((K)x,dummy.left.key);
			return (K)x;
		} catch (ClassCastException ex) {
			return null;
		}
	}

	// TODO: many methods to override here:
	// size, containsKey(Object), get(Object), clear(), put(K, V), remove(Object)
	// make sure to use @Override and assert wellformedness
	// plus any private helper methods.
	// Our solution has getNode(Object)
	// Increase version if data structure if modified.

	@Override
	public V get(Object o) {
		Set<Entry<K,V>> temp = this.entrySet();
		Iterator<Entry<K,V>> it = temp.iterator();
		
		while(it.hasNext()) {
			Entry<K,V> value = it.next();
			if(value.getKey().equals(asKey(o))) return value.getValue();
		}
		return null;

	}

	@Override
	public V put(K k, V v) {
		assert wellFormed() : "invariant broken at start of put";
		
		Node<K,V> temp = dummy.left;
		Node<K,V> ahead = dummy;
		int c = 0;
		
		
		while(temp != null && (c = comparator.compare(temp.key, k)) != 0) {
			ahead = temp;
			if(c < 0) temp = temp.right;
			else temp = temp.left;
		}
		
		if(temp == null) {
			temp = new Node(k,v);
			
			if(ahead == null) dummy.left = temp;
			else if(c<0) {
				ahead.right = temp;
				temp.parent = ahead;
			}
			else {
				ahead.left = temp;
				temp.parent = ahead;
			}
			++numItems;
			return temp.value;
		}
		
		
		return null;

	}


	private volatile Set<Entry<K,V>> entrySet;

	@Override
	public Set<Entry<K, V>> entrySet() {
		assert wellFormed() : "invariant broken at beginning of entrySet";
		if (entrySet == null) {
			entrySet = new EntrySet();
		}
		return entrySet;
	}

	/**
	 * The set for this map, backed by the map.
	 * By "backed: we mean that this set doesn't have its own data structure:
	 * it uses the data structure of the map.
	 */
	private class EntrySet extends AbstractSet<Entry<K,V>> {
		// Do NOT add any fields! 

		@Override
		public int size() {
			// TODO: Easy: delegate to TreeMap.size()
			return TreeMap.this.size();

		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new MyIterator();
		}





		@Override
		public boolean contains(Object o) {
			assert wellFormed() : "Invariant broken at start of EntrySet.contains";
			// TODO if o is not an entry (instanceof Entry<?,?>), return false
			// Otherwise, check the entry for this entry's key.
			// If there is no such entry return false;
			// Otherwise return whether the entries match (use the equals method of the Node class). 
			// N.B. You can't check whether the key is of the correct type
			// because K is a generic type parameter.  So you must handle any
			// Object similarly to how "get" does.

			if(o instanceof Entry) return false;

			
			

			return true;

		}

		@Override
		public boolean remove(Object x) {
			// TODO: if the tree doesn't contain x, return false
			// otherwise do a TreeMap remove.
			// make sure that the invariant is true before returning.


		}

		@Override
		public void clear() {
			// TODO: Easy: delegate to the TreeMap.clear()
			entrySet.clear();

		}

	}


	/**
	 * Iterator over the map.
	 * We use parent pointers.
	 * next points to dummy indicating no more next.
	 */
	private class MyIterator implements Iterator<Entry<K,V>> {

		Node<K, V> current, next;
		int myVersion = version;

		boolean wellFormed() {
			// TODO: See Homework description for more details.  Here's a summary:
			// (1) check the outer _wellFormed()
			// (2) If version matches, do the remaining checks:
			//     (a) neither current nor next should be null; both should be in the tree (maybe dummy).
			//     (b) if current is not next, make sure it is the last node before where next is.



			if(myVersion == version) {
				if(current == null || next == null) report("iterator current or next is null");
				if(current != next) {
					Node<K, V> temp = next;
					while(hasNext()) {
						current = temp;
						temp = temp.right;
					}
					if(next != temp) return report("next is not the last since current != next");
				}

			}


			return true;
		}

		MyIterator(boolean ignored) {} // do not change this

		MyIterator() {
			// TODO: initialize current, next to the leftmost node
			Node<K,V> temp = next;

			while (temp.left != null) {
				current = temp;
				temp = temp.left;
			}

			assert wellFormed() : "invariant broken after iterator constructor";
		}


		public boolean hasNext() {
			assert wellFormed() : "invariant broken before hasNext()";
			// TODO: easy!
			if(current.right != null) return true;

			return false;
		}

		public Entry<K, V> next() {
			assert wellFormed() : "invariant broken at start of next()";
			// TODO

			if(hasNext()) {
				current = current.right;
				if(hasNext() == false) {
					next = null;
				}
				else {
					next = next.right;
				}
			}

			assert wellFormed() : "invariant broken at end of next()";
			return current;
		}

		public void remove() {
			assert wellFormed() : "invariant broken at start of iterator.remove()";
			// TODO: check that there is something to remove.
			// Use the remove method from TreeMap to remove it.
			// (See handout for details.)
			// After removal, record that there is nothing to remove any more.
			// Handle versions.
			assert wellFormed() : "invariant broken at end of iterator.remove()";
		}

	}


	/// Junit test case of private internal structure.

	public static class TestSuite extends TestCase {
		private Comparator<Integer> normal = new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return arg0 - arg1;
			}
		};
		private Comparator<Integer> backward = new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return arg1 - arg0;
			}
		};
		private Comparator<Integer> strange = new Comparator<Integer>() {
			public int compare(Integer arg0, Integer arg1) {
				return 0;
			}
		};
		private TreeMap<Integer,String> tree;
		private Node<Integer,String> n1, n2, n3, n4, n5, n3a;
		private Node<Integer,String> d;

		@Override
		protected void setUp() {
			tree = new TreeMap<Integer,String>(false);
			d = new Node<Integer,String>(-1,"DUMMY");
			n1 = new Node<Integer,String>(1,"one");
			n2 = new Node<Integer,String>(2,"two");
			n3 = new Node<Integer,String>(3,"three");
			n4 = new Node<Integer,String>(4,"four");
			n5 = new Node<Integer,String>(5,"five");
			n3a = new Node<Integer,String>(3,"three");
		}

		public void testNull() {
			tree.dummy = d;
			assertFalse("no comparator",tree.wellFormed());
			tree.comparator = normal;
			assertTrue("empty",tree.wellFormed());
			TreeMap<Integer,String>.MyIterator it = tree.new MyIterator(false);
			assertFalse("null current, next", it.wellFormed());
			it.current = it.next = d;
			assertTrue("empty",it.wellFormed());
			tree.comparator = null;
			assertFalse("no comparator",tree.wellFormed());
		}

		public void testDummy(){
			tree.comparator = normal;
			assertFalse("no dummy node", tree.wellFormed());
			TreeMap<Integer,String>.MyIterator it = tree.new MyIterator(false);
			assertFalse("no dummy node", it.wellFormed());
			tree.dummy = d;			
			assertTrue("empty", tree.wellFormed());
			assertFalse("null current, next", it.wellFormed());
			it.current = it.next = d;
			assertTrue("empty", it.wellFormed());
			d.right = n1;
			n1.parent = d;
			assertFalse("dummy has right child", tree.wellFormed());
			assertFalse("dummy has right child", it.wellFormed());			
			d.right = null;
			d.parent = n2;
			n2.right = d;
			assertFalse("dummy has parent", tree.wellFormed());
			assertFalse("dummy has parent", it.wellFormed());
			n2.left = d;
			n2.right = null;
			assertFalse("dummy has parent", tree.wellFormed());
			assertFalse("dummy has parent", it.wellFormed());			
			tree.dummy = n2;
			tree.numItems = 1;
			assertTrue("n2 is dummy", tree.wellFormed());
			assertTrue("n2 is dummy", it.wellFormed());			
		}

		public void testEmpty() {
			tree.comparator = normal;
			tree.dummy = d;
			tree.numItems = 1;
			assertFalse("should find numItems wrong",tree.wellFormed());
		}

		public void testOne() {
			tree.comparator = normal;
			tree.dummy = d;
			d.left = n3;
			n3.parent = d;
			assertFalse("should find numItems insufficient",tree.wellFormed());
			tree.numItems = 1;
			tree.comparator = strange;
			assertTrue(tree.wellFormed());
			tree.comparator = null;
			assertFalse("should find bad comparator",tree.wellFormed());
			tree.comparator = normal;
			n3.parent = null;
			assertFalse("null parent", tree.wellFormed());
			n3.parent = n2;
			assertFalse("wrong parent", tree.wellFormed());
			n2.right = n3;
			assertFalse("wrong parent", tree.wellFormed());
		}

		public void testTwo() {
			tree.comparator = normal;
			tree.dummy = d;
			d.left = n3;
			n3.parent = d;
			n3.left = n1;
			n1.parent = n3;
			tree.numItems = 1;
			assertFalse("should find numItems insufficient",tree.wellFormed());
			tree.numItems = 2;
			assertTrue(tree.wellFormed());
			tree.comparator = strange;
			assertFalse("should find duplicate",tree.wellFormed());
			tree.comparator = backward;
			assertFalse("should find out of order",tree.wellFormed());
			tree.comparator = normal;
			assertTrue(tree.wellFormed());
			n1.parent = null;
			assertFalse("null parent", tree.wellFormed());
			n1.parent = n3a;
			assertFalse("wrong parent", tree.wellFormed());
			n3a.left = n1;
			assertFalse("wrong parent", tree.wellFormed());
		}

		public void testBadOrder2() {
			tree.dummy = d;
			d.left = n3;
			n3.parent = d;
			n4.parent = n3;
			n3.left = n4;
			tree.numItems = 2;
			tree.comparator = normal;
			assertFalse("should find bad order",tree.wellFormed());
			n3.left = n3;
			assertFalse("should find duplicate",tree.wellFormed());
			n3.parent = n3;
			assertFalse("should find duplicate",tree.wellFormed());
			n3.left = null;
			n3.right = n3;
			assertFalse("should find duplicate",tree.wellFormed());
			n3.parent = d;
			assertFalse("should find duplicate",tree.wellFormed());
		}

		public void test4() {
			tree.dummy = d;
			d.left = n5;
			n5.parent = d;
			n5.left = n2; 
			n2.parent = n5;
			n2.right = n3; 
			n3.parent = n2;
			n3.left = n1; 
			n1.parent = n3;
			tree.numItems = 4;
			tree.comparator = normal;
			assertFalse("should find bad order",tree.wellFormed());

			n3.left = null; n3.right = n4; 
			assertFalse("n4.parent is null", tree.wellFormed());
			n4.parent = n3;
			assertTrue(tree.wellFormed());

			tree.numItems = 5;
			assertFalse("should find wrong count",tree.wellFormed());
			tree.numItems = 4;
			n4.parent = null;
			assertFalse("null parent", tree.wellFormed());
			n4.parent = n3a;
			assertFalse("wrong parent", tree.wellFormed());
			n3a.right = n4;
			assertFalse("wrong parent", tree.wellFormed());
		}

		public void test4a() {
			tree.dummy = d;
			d.left = n1;
			n1.parent = d;
			n1.right = n4;
			n4.parent = n1;
			n4.left  = n3;
			n3.parent = n4;
			n3.right = n5;
			n5.parent = n3;
			tree.numItems = 4;
			tree.comparator = normal;
			assertFalse("should find bad order",tree.wellFormed());

			n3.right = null; n3.left = n2;
			assertFalse("null parent", tree.wellFormed());
			n2.parent = n3;
			assertTrue(tree.wellFormed());
			n2.parent = n3a;
			assertFalse("wrong parent", tree.wellFormed());
			n3a.left = n2;
			assertFalse("wrong parent", tree.wellFormed());
		}

		public void test5() {
			tree.dummy = d;
			d.left = n3;
			n3.parent = d;
			n3.left = n2; 
			n2.parent = n3;
			n2.left = n1; 
			n1.parent = n2;
			n3.right = n5;
			n5.parent = n3;
			n5.left = n4;
			n4.parent = n5;
			tree.comparator = normal;
			for (int i=0; i < 10; ++i) {
				tree.numItems = i;
				if (i == 5) assertTrue(tree.wellFormed());
				else assertFalse("should bad num items: " + i, tree.wellFormed());
			}
			tree.numItems = 5;
			tree.comparator = strange;
			assertFalse("many duplicates",tree.wellFormed());
		}

		public void testIterator() {
			tree.dummy = d;
			d.left = n3;
			n3.parent = d;
			n3.left = n2; 
			n2.parent = n3;
			n2.left = n1; 
			n1.parent = n2;
			n3.right = n5;
			n5.parent = n3;
			n5.left = n4;
			n4.parent = n5;
			tree.comparator = normal;
			tree.numItems = 5;
			n3a.left = n2;
			n3a.right = n5;
			TreeMap<Integer,String>.MyIterator it = tree.new MyIterator(false);
			assertFalse("current, next are null",it.wellFormed());
			it.current = n5;
			assertFalse("next null",it.wellFormed());
			it.current = null; it.next = n5;
			assertFalse("current null",it.wellFormed());
			it.current = n5;
			assertTrue(it.wellFormed());
			it.current = n4;
			assertTrue(it.wellFormed());
			it.current = n3;
			assertFalse("current can't be n3 if n5 is next",it.wellFormed());
			it.next = n4;
			assertTrue(it.wellFormed());
			it.current = n3a;
			assertFalse("wrong node for n3",it.wellFormed());
			it.current = n2;
			assertFalse("current can't be n2 if next is n4",it.wellFormed());
			it.next = n3;
			assertTrue(it.wellFormed());
			it.current = n1;
			assertFalse(it.wellFormed());
			it.next = n2;
			assertTrue(it.wellFormed());
		}

		public void testIterator2() {
			tree.dummy = d;
			d.left = n4;
			n4.parent = d;
			n4.left = n2;
			n2.parent = n4;
			n4.right = n5;
			n5.parent = n4;
			n2.left = n1; 
			n1.parent = n2;
			n2.right = n3;
			n3.parent = n2;
			tree.comparator = normal;
			tree.numItems = 5;
			TreeMap<Integer,String>.MyIterator it = tree.new MyIterator(false);
			assertFalse("null references", it.wellFormed());
			it.current = it.next = d;
			assertTrue(it.wellFormed());
			it.current = n1;
			assertFalse("skip from n1 to end",it.wellFormed());
			it.current = n2;
			assertFalse("skip from n2 to end",it.wellFormed());
			it.current = n3;
			assertFalse("skip from n3 to end",it.wellFormed());
			it.next = n4;
			assertTrue(it.wellFormed());
			it.current = n3a;
			assertFalse("current is wrong node",it.wellFormed());
			it.current = n3;
			assertTrue(it.wellFormed());
			it.next = n5;
			assertFalse("missing n4",it.wellFormed());
			it.current = null;
			assertFalse("null current", it.wellFormed());
			it.current = n4;
			assertTrue(it.wellFormed());
			it.next = n3;
			assertFalse("crossed the streams",it.wellFormed());
		}
	}
}
