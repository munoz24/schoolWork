import java.util.function.Supplier;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Appointment;
import edu.uwm.cs351.ApptBook;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Period;
import edu.uwm.cs351.Time;


public class TestApptBook extends LockedTestCase {
	Time now = new Time();
	Appointment e1 = new Appointment(new Period(now,Duration.HOUR),"1: think");
	Appointment e2 = new Appointment(new Period(now,Duration.DAY),"2: current");
	Appointment e3 = new Appointment(new Period(now.add(Duration.HOUR),Duration.HOUR),"3: eat");
	Appointment e4 = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");
	Appointment e5 = new Appointment(new Period(now.add(Duration.DAY),Duration.DAY),"5: tomorrow");

	ApptBook s;
	
	@Override
	protected void setUp() {
		s = new ApptBook();
		try {
			assert 1/((int)e1.getTime().getLength().divide(Duration.HOUR)-1) == 42 : "OK";
			System.err.println("Assertions must be enabled to use this test suite.");
			System.err.println("In Eclipse: add -ea in the VM Arguments box under Run>Run Configurations>Arguments");
			assertFalse("Assertions must be -ea enabled in the Run Configuration>Arguments>VM Arguments",true);
		} catch (ArithmeticException ex) {
			return;
		}
	}

	protected <T> void assertException(Class<?> excClass, Supplier<T> producer) {
		try {
			T result = producer.get();
			assertFalse("Should have thrown an exception, not returned " + result,true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	protected <T> void assertException(Runnable f, Class<?> excClass) {
		try {
			f.run();
			assertFalse("Should have thrown an exception, not returned",true);
		} catch (RuntimeException ex) {
			if (!excClass.isInstance(ex)) {
				assertFalse("Wrong kind of exception thrown: "+ ex.getClass().getSimpleName(),true);
			}
		}		
	}

	/**
	 * Return the appointment as an integer
	 * <dl>
	 * <dt>-1<dd><i>(an exception was thrown)
	 * <dt>0<dd>null
	 * <dt>1<dd>e1
	 * <dt>2<dd>e2
	 * <dt>3<dd>e3
	 * <dt>4<dd>e4
	 * <dt>5<dd>e5
	 * </dl>
	 * @return integer encoding of appointment supplied
	 */
	protected int asInt(Supplier<Appointment> g) {
		try {
			Appointment n = g.get();
			if (n == null) return 0;
			return (n.getDescription().charAt(0))-'0';
		} catch (RuntimeException ex) {
			return -1;
		}
	}

	public void test00() {
		// Nothing inserted yet:
		assertEquals(Ti(1112658640),s.size());
		assertFalse(s.isCurrent());
		s.start();
		assertFalse(s.isCurrent());
	}
	
	public void test01() {
		// Initially empty.
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(Ti(1848063),asInt(() -> s.getCurrent()));
		s.insert(e1);
		assertEquals(Ti(1468946718),asInt(() -> s.getCurrent()));
		s.start();
		assertEquals(Ti(901033071),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(257085790),asInt(() -> s.getCurrent()));
	}
	
	public void test02() {
		// Initially empty.
		s.insert(e4);
		s.insert(e5);
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(Ti(1671626331),asInt(() -> s.getCurrent()));
		s.start();
		assertEquals(Ti(56523864),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(1876093076),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(-1,asInt(() -> s.getCurrent()));
	}
	
	public void test03() {
		// Initially empty
		s.insert(e3);
		s.start();
		s.insert(e2);
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(Ti(2073343044),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(2129376917),asInt(() -> s.getCurrent()));
		s.start();
		assertEquals(Ti(1068398624),asInt(() -> s.getCurrent()));
	}
	
	public void test04() {
		// Initial empty
		s.insert(e1);
		s.start();
		s.insert(e5);
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(Ti(141752869),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(70311406),asInt(() -> s.getCurrent()));
		s.insert(e3);
		assertEquals(Ti(1098573029),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(1316202468),asInt(() -> s.getCurrent()));
	}
	
	public void test05() {
		assertException(() -> s.insert(null), IllegalArgumentException.class);
		assertEquals(0,s.size());
	}
	
	public void test06() {
		s.insert(e1);
		s.insert(e2);
		s.start();
		s.advance();
		assertSame(e2,s.getCurrent());
		ApptBook s2 = new ApptBook();
		s2.insert(e4);
		s.insertAll(s2);
		assertEquals(Ti(1153117195),s.size());
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(Ti(897488737),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(429923611),asInt(() -> s.getCurrent()));
		s.advance();
		assertEquals(Ti(1034153840),asInt(() -> s.getCurrent()));
	}
	
	public void test07() {
		s.start();
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
	}
	
	/*public void test08() {
		s.start();
		try {
			s.removeCurrent();
			assertFalse("empty.removeCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
	}*/
	
	public void test09() {
		try {
			s.advance();
			assertFalse("s.advance() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
	}
	
	public void test10() {
		s.insert(e1);
		assertEquals(1,s.size());
		assertFalse(s.isCurrent());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertEquals(1,s.size());
		assertFalse(s.isCurrent());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(1,s.size());
	}

	/*public void test11() {
		s.insert(e1);
		s.start();
		s.removeCurrent();
		assertFalse(s.isCurrent());
		assertEquals(0,s.size());	
		s.insert(e2);
		s.start();
		assertSame(e2,s.getCurrent());
		assertEquals(1,s.size());
	}*/
	
	public void test12() {
		s.insert(e2);
		s.start();
		s.advance();
		try {
			s.advance();
			assertFalse("s.advance() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		assertFalse(s.isCurrent());
		assertEquals(1,s.size());
	}


	/*
	public void test13() {
		s.insert(e2);
		try {
			s.removeCurrent();
			assertFalse("s.removeCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		assertFalse(s.isCurrent());
		assertEquals(1,s.size());
	}*/

	public void test14() {
		s.insert(e2);
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		assertFalse(s.isCurrent());
		assertEquals(1,s.size());
	}

	public void test16() {
		s.insert(e4);
		s.setCurrent(e3);
		// -1 for error, 0 for null, 1 for e1, 2 for e2 ...
		assertEquals(Ti(368618530),asInt( () -> s.getCurrent()));
		// e4a is the same as e4 except it's not the same object.
		Appointment e4a = new Appointment(new Period(now.add(Duration.HOUR.scale(2)),Duration.HOUR.scale(8)),"4: sleep");
		s.setCurrent(e4a);
		assertEquals(Ti(1701171281),asInt( () -> s.getCurrent()));
		s.setCurrent(e5);
		assertEquals(Ti(1942982815),asInt( () -> s.getCurrent()));
	}
	
	public void test20() {
		s.insert(e1);
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		s.insert(e2);
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(2,s.size());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertTrue(s.isCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());
		assertEquals(2,s.size());
		s.start();
		assertSame(e1,s.getCurrent());
		s.advance();
		s.start();
		assertSame(e1,s.getCurrent());
	}
	
	public void test21() {
		s.insert(e1);
		assertFalse(s.isCurrent());
		s.start();
		s.advance();
		s.insert(e2);
		assertFalse(s.isCurrent());
		assertEquals(2,s.size());
		s.start();
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
		assertTrue(s.isCurrent());
	}
	
	/*
	 * Tests removeCurrent which is not supported in Homework #8
	 *
	public void test22() {
		s.insert(e2);
		s.insert(e1);
		s.start();
		s.removeCurrent();
		assertTrue(s.isCurrent());
		assertEquals(1,s.size());
		assertEquals(e2,s.getCurrent());
		s.insert(e3);
		s.advance();
		assertTrue(s.isCurrent());
		assertEquals(2,s.size());
		assertSame(e3,s.getCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
	}

	public void test23() {
		s.insert(e2);
		s.insert(e1);
		s.start();
		s.advance();
		s.removeCurrent();
		assertFalse(s.isCurrent());
		assertEquals(1,s.size());
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex,ex instanceof IllegalStateException);
		}
		s.insert(e3);
		assertFalse(s.isCurrent());
		assertEquals(2,s.size());
		s.start();
		assertTrue(s.isCurrent());
		assertEquals(e1,s.getCurrent());
		s.advance();
		assertEquals(e3,s.getCurrent());
	}
	
	public void test24() {
		s.insert(e1);
		assertFalse(s.isCurrent());
		s.start();
		s.insert(e1);
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e1,s.getCurrent());
	}
	
	public void test25() {
		// complete copy of e1:
		Appointment e1a = new Appointment(new Period(now,Duration.HOUR),"1: think");
		s.insert(e1);
		assertFalse(s.isCurrent());
		s.start();
		s.insert(e1a);
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e1a,s.getCurrent());
	}
	
	public void test27() {
		// complete copy of e1:
		Appointment e1a = new Appointment(new Period(now,Duration.HOUR),"1: think");
		s.insert(e1);
		s.insert(e1a);
		s.setCurrent(e1a);
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e1a,s.getCurrent());
	}
	*/
	
	public void test30() {
		s.insert(e3);
		s.insert(e1);
		s.insert(e2);
		assertEquals(3,s.size());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
		assertTrue(s.isCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertTrue(s.isCurrent());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());
		assertEquals(3,s.size());
		s.start();
		assertSame(e1,s.getCurrent());
		s.advance();
		s.start();
		assertSame(e1,s.getCurrent());
	}
	
	public void test31() {
		s.insert(e1);
		s.start();
		s.advance();
		s.insert(e4);
		s.insert(e3);
		assertEquals(3,s.size());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertTrue(s.isCurrent());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertTrue(s.isCurrent());
		assertSame(e4,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());
		assertEquals(3,s.size());
		s.start();
		assertSame(e1,s.getCurrent());
	}

	public void test32() {
		s.insert(e2);
		s.start();
		s.advance();
		s.insert(e3);
		s.start();
		s.insert(e1);
		assertEquals(3,s.size());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertTrue(s.isCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertTrue(s.isCurrent());
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());
		assertEquals(3,s.size());
	}
	
	/*
	 * Testing removeCurrent 
	 *
	public void test33() {
		s.insert(e3);
		s.insert(e2);
		s.insert(e1);
		s.start(); 
		assertEquals(e1,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertTrue(s.isCurrent());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
	}

	public void test34() {
		s.insert(e3);
		s.insert(e2);
		s.insert(e1);
		s.start(); // REDUNDANT
		s.advance();
		assertSame(e2,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertTrue(s.isCurrent());
		assertSame(e3,s.getCurrent());
	}
	
	public void test35() {
		s.insert(e3);
		s.insert(e2);
		s.insert(e1);
		s.start();
		s.advance();
		s.advance();
		assertSame(e3,s.getCurrent());
		s.removeCurrent();
		assertEquals(2,s.size());
		assertFalse(s.isCurrent());
		try {
			s.getCurrent();
			assertFalse("s.getCurrent() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex, ex instanceof IllegalStateException);
		}
		try {
			s.advance();
			assertFalse("s.advance() should not return",true);
		} catch (RuntimeException ex) {
			assertTrue("wrong exception thrown: " + ex, ex instanceof IllegalStateException);
		}
		s.start();
		assertSame(e1,s.getCurrent());
	}
	*/
 
	public void test37() {
		s.insert(e1);
		s.insert(e1); // has no effect in HW #8
		s.start();
		s.advance();
		s.insert(e1); // has no effect in HW #8
		assertFalse(s.isCurrent());
	}
	
	public void test39() {
		s.insert(e1);
		s.insert(e2);
		s.insert(e3);
		s.insert(e4);
		s.insert(e5);
		assertFalse(s.isCurrent());
		// In Homework #8: none of the following 7 inserts has any effect:
		s.insert(e1);
		s.insert(e2);
		s.insert(e3);
		s.insert(e4);
		s.insert(e5);
		s.insert(e1);
		s.insert(e2);
		assertEquals(5,s.size());
		s.start();
		// s.removeCurrent();
		assertTrue(s.isCurrent());
		s.start();
		// s.removeCurrent();
		assertSame(e1,s.getCurrent());
		assertEquals(5,s.size());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());
	}
	
	/// test4X: no longer here

	/// test5X: no longer here
	
	public void test60() {
		ApptBook se = new ApptBook();
		s.insertAll(se);
		assertFalse(s.isCurrent());
		assertEquals(0,s.size());
		s.insert(e1);
		s.start();
		s.insertAll(se);
		assertTrue(s.isCurrent());
		assertEquals(1,s.size());
		assertEquals(0,se.size());
		assertSame(e1,s.getCurrent());
		s.advance();
		s.insertAll(se);
		assertFalse(s.isCurrent());
		assertEquals(1,s.size());
		assertEquals(0,se.size());
		s.insert(e2);
		s.setCurrent(e2);
		s.insertAll(se);
		assertTrue(s.isCurrent());
		assertSame(e2,s.getCurrent());
		assertEquals(2,s.size());
		assertEquals(0,se.size());
		s.start();
		s.insertAll(se);
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(2,s.size());
		assertEquals(0,se.size());
	}
	
	public void test61() {
		ApptBook se = new ApptBook();
		se.insert(e1);
		se.start();
		s.insertAll(se);
		assertFalse(s.isCurrent());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		assertTrue(se.isCurrent());
		assertEquals(1,s.size());
		assertEquals(1,se.size());
	}
	
	public void test62() {
		ApptBook se = new ApptBook();
		se.insert(e1);
		s.insert(e2);
		s.start();
		s.insertAll(se);
		assertEquals(e2,s.getCurrent());
		s.start();
		assertTrue(s.isCurrent());
		assertEquals(2,s.size());
		assertEquals(1,se.size());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
	}
	
	public void test63() {
		ApptBook se = new ApptBook();
		se.insert(e3);
		s.insert(e2);
		s.start();
		s.advance();
		s.insertAll(se);
		assertFalse(s.isCurrent());
		assertEquals(2,s.size());
		assertEquals(1,se.size());
		assertFalse(se.isCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
	}
	
	public void test64() {
		ApptBook se = new ApptBook();
		se.insert(e1);
		s.insert(e2);
		s.insert(e3);
		s.insertAll(se);
		assertFalse(s.isCurrent());
		s.start();
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		assertEquals(3,s.size());
		assertEquals(1,se.size());
		assertFalse(se.isCurrent());
		s.advance();
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());	
	}
	
	public void test65() {
		ApptBook se = new ApptBook();
		se.insert(e3);
		s.insert(e2);
		s.start();
		s.insert(e4);
		s.insertAll(se);
		assertTrue(s.isCurrent());
		assertEquals(3,s.size());
		assertEquals(1,se.size());
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
		s.advance();
		assertSame(e4,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
	}
	
	public void test66() {
		ApptBook se = new ApptBook();
		se.insert(e5);
		se.start();
		s.insert(e2);
		s.start();
		s.insert(e3);
		s.advance();
		assertTrue(s.isCurrent());
		s.insertAll(se);
		assertTrue(s.isCurrent());
		assertEquals(e3,s.getCurrent());
		s.advance();
		assertSame(e5,s.getCurrent());
		assertEquals(3,s.size());
		assertEquals(1,se.size());
		assertSame(e5,se.getCurrent());
		s.start();
		assertSame(e2,s.getCurrent());
		s.advance();
		assertSame(e3,s.getCurrent());
		s.advance();
		assertSame(e5,s.getCurrent());
	}

	public void test67() {
		ApptBook se = new ApptBook();
		se.insert(e1);
		se.insert(e3);	
		s.insert(e2);
		s.insert(e4);
		s.insertAll(se);
		assertFalse(s.isCurrent());
		assertEquals(4,s.size());
		assertEquals(2,se.size());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());	
	}

	public void test68() {
		ApptBook se = new ApptBook();
		se.insert(e2);
		se.insert(e1);
		se.start();
		se.advance();
		s.insert(e3);
		s.start();
		s.insert(e4);
		s.insertAll(se);
		assertTrue(s.isCurrent());
		assertEquals(4,s.size());
		assertEquals(2,se.size());
		assertSame(e2,se.getCurrent()); se.advance();
		assertFalse(se.isCurrent());
		// check s
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());	
		s.start();
		assertSame(e1,s.getCurrent());
	}

	public void test69() {
		ApptBook se = new ApptBook();
		se.insert(e4);
		se.insert(e1);
		se.start();
		se.advance();
		se.advance();
		s.insert(e3);
		s.start();
		s.advance();
		s.insert(e2);
		assertFalse(s.isCurrent());
		assertFalse(se.isCurrent());
		s.insertAll(se);
		assertFalse(s.isCurrent());
		assertFalse(se.isCurrent());
		assertEquals(4,s.size());
		assertEquals(2,se.size());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());	
	}

	public void test70() {
		ApptBook se = new ApptBook();
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		se.insert(e3);
		se.insert(e4);
		se.insert(e5);
		// se has 5 elements
		s.insert(e1);
		s.insert(e2);
		s.insertAll(se);
		assertEquals(5,s.size());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		s.insertAll(se);
		assertEquals(5,s.size());
		assertSame(e4,s.getCurrent()); s.advance();		
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertSame(e3,s.getCurrent()); s.advance();
		assertSame(e4,s.getCurrent()); s.advance();
		assertSame(e5,s.getCurrent()); s.advance();
	}
	
	public void test71() {
		s.insertAll(s);
		assertFalse(s.isCurrent());
		assertEquals(0,s.size());
	}
	
	
	public void test72() {
		s.insert(e1);
		s.start();
		s.insertAll(s);
		assertEquals(1,s.size());
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent());
		s.advance();
		assertFalse(s.isCurrent());
	}
	
	public void test73() {
		s.insert(e1);
		s.start();
		s.advance();
		s.insertAll(s);
		assertEquals(1,s.size());
		assertFalse(s.isCurrent());
	}
	
	/*
	public void test74() {
		s.insert(e1);
		s.start();
		s.removeCurrent();
		assertEquals(0,s.size());
		assertFalse(s.isCurrent());
	}*/
	
	public void test75() {
		s.insert(e2);
		s.insert(e1);
		s.start();
		s.insertAll(s);
		assertEquals(2,s.size());
		assertTrue(s.isCurrent());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());		
	}
	
	public void test76() {
		s.insert(e1);
		s.start();
		s.insert(e2);
		s.advance();
		s.insertAll(s);
		assertEquals(2,s.size());
		assertTrue(s.isCurrent());
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());	
		s.start();
		assertSame(e1,s.getCurrent());
	}

	public void test77() {
		s.insert(e1);
		s.insert(e2);
		assertFalse(s.isCurrent());
		s.insertAll(s);
		assertFalse(s.isCurrent());
		assertEquals(2,s.size());
		s.start();
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e2,s.getCurrent()); s.advance();
		assertFalse(s.isCurrent());		
	}
	
	public void test79() {
		ApptBook se = new ApptBook();
		se.insert(e3);
		se.start();
		se.insert(e2);	
		s.insert(e1);
		s.insert(e5);
		s.start();
		s.advance();
		s.insertAll(se); // s = e1 e2 e3 * e5
		s.insert(e4); // s = e1 e2 e3 e4 * e5
		assertTrue(s.isCurrent());
		assertSame(e5,s.getCurrent());
		assertEquals(5,s.size());
		assertEquals(2,se.size());
		assertSame(e3,se.getCurrent());
		se.advance();
		assertFalse(se.isCurrent());
		se.start();
		assertSame(e2,se.getCurrent());
	}
	
	public void test80() {
		ApptBook c = s.clone();
		assertFalse(c.isCurrent());
		assertEquals(0, c.size());
	}
	
	public void test81() {
		s.insert(e1);
		s.start();
		ApptBook c = s.clone();
		
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e1,s.getCurrent()); s.advance();
		assertSame(e1,c.getCurrent()); c.advance();
		assertFalse(s.isCurrent());
		assertFalse(c.isCurrent());
	}
	
