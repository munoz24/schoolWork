import java.util.Iterator;

import edu.uwm.cs351.util.LinkedCollection;


public class TestLinkedCollection extends TestCollection {
	@Override
	protected void initCollections() {
		c = new LinkedCollection<Integer>();
		cs = new LinkedCollection<String>();
	}
	
	private void ask(String question, String answer) {
		// do nothing
	}
	
	private void testquestions(int x) {
		ask("What is the code to get the last element in the list?","tail.data");
		ask("What is the code to get the dummy node?",Ts(298186125));
		ask("What is the code to get the first node in the list?",Ts(313744645));
		ask("What is the code to get the first element in the list?",Ts(1198911220));
	}
	
	public void test() {
		testquestions(-1);
	}
	
	
	// Extra tests
	public void test80() {
		LinkedCollection<Object> lc = new LinkedCollection<>();
		lc.add(lc);
		lc.add(null);
		lc.add("Apples");
		lc.add(8080);
		lc.add(new Object(){ @Override public String toString() { return "Oranges";} });
		assertEquals(5,lc.size());
		Iterator<Object> it = lc.iterator();
		it.next();
		assertNull(it.next());
		assertEquals("Apples",it.next());
	}
	
}
