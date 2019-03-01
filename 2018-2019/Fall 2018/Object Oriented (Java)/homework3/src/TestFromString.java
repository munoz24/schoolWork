import java.util.Calendar;
import java.util.TimeZone;

import edu.uwm.cs351.Appointment;
import edu.uwm.cs351.Duration;
import edu.uwm.cs351.Period;
import edu.uwm.cs351.Time;
import junit.framework.TestCase;


/**
 * Test the additions required for the classes.
 */
public class TestFromString extends TestCase {
    protected static void assertException(Class<? extends Throwable> c, Runnable r) {
    	try {
    		r.run();
    		assertFalse("Exception should have been thrown",true);
        } catch (RuntimeException ex) {
        	assertTrue("should throw exception of " + c + ", not of " + ex.getClass(), c.isInstance(ex));
        	if (c != NullPointerException.class) {
        		assertNotNull("needs a message in exception",ex.getMessage());
        		assertTrue("need a real message",ex.getMessage().length() > 0);
        	}
        }	
    }	
	
    public void testD0() {
    	assertEquals(Duration.SECOND.scale(0.25),Duration.fromString("250 ms."));
    }
    
	public void testD1() {
		assertEquals(Duration.SECOND.scale(90),Duration.fromString("1.5 min."));
	}

	public void testD2() {
		assertException(NumberFormatException.class,() -> Duration.fromString("-2 s."));
	}
	
	public void testD3() {
		assertException(NumberFormatException.class, () -> Duration.fromString(""));
	}
	
	public void testD4() {
		assertException(NumberFormatException.class, () -> Duration.fromString(" "));
	}
	
	public void testD5() {
		assertEquals(Duration.INSTANTANEOUS,Duration.fromString("0.0 years"));
	}
	
	public void testD6() {
		assertEquals(Duration.SECOND,Duration.fromString("999.6 ms."));
	}
	
	public void testD9() {
		assertException(NullPointerException.class, () -> Duration.fromString(null));		
	}
	
	
	private static Calendar lectureStart() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("America/Chicago"));
		cal.set(2018, Calendar.SEPTEMBER, 4, 9, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public void testT0() {
		assertEquals(new Time(lectureStart()), Time.fromString("CDT AD 2018/09/04 09:00:00"));
	}
	
	public void testT1() {
		Time t = new Time();
		// going to a string loses milliseconds,
		// but loss should be less than a second.
		Duration delta = t.difference(Time.fromString(t.toString()));
		assertTrue(delta.compareTo(Duration.SECOND) < 0);
	}
	
	public void testT2() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("GMT"));
		c.set(1970, Calendar.JANUARY, 1, 0, 0, 0);
		c.set(Calendar.MILLISECOND, 0);
		Time epoch = new Time(c);
		assertEquals(epoch, Time.fromString("UTC AD 1970/01/01 00:00:00"));
	}
	
	public void testT3() {
		Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		c.set(1776, Calendar.JULY, 4, 16, 28, 41);
		c.set(Calendar.MILLISECOND,0);
		Time approve = new Time(c);
		assertEquals(approve, Time.fromString("EST AD 1776/07/04 16:28:41"));
	}
	
	public void testT4() {
		assertException(NumberFormatException.class, () -> Time.fromString("UTC+2 AD -586/07/01 10:00:00"));
	}
	
	public void testT5() {
		assertException(NumberFormatException.class, () -> Time.fromString("UTC AD 2018/09/04 14:00:00 extra stuff"));
	}
	
	public void testT6() {
		assertException(NumberFormatException.class, () -> Time.fromString(""));
	}
	
	public void testT7() {
		assertException(NumberFormatException.class, () -> Time.fromString(" "));
	}
	
	public void testT9() {
		assertException(NullPointerException.class, () -> Time.fromString(null));
	}
	
	
	public void testP0() {
		Time now = new Time();
		Time nomil = Time.fromString(now.toString());
		assertEquals(new Period(nomil,Duration.HOUR),Period.fromString("["+nomil+"; 60 min.]"));
	}
	
	public void testP2() {
		Time now = new Time();
		Time nomil = Time.fromString(now.toString());
		assertException(NumberFormatException.class, () -> Period.fromString("["+nomil+"; -2 hr.]"));
	}
	
	public void testP3() {
		assertException(NumberFormatException.class,() -> Period.fromString(""));
	}
	
	public void testP4() {
		assertException(NumberFormatException.class,() -> Period.fromString("[]"));
	}
	
	public void testP5() {
		assertException(NumberFormatException.class,() -> Period.fromString("(" + new Time() + "; " + Duration.HOUR + ")"));
	}
	
	public void testP6() {
		assertException(NumberFormatException.class,() -> Period.fromString("["));
	}
	
	public void testP7() {
		assertException(NumberFormatException.class,() -> Period.fromString("]"));
	}
	
	public void testP9() {
		assertException(NullPointerException.class,() -> Period.fromString(null));
	}
	
	
	public void testA0() {
		Time lectureStart = new Time(lectureStart());
		Duration time = Duration.MINUTE.scale(50);
		assertEquals(new Appointment(new Period(lectureStart,time),""),
				Appointment.fromString("[UTC AD 2018/09/04 14:00:00; 50 min.]"));
	}
	
	public void testA1() {
		Time lectureStart = new Time(lectureStart());
		Duration time = Duration.MINUTE.scale(50);
		assertEquals(new Appointment(new Period(lectureStart,time),"CS 351"),
				Appointment.fromString("[UTC AD 2018/09/04 14:00:00; 50 min.]CS 351"));		
	}
	
	public void testA2() {
		Time lectureStart = new Time(lectureStart());
		Duration time = Duration.MINUTE.scale(50);
		assertEquals(new Appointment(new Period(lectureStart,time),"[CS 351]"),
				Appointment.fromString("[UTC AD 2018/09/04 14:00:00; 50 min.][CS 351]"));	
	}

	public void testA4() {
		assertException(NumberFormatException.class, () -> Appointment.fromString(""));
	}

	public void testA5() {
		assertException(NumberFormatException.class, () -> Appointment.fromString("]"));
	}

	public void testA6() {
		assertException(NumberFormatException.class, () -> Appointment.fromString("["));
	}

	public void testA7() {
		assertException(NumberFormatException.class, () -> Appointment.fromString("[]"));
	}

	public void testA8() {
		assertException(NumberFormatException.class, () -> Appointment.fromString(" UTC AD 2018/09/04 14:00:00; 50 min.]CS 351"));
	}

	public void testA9() {
		assertException(NullPointerException.class, () -> Appointment.fromString(null));
	}
}
