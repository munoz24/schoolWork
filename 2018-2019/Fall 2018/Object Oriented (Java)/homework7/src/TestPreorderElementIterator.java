import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.PreorderElementIterator;
import edu.uwm.cs351.util.Element;


public class TestPreorderElementIterator extends LockedTestCase {
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

	private static Element eh = new Element("h");
	private static Element ef = new Element("f","g",eh);
	private static Element ej = new Element("j");
	private static Element ei = new Element("i",ej);
	private static Element test1 = new Element("a","bc","d e",ef,ei);
	private static Iterator<Object> it;
	
	protected void setUp() {
		it = new PreorderElementIterator(test1);
	}
	
	public void test0() {
		// it points to element <a>bc d e ...</a>
		assertEquals(Tb(1574077876),it.hasNext());
	}
	
	public void test1() {
		// it points to element <a>bc d e ...</a>
		assertSame(test1,it.next());
		assertEquals(Tb(1794997492),it.hasNext());
	}
	
	public void test2() {
		// test1 = new Element("a","bc","d e",ef,ei);
		assertSame(test1,it.next());
		assertEquals(Ts(632230633),it.next());
		assertTrue(it.hasNext());
	}

	public void test3() {
		// test1 = new Element("a","bc","d e",ef,ei);
		it.next(); // "a"
		assertEquals(Ts(632230633),it.next());
		assertTrue(it.hasNext());
		assertEquals(Ts(729888243),it.next()); // don't process strings!
	}
	
	public void test4() {
		it.next(); // a
		it.next(); // bc
		it.next(); // d e
		assertSame(ef,it.next());
		assertTrue(it.hasNext());
	}
	
	public void test5() {
		it.next(); // a
		it.next(); // bc
		it.next(); // d e
		assertSame(ef,it.next());
		assertEquals("g",it.next());
		assertTrue(it.hasNext());
	}
	
	public void test6() {
		it.next(); // a
		it.next(); // bc
		it.next(); // d e
		it.next(); // <f>
		it.next(); // g
		assertSame(eh,it.next());
		assertTrue(it.hasNext()); // top iterator is done, but others have things still
	}
	
	public void test7() {
		it.next(); // a
		it.next(); // bc
		it.next(); // d e
		it.next(); // <f>
		it.next(); // g
		it.next(); // <h>
		assertSame(ei,it.next());
		assertTrue(it.hasNext());
	}
	
	public void test8() {
		it.next(); // a
		it.next(); // bc
		it.next(); // d e
		it.next(); // <f>
		it.next(); // g
		it.next(); // <h>
		it.next(); // <i>
		assertSame(ej,it.next());
		assertFalse(it.hasNext());
	}
	
	public void test9() {
		it.next(); // a
		it.next(); // bc
		it.next(); // d e
		it.next(); // <f>
		it.next(); // g
		it.next(); // <h>
		it.next(); // <i>
		it.next(); // <j>
		assertException(NoSuchElementException.class, () -> it.next());
	}
}
