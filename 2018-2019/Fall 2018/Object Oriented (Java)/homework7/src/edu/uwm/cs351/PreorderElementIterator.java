package edu.uwm.cs351;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uwm.cs351.util.Element;
import edu.uwm.cs351.util.Stack;

/**
 * Iterator over the element tree top-down,
 * returning each element or string (hence "Object") as we encounter it.
 * After each element, we traverse its children.  This is done using 
 * an explicit stack rather than recursion.  This class is not used by
 * {@link HTMLWordIterator} but that class uses the same logic as here.
 */
public class PreorderElementIterator implements Iterator<Object> {
	private Stack<Iterator<Object>> pending = new Stack<>();
	
	public PreorderElementIterator(Element e) {
		pending.push(Arrays.<Object>asList(e).iterator());
	}

	@Override
	public boolean hasNext() {
		return pending.isEmpty(); // TODO (fairly easy)
	}

	@Override
	public Object next() {
		
		
		if(this.pending.isEmpty() == false) {
			
			
			
		}
		
		
		return false; // TODO
	}

	
}
