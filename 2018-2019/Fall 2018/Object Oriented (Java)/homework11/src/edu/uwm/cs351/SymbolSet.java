package edu.uwm.cs351;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uwm.cs.util.PowersOfTwo;
import junit.framework.TestCase;

/**
 * Manage interned strings as "Symbols".
 */
public class SymbolSet extends AbstractSet<SymbolSet.Symbol> {

	public class Symbol {
		private final String rep;
		private Symbol nextCreated;

		private Symbol(String s) {
			rep = s;
		}

		public String name() {
			return rep;
		}

		public SymbolSet getSet() {
			return SymbolSet.this;
		}

		@Override
		public String toString() {
			return "Symbol(" + rep + ")";
		}
	}

	private static final int INITIAL_CAPACITY = 8;
	private static final float FILL_FRACTION = 2/3.0f;
	private Symbol[] table; // must not be null
	private Symbol dummy;   // must not be null, must have a null rep
	private Symbol last;    // must not be null, must be reachable from dummy in created
	private int count;      // must equal the number in "created chain"
	// Additional invariant
	// - the symbols in the table must be the same as those in the "created" chain
	// - none of the symbols in the table can have null reps
	// - every symbol is in correct place in the table
	// - no two symbols can have the same rep
	// - number of used spots is no more than FILL_FRACTION times the table length;


	private static boolean showErrors = true;
	private boolean report(String message) {
		if (showErrors) System.err.println("Invariant error: " + message);
		return false;
	}

	private boolean wellFormed() {
		// TODO check that everything is OK
		// Don't get caught in infinite loops!
		// (Hint: our solution uses "count" to get out of cycles.)

		/*
		if(table == null) report("table is null");
		if(dummy == null || dummy.rep != null) report("dummy was null or dummy.rep was not null");
		if(last == null) report("last was null");
		Symbol temp = dummy;
		int i = 0;
		for(i = 0; i < count-1; ++i) {
			if(table[i] == null) report("Symbol at " + (i-1) + "was null in table");
			temp = table[i];

		}
		if(temp != last) report("dummy can not reach last");
		if(count != i+1) report("count is not accurate");

		 */
		return true;
	}

	private SymbolSet(boolean ignore) {} // do not change.  Used by invariant checker

	public SymbolSet() {
		// TODO initialize the data structure (unless init'ed at declaration)

		count = 0;
		table = new Symbol[INITIAL_CAPACITY]; 
		dummy = new Symbol(null);
		last = dummy;
		dummy.nextCreated = last;

		assert wellFormed();
	}

	// The next two methods are used by the test driver
	// to make sure your hash table is properly structured.  Don't change them.

	/**
	 * Return the size of the internal table.
	 * For debugging purposes only.
	 * @return size of internal table
	 */
	public int getCapacity() {
		return table.length;
	}
	/**
	 * Return the i'th element of the 
	 * internal table.  For debugging purposes only.
	 * @param i must be between 0 (inclusive) and capacity (exclusive)
	 */
	public Symbol getInternal(int i) {
		Symbol s = table[i];
		return s;
	}

	/**
	 * Return the symbol with the given name.
	 * If one didn't exist before, a new one is created.
	 * @param x name for symbol, must not be null
	 * @return unique symbol for this name
	 */
	public Symbol create(String x) {
		Symbol s;
		// TODO: assign s to symbol (creating only if necessary)
		// rehash if the table is overly full.
		
		if(x == null) throw new IllegalArgumentException();
		
		
		Symbol temp = new Symbol(x);
		if(contains(temp) == true) {
			Iterator<Symbol> it = this.iterator();
			Symbol forIt = null;
			while(it.hasNext()) {
				forIt = it.next();
				if(forIt.rep.equals(x)) return forIt;
			}
			
		}

		s = new Symbol(x);
		table[count] = s;
		
		++count;

		assert wellFormed();
		return s;
	}

	// TODO override things from AbstractSet
	// that cannot remain the same (add/size/contains/iterator)

	@Override
	public int size() {
		return count;
	}


	@Override
	public boolean add(Symbol s) {

		if(s == null) throw new IllegalArgumentException();
		if(this.contains(s)) return false;
		if(!this.contains(s)) throw new IllegalArgumentException();
		
		Symbol temp = table[count];
		
		if(!dummy.equals(temp)) {
			temp.nextCreated = s;
		}
		
		last = s;
		
		
		return true;
	}

	@Override
	public boolean contains(Object o) {

		
		for(int i = 0; i < count; ++i) {
			if(o.equals(table[i])) return true;
		}

		return false;
	}




	// TODO: Iterator class.  Very easy: follow the created chain.
	// remove() is unsupported.
	// Iterator should never get stale!

	@Override
	public Iterator<Symbol> iterator() {
		return new MyIterator();
	}
	public class MyIterator implements Iterator<Symbol>{
		Symbol cur;

		public MyIterator() {
			cur = dummy;
		}

		@Override
		public boolean hasNext() {
			return cur.nextCreated != null;
		}

		@Override
		public Symbol next() {
			if(hasNext()) {
				return cur = cur.nextCreated;
			}
			return null;
		}

	}



	public static class TestInternals extends TestCase {
		private SymbolSet self;

		private Symbol[] a;
		private Symbol d;
		private Symbol s0, s1, s2, s2a, s2b, s3, s3a, sn;

