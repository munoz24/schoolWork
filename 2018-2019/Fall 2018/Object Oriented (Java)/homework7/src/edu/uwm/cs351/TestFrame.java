package edu.uwm.cs351;

import java.awt.Font;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class TestFrame extends FormattedTextFrame {

	public TestFrame() {}
	
	/**
	 * Keep Eclipse happy
	 */
	private static final long serialVersionUID = 1L;
	
	private final Font normal = new Font("Serif", Font.PLAIN, 20);
	private final Font bold = new Font("Serif", Font.BOLD, 20);
	private final Font italic = new Font("Serif", Font.ITALIC,20);
	private final Font bigger = new Font("Serif", Font.PLAIN, 24);
	
	private final Word[] _words = new Word[] {
			new Word("This", normal),
			new Word("is", normal),
			new Word("a",normal),
			new Word("bold",bold),
			new Word("step",normal),
			new Word("into",normal),
			new Word("the",normal),
			new Word("unknown.",italic),
			null,
			new Word("This",bigger),
			new Word("is",bigger),
			new Word("bigger.",bigger)
	};
	
	@Override
	protected Iterator<Word> getWords() {
		return Arrays.asList(_words).iterator();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame f = new TestFrame();
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			f.setSize(300,200);
			f.setVisible(true);
		});
	}
}
