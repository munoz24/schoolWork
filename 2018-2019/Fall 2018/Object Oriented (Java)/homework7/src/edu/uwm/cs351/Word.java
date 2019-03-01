package edu.uwm.cs351;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * A string and a font together.
 * Words are rendered in a graphical context with their font.
 */
public class Word {
	private final String _text;
	private final Font _font;
	
	/**
	 * Create a word with given text and font.
	 * @param t text of word, must not be null
	 * @param f font of word, must not be null
	 */
	public Word(String t, Font f) {
		if (t == null) throw new IllegalArgumentException("Word doesn't accept null string");
		if (f == null) throw new IllegalArgumentException("word doesn't accept null font");
		_text = t;
		_font = f;
	}
	
	public String getText() { return _text; }
	public Font getFont() { return _font; }
	
	@Override
	public boolean equals(Object x) {
		if (!(x instanceof Word)) return false;
		Word other = (Word)x;
		return _text.equals(other._text) && _font.equals(other._font);
	}
	
	@Override
	public int hashCode() {
		return _text.hashCode() + _font.hashCode() * 251;
	}
	
	/**
	 * Render the word in the graphical context.
	 * @param g graphics context, must not be null
	 * @param x x coordination of the place to start
	 * @param y y coordinate of the baseline
	 */
	public void draw(Graphics g, int x, int y) {
		g.setFont(_font);
		g.drawString(_text, x, y);
	}
	
	/**
	 * Get the width of the word in the given graphics context.
	 * @param g graphics context, must not be null
	 * @return width of word in graphic units (usually pixels).
	 */
	public int getWidth(Graphics g) {
		FontMetrics fm = g.getFontMetrics(_font);
		return fm.stringWidth(_text);
	}
	
	/**
	 * Get the height of the font (ascender + descender + baseline)
	 * used for this word.  The height does not depend on the text
	 * of the word.
	 * @param g graphics context, must not be null
	 * @return height of font in graphic units (usually pixels).
	 */
	public int getHeight(Graphics g) {
		FontMetrics fm = g.getFontMetrics(_font);
		return fm.getHeight();
	}
	
	@Override
	public String toString() {
		return "Word(" + _text + "," + _font + ")";
	}
}
