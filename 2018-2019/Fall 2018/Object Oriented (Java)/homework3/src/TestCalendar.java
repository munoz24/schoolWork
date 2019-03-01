import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import edu.uwm.cs.junit.LockedTestCase;
import edu.uwm.cs351.Appointment;
import edu.uwm.cs351.Calendar;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Period;
import edu.uwm.cs351.Time;

public class TestCalendar extends LockedTestCase {

	private Calendar cal;
	private Time tA = Time.fromString(new Time().toString());
	private Time tB = tA.add(Duration.HOUR);
	private Time tC = tB.add(Duration.HOUR);
	
	private Appointment a00 = new Appointment(new Period(tA,Duration.INSTANTANEOUS),"");
	private Appointment b00 = new Appointment(new Period(tB,Duration.INSTANTANEOUS),"");

	private Appointment a0 = new Appointment(new Period(tA,Duration.INSTANTANEOUS),"a0");
	private Appointment a1 = new Appointment(new Period(tA,Duration.HOUR),"a1");
	private Appointment a2 = new Appointment(new Period(tA,Duration.HOUR.scale(2)),"a2");
	private Appointment a3 = new Appointment(new Period(tA,Duration.HOUR.scale(3)),"a3");
	
	private Appointment b0 = new Appointment(new Period(tB,Duration.INSTANTANEOUS),"b0");
	private Appointment b1 = new Appointment(new Period(tB,Duration.HOUR),"b1");
	private Appointment b2 = new Appointment(new Period(tB,Duration.HOUR.scale(2)),"b2");
	private Appointment b3 = new Appointment(new Period(tB,Duration.HOUR.scale(3)),"b3");
	
	private Appointment c0 = new Appointment(new Period(tC,Duration.INSTANTANEOUS),"c0");
	private Appointment c1 = new Appointment(new Period(tC,Duration.HOUR),"c1");
	private Appointment c2 = new Appointment(new Period(tC,Duration.HOUR.scale(2)),"c2");
	private Appointment c3 = new Appointment(new Period(tC,Duration.HOUR.scale(3)),"c3");
	
	private StringWriter output;
	private PrintWriter outputCollector;
	
	public void setUp() {
		cal = new Calendar();
		output = new StringWriter();
		outputCollector = new PrintWriter(output);
	}
	
	public String[] getOutput() {
		outputCollector.close();
		List<String> results = new ArrayList<>();
		BufferedReader br = new BufferedReader(new StringReader(output.toString()));
		String s;
		try {
			while ((s = br.readLine()) != null) {
				results.add(s);
			}
		} catch (IOException e) {
			assertFalse("Internal Error",true);
		}
		return results.toArray(new String[results.size()]);
	}
	
	protected static String toString(Object... as) {
		StringBuilder sb = new StringBuilder();
		for (Object a : as) {
			sb.append(a);
			sb.append(System.lineSeparator());
		}
		String string = sb.toString();
		return string;
	}

	protected static Scanner makeScanner(Object... as) {
		String string = toString(as);
		return new Scanner(new StringReader(string));
	}
	
	protected static void assertContents(Collection<?> col, Object... as) {
		assertEquals(Arrays.toString(as) + ".size != " + col + ".size()",as.length,col.size());
		Iterator<?> it = col.iterator();
		for (Object a : as) {
			assertEquals(a,it.next());
		}
	}

	
	// Locked Tests
	
	public void test() {
		// testing doImport
		cal.add(b2);
		cal.doImport(makeScanner(a0,c0,"Garbage",b1), outputCollector);
		String[] errors = getOutput();
		// How many appointments in the calendar now?
		assertEquals(Ti(50362525),cal.size());
		// How many error messages were generated?
		assertEquals(Ti(971122595),errors.length);
		testcont(false);
	}
	
	private void testcont(boolean ignored) {
		// testing starting
		cal.clear();
		cal.add(a0); // at Time "tA"
		cal.add(a3); // at Time "tA"
		cal.add(c2); // at Time "tC"
		Iterator<Appointment> it = cal.starting(tA);
		// Which appointment do we get? a0, a3 or c2 ?
		assertEquals(Ts(1473802904),it.next().getDescription());
		it = cal.starting(tB);
		assertEquals(Ts(1378950157),it.next().getDescription());
		it = cal.starting(tC);
		assertEquals(Ts(1897458935),it.next().getDescription());
		testcont2(false);
	}
	
	private void testcont2(boolean ignored) {
		cal.clear();
		cal.add(a2); // two hours long at tA
		cal.add(b2); // two hours long at tA+(1 hr.)
		List<Appointment> conflicts = cal.removeConflicts();
		assertEquals(Ti(1271922408),conflicts.size());
		// give the name of the removed appointment:
		assertEquals(Ts(1338484779),conflicts.get(0).getDescription());
		assertEquals(Ti(505959939),cal.size()); // how many left?
		cal.clear(); // start over
		cal.add(a3); // three hours long at tA
		cal.add(b1); // one hour long at tA+(1 hr.)
		cal.add(c1); // one hour long at tA+(2 hr.)
		conflicts = cal.removeConflicts();
		assertEquals(Ti(321721433),conflicts.size()); // how many conflicts?
		assertEquals(Ti(1008304723),cal.size()); // how many left?
		// who is left?
		assertEquals(Ts(28435724),cal.iterator().next().getDescription());
	}
	
	
	/// test1X: tests of import

