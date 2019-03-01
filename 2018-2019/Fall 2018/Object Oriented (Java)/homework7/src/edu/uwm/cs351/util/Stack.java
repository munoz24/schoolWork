package edu.uwm.cs351.util;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Stack ADT.
 * This is the standard LIFO container.
 * @param T type of elements in stack
 */
public class Stack<T> {
	private final List<T> _contents = new ArrayList<T>();
	
	/**
	 * Return whether this stack is empty.
	 * @return true if stack is empty
	 */
	public boolean isEmpty() {
		if(this._contents.size() == 0) return true;

		
		return false;
	}
	
	/**
	 * Put a new element onto the stack.
	 * @param x element to add (may be null)
	 */
	public void push(T x) {
		
		this._contents.add(x);
		
	}
	
	/**
	 * Remove and return the top element from the stack.
	 * @return top element from stack.
	 * @throws NoSuchElementException if stack is empty
	 */
	public T pop() throws NoSuchElementException {
		if(this.isEmpty()) throw new NoSuchElementException("isEmpty on pop");
		
		T temp = this._contents.remove(this._contents.size()-1);
		return temp;
		
	}
	
	
	/**
	 * Return the top element from the stack without removing it.
	 * @return top element on stack.
	 * @throws NoSuchElementException if stack is empty
	 */
	public T peek() throws NoSuchElementException {
		
		if(this.isEmpty()) throw new NoSuchElementException("isEmpty on peek");
		
		T temp = pop();
		
		push(temp);
		
		return temp;
		
		
	}
}
