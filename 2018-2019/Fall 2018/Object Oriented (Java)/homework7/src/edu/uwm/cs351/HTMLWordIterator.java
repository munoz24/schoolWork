package edu.uwm.cs351;

import java.awt.Font;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uwm.cs351.util.Element;
import edu.uwm.cs351.util.Queue;
import edu.uwm.cs351.util.Stack;

/**
 * Turn an XML document that uses HTML elements into a stream of {@link Word} 
 * objects and nulls, where the latter represent line breaks.  The words
 * are given fonts appropriate to the constructs they are nested in.
 */
public class HTMLWordIterator implements Iterator<Word> {
	private Queue<Word> ready = new Queue<Word>();
	private Stack<Iterator<Object>> pending = new Stack<Iterator<Object>>();
	private Stack<Font> fonts = new Stack<Font>();
	
	public static final Font INITIAL_FONT = new Font("Serif",Font.PLAIN,20);
	public static final Font[] HEADER_FONTS = new Font[] { null,
		new Font("Serif",Font.BOLD,36),
		new Font("Serif",Font.BOLD,32),
		new Font("Serif",Font.BOLD,28),
		new Font("Serif",Font.BOLD,24)
	};
	
	public HTMLWordIterator(Element root) {
		if (!root.getName().equalsIgnoreCase("html")) {
			throw new IllegalArgumentException("Can only accept HTML elements");
		}
		// TODO: initialize stacks
		// pending stack should hold contents of the initial element
		// font stack should hold the default font.
		
		
	
	
	
	}
	
	/**
	 * If the queue is empty, continue through the
	 * document looking for words or line breaks to add.
	 * If the queue is not empty, do nothing.
	 * If the queue is empty <i>after</i> this method returns,
	 * that means there are no more words in the document.
	 * <p>
	 * Strings (XML calls this CData) in the document should be split into individual words
	 * using the method "split" with the regular expression \s+
	 * (which means one or more spacing characters). Remember to escape
	 * backslashes in string literals.
	 * <p>
	 * (Line-)Breaks are represented by nulls being added to the queue.
	 * <p>
	 * The following tags are "understood"
	 * <dl>
	 * <dt>script,style<dd> The entire element is ignored
	 * <dt>h1,h2,h3,h4<dd> Break for 5-n lines, where n is the number (1 for h1, 2 for h2 etc)
	 *     and use the appropriate header font.  For example
	 *     <h3> should generate 5-3=2 line breaks (nulls).
	 * <dt>b<dd> Make current font bold
	 * <dt>i<dd> Make current font italic
	 * <dt>em<dd> Make current font italic unless already italic, in which case remove
	 * the italicness.
	 * <dt>p<dd> Break the line
	 * <dt>br<dd> Break the line
	 * <dt>ol,ul<dd> Break the line
	 * <dt>li<dd> Break the line and add a bullet character: \u2022
	 * <dt>font size=X<dd> If x is "+1" add two (not one!) to the font size,
	 * and similarly if x is "-1" subtract two from the font size (never smaller than 1),
	 * otherwise x should be a number.  Use this as the font size (directly, not multiplied by two).
	 * If x isn't a number or there isn't a "size" attribute, do nothing (no error message).
	 * </dl>
	 */
	private void fillQueue() {
		// TODO: BIG
		// Basic outline:
		// Loop until queue has something in it, or we are done.
		//   Inside the loop, we make sure we can get something from the current
		//   iterator.  Then if it is a String, we break up the string into words
		//   as explained in the documentation comment for this method,
		//   and put all the words into the queue using the current (top) font.
		//   Otherwise, if it is an element that should not be ignored, 
		//   we push a new iterator on the stack and a new font on the stack.  
		//   The new font may be the same or maybe not.
		//   Do a case analysis on the name of the element.
		//   If line breaks need to be added, add them directly.
		//   If you don't recognize the tag, the new font is the same as the current font.
		//   if you need to change the font, use ".deriveFont(...)" to keep everything
		//   the same except for the part changing.
		// Make sure you push/pop the two stacks in parallel.
		// fillQueue should stop work as soon as the queue is non-empty or
		// the end of the document is reached.
		
		while(!ready.isEmpty()) {
			if(pending.getClass().getSimpleName() == "String") {
				while(!pending.isEmpty()) {
					ready.add(pending.pop());
				}
			}
			
			
			
			
		}
		
		
		
	}
	
	public boolean hasNext() {
		fillQueue();
		return !ready.isEmpty();
	}

	public Word next() throws NoSuchElementException {
		fillQueue();
		return ready.remove(); // will (correctly) throw exception if empty 
	}

	public void remove() {
		throw new UnsupportedOperationException("cannot remove");
	}

}
