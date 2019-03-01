
import java.util.NoSuchElementException;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.util.Queue;


public class TestQueue extends LockedTestCase {

	Queue<Integer> queue;
	
	@Override
	public void setUp(){
		queue = new Queue<Integer>();
	}

	public void test0() {
		testEmpty(false);
	}

	private void testEmpty(boolean ignored) {
		assertTrue(queue.isEmpty());
		try {
			queue.remove();
			assertFalse("remove on empty stack succeeded",true);
		} catch (RuntimeException ex) {
			assertTrue(ex instanceof NoSuchElementException);
		}
	}
	
	public void test1() {
		queue.add(42);
		assertFalse(queue.isEmpty());
		assertEquals(Integer.valueOf(42),queue.remove());
		assertTrue(queue.isEmpty());
		testEmpty(false);
	}
	
	public void test3() {
		queue.add(19);
		queue.add(25);
		queue.add(-3);
		assertFalse(queue.isEmpty());
		assertEquals(Integer.valueOf(Ti(269043123)),queue.remove());
		assertFalse(queue.isEmpty());
		assertEquals(Integer.valueOf(Ti(1745481133)),queue.remove());
		assertFalse(queue.isEmpty());
		assertEquals(Integer.valueOf(Ti(1808159128)),queue.remove());
		testEmpty(false);
	}
	
	public void testMix() {
		queue.add(1776);
		queue.add(64);
		queue.add(-5);
		assertEquals(Integer.valueOf(Ti(1960231677)),queue.remove());
		queue.add(0);
		queue.add(100);
		assertEquals(Integer.valueOf(Ti(19816166)),queue.remove());
		assertEquals(Integer.valueOf(Ti(1674778120)),queue.remove());
		assertEquals(Integer.valueOf(Ti(1117752334)),queue.remove());
		assertEquals(Integer.valueOf(Ti(1048044772)),queue.remove());
		testEmpty(false);
	}
	
	public void testEfficiency() {
		for (int i=0; i < 1000000; ++i) {
			queue.add(i);
			if ((i & 3) != 0) {
				queue.remove();
			}
		}
		assertEquals(Integer.valueOf(750000),queue.remove());
	}
}
