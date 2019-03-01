import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import junit.framework.TestCase;
import edu.uwm.cs351.Line;
import edu.uwm.cs351.Word;



public class TestLine_linux extends TestCase {

	private static final int MAX_WIDTH = 100;
	private static final Font font1 = new Font("DejaVu Serif",Font.BOLD,12);
	private static final Font font2 = new Font("DejaVu Serif",Font.ITALIC,14);
	
	Graphics g;
	Line line;
	
	@Override
	protected void setUp() {
		BufferedImage b = new BufferedImage(100,40, BufferedImage.TYPE_INT_ARGB);
		g = b.getGraphics();
		line = new Line(g,MAX_WIDTH);
	}
	
	public void testEmpty() {
		assertEquals(12,line.getHeight());
	}
	
	public void testBig() {
		assertTrue(line.add(new Word("antidisestablishmentarianism",font1)));
		assertEquals(15,line.getHeight());
	}
	
	public void testSmall() {
		assertTrue(line.add(new Word("a",font1)));
		assertTrue(line.add(new Word("b",font2)));
		assertEquals(17,line.getHeight());
		line.clear();
		assertEquals(12,line.getHeight());
		assertTrue(line.add(new Word("b",font2)));
		assertTrue(line.add(new Word("a",font1)));
		assertEquals(17,line.getHeight());
	}
	
	public void testFits2() {
		assertTrue(line.add(new Word("Compute",font1)));
		assertTrue(line.add(new Word("brox",font2)));
	}

	public void testNoFits2() {
		assertTrue(line.add(new Word("Compute",font1)));
		assertFalse(line.add(new Word("brow",font2)));
	}
	
	public void testFitsN() {
		assertTrue(line.add(new Word("0",font1)));
		assertTrue(line.add(new Word("1",font1)));
		assertTrue(line.add(new Word("2",font1)));
		assertTrue(line.add(new Word("3",font1)));
		assertTrue(line.add(new Word("4",font1)));
		assertTrue(line.add(new Word("6",font1)));
		assertTrue(line.add(new Word("7",font1)));
		assertTrue(line.add(new Word("8",font1)));
		//assertTrue(line.add(new Word("9",font1)));
		//assertTrue(line.add(new Word("0",font1)));
		line.clear();
		assertTrue(line.add(new Word("0123456789012345678901234567890",font1)));
	}

	public void testNofitsN() {
		assertTrue(line.add(new Word("0",font1)));
		assertTrue(line.add(new Word("1",font1)));
		assertTrue(line.add(new Word("2",font1)));
		assertTrue(line.add(new Word("3",font1)));
		assertTrue(line.add(new Word("4",font1)));
		assertTrue(line.add(new Word("6",font1)));
		assertTrue(line.add(new Word("7",font1)));
		assertTrue(line.add(new Word("8",font1)));
		assertFalse(line.add(new Word("9",font1)));
		assertFalse(line.add(new Word("0",font1)));
	}
}
