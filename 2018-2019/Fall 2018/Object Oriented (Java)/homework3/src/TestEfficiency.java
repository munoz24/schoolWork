import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import edu.uwm.cs351.Appointment;
import edu.uwm.cs351.Calendar;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Period;
import edu.uwm.cs351.Time;


public class TestEfficiency extends TestCase {
	private static final int POWER = 20; // 1/2 million entries
	private static final int TESTS = 100_000;

	private Time now = new Time();
	private Duration threesec = Duration.SECOND.scale(3);
	private Random random;
	private Calendar cal;
	
	protected Time t(int i) {
		return now.add(Duration.SECOND.scale(i));
	}
	
	protected Appointment a(int i) {
		return new Appointment(new Period(t(i),threesec),"Test " + i);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		random = new Random();
		try {
			assert cal.size() == TESTS : "cannot run test with assertions enabled";
		} catch (NullPointerException ex) {
			throw new IllegalStateException("Cannot run test with assertions enabled");
		}
		cal = new Calendar();
		int max = (1 << (POWER)); // 2^(POWER) = 2 million
		for (int power = POWER; power > 1; --power) {
			int incr = 1 << power;
			for (int i=1 << (power-1); i < max; i += incr) {
				cal.add(a(i));
			}
		}
	}

	public void testIT() {
		for (int i=0; i < TESTS; ++i) {
			int n = random.nextInt(1 << POWER);
			n &= ~1; // make n even
			if (n == 0) continue; // unlikely
			Time t = t(n);
			Iterator<Appointment> it = cal.starting(t);
			assertEquals(a(n),it.next());
		}
	}

	public void testRC() {
		List<Appointment> as = cal.removeConflicts();
		assertEquals(now.add(Duration.SECOND.scale(4)),as.get(0).getTime().getStart());
		assertEquals((1<<(POWER-2))-1,as.size());
		assertEquals((1<<(POWER-2)),cal.size());
	}
}
