import java.util.Iterator;

import junit.framework.TestCase;
import edu.uwm.cs351.SymbolSet;
import edu.uwm.cs351.SymbolSet.Symbol;


public class TestEfficiency extends TestCase {
	private SymbolSet ss1;
	
	protected void setUp() throws Exception {
		try {
			assert ss1.size() == 42 : "cannot run test with assertions enabled";
		} catch (NullPointerException ex) {
			throw new IllegalStateException("Cannot run test with assertions enabled");
		}
		ss1 = new SymbolSet();
	}

	public static int SIZE = 1000000;
	
	public void testBuild() {
		for (int i=0; i<SIZE; ++i) {
			assertEquals(i,ss1.size());
			ss1.create(""+i);
		}
		assertEquals(SIZE,ss1.size());
		Iterator<Symbol> it = ss1.iterator();
		for (int i=0; i < SIZE; ++i) {
			assertTrue(it.hasNext());
			Symbol s = it.next();
			assertEquals(SIZE,ss1.size());
			assertSame(s,ss1.create(""+i));
			assertTrue(ss1.contains(s));
		}
		assertEquals((1<<21),ss1.getCapacity());
	}
}