		@Override
		protected void setUp() {
			self = new SymbolSet(false);
			a = new Symbol[INITIAL_CAPACITY];
			d = self.new Symbol(null);
			s0 = self.new Symbol("@");
			s1 = self.new Symbol("a");
			s2 = self.new Symbol("b");
			s2a = self.new Symbol("B");
			s2b = self.new Symbol("BA".substring(0,1));
			s3 = self.new Symbol("c");
			//s4 = self.new Symbol("D");
			s3a = self.new Symbol("C");
			sn = self.new Symbol(null);
			showErrors = false;
		}

		public void testA() {
			self.table = a;
			self.dummy = d;
			self.last = d;
			showErrors = true;
			assertTrue(self.wellFormed());
			showErrors = false;
		}

		public void testB() {
			testA();
			self.table = null;
			assertFalse(self.wellFormed());
		}

		public void testC() {
			testA();
			self.dummy = null;
			self.last = null;
			assertFalse(self.wellFormed());
		}

		public void testD() {
			testA();
			self.dummy = null;
			assertFalse(self.wellFormed());
		}

		public void testE() {
			testA();
			self.last = null;
			assertFalse(self.wellFormed());
		}

		public void testF() {
			testA();
			a[3] = d;
			assertFalse(self.wellFormed());
		}

		public void testG() {
			testA();
			a[3] = sn;
			assertFalse(self.wellFormed());
		}

		public void testH() {
			testA();
			self.count = 1;
			a[0] = d;
			assertFalse(self.wellFormed());
		}

		public void testI() {
			testA();
			self.count = 1;
			a[0] = sn;
			assertFalse(self.wellFormed());
		}

		public void testJ() {
			testA();
			a[0] = s0;
			assertFalse(self.wellFormed());
		}

		public void testK() {
			testA();
			d.nextCreated = d;
			assertFalse(self.wellFormed());
		}

		public void testM(){
			self.table = a;
			self.dummy = d;
			d.nextCreated = s2;
			self.last = s2;
			a[2] = s2;
			self.count = 1;
			showErrors = true;
			assertTrue(self.wellFormed());
			showErrors = false;
		}

		public void testN() {
			testM();
			self.count = 2;
			assertFalse(self.wellFormed());
		}

		public void testO() {
			testM();
			self.last = d;
			assertFalse(self.wellFormed());
		}

		public void testP() {
			testM();
			self.last = d;
			d.nextCreated = null;
			assertFalse(self.wellFormed());
		}

		public void testQ() {
			testM();
			a[5] = sn;
			assertFalse(self.wellFormed());
		}

		public void testR() {
			testM();
			s2.nextCreated = s3;
			self.last = s3;
			assertFalse(self.wellFormed());
		}

		public void testS(){
			self.table = a;
			self.dummy = d;
			d.nextCreated = s2a;
			s2a.nextCreated = s2;
			s2.nextCreated = s3;
			self.last = s3;
			a[2] = s2;
			a[3] = s3;
			a[5] = s2a;
			self.count = 3;
			showErrors = true;
			assertTrue(self.wellFormed());
			showErrors = false;
		}

		public void testT() {
			testS();
			a[5] = s2b;
			assertFalse(self.wellFormed());
		}

		public void testU() {
			testS();
			a[2] = s2b;
			s2a.nextCreated = s2b;
			self.last = s2b;
			assertFalse(self.wellFormed());
		}

		public void testV() {
			testS();
			a[3] = null;
			a[4] = s3;
			assertFalse(self.wellFormed());
		}

		public void testW() {
			self.table = a;
			self.dummy = d;
			d.nextCreated = s2;
			s2.nextCreated = s3;
			s3.nextCreated = s3a;
			s3a.nextCreated = s2a;
			self.last = s2a;
			a[2] = s2;
			a[3] = s2a;
			a[4] = s3;
			a[6] = s3a;
			self.count = 4;
			showErrors = true;
			assertTrue(self.wellFormed());
			showErrors = false;
		}

		public void testX() {
			testW();
			Symbol s3b = self.new Symbol("c");
			s2.nextCreated = sn;
			sn.nextCreated = s3.nextCreated;
			assertFalse(self.wellFormed());
			s2.nextCreated = s3b;
			s3b.nextCreated = s3.nextCreated;
			assertFalse(self.wellFormed());
			s2.nextCreated = s3;
			self.last.nextCreated = s3b;
			self.last = s3b;
			assertFalse(self.wellFormed());
		}

		public void testY() {
			testW();
			a[3] = null;
			a[5] = s2a;
			assertFalse(self.wellFormed());
			a[3] = s2a;
			assertFalse(self.wellFormed());
			a[3] = sn;
			assertFalse(self.wellFormed());
			a[3] = s2a;
			a[5] = null;
			self.count = 3;
			assertFalse(self.wellFormed());
		}

		public void testZ() {
			testW();
			a[0] = s0;
			assertFalse(self.wellFormed());
			self.count = 5;
			assertFalse(self.wellFormed());
			self.last.nextCreated = s0;
			assertFalse(self.wellFormed());
			self.last = s0;
			showErrors = true;
			assertTrue(self.wellFormed());
			showErrors = false;
			a[1] = s1;
			self.last.nextCreated = s1;
			self.last = s1;
			assertFalse(self.wellFormed());
			self.count = 6;
			assertFalse(self.wellFormed());
		}
	}


}
