package edu.uwm.cs351;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * A container for words getting ready for rendering.
 * We have a fixed width that we try to fits words within.
 * WHen a line is rendered, the line can be filled with space
 * so that the text is justified on both sides (assuming at least two words)
 * or it can have a ragged right right.  There is a minimum spacing
 * between words with more added to fill the line, if desired.
 */
public class Line {
	// TODO: declare fields here.
	// We recommend use an array list to hold words.
	// You will need other fields too.
	
	List<Word> _words = new ArrayList<Word>();
	
	int _mw;
	Graphics _g;
	
	public static final int MIN_SPACE = 4;
	
	/**
	 * Create a line using the given graphics context
	 * (needed so that we can find the width of words)
	 * and window width.
	 * @param g graphic context, must not be null
	 * @param mw width of window (maximum width of line)
	 */
	public Line(Graphics g, int mw) {
		// TODO
		_g = g;
		_mw = mw;
	}
	
	/**
	 * Clear the line, and get ready for some new words. 
	 */
	public void clear() {
		// TODO
		
		_words.remove(_words.size()-1);
		
	}
	
	/**
	 * Try to add a word to the line.  If it doesn't fit,
	 * then return false (and don't add it) so that the line
	 * can be printed and cleared before adding it.  
	 * As a special exception, however,
	 * if the line is currently empty, any word can be added.
	 * (If the line is empty, it wouldn't help to clear and 
	 * add again later.  It still wouldn't fit.)
	 * <p>
	 * There is space to add a word to a non-empty line
	 * if the sum of the widths of all words in the line plus
	 * the width of the word plus minimum spacing 
	 * <em>between</em> all words is less than or equal to
	 * the window size being used.
	 * @param w word to add
	 * @return whether word could be added
	 */
	public boolean add(Word w) {
		// TODO: complete this method
		
		//this is not right
		if(_words.size() >= 10) return false;
		
		_words.add(w);
		return true;
		
	}
	
	public static final int MIN_HEIGHT = 12;
	
	/**
	 * The height of a line.  The height
	 * is the maximum of heights of all words
	 * and the {@link #MIN_HEIGHT}.
	 * @return maximum height of any word in the line.
	 */
	public int getHeight() {
		// TODO
		Word w;
		int wSize = 0;
		int temp = 0;
		
		for(int i = 0; i < _words.size(); ++i) {
			w = _words.get(i);
			temp = w.getFont().getSize();
			if(wSize <= temp) wSize = temp;
		}
		if(_words.size() == 0) wSize = _g.getFont().getSize();
		
		return wSize;
	}
	
	/**
	 * Draw the line at the given y displacement in the window.
	 * If "fill" is indicated then increase spacing between words
	 * so that the text is flush to both sides of the window.
	 * (Of course if there is only one word, "fill" can't do anything.)
	 * Otherwise, simply use the minimum spacing between words.
	 * <p>
	 * Filling is tricky.
	 * Integers are used for the spacing, but you must be sure
	 * to handle non-exact division.  So, for example,
	 * if there are 17 points of space to divide between 6 spaces,
	 * it is not OK to give each an extra 17/6 = 2 points leaving 5 extra
	 * spaces either to the right margin (not flush) or to the
	 * last (excessive inequality).  Nor is it OK to divide 13 spaces again 
	 * between 6 spaces by giving each 3 and then pushing the last word
	 * over the window edge.   Spacing must differ by
	 * no more than 1 point between words.
	 * Our solution recomputes the space to use between words
	 * at every point between words to avoid the problems described here.
	 * @param baseLine y coordinate to use for drawing
	 * @param fill whether to space words out to fill the line
	 */
	public void draw(int baseLine, boolean fill) {
		// TODO
		if(fill) {
			int space = MIN_SPACE + baseLine;
			//??????
		}
		//use MIN_SPACE for if fill is empty
		
		
		
	}
}
