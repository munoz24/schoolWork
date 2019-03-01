
public class SmartDeck {
	
	private boolean[] deck = new boolean[52];
	static int cardsDealt;
	
	public void initDeck()
	{
		cardsDealt = 0;
		for (int i = 0; i < this.deck.length; ++i)
		{
		      this.deck[i] = true;
		}
		
	}
	public boolean emptyDeck(){
		if(cardsDealt < this.deck.length)
			return false;
		else
			return true;
	}
	public int dealCard(){
		cardsDealt++;
		int card = (int)(Math.random() * 52);
		while (this.deck [card] == false)
		{
			card = (int)(Math.random() * 52);
		}
		this.deck[card] = false;
		return card;
	}
	public static String cardToString(int card){
		String [] value = {"AS","2S","3S","4S","5S","6S","7S","8S","9S","10S","JS","QS","KS",
				"AD","2D","3D","4D","5D","6D","7D","8D","9D","10D","JD","QD","KD",
				"AC","2C","3C","4C","5C","6C","7C","8C","9C","10C","JC","QC","KC",
				"AH","2H","3H","4H","5H","6H","7H","8H","9H","10H","JH","QH","KH"};
		return value[card];
	}
}
