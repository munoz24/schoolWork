package edu.uwm.cs351;

import static edu.uwm.cs351.Card.Rank.ACE;
import static edu.uwm.cs351.Card.Rank.DEUCE;
import static edu.uwm.cs351.Card.Rank.KING;
import static edu.uwm.cs351.Card.Rank.SIX;
import static edu.uwm.cs351.Card.Suit.CLUB;
import static edu.uwm.cs351.Card.Suit.DIAMOND;
import static edu.uwm.cs351.Card.Suit.HEART;
import static edu.uwm.cs351.Card.Suit.SPADE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import edu.uwm.cs.junit.LockedTestCase;

/**
 * Traditional playing cards.
 * @author boyland
 */
public class Card {
	public enum Suit { CLUB, DIAMOND, HEART, SPADE };
	public enum Rank {
		ACE(1), DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT (8), NINE(9), TEN(10),
		JACK(11), QUEEN(12), KING(13);

		private final int rank;
		private Rank(int r) {
			rank = r;
		}

		public int asInt() {
			return rank;
		}
	}

	private final Suit suit;
	private final Rank rank;
	private Card prev, next;
	private Group group;

	public Card(Rank r, Suit s) {
		rank = r;
		suit = s;
	}

	// getters for all fields:

	public Suit getSuit() {
		return suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Card getPrevious() {
		return prev;
	}

	public Card getNext() {
		return next;
	}

	public Group getGroup() {
		return group;
	}

	// no setters!

	@Override
	/** Return true if the suit and rank are the same.
	 * Caution: do not use this method to check if you have the same card!
	 */
	public boolean equals(Object x) {
		if (!(x instanceof Card)) return false;
		Card other = (Card)x;
		return suit == other.suit && rank == other.rank;
	}

	@Override
	public String toString() {
		return rank + " of " + suit + "S";
	}


	/**
	 * An endogenous DLL of card objects.
	 */
	public static class Group {
		private Card first, last;
		private int size;

		/**
		 * Create an empty group.
		 */
		public Group() {
			first = last = null;
			size = 0;
		}

		public Card getFirst() { return first; }
		public Card getLast() { return last; }

		private static boolean reportErrors = true; // do not change
		private boolean report(String s) {
			if (reportErrors) System.err.println("Invariant error: " + s);
			return false;
		}
		private boolean wellFormed() {
			// TODO: Implement the invariant:
			// - The cards must be properly linked up in a doubly-linked list.
			// - All cards must have their group set to this.
			// This code must terminate and must not crash even if there are problems.


			Group e = this;

			int i = 0;
			if(e.first == null && e.last == null && e.size == 0) {
				return true;
			}


			Card p = e.first;
			boolean check = false;
			if(e.first != null && e.last != null) {
				Card n = null;
				if(e.first.prev != null) return report("There is a loop");

				for(p = e.first; p.next != null; p = p.next) {

					n = p.next;

					if(n.prev != p) return report("p.next is null");
					if(this != p.group) {
						return report("this is not set to the group");
					}


				}

				if(e.last.next != null) {
					return report("Size is not equal to the amount of cards");
				}

				for(Card c = e.first; c.next != null; c = c.next) {
					++i;
				}
				if(i != this.size-1) {
					return report("Size is not equal to the amount of cards");
				}
				check = true;
			}
			if(check != true) {
				if(i != this.size) {
					return report("Size is not equal to the amount of cards");
				}
				if(e.first == null || e.last == null) {
					return report("Error");
				}
			}
			
			return true;
		}

		/**
		 * Return true if there are no cards,
		 * that is, if and only if getFirst() == null. O(1)
		 */
		public boolean isEmpty() {
			assert wellFormed() : "invariant false on entry to isEmpty()";

			if(this.getFirst() == null) {
				return true;
			}

			return false;
		}

		/**
		 * Return the number of cards in this pile. O(1)
		 */
		public int count() {
			assert wellFormed() : "invariant false on entry to count()";

			int count = 0;
			for(Card p = this.first; p != null; p = p.next) {
				++count;
			}

			return count; // TODO
		}

		/**
		 * Add a card to the end of this pile/hand. O(1)
		 * @param c card to add, must not be null or in a group already.
		 * @throws IllegalArgumentException if the card is in a group already.
		 */
		public void add(Card c) {
			// TODO
			// No loops allowed!
			// Make sure to test invariant at start and before returning.
			assert wellFormed() : "invariant false on entry to add()";
			if(c == null) throw new IllegalArgumentException("C is null");
			
			if(c.group != null) throw new IllegalArgumentException("c.group is not null");


			if(this.isEmpty()) {
				this.first = c;
				this.last = c;
			}else {
				Card temp = this.last;
				temp.next = c;
				c.prev = temp;
				this.last = c;
			}

			c.group = this;
			++this.size;

			assert wellFormed() : "invariant false on return to add()";

		}

		/**
		 * Remove the first card and return it.
		 * The group must not be empty.  The resulting card
		 * will not belong to any group afterwards. O(1)
		 *@throws IllegalStateException if group empty
		 */
		public Card draw() {
			// TODO
			// No loops allowed!
			// Make sure to test invariant at start and before returning.
			assert wellFormed() : "invariant false on entry to draw()";

			if(this.isEmpty()) {
				throw new IllegalStateException("group is empty");
			}

			Card d = null;
			Card temp = null;

			if(this.size == 1) {
				d = this.first;
				this.first = null;
				this.last = null;
			}
			else {
				d = this.first;
				temp = this.first.next;
				this.first = temp;
				temp.prev = null;

			}
			d.next = null;
			d.prev = null;
			d.group = null;
			--this.size;

			assert wellFormed() : "invariant false on return to draw()";

			return d;

		}

		/**
		 * Remove the given card from this group.
		 * Afterwards the card is not in this group. O(1)
		 * @param c, card in this group, must not be null
		 * @throws IllegalArgumentException if c is not in this group
		 */
		public void remove(Card c) {
			// TODO.  
			// No loops allowed!
			// Make sure to test invariant!
			assert wellFormed() : "invariant false on entry to remove()";

			if(c.group != this) throw new IllegalArgumentException("Card is not part of group");

			if(c == this.first) {
				if(this.first.next != null) {
					this.first = this.first.next;
					this.first.prev = null;
				}else {
					this.first = null;
					this.last = null;
				}

			}else if(c == this.last) {
				this.last = this.last.prev;
				this.last.next = null;
			}else {

				c.prev.next = c.next;
				c.next.prev = c.prev;

			}

			c.group = null;
			c.next = null;
			c.prev = null;

			--this.size;

			assert wellFormed() : "invariant false on return to remove()";

		}

		/**
		 * Sort the cards using the given comparison, so that
		 * after sorting for all cards c in the group that is not last
		 * <code>cmp.compare(c,c.next)</code> is never positive.
		 * This code must use insertion sort so that it is efficient
		 * on (mostly) sorted lists.
		 * @param cmp comparator to use for sorting.  Must not be null.
		 * The comparator should work correctly, or the final result is undefined.
		 */
		public void sort(Comparator<Card> cmp) {
			assert wellFormed() : "invariant false on entry to sort()";
			// TODO
			// Implement insertion sort efficiently.
			// You may find it helpful to use "remove" (but watch about size!).
			// DO NOT use anything in the CardUtil class or any java.util class


			int n = 0;
			Card current = this.first;


			if(this.size > 0) {
				
				Card pointer, temp;
				current = current.next;

				while(current != null) {
					n = 0;
					pointer = current;
					temp = current.prev;
					current = current.next;

					while(temp != null && cmp.compare(temp, pointer) > 0) {
						++n;
						temp = temp.next;
					}
					
					if(n != 0) {

						pointer.prev.next = pointer.next;
						if(pointer.next != null) {
							pointer.next.prev = pointer.prev;
						}

						if(temp == null) {
							temp = this.first;
							pointer.prev = null;
							pointer.next = temp;
							pointer.next.prev = pointer;
							this.first = pointer;
						}
						else {
							temp = temp.next;
							temp.prev.next = pointer;
							pointer.prev = temp.prev;
							temp.prev = pointer;
							pointer.next = temp;
						}					
					}


				}
			}

			assert wellFormed() : "invariant false on exit to sort()";
		}

		/**
		 * Randomize the order of the cards in this group.
		 */
		public void shuffle() {
			/*
			 * This is very different from the sort method because:
			 * @ we decant the cards into an array list;
			 * @ we use a library function to do the work;
			 * The implementation you write for the sort method should
			 * have *neither* of these characteristics.
			 */
			List<Card> cards = new ArrayList<Card>();
			while (!isEmpty()) {
				cards.add(draw());
			}
			Collections.shuffle(cards);
			for (Card c: cards) {
				add(c);
			}
		}
	}

	// Do not change this code!
	public static class TestInvariant extends LockedTestCase {
		private Group self;
		private Card c[] = { null,
				new Card(ACE,DIAMOND),
				new Card(DEUCE,DIAMOND),
				new Card(KING,DIAMOND),
				new Card(DEUCE,CLUB),
				new Card(KING,HEART),
				new Card(SIX,HEART),	
				new Card(ACE,SPADE),
		};

		@Override
		protected void setUp() {
			Group.reportErrors = false;
			self = new Group();
		}

		public void test0() {
			// self starts empty
			assertEquals(Tb(1664353579),self.wellFormed());
			self.size = 1;
			assertEquals(Tb(1773780887),self.wellFormed());
			self.first = c[1];
			assertEquals(Tb(1258588040),self.wellFormed());
			self.first = null;
			self.last = c[2];
			self.size = 0;
			assertEquals(Tb(1588849059),self.wellFormed());
			self.last = null;
			assertEquals(Tb(1369135158),self.wellFormed());
		}

		public void test1() {
			// self starts empty
			self.first = c[2];
			assertEquals(Tb(1456599228),self.wellFormed());
			self.last = c[2];
			assertEquals(Tb(702186087),self.wellFormed());
			self.size = 1;
			assertEquals(false,self.wellFormed());
			c[2].group = self;
			assertEquals(Tb(498442860),self.wellFormed());
			self.first.prev = self.last;
			self.last.next = self.first;
			assertEquals(Tb(62398800),self.wellFormed());
			self.first = new Card(KING,SPADE);
			self.last = new Card(KING,SPADE);
			self.first.group = self;
			self.last.group = self;
			assertEquals(Tb(1545236604),self.wellFormed());
			self.last = self.first;
			assertEquals(Tb(2115154626),self.wellFormed());
		}

		public void test2() {
			// self starts empty
			self.first = c[3];
			self.last = c[4];
			c[3].group = self;
			c[4].group = self;
			assertEquals(Tb(1695835262),self.wellFormed());
			self.size = 2;
			assertEquals(Tb(1659916700),self.wellFormed());
			self.first.next = self.last;
			self.last.prev = self.first;
			assertEquals(Tb(1197524895),self.wellFormed());
			self.size = 1;
			assertEquals(Tb(2050626739),self.wellFormed());
		}

		public void test2Cycle() {
			self.first = c[3];
			self.last = c[4];
			c[3].group = self;
			c[4].group = self;
			c[3].next = c[4];
			c[4].prev = c[3];
			self.size = 2;
			assertEquals(true,self.wellFormed());
			c[4].next = c[4];
			assertEquals(false,self.wellFormed());
			c[4].next = c[3];
			assertEquals(false,self.wellFormed());
			c[4].next = null;
			c[3].next = c[3];
			assertEquals(false,self.wellFormed());
		}

		public void test3() {
			self.first = c[1];
			c[1].next = c[2]; 
			c[2].next = c[3]; 
			self.last = c[3];
			c[1].group = self;
			c[2].group = self;
			c[3].group = self;
			self.size = 3;
			assertEquals(false,self.wellFormed());
			c[2].prev = c[1]; c[3].prev = c[2];
			assertEquals(true,self.wellFormed());
			self.size = 2;
			assertEquals(false,self.wellFormed());
			self.size = 3;
			c[3].next = c[4];
			c[4].prev = c[3];
			assertEquals(false,self.wellFormed());
			c[3].next = null;
			assertEquals(true,self.wellFormed());
			c[3].prev = c[1];
			assertEquals(false,self.wellFormed());
		}

		public void test3Cycle21() {
			self.first = c[1];
			c[1].next = c[2]; c[2].prev = c[1]; 
			c[2].next = c[3]; c[3].prev = c[2];
			self.last = c[3];
			c[1].group = self;
			c[2].group = self;
			c[3].group = self;
			self.size = 3;
			assertEquals(true,self.wellFormed());
			c[2].next = c[1];
			assertEquals(false,self.wellFormed());
		}

		public void test3Cycle22() {
			self.first = c[1];
			c[1].next = c[2]; c[2].prev = c[1]; 
			c[2].next = c[3]; c[3].prev = c[2];
			self.last = c[3];
			c[1].group = self;
			c[2].group = self;
			c[3].group = self;
			self.size = 3;
			assertEquals(true,self.wellFormed());
			c[2].next = c[2];
			assertEquals(false,self.wellFormed());
		}

		public void test3Cycle31() {
			self.first = c[1];
			c[1].next = c[2]; c[2].prev = c[1]; 
			c[2].next = c[3]; c[3].prev = c[2];
			self.last = c[3];
			c[1].group = self;
			c[2].group = self;
			c[3].group = self;
			self.size = 3;
			assertEquals(true,self.wellFormed());
			c[3].next = c[1];
			assertEquals(false,self.wellFormed());
		}

		public void test3Cycle32() {
			self.first = c[1];
			c[1].next = c[2]; c[2].prev = c[1]; 
			c[2].next = c[3]; c[3].prev = c[2];
			self.last = c[3];
			c[1].group = self;
			c[2].group = self;
			c[3].group = self;
			self.size = 3;
			assertEquals(true,self.wellFormed());
			c[3].next = c[2];
			assertEquals(false,self.wellFormed());
		}

		public void test3Cycle33() {
			self.first = c[1];
			c[1].next = c[2]; c[2].prev = c[1]; 
			c[2].next = c[3]; c[3].prev = c[2];
			self.last = c[3];
			c[1].group = self;
			c[2].group = self;
			c[3].group = self;
			self.size = 3;
			assertEquals(true,self.wellFormed());
			c[3].next = c[3];
			assertEquals(false,self.wellFormed());
		}
	}


	/** Create and return a fresh pack of cards.
	 * A "static" method is a class method.  It is invoked using
	 * the class, not an instance.
	 * @return a fresh pack of 52 cards
	 */
	public static Group newDeck() {
		Group g = new Group();
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				Card c = new Card(r,s);
				g.add(c);
			}
		}
		return g;
	}
}