	public void test82() {
		s.insert(e1);
		ApptBook c = s.clone();
		
		assertFalse(s.isCurrent());
		assertFalse(c.isCurrent());
	}

	public void test83() {
		ApptBook c = s.clone();
		assertFalse(c.isCurrent());
		
		s.insert(e1);
		s.start();
		c = s.clone();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		
		s.insert(e2);
		s.advance();
		c = s.clone();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		s.advance();
		c.advance();
		assertFalse(s.isCurrent());
		assertFalse(c.isCurrent());
		
		s.insert(e3);
		assertFalse(s.isCurrent());
		assertFalse(c.isCurrent());
		
		s.start();
		s.advance();
		s.advance();
		c = s.clone();
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());
		s.advance();
		c.advance();
		assertFalse(s.isCurrent());
		assertFalse(c.isCurrent());
		s.start();
		c.start();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		s.advance();
		c.advance();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		
		s.start();
		c = s.clone();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e1,s.getCurrent());
		assertSame(e1,c.getCurrent());
		s.advance();
		c.advance();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e2,s.getCurrent());
		assertSame(e2,c.getCurrent());
		s.advance();
		c.advance();
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());		
	}
	
	/*public void test84() {
		s.insert(e1);
		s.insert(e3);
		s.insert(e2);
		s.start();
		s.advance();
		s.removeCurrent();
		
		ApptBook c = s.clone();
		
		assertEquals(2,c.size());
		
		assertTrue(s.isCurrent());
		assertTrue(c.isCurrent());
		
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());
	}*/

	public void test85() {
		s.insert(e3);
		s.insert(e4);
		
		ApptBook c = s.clone();
		s.insert(e1);
		c.insert(e2);
		
		s.start();
		c.start();
		assertSame(e1,s.getCurrent());
		assertSame(e2,c.getCurrent());
		s.advance();
		c.advance();
		assertSame(e3,s.getCurrent());
		assertSame(e3,c.getCurrent());
		s.advance();
		c.advance();
		assertSame(e4,s.getCurrent());
		assertSame(e4,c.getCurrent());
		s.advance();
		c.advance();
		assertFalse(s.isCurrent());
		assertFalse(c.isCurrent());
	}
	
}
