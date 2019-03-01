package edu.uwm.cs351;

import java.io.BufferedInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import edu.uwm.cs351.util.Element;
import edu.uwm.cs351.util.XMLParseException;
import edu.uwm.cs351.util.XMLReader;

public class SimpleBrowser extends FormattedTextFrame {

	/**
	 *  Keep Eclipse Happy
	 */
	private static final long serialVersionUID = 1L;
	private Element contents;
	
	public SimpleBrowser(String url) {
		contents = new Element("html","Nothing");
		if (url != null) {
			try {
				Reader r= new InputStreamReader(new BufferedInputStream(new URL(url).openStream()));
				final XMLReader t = new XMLReader(r);
				t.addCDATA("script");
				Object next = t.next();
				if (next instanceof Element) {
					contents = (Element)next;
					if (!contents.getName().equalsIgnoreCase("html")) {
						throw new XMLParseException("element must be HTML not " + contents.getName());
					}
				} else {
					throw new XMLParseException("contents must be HTML, not " + next);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error opening URL", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected Iterator<Word> getWords() {
		return new HTMLWordIterator(contents);
	}

	public static void main(String[] args) {
		int width = 300, height = 600;
		String url = "https://en.wikipedia.org/wiki/Main_Page";
		for (int i=0; i < args.length; ++i) {
			if (args[i].startsWith("-")) {
				String opt = args[i];
				if (opt.startsWith("--width=")) {
					width = Integer.parseInt(opt.substring("--width=".length()));
				} else if (opt.startsWith("--height=")) {
					height = Integer.parseInt(opt.substring("--height=".length()));
				} else if (opt.startsWith("--url=")) {
					url = opt.substring("--url=".length());
				} else {
					System.err.println("Unknown option: " + opt);
				}
			} else {
				url = args[i];
			}
		}
		// for weird Java reasons:
		String url2 = url;
		int width2 = width, height2 = height;
		SwingUtilities.invokeLater(() -> {
			JFrame f = new SimpleBrowser(url2);
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			f.setSize(width2,height2);
			f.setVisible(true);
		});
	}
}
