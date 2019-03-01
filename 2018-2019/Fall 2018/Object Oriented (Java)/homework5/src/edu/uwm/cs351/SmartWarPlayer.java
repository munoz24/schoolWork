package edu.uwm.cs351;

import java.util.Comparator;


public class SmartWarPlayer extends PlayWar{

	
	/*
	 * Note:
	 * For this to run without error:
	 * wellFormed should be always true
	 * add() should comment out line 209
	 * 
	 * This should have player 1 win always
	 * 
	 */
	
	
	/**
	 * This is the gosh dangit best AI War Player this
	 * round earth has ever seen
	 * @param disputed number of disputed cards on table
	 * @return card removed from hand
	 */
	@Override
	public Card play(Comparator<Card> cmp, int disputed)
	{
		Card high = this.hand.getFirst();
		for(Card p = this.hand.getFirst(); p.getNext() != null; p = p.getNext()) {
			if(cmp.compare(p, high) > 0) {
				high = p;
			}
		}

		return high;
	}


}
