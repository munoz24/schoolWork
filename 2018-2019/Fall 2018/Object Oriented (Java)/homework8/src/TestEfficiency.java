import java.util.Random;

import edu.uwm.cs351.Appointment;
import edu.uwm.cs351.ApptBook;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Period;
import edu.uwm.cs351.Time;
import junit.framework.TestCase;


/**
 * Efficiency tests for ApptBook.
 */
public class TestEfficiency extends TestCase {
	private Time now = new Time();
	
	private Random random;
	
	private static final int POWER = 20; // 1/2 million entries
	private static final int TESTS = 100_000;
	private static final int SIZE = (1<<(POWER-1))-1;
	
	protected Appointment a(int i) {
		return new Appointment(new Period(now,Duration.MILLISECOND.scale(i)),i+"");
	}
	
	private ApptBook book;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		random = new Random();
		try {
			assert book.size() == TESTS : "cannot run test with assertions enabled";
		} catch (NullPointerException ex) {
			System.out.println("Go to Run > Run Configurations");
			System.out.println("Then go to the 'Arguments' tab.");
			System.out.println("Remove '-ea' from the VM Arguments box.");
			throw new IllegalStateException("Cannot run test with assertions enabled")
;
		}
		book = new ApptBook();
		int max = (1 << (POWER)); // 2^(POWER) = 2 million
		for (int power = POWER; power > 1; --power) {
			int incr = 1 << power;
			for (int i=1 << (power-1); i < max; i += incr) {
				book.insert(a(i));
			}
		}
	}


	public void testSize() {
		for (int i=0; i < TESTS; ++i) {
			assertEquals(SIZE,book.size());
		}
	}
	
	public void testStart() {
		for (int i=0; i < TESTS; ++i) {
			book.start();
			assertEquals(a(2),book.getCurrent());
			book.advance();
			assertEquals(a(4),book.getCurrent());
		}
	}
	
	public void testCursor() {
		book.start();
		for (int i=1; i <= SIZE; ++i) {
			assertEquals(a(i*2),book.getCurrent());
			book.advance();
		}
		assertFalse(book.isCurrent());
	}
	
	public void testSetCursor() {
		for (int i=0; i < TESTS; ++i) {
			int j = random.nextInt(SIZE-1)+1;
			int jp = (j+1)&~1;
			book.setCurrent(a(j));
			assertEquals(a(jp),book.getCurrent());
		}
	}
	
	public void testInsertAll() {
		ApptBook copy = new ApptBook();
		copy.insert(a(500_001));
		copy.insertAll(book);
		assertEquals(SIZE+1,copy.size());
	}
	
	public void testClone() {
		ApptBook copy = book.clone();
		copy.insert(a(101));
		assertEquals(SIZE+1,copy.size());
	}
}
