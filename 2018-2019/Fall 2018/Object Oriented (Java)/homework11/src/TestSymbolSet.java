import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.SymbolSet;
import edu.uwm.cs351.SymbolSet.Symbol;


public class TestSymbolSet extends LockedTestCase {
    protected static void assertException(Class<? extends Throwable> c, Runnable r) {
    	try {
    		r.run();
    		assertFalse("Exception should have been thrown",true);
        } catch (RuntimeException ex) {
        	assertTrue("should throw exception of " + c + ", not of " + ex.getClass(), c.isInstance(ex));
        }	
    }	

	SymbolSet ss1;
	SymbolSet ss2;
	
	@Override
	protected void setUp() throws Exception {
		try {
			assert ss1.create("Foo") == null;
			System.err.println("Assertions must be enabled to use this test suite.");
			System.err.println("In Eclipse: add -ea in the VM Arguments box under Run>Run Configurations>Arguments");
			assertFalse("Assertions must be -ea enabled in the Run Configuration>Arguments>VM Arguments",true);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		ss1 = new SymbolSet();
		ss2 = new SymbolSet();
	}

	protected String clone(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < s.length(); ++i) {
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}
	
	public void test() {
		assertEquals(Ti(1695772929),ss1.size());
		assertEquals(8,ss1.getCapacity());
		Iterator<Symbol> it = ss1.iterator();
		Symbol s1 = ss1.create("A");
		
		assertEquals(Ts(1466984781),s1.name());
		assertSame(ss1,s1.getSet());
		assertEquals(Ti(2090406436),ss1.size());
		
		assertEquals(Tb(1371297245),ss1.contains(s1));
		assertEquals(Tb(992850510),ss2.contains(s1));		
		assertEquals(Tb(2108064883),ss1.add(s1));
		
		assertEquals(Tb(1683168884),it.hasNext());
		assertSame(s1,it.next());
		assertEquals(Tb(1175669236),it.hasNext());
		
		assertFalse(ss2.iterator().hasNext());
	}
	
	
	/// test0x: tests of the empty table
	
	public void test00() {
		assertEquals(0,ss1.size());
	}
	
	public void test01() {
		assertFalse(ss1.contains("bread"));
	}
	
	public void test02() {
		assertEquals(8,ss1.getCapacity());
	}
	
	public void test03() {
		for (int i=0; i < 8; ++i) {
			assertNull(ss1.getInternal(i));
		}
	}
	
	public void test05() {
		assertFalse(ss1.contains(null));
	}
	
	public void test09() {
		assertException(IllegalArgumentException.class,() -> ss1.add(null));
	}
	
	
	/// test1X: tests with one element
	
	public void test10() {
		Symbol s = ss1.create("a");
		assertEquals("a",s.name());
	}
	
	public void test11() {
		Symbol s = ss1.create("b");
		assertSame(ss1,s.getSet());
	}
	
	public void test12() {
		ss1.create("c");
		assertEquals(1,ss1.size());
	}
	
	public void test13() {
		Symbol s = ss1.create("d");
		assertFalse(ss1.add(s));
		assertEquals(1,ss1.size());
	}
	
	public void test14() {
		Symbol s = ss1.create("e");
		assertException(IllegalArgumentException.class, () -> ss2.add(s));
	}
	
	public void test15() {
		ss1.create("f");
		assertEquals(8,ss1.getCapacity());
	}
	
	public void test16() {
		Symbol s = ss1.create("g");
		assertSame(s,ss1.getInternal(7));
	}
	
	public void test17() {
		Symbol s = ss1.create("h");
		assertSame(s,ss1.getInternal(0));
		for (int i=1; i < 8; ++i) {
			assertNull(ss1.getInternal(i));
		}
	}
	
	public void test18() {
		Symbol s = ss1.create("ABCDEFGHIJKLM");
		assertEquals(1,ss1.size());
		if (ss1.getInternal(1) == null) {
			assertSame(s,ss1.getInternal(7));
		}
	}
	
	public void test19() {
		assertException(IllegalArgumentException.class, () -> ss1.create(null));
	}
	
	
	/// test2X: test of two symbols
	
	public void test20() {
		Symbol s1 = ss1.create("A");
		Symbol s2 = ss1.create("B");
		assertEquals(2,ss1.size());
		assertNotNull(s1);
		assertNotNull(s2);
		assertEquals("B",s2.name());
	}
	
	public void test21() {
		Symbol s1 = ss1.create("C");
		Symbol s2 = ss1.create("D");
		assertEquals(8,ss1.getCapacity());
		assertSame(s1,ss1.getInternal(3));
		assertSame(s2,ss1.getInternal(4));
	}
	
	public void test22() {
		Symbol s1 = ss1.create("E");
		Symbol s2 = ss1.create("M");
		assertEquals(8,ss1.getCapacity());
		assertSame(s1,ss1.getInternal(5));
		assertSame(s2,ss1.getInternal(6));
	}
	
	public void test23() {
		Symbol s1 = ss1.create("F");
		assertSame(s1,ss1.create(clone("F")));
	}
	
	public void test24() {
		Symbol s1 = ss1.create("G");
		Symbol s2 = ss1.create("g");
		assertFalse(s1 == s2);
		assertSame(s2,ss1.getInternal(0));
	}
	
	public void test25() {
		Symbol s1 = ss1.create("H");
		Symbol s2 = ss2.create("H");
		assertFalse(s1 == s2);
		assertTrue(ss1.contains(s1));
		assertFalse(ss2.contains(s1));
		assertFalse(ss1.contains(s2));
	}
	
	
	/// test3X: testing collisions with 3 or more symbols
	
	public void test30() {
		Symbol s1 = ss1.create("0");
		Symbol s2 = ss1.create("8");
		Symbol s3 = ss1.create(" ");
		assertEquals(8,ss1.getCapacity());
		assertSame(s1,ss1.getInternal(0));
		assertSame(s2,ss1.getInternal(1));
		assertNull(ss1.getInternal(2));
		assertSame(s3,ss1.getInternal(3));
	}
	
	public void test31() {
		Symbol s1 = ss1.create("1");
		Symbol s2 = ss1.create("9");
		Symbol s3 = ss1.create("!");
		assertEquals(3,ss1.size());
		assertSame(s1,ss1.create(clone("1")));
		assertSame(s2,ss1.create(clone("9")));
		assertSame(s3,ss1.create(clone("!")));
		assertEquals(3,ss1.size());
	}
	
	public void test32() {
		Symbol s1 = ss1.create("2");
		Symbol s2 = ss1.create("3");
		Symbol s3 = ss1.create("B");
		assertSame(s1,ss1.getInternal(2));
		assertSame(s2,ss1.getInternal(3));
		assertSame(s3,ss1.getInternal(5));
	}
	
	public void test33() {
		Symbol s1 = ss1.create("3");
		Symbol s2 = ss1.create("C");
		Symbol s3 = ss1.create("4");
		assertSame(s1,ss1.getInternal(3));
		assertSame(s2,ss1.getInternal(4));
		assertSame(s3,ss1.getInternal(5));
	}
	
	public void test34() {
		Symbol s1 = ss1.create("4");
		Symbol s2 = ss1.create("D");
		Symbol s3 = ss1.create("d");
		Symbol s4 = ss1.create("L");
		assertEquals(s1,ss1.getInternal(4));
		assertEquals(s2,ss1.getInternal(5));
		assertEquals(s3,ss1.getInternal(7));
		assertEquals(s4,ss1.getInternal(2));
	}
	
	public void test35() {
		Symbol s1 = ss1.create("4");
		Symbol s2 = ss1.create("D");
		Symbol s3 = ss1.create("d");
		Symbol s4 = ss1.create("L");
		assertEquals(0,ss2.size());
		Symbol s5 = ss2.create("d");
		assertFalse(s1 == s5);
		assertFalse(s2 == s5);
		assertFalse(s3 == s5);
		assertFalse(s4 == s5);
	}
	
	public void test36() {
		Symbol s1 = ss1.create("5");
		Symbol s2 = ss1.create("6");
		Symbol s3 = ss1.create("E");
		Symbol s4 = ss1.create("F");
		assertEquals(8,ss1.getCapacity());
		assertSame(s1,ss1.getInternal(5));
		assertSame(s2,ss1.getInternal(6));
		assertSame(s3,ss1.getInternal(0));
		assertSame(s4,ss1.getInternal(7));
	}
	
	public void test37() {
		Symbol s1 = ss1.create("7");
		Symbol s2 = ss1.create("G");
		Symbol s3 = ss1.create("g");
		Symbol s4 = ss1.create("O"); // letter O (15th letter of alphabet)
		Symbol s5 = ss1.create("o");
		assertEquals(8,ss1.getCapacity());
		assertSame(s1,ss1.getInternal(7));
		assertSame(s2,ss1.getInternal(0));
		assertSame(s3,ss1.getInternal(2));
		assertSame(s4,ss1.getInternal(5));
		assertSame(s5,ss1.getInternal(1));
	}
	
	
	/// test4X: bigger tests
	
	public void test40() {
		Symbol s1 = ss1.create("0");
		Symbol s2 = ss1.create("8");
		Symbol s3 = ss1.create(" ");
		Symbol s4 = ss1.create("H");
		Symbol s5 = ss1.create("@");
		Symbol s6 = ss1.create("h");
		assertEquals(32,ss1.getCapacity());
		assertSame(s1,ss1.getInternal(16));
		assertSame(s2,ss1.getInternal(24));
		assertSame(s3,ss1.getInternal(0));
		assertSame(s4,ss1.getInternal(8));
		assertSame(s5,ss1.getInternal(1));
		assertSame(s6,ss1.getInternal(9));
	}
	
	public void test41() {
		Symbol s65 = ss1.create("A");
		Symbol s66 = ss1.create("B");
		Symbol s67 = ss1.create("C");
		Symbol s49 = ss1.create("1");
		Symbol s50 = ss1.create("2");
		
		assertEquals("A",s65.name());
		
		assertEquals(5,ss1.size());
		assertEquals(8,ss1.getCapacity());
		assertSame(s49,ss1.getInternal(4));
		assertSame(s50,ss1.getInternal(5));
		assertSame(s66,ss1.getInternal(2));
		assertSame(s67,ss1.getInternal(3));
		assertNull(ss1.getInternal(0));
		assertNull(ss1.getInternal(6));
	
		Symbol s51 = ss1.create("3");
		assertEquals(32,ss1.getCapacity());
		assertSame(s51,ss1.getInternal(19));
	
		Symbol s97 = ss1.create("a");
		assertEquals(7,ss1.size());
		assertSame(s97,ss1.getInternal(4));
	}

	public void test44() {
		for (int i='A'; i <= 'E'; ++i) {
			ss1.create(""+(char)i);
		}
		assertEquals(5,ss1.size());
		assertEquals(8,ss1.getCapacity());
		ss1.create("F");
		assertEquals(32,ss1.getCapacity());
		for (int i='G'; i <= 'U'; ++i) {
			ss1.create(""+(char)i);
		}
		assertEquals(21,ss1.size());
		assertEquals(32,ss1.getCapacity());
		ss1.create("V");
		assertEquals(128,ss1.getCapacity());
	}

	
	/// test5X: simple iterator tests
	
	public void test50() {
		assertTrue(ss1.iterator() != null);
	}
	
	public void test51() {
		assertFalse(ss1.iterator().hasNext());
	}
	
	public void test52() {
		assertException(NoSuchElementException.class, () -> ss1.iterator().next());
	}
	
	public void test53() {
		ss1.create("3");
		assertTrue(ss1.iterator().hasNext());
	}
	
	public void test54() {
		Symbol s4 = ss1.create("4");
		assertSame(s4,ss1.iterator().next());
	}
	
	public void test55() {
		ss1.create("5");
		Iterator<Symbol> it = ss1.iterator();
		it.next();
		assertFalse(it.hasNext());
	}
	
	public void test56() {
		ss1.create("6");
		Iterator<Symbol> it = ss1.iterator();
		it.next();
		assertException(NoSuchElementException.class, () -> it.next());
	}
	
	public void test57() {
		Iterator<Symbol> it = ss1.iterator();
		ss1.create("7");
		assertTrue(it.hasNext()); // not stale!
	}
	
	public void test58() {
		Iterator<Symbol> it = ss1.iterator();
		Symbol s8 = ss1.create("8");
		assertSame(s8,it.next());
	}
	
	public void test59() {
		ss1.create("9");
		Iterator<Symbol> it = ss1.iterator();
		it.next();
		assertException(UnsupportedOperationException.class, () -> it.remove());
	}
	
	
	/// test6X: bigger iterator tests
	
	public void test60() {
		Symbol s1 = ss1.create("0");
		Symbol s2 = ss1.create("8");
		Iterator<Symbol> it = ss1.iterator();
		assertTrue(it.hasNext());
		assertSame(s1,it.next());
		assertTrue(it.hasNext());
		assertSame(s2,it.next());
		assertFalse(it.hasNext());
	}
	
	public void test61() {
		Symbol s1 = ss1.create("1");
		Iterator<Symbol> it = ss1.iterator();
		Symbol s2 = ss1.create("8");
		assertTrue(it.hasNext());
		assertSame(s1,it.next());
		assertTrue(it.hasNext());
		assertSame(s2,it.next());
		assertFalse(it.hasNext());
	}
	
	public void test62() {
		Iterator<Symbol> it = ss1.iterator();
		Symbol s1 = ss1.create("6");
		Symbol s2 = ss1.create("2");
		assertTrue(it.hasNext());
		assertSame(s1,it.next());
		assertTrue(it.hasNext());
		assertSame(s2,it.next());
		assertFalse(it.hasNext());
	}
	
	public void test63() {
		Iterator<Symbol> it = ss1.iterator();
		Symbol s1 = ss1.create("6");
		assertSame(s1,it.next());
		assertFalse(it.hasNext());
		Symbol s2 = ss1.create("3");
		assertTrue(it.hasNext());
		assertSame(s2,it.next());
		assertSame(s1,ss1.create(clone("6")));
		assertFalse(it.hasNext());
		Symbol s3 = ss1.create("9");
		assertTrue(it.hasNext());
		Symbol s4 = ss1.create("0");
		assertTrue(it.hasNext());
		assertSame(s3,it.next());
		assertSame(s4,it.next());
	}
	
	
	/// test8X: combined tests.
	
	public void test82() {
		Iterator<Symbol> it1 = ss1.iterator();
		Symbol s1 = ss1.create("A");
		
		Iterator<Symbol> it2 = ss1.iterator();
		assertEquals(s1,it2.next());
		assertEquals(false,it2.hasNext());
		Symbol s2 = ss1.create("F");
		assertEquals(2,ss1.size());
		
		assertEquals(true,it1.hasNext());
		assertEquals(true,it2.hasNext());
		
		assertSame(s1,it1.next());
		assertSame(s2,it1.next());
		assertSame(s2,it2.next());
		
		assertTrue(ss1.contains(s1));
		assertTrue(ss1.contains(s2));
	
		assertSame(s1,ss1.create(clone("A")));
		assertSame(s2,ss1.create("F"));
		
		assertSame(s1,ss1.getInternal(1));
		assertSame(s2,ss1.getInternal(6));
	}
	
	public void test83() {
		Iterator<Symbol> it1 = ss1.iterator();
		Iterator<Symbol> it2 = ss2.iterator();
		Symbol s1 = ss1.create("B");
		Symbol s2 = ss2.create("C");
		
		assertSame(s1,it1.next());
		assertSame(s2,it2.next());
		assertEquals(false,it1.hasNext());
		assertEquals(false,it2.hasNext());
		
		assertEquals(true,ss1.contains(s1));
		assertEquals(false,ss1.contains(s2));
		assertEquals(false,ss2.contains(s1));
		assertEquals(true,ss2.contains(s2));
		
		assertFalse(ss1.add(s1));
		assertFalse(ss2.add(s2));
		
		try {
			ss2.add(s1);
			assertFalse("should not return", true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception type: " + ex, ex instanceof IllegalArgumentException);			
		}
	}
	
	public void test84() {
		Iterator<Symbol> it1 = ss1.iterator();
		Symbol s1 = ss1.create("A");
		
		Iterator<Symbol> it2 = ss1.iterator();
		assertEquals(s1,it2.next());
		assertEquals(false,it2.hasNext());
		Symbol s2 = ss1.create("H");
		assertEquals(2,ss1.size());
		
		assertEquals(true,it1.hasNext());
		assertEquals(true,it2.hasNext());
		
		assertSame(s1,it1.next());
		assertSame(s2,it1.next());
		assertSame(s2,it2.next());
		
		assertTrue(ss1.contains(s1));
		assertTrue(ss1.contains(s2));
	}
	
	public void test88() {
		Symbol sm1 = ss1.create("ABCDEFGHIJKLM");
		Symbol s1 = ss1.create("a");
		Symbol s7 = ss1.create("G");
		Symbol x1 = ss1.getInternal(1);
		Symbol x7 = ss1.getInternal(7);
		if (x1 == sm1) {
			assertSame(s1,ss1.getInternal(2));
		} else if (x7 == sm1) {
			assertSame(s7,ss1.getInternal(0));
		} else {
			assertSame(s1,x1);
			assertSame(s7,x7);
		}
		
		assertSame(sm1,ss1.create(clone("A") + "BCDEFGHIJKLM"));
		
		Symbol s2 = ss1.create("B");
		Symbol s3 = ss1.create("C");
		Symbol s7a = ss1.create("g");
		Symbol s1a = ss1.create("A");
		
		Iterator<Symbol> it = ss1.iterator();
		assertSame(sm1,it.next());
		assertSame(s1,it.next());
		assertSame(s7,it.next());
		assertSame(s2,it.next());
		assertSame(s3,it.next());
		assertSame(s7a,it.next());
		assertSame(s1a,it.next());
		
		assertEquals(32,ss1.getCapacity());
		x7 = ss1.getInternal(7);
		Symbol x25 = ss1.getInternal(25);
		if (x7 == null) assertSame(sm1,x25);
		else assertSame(sm1,x7);	
	}
}
