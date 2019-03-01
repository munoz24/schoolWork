package edu.uwm.cs351.util;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Queue<T> {

	private final LinkedList<T> _contents = new LinkedList<T>();
	
	/**
	 * Return true if the queue is empty.
	 * @return true if queue is empty.
	 */
	public boolean isEmpty() {
		// TODO
		
		if(this._contents.size() == 0) return true;
		
		return false;
	}
	
	/**
	 * Enqueue an element.
	 * @param x element to add to queue, may be null
	 */
	public void add(T x) {
		// TODO
		
		this._contents.add(x);
		
	}
	
	/**
	 * Remove and return the first element in this queue.
	 * The queue must not be empty.
	 * @return first element from queue
	 * @throws NoSuchElementException if queue is empty
	 */
	public T remove() throws NoSuchElementException {
		// TODO
		if(this.isEmpty()) throw new NoSuchElementException("isEmpty on remove");
		
		T temp = this._contents.removeFirst();		
		
		return temp;
		
	}
}