	public void test10() {
		cal.doImport(makeScanner(a3), outputCollector);
		String[] errors = getOutput();
		assertEquals(0,errors.length);
		assertContents(cal,a3);
	}
	
	public void test11() {
		cal.doImport(makeScanner(), outputCollector);
		String[] errors = getOutput();
		assertEquals(0,errors.length);
		assertContents(cal);
	}
	
	public void test12() {
		cal.doImport(makeScanner(c2,a0),outputCollector);
		String[] errors = getOutput();
		assertEquals(0,errors.length);
		assertContents(cal,a0,c2);
	}
	
	public void test13() {
		cal.doImport(makeScanner(a0,a1,b0,c2), outputCollector);
		String[] errors = getOutput();
		assertEquals(0,errors.length);
		assertContents(cal,a0,a1,b0,c2);
	}
	
	public void test14() {
		cal.add(b1);
		cal.doImport(makeScanner(a0,a1,b0,c2), outputCollector);
		String[] errors = getOutput();
		assertEquals(0,errors.length);
		assertContents(cal,a0,a1,b0,b1,c2);
	}
	
	public void test15() {
		cal.doImport(makeScanner(a2,"Bad",b0), outputCollector);
		String[] errors = getOutput();
		assertEquals(1,errors.length);
		assertContents(cal,a2,b0);
	}
	
	public void test16() {
		cal.add(b3);
		cal.add(a0);
		cal.add(c3);
		cal.doImport(makeScanner(a2,c3,"Bad",b0,"[]Who?"), outputCollector);
		String[] errors = getOutput();
		assertEquals(2,errors.length);
		assertContents(cal,a0,a2,b0,b3,c3);
	}
	
	
	/// test2X: test of doExport
	
	public void test20() {
		cal.doExport(outputCollector);
		String[] output = getOutput();
		assertEquals(0,output.length);
	}
	
	public void test21() {
		cal.add(b3);
		cal.doExport(outputCollector);
		String[] output = getOutput();
		assertEquals(1,output.length);
		assertEquals(b3.toString(),output[0]);
	}
	
	public void test22() {
		cal.add(a1);
		cal.add(c0);
		cal.doExport(outputCollector);
		String[] output = getOutput();
		assertEquals(2,output.length);
		assertEquals(a1.toString(),output[0]);
		assertEquals(c0.toString(),output[1]);
	}
	
	
	/// test3X: test of starting
	
	public void test30() {
		cal.add(b2);
		Iterator<Appointment> it = cal.starting(tA);
		assertEquals(b2,it.next());
	}
	
	public void test31() {
		cal.add(b2);
		Iterator<Appointment> it = cal.starting(tB);
		assertSame(b2,it.next());		
	}
	
	public void test32() {
		cal.add(b2);
		Iterator<Appointment> it = cal.starting(tC);
		assertFalse(it.hasNext());
	}
	
	public void test33() {
		cal.add(a3);
		cal.add(c1);
		Iterator<Appointment> it = cal.starting(tB);
		assertEquals(2,cal.size());
		assertEquals(c1,it.next());
	}
	
	public void test34() {
		cal.add(b0);
		cal.add(c0);
		Iterator<Appointment> it = cal.starting(tB);
		assertSame(b0,it.next());
	}
	
	public void test35() {
		cal.add(a00);
		cal.add(a0);
		cal.add(b00);
		cal.add(b0);
		Iterator<Appointment> it = cal.starting(tB);
		assertSame(b00,it.next());
	}
	
	public void test36() {
		cal.add(a0);
		cal.add(a1);
		cal.add(b0);
		cal.add(b2);
		cal.add(c2);
		cal.add(c3);
		Iterator<Appointment> it = cal.starting(tA.subtract(Duration.YEAR));
		assertEquals(a0,it.next());
		it = cal.starting(tA.add(Duration.YEAR));
		assertFalse(it.hasNext());
	}
	
	public void test40() {
		cal.add(a1);
		cal.add(b1);
		cal.add(c1);
		// no conflicts!
		assertContents(cal.removeConflicts());
		assertEquals(3,cal.size());
	}
	
	public void test41() {
		assertContents(cal.removeConflicts());
	}
	
	public void test42() {
		cal.add(a1);
		cal.add(a2);
		assertContents(cal.removeConflicts(),a2);
		assertContents(cal,a1);
	}
	
	public void test43() {
		cal.add(a3);
		cal.add(b0);
		cal.add(b2);
		cal.add(c1);
		assertContents(cal.removeConflicts(),b0,b2,c1);
	}
	
	public void test44() {
		cal.add(a2);
		cal.add(b2);
		cal.add(c2);
		assertContents(cal.removeConflicts(),b2);
		assertContents(cal,a2,c2);
	}
	
	public void test45() {
		cal.add(a0);
		cal.add(b0);
		cal.add(c3);
		// no conflicts!
		assertContents(cal.removeConflicts());
		assertContents(cal,a0,b0,c3);
	}
}
