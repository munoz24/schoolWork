
import java.util.NoSuchElementException;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.util.Stack;


public class TestStack extends LockedTestCase {

	Stack<Integer> stack;
	
	@Override
	public void setUp(){
		stack = new Stack<Integer>();
	}

	public void test0() {
		testEmpty(false);
	}

	private void testEmpty(boolean ignored) {
		assertTrue(stack.isEmpty());
		try {
			stack.pop();
			assertFalse("pop on empty stack succeeded",true);
		} catch (RuntimeException ex) {
			assertTrue(ex instanceof NoSuchElementException);
		}
		try {
			stack.peek();
			assertFalse("peek on empty stack succeeded",true);
		} catch (RuntimeException ex) {
			assertTrue(ex instanceof NoSuchElementException);
		}
	}
	
	public void test1() {
		stack.push(42);
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(42),stack.peek());
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(42),stack.pop());
		assertTrue(stack.isEmpty());
		testEmpty(false);
	}
	
	public void test3() {
		stack.push(19);
		stack.push(25);
		stack.push(-3);
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(Ti(1496324646)),stack.peek());
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(Ti(1977775883)),stack.pop());
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(Ti(1427876916)),stack.peek());
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(Ti(460046290)),stack.pop());
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(Ti(1059969345)),stack.peek());
		assertFalse(stack.isEmpty());
		assertEquals(Integer.valueOf(Ti(2082068687)),stack.pop());
		testEmpty(false);
	}
	
	public void testMix() {
		stack.push(1776);
		stack.push(64);
		stack.push(-5);
		assertEquals(Integer.valueOf(Ti(866261628)),stack.pop());
		stack.push(0);
		stack.push(100);
		assertEquals(Integer.valueOf(Ti(1038410043)),stack.pop());
		assertEquals(Integer.valueOf(Ti(1793765237)),stack.pop());
		assertEquals(Integer.valueOf(Ti(654664386)),stack.peek());
	}
	
	public void testEfficiency() {
		for (int i=0; i < 1000000; ++i) {
			stack.push(i);
			if ((i & 3) != 0) {
				assertEquals(Integer.valueOf(i),stack.pop());
			}
		}
		assertEquals(Integer.valueOf(999996),stack.pop());
	}
}
