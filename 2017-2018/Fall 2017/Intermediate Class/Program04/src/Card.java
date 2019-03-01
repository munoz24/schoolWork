public class Card {
	private String rank;
	private String suit;
	
	public Card()
	{
		this("A", "C");
	}
	
	public Card(String rank, String suit)
	{
		setSuit(suit);
		setRank(rank); 
		
	}
    
	public Card(Card guest)
	{
		this(guest.rank, guest.suit);
	}
	
	public String getSuit()
	{
		return this.suit;
	}
	
	public String getRank()
	{
		return this.rank;
	}
	
	
	private void setSuit(String suit)
	{
		if(suit.equals("H")
				|| suit.equals("C")
				|| suit.equals("S") 
				|| suit.equals("D"))
			this.suit = suit; 
	}
	
	private void setRank(String rank)
	{
		if(rank.equals("A") ||rank.equals("K") 
				||rank.equals("Q") || rank.equals("J") 
				||rank.equals("10") ||rank.equals("9") 
				||rank.equals("8") ||rank.equals("7") 
				||rank.equals("6") ||rank.equals("5") 
				||rank.equals("4") ||rank.equals("3") 
				||rank.equals("2"))
			this.rank = rank;
		
	}
	
	public String toString()
	{
		return (this.rank + this.suit);
	}
	
	public boolean cardEquals(Card guest)
	{
		if(guest.toString().equals(this.toString()))
			return true;
		else 
			return false;
		
	}
	public Card clone(Card guest)
	{
		return new Card(guest);
	}
	
	public void swap(Card guest)
	{
		this.rank = guest.rank;
		this.suit = guest.suit;
	}
}