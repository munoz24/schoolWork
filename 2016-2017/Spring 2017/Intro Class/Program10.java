import java.util.Scanner;

public class Program10
{
	public static void main(String[] args)
	{
		final int FOAK = 6545;
		final int STRF = 2452;
		final int F = 123;
	    final int STR = 79;
	    final int TOAK = 51;
	    final int TP = 22;
	    final int OP = 1;

	    int bank = 100;
	    int [] deck = new int[36];
	    int[] hand = new int[4];
	    
	    int nHands = 0;
	    int nWins = 0;
	    int nLosses = 0;

	    Scanner stdIn = new Scanner(System.in);
	    String op = "n";

	    System.out.println("\n\tWelcome to 4 Card Poker");
	    System.out.println("   Your initial bank roll is $100.00");
	    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++\n");
	    
	    do
	    {
	      if (bank > 0)
	      {
	        do
	        {
	          System.out.print("\nPlay a hand [y / n] ? ");
	          op = stdIn.next();
	        } while (!(op.charAt(0) == 'y' || op.charAt(0) == 'Y' || 
	                 op.charAt(0) == 'n' || op.charAt(0) == 'N'));
	        if (op.charAt(0) == 'y' || op.charAt(0) == 'Y')
	        {
	          ++nHands;
	          int bet= getBet(stdIn, bank);
	          
	          System.out.println("\nLet the cards fall where they may ...\n");
	          dealHand(deck, hand);
	          displayHand(hand);
	          System.out.println("\n");
	          
	          
	          if (isQuad(hand))
	          {
	            ++nWins;
	            bank += bet * FOAK;
	            System.out.println("Congrats: You got 4 of a Kind " +
	                               "and have won $" + bet * FOAK + ".");
	          }
	          else if (isStraightFlush(hand))
	          {
	        	  ++nWins;
	        	  bank += bet * STRF;
	        	  System.out.println("Congrats: You got a Straight Flush " +
	        			  "and have won $" + bet * STRF + ".");
	          }
	          else if (isFlush(hand))
	          {
	        	  ++nWins;
	        	  bank += bet * F;
	        	  System.out.println("Congrats: You got a Flush " +
	        			  "and have won $" + bet * F + ".");
	          }
	          else if (isTrip(hand))
	          {
	            ++nWins;
	            bank += bet * TOAK;
	            System.out.println("Congrats: You got 3 of a Kind " +
	                               "and have won $" + bet * TOAK + ".");
	          }
	          else if (isStraight(hand))
	          {
	            ++nWins;
	            bank += bet * STR;
	            System.out.println("Congrats: You got a Straight " +
	                               "and have won $" + bet * STR + ".");
	          }
	          else if (is2Pair(hand))
	          {
	            ++nWins;
	            bank += bet * TP;
	            System.out.println("Congrats: You got 2 Pair " +
	                               "and have won $" + bet * TP + ".");
	          }
	          else if (isPair(hand))
	          {
	            ++nWins;
	            bank += bet * OP;
	            System.out.println("Congrats: You got a Pair " +
	                               "and have won $" + bet * OP + ".");
	          }
	          else // Bubkiss
	          {
	            ++nLosses;
	            bank -= bet;
	            System.out.println("Sorry: you got Bubkiss " +
	                               "and have lost $" + bet + ".");
	          }
	          
	          
	        }
	      }
	    }while ((op.charAt(0) == 'y' || op.charAt(0) == 'Y') && bank > 0);
	      
	      report(nHands, nWins, nLosses, bank);
	      
	}
	public static int cardValue(int card)
	  {
	    return card % 9 + 1;
	  }
	public static String cardSuit(int card)
	  {
	    String [] suits = {"Clubs", "Spades", "Heart", "Diamonds"};

	    return suits[ card / 9 ];
	  }
	public static void displayCard(int card)
	  {
	    System.out.print(cardValue(card) + " "+ cardSuit(card) + ", ");
	  }
	public static void initDeck(int[] deck)
	  {
	    for (int i = 0; i < deck.length; ++i)
	      deck[i] = i;
	  }
	public static void shuffleDeck(int[] deck, int n)
	  {
	    int loc1 = 0;
	    int loc2 = 0;
	    int tCard = 0;
	    for (int i = 1; i <= n; ++i)
	    {
	      loc1 = (int)(Math.random() * 36);
	      loc2 = (int)(Math.random() * 36);
	      tCard = deck[loc1];
	      deck[loc1] = deck[loc2];
	      deck[loc2] = tCard;
	    }
	  }
	public static void dealHand(int [] deck, int[] hand)
	  {
	    initDeck(deck); 
	    shuffleDeck(deck, 7); //to make games harder
	    for (int i = 0; i < 4; ++i)//(get more Straight Flushes/Flushes/Straight)
	    {						   //change 7 to 100  (:
	      hand[i] = deck[i];
	    }
	    sortHand(hand);
	  }
	public static void sortHand(int[] hand)
	{
		for (int i = 0; i < hand.length; ++i)
		  {
		    int maxLoc = i;
		    for (int j = i+1; j < hand.length; ++j)
		      if (cardValue(hand[j]) > cardValue(hand[maxLoc]))
		        maxLoc = j;
		    int tmp = hand[i];
		    hand[i] = hand[maxLoc];
		    hand[maxLoc] = tmp;
	  } 
	}
	public static void displayHand(int[] hand)
	{
		 for (int i = 0; i < hand.length; ++i)
		    {
		      if (i < hand.length - 1)
		        displayCard(hand[i]);
		      else
		    	  displayCard(hand[i]);
		    }
	}
	public static int getBet(Scanner stdIn, int bank)
	{
		int bet;
        do
        {
          System.out.print("\nPlace your bet [1, " + bank + "] : ");
          bet = stdIn.nextInt();
        } while (bet < 1 || bet > bank);
        return bet;
	}
	public static boolean isQuad(int[] hand)
	{
		if (hand[0] == hand[1] &&
	            hand[1] == hand[2] &&
	            hand[2] == hand[3])
			return true;
		else 
			return false;
	}
	public static boolean isStraightFlush(int[] hand)
	{
	if (cardValue(hand[0]) == cardValue(hand [1]+1) && cardValue(hand[1]) == cardValue(hand [2]+1) && cardValue(hand[2]) == cardValue(hand [3]+1))
		if (cardSuit(hand[0]) == cardSuit(hand[1]) && cardSuit(hand[1]) == cardSuit(hand[2]) && cardSuit(hand[2]) == cardSuit(hand[3]))
			return true;
		else
			return false;
	return false;
	}
	public static boolean isFlush(int[] hand)
	{
	if (cardSuit(hand[0]) == cardSuit(hand[1]) && cardSuit(hand[1]) == cardSuit(hand[2]) && cardSuit(hand[2]) == cardSuit(hand[3]))
		return true;
	else 
		return false;
	}
	public static boolean isStraight(int[] hand)
	{
		if (hand[0] == hand[1]+1 &&
	            hand[1] == hand[2]+1 &&   
	            hand[2] == hand[3]+1)
			return true;
		else
			return false;
	}
	public static boolean isTrip(int[] hand)
	{
		if ((hand[0] == hand[1] &&
	             hand[1] == hand[2]) ||
	            (hand[1] == hand[2] &&
	             hand[2] == hand[3]))
			return true;
		else 
			return false;
	}
	public static boolean is2Pair(int[] hand)
	{
		if (cardValue(hand[0]) ==cardValue(hand[1]) && cardValue(hand[2]) ==cardValue(hand[3]) && cardValue(hand[1]) != cardValue(hand[2]) ||
				cardValue(hand[0]) ==cardValue(hand[2]) && cardValue(hand[1]) ==cardValue(hand[3]) && cardValue(hand[2]) !=cardValue(hand[1]) ||
				cardValue(hand[0]) ==cardValue(hand[3]) && cardValue(hand[1]) ==cardValue(hand[2]) && cardValue(hand[3]) !=cardValue(hand[1]))
			return true;
		else
			return false;
	}
	public static boolean isPair(int[] hand)
	{
		if (cardValue(hand[0]) ==cardValue(hand[1]) || cardValue(hand[0]) ==cardValue(hand[2]) || cardValue(hand[0]) ==cardValue(hand[3]) ||
				cardValue(hand[1]) ==cardValue(hand[2]) || cardValue(hand[1]) ==cardValue(hand[3]) ||
				cardValue(hand[2]) ==cardValue(hand[3]))
			return true;
		else 
			return false;
	}
	public static void report(int nHands, int nWins, int nLosses, int bank)
	{
		System.out.println("\n\n+++++++++++++++++++++++++++++++++++++++++++\n\n");
	    if (bank == 0)
	      System.out.println("Party's Over: you are out of chips.\n\n");

	    System.out.println("Thanks for playing ... \n");

	    System.out.println("You played a total of " + nHands + " hands.");
	    System.out.println("Of which, you won " + nWins + ".");
	    System.out.println("And you lost " + nLosses + "."); 

	    System.out.print("\nBut in the end you ");
	    if (bank - 100 > 0)
	      System.out.print("won $" + (bank - 100));
	    else if (bank - 100 < 0)
	      System.out.print("lost $" + (100 - bank));
	    else
	      System.out.print("Broke even");
	    System.out.println(".\n\n"); 
	}
}
