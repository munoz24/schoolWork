
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uwm.cs.junit.LockedTestCase;

/*
 * Abstract class for testing collections.  Do not try to run it on its own.
 */
public abstract class TestCollection extends LockedTestCase {

	protected <T> void assertException(Class<?> excClass, Runnable f) {
		try {
			f.run();
			assertFalse("Should have thrown an exception, not returned",true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	protected Collection<Integer> c;
	protected Collection<String> cs;
	
	private Iterator<Integer> it;
	
	protected <T> void testcol(Collection<T> l, String name, @SuppressWarnings("unchecked") T... parts)
	{
		assertEquals(name + ".size()",parts.length,l.size());
		Iterator<T> it = l.iterator();
		int i=0;
		while (it.hasNext() && i < parts.length) {
			assertEquals(name + ".next[" + i + "]",parts[i],it.next());
			++i;
		}
		assertFalse(name + ".hasNext() is true, even after " + parts.length + " next() calls",it.hasNext());
		assertFalse(name + ".hasNext() is false, after only " + i + " next() calls",(i < parts.length));
	}

	@Override
	protected void setUp() {
		try {
			assert c.size() == cs.size();
			assertTrue("Assertions not enabled.  Add -ea to VM Args Pane in Arguments tab of Run Configuration",false);
		} catch (NullPointerException ex) {
			assertTrue(true);
		}
		initCollections();
	}
	
	/**
	 * Initialize c and cs.
	 */
	protected abstract void initCollections();
	
	
	/// test0X: tests of add/size without using iterators.
	
	public void test00() {
		assertEquals(0,c.size());
	}
	
	public void test01() {
		c.add(1);
		assertEquals(1,c.size());
	}
	
	public void test02() {
		c.add(1);
		c.add(4);
		assertEquals(2,c.size());
	}
	
	public void test03() {
		c.add(2);
		c.add(5);
		c.add(8);
		assertEquals(3,c.size());
	}
	
	public void test04() {
		c.add(null);
		c.add(5);
		assertEquals(2,c.size());
	}
	
	public void test05() {
		c.add(null);
		c.add(null);
		c.add(null);
		assertEquals(3,c.size());
	}
	
	public void test06() {
		c.add(6);
		c.add(5);
		c.add(4);
		c.add(3);
		c.add(2);
		c.add(1);
		assertEquals(6,c.size());
	}
	

	public void test07() {	
		cs.add("Hello");
		cs.add("Bye");
		assertEquals(2,cs.size());
	}
	
	
	/// test1X: test of hasNext

	public void test10() {
		it = c.iterator();
		assertFalse(it.hasNext());
	}
	
	public void test11() {
		c.add(1);
		it = c.iterator();
		assertTrue(it.hasNext());
	}
	
	public void test12() {
		c.add(3);
		c.add(6);
		it = c.iterator();
		assertTrue(it.hasNext());
	}
	
	public void test13() {
		c.add(null);
		it = c.iterator();
		assertTrue(it.hasNext());
	}
	
	public void test14() {
		it = c.iterator();
		c.add(4);
		assertException(ConcurrentModificationException.class,() -> it.hasNext());
	}
	
	public void test15() {
		c.add(5);
		it = c.iterator();
		c.add(6);
		assertException(ConcurrentModificationException.class,() -> it.hasNext());
	}
	
	public void test16() {
		Iterator<Integer> it2 = c.iterator();
		c.add(6);
		it = c.iterator();
		assertTrue(it.hasNext());
		assertException(ConcurrentModificationException.class,() -> it2.hasNext());
	}
	
	public void test17() {
		c.add(7);
		Iterator<Integer> it2 = c.iterator();
		c.add(8);
		it = c.iterator();
		assertTrue(it.hasNext());
		assertException(ConcurrentModificationException.class,() -> it2.hasNext());
	}
	
	public void test18() {
		c.add(8);
		Iterator<Integer> it2 = c.iterator();
		it = c.iterator();
		assertTrue(it.hasNext());
		assertTrue(it2.hasNext());
	}
	
	public void test19() {
		it = c.iterator();
		assertFalse(it.hasNext());
		it = c.iterator();
		assertFalse(it.hasNext());
		c.add(null);
		it = c.iterator();
		assertTrue(it.hasNext());
		it = c.iterator();
		assertTrue(it.hasNext());
	}
	
	
	/// test2X: test of iterator next
	
	public void test20() {
		it = c.iterator();
		assertException(NoSuchElementException.class,() -> it.next());
	}
	
	public void test21() {
		c.add(21);
		it = c.iterator();
		assertEquals(21,it.next().intValue());
	}
	
	public void test22() {
		c.add(22);
		it = c.iterator();
		it.next(); // for side-effect
		assertException(NoSuchElementException.class,() -> it.next());
	}
	
	public void test23() {
		c.add(2);
		c.add(3);
		it = c.iterator();
		assertEquals(2,it.next().intValue());
	}
	
	public void test24() {
		c.add(2);
		c.add(4);
		it = c.iterator();
		it.next(); // for side-effect
		assertEquals(4,it.next().intValue());
	}
	
	public void test25() {
		c.add(5);
		c.add(2);
		it = c.iterator();
		it.next();
		it.next();
		assertException(NoSuchElementException.class,() -> it.next());
	}
	
	public void test26() {
		c.add(6);
		c.add(7);
		Iterator<Integer> it2 = c.iterator();
		it = c.iterator();
		assertEquals(6, it.next().intValue());
		assertEquals(6, it2.next().intValue());
		assertEquals(7, it.next().intValue());
	}
	
	public void test27() {
		it = c.iterator();
		c.add(72);
		assertException(ConcurrentModificationException.class, () -> it.next());
	}
	
	public void test28() {
		c.add(2);
		it = c.iterator();
		assertEquals(2,it.next().intValue());
		c.add(4);
		it = c.iterator();
		assertEquals(2,it.next().intValue());
		c.add(6);
		Iterator<Integer> it2 = c.iterator();
		assertException(ConcurrentModificationException.class, () -> it.next());
		assertEquals(2,it2.next().intValue());
		it = c.iterator();
		assertEquals(4,it2.next().intValue());
		c.add(8);
		assertException(ConcurrentModificationException.class, () -> it.next());
		assertException(ConcurrentModificationException.class, () -> it2.next());
	}
	
	public void test29() {
		c.add(3);
		c.add(6);
		c.add(9);
		it = c.iterator();
		assertEquals(3,it.next().intValue());
		Iterator<Integer> it2 = c.iterator();
		Iterator<Integer> it3 = c.iterator();
		assertEquals(3,it3.next().intValue());
		assertEquals(6,it.next().intValue());
		assertEquals(9,it.next().intValue());
		assertEquals(6,it3.next().intValue());
		assertEquals(3,it2.next().intValue());
		assertException(NoSuchElementException.class,() -> it.next());
	}
	
	
	/// test3X: test iterators using "testcol"
	
	public void test30() {
		testcol(c,"empty");
	}
	
	public void test31()
	{
		c.add(1);
		testcol(c,"{1}",1);
	}
	
	public void test32() {
		c.add(10);
		c.add(4);
		testcol(c,"{10,4}",10,4);
	}
	
	public void test33() {
		c.add(1);
		c.add(4);
		c.add(-5);
		testcol(c,"{1,4,-5}",1,4,-5);
	}
	
	public void test34() {
		c.add(8);
		c.add(34);
		c.add(-1);
		c.add(0);
		testcol(c,"{8,34,-1,0}",8,34,-1,0);
	}
	
	public void test35() {
		cs.add("Hello,");
		cs.add(null);
		cs.add("world!");
		testcol(cs,"{Hello, null world!}","Hello,",null,"world!");
	}
	
	
	/// text4X: testing clear
	
	public void test40() {
		c.clear();
		assertEquals(0,c.size());
	}
	
	public void test41() {
		c.add(1);
		c.clear();
		assertFalse(c.iterator().hasNext());
	}
	
	public void test42() {
		c.add(4);
		c.add(2);
		c.clear();
		it = c.iterator();
		assertException(NoSuchElementException.class,() -> it.next());
	}
	
	public void test43() {
		it = c.iterator();
		c.add(3);
		c.clear();
		assertException(ConcurrentModificationException.class, () -> it.hasNext());
	}
	
	public void test44() {
		it = c.iterator();
		c.add(4);
		c.add(4);
		c.clear();
		assertException(ConcurrentModificationException.class, () -> it.next());
	}
	
	public void test45() {
		c.add(4);
		c.add(5);
		c.clear();
		assertEquals(0,c.size());
		c.add(45);
		assertEquals(1,c.size());
	}
	
	public void test46() {
		c.add(4);
		c.add(46);
		it = c.iterator();
		c.clear();
		assertException(ConcurrentModificationException.class, () -> it.hasNext());
		assertException(ConcurrentModificationException.class, () -> it.next());
		it = c.iterator();
		assertFalse(it.hasNext());
		assertException(NoSuchElementException.class,() -> it.next());
	}
	
	
	/// test5X: tests of remove
	
	public void test50() {
		c.add(15);
		it = c.iterator();
		it.next();
		it.remove();
		assertFalse(it.hasNext());
		testcol(c,"{15} after remove(15)");
	}
	
	public void test51() {
		c.add(8);
		c.add(-4);
		it = c.iterator();
		it.next();
		it.remove();
		testcol(c,"{8,-4} after remove(8)",-4);
		assertTrue(it.hasNext());
		assertEquals(new Integer(-4),it.next());
	}
	
	public void test52() {
		c.add(8);
		c.add(-4);
		it = c.iterator();
		it.next();
		it.next();
		it.remove();
		testcol(c,"{8,-4} after remove(-4)",8);
		assertFalse(it.hasNext());
	}
	
	public void test53() {
		c.add(6);
		c.add(11);
		c.add(-3);
		it = c.iterator();
		it.next();
		it.remove();
		testcol(c,"{6,11,-3} after remove(6)",11,-3);
		assertTrue(it.hasNext());
		assertEquals(11,it.next().intValue());		
	}
	
	public void test54() {
		c.add(6);
		c.add(11);
		c.add(-3);
		it = c.iterator();
		it.next();
		it.next();
		it.remove();
		testcol(c,"{6,11,-3} after remove(11)",6,-3);
		assertTrue(it.hasNext());
		assertEquals(-3,it.next().intValue());		
	}

	public void test55() {
		c.add(6);
		c.add(11);
		c.add(-3);
		it = c.iterator();
		it.next();
		it.next();
		it.next();
		it.remove();
		testcol(c,"{6,11,-3} after remove(-3)",6,11);
		assertFalse(it.hasNext());	
	}

	public void test56() {
		c.add(6);
		c.add(11);
		c.add(-3);
		it = c.iterator();
		it.next(); it.remove();
		it.next(); it.remove();
		testcol(c,"{6,11,-3} after remove(6,11)",-3);
		assertTrue(it.hasNext());	
		assertEquals(-3,it.next().intValue());		
	}

	public void test57() {
		c.add(6);
		c.add(11);
		c.add(-3);
		it = c.iterator();
		it.next(); it.remove();
		it.next();
		it.next(); it.remove();
		testcol(c,"{6,11,-3} after remove(6,-3)",11);
		assertFalse(it.hasNext());	
	}

	public void test58() {
		c.add(6);
		c.add(11);
		c.add(-3);
		it = c.iterator();
		it.next(); it.remove();
		it.next(); it.remove();
		it.next(); it.remove();
		testcol(c,"{6,11,-3} after remove(6,11,-3)");
		assertFalse(it.hasNext());	
	}

	public void test59() 
	{
		Collection<String> c = cs;
		String x1 = "Apples", x2 = "Bread", x3 = "Cheese", x4 = "Doughnuts";

		testcol(c,"empty #2");
		
		c.add(x1);
		c.add(x2);
		c.add(x3);
		c.add(x4);
		testcol(c,"ABCD",x1,x2,x3,x4);
		
		Iterator<String> it = c.iterator();
		it.next();
		it.next();
		it.remove();
		assertTrue("Two more after B removed",it.hasNext());
		assertEquals("Next after B removed",x3,it.next());
		assertTrue("One more and next() after B removed",it.hasNext());
		assertEquals("Next after next() after B removed",x4,it.next());
		assertTrue("Only two more after B removed",!it.hasNext());		
		testcol(c,"ACD",x1,x3,x4);
		
		c.clear();
		testcol(c,"cleared");
		
	}
	
	
	/// test6X: complex tests with remove
	
	public void test60() {
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		
		it = c.iterator();
		it.next();
		it.remove();
		assertTrue("Three more after 1 removed",it.hasNext());
		assertEquals("Next after 1 removed",2,it.next().intValue());
		testcol(c,"{X1,2,3,4}",2,3,4);
		assertTrue("Two more after next() and 1 removed",it.hasNext());
		assertEquals("Next after next() after 1 removed",3,it.next().intValue());
		it.remove();
		testcol(c,"{X1,2,X3,4}",2,4);
		assertTrue("One more after 1,3 removed",it.hasNext());
		assertEquals("Next after 1,3 removed",4,it.next().intValue());
		it.remove();
		testcol(c,"{X1,2,X3,X4}",2);
		assertTrue("No more after 1,3,4 removed",!it.hasNext());
		
		it = c.iterator();
		it.next();
		it.remove();
		testcol(c,"all removed");
		assertTrue("No more after everything removed",!it.hasNext());
		
	}
	
	public void test61() {
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		
		it= c.iterator();
		it.next();
		it.remove();
		testcol(c,"{X1,2,3,4}",2,3,4);
		it = c.iterator();
		it.next();
		it.remove();
		testcol(c,"{X1,X2,3,4}",3,4);
		it = c.iterator();
		it.next();
		it.next();
		it.remove();
		testcol(c,"{X1,X2,3,X4}",3);
		it = c.iterator();
		it.next();
		it.remove();
		testcol(c,"all removed again");
	}

	
	/// test7X: remove errors
	
	public void test70()
	{
		it = c.iterator();
		assertException(IllegalStateException.class,() -> it.remove());
	}
	
	public void test71()
	{
		c.add(15);
		it = c.iterator();
		assertException(IllegalStateException.class,() -> it.remove());
		testcol(c,"still {15}",15);
	}
	
	public void test72()
	{
		c.add(-9);
		it = c.iterator();
		it.next();
		it.remove();
		assertException(NoSuchElementException.class, () -> it.next());
		testcol(c,"{X-9}");
	}
	
	public void test73()
	{
		c.add(109);
		c.add(45);
		it = c.iterator();
		it.next();
		it.remove();
		assertException(IllegalStateException.class,() -> it.remove());
		testcol(c,"{X109,45}",45);
	}
	
	public void test74()
	{
		c.add(99);
		c.add(8);
		it = c.iterator();
		Iterator<Integer> it2 = c.iterator();
		it.next();
		it2.next();
		it.remove();
		assertException(ConcurrentModificationException.class, () -> it2.hasNext());
		assertException(ConcurrentModificationException.class, () -> it2.next());
		assertException(ConcurrentModificationException.class, () -> it2.remove());
		assertTrue("after remove() after first remove(), hasNext() should still be true",it.hasNext());
		testcol(c,"{X99,8}",8);		
	}
	
	public void test75() {
		c.add(19);
		c.add(80);
		it = c.iterator();
		it.next();
		it.remove();
		
		Iterator<Integer> it2 = c.iterator();
		it.next();
		it.remove();
		
		assertException(ConcurrentModificationException.class, () -> it2.hasNext());
		assertException(ConcurrentModificationException.class, () -> it2.next());
		assertException(ConcurrentModificationException.class, () -> it2.remove());

		assertException(IllegalStateException.class, () -> it.remove());
		
		assertTrue("after remove() after second remove(), hasNext() should still be false",(!it.hasNext()));
		testcol(c,"{X19,X80}");
	}


}
