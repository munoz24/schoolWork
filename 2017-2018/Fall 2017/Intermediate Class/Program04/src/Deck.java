public class Deck{

	private Card[] deck = new Card[52];
	private int cardsDealt;
	private static final String rank[] = {"A", "2", "3", "4", "5", "6", "7", "8",
										"9", "10","J", "Q", "K"};
	private static final String suit[] = {"H", "C", "D","S"};

	public Deck()
	{
		collectCards(); 
		for(int i = 0; i < 13; i++)
		{
			for(int x = 0; x < 4; x++)
			{
				deck[this.getCardsDealt()]= new Card(rank[i], suit[x]);
				setCardsDealt(this.getCardsDealt()+1); 
			}	
		}
		collectCards(); 
	}

	public int getCardsDealt() 
	{
		return cardsDealt;
	}

	private void setCardsDealt(int cardsDealt)
	{
		if(cardsDealt >= 0)
		{
			this.cardsDealt = cardsDealt; 
		}
	}

	public boolean emptyDeck()
	{
		if(this.getCardsDealt() == 52)
			return true;
		else 
			return false;
	}

	public void collectCards()
	{
		setCardsDealt(0);
	}

	public void collectCards(int cardCnt)
	{
		setCardsDealt(this.getCardsDealt()-cardCnt);
	}

	public Card dealCard()
	{
		if(this.emptyDeck() == false)
		{
			setCardsDealt(this.getCardsDealt() +1);
			return deck[this.getCardsDealt()-1];
		}
		else if(this.getCardsDealt() > 0)
		{
			setCardsDealt(this.getCardsDealt() +1);
			return deck[0];
		}
		else
			return null;
		
	}

	public void shuffleDeck()
	{
		shuffleDeck(100);
	}

	public void shuffleDeck(int swapCnt)
	{
		Card c2 = new Card();
		int random1;
		int random2;
		for(int x = 0; x < swapCnt; x++)
		{
			
			random1 = (int)(Math.random() * 52);
			random2 = (int)(Math.random() * 52);
			c2.swap(deck[random1]);
			deck[random1].swap(deck[random2]);
			deck[random2].swap(c2);
			
		}
	}
}