
public class CardDriver {
	public static void main(String [] args)
	{
		Card Deck[] = new Card[52];
		Card queen = new Card("Q", "C");
		String rank[] = {"A", "2", "3", "4", "5", "6", 
				"7", "8", "9", "10","J", "Q", "K"};
		String suit[] = {"H", "C", "D","S"};
		int cardCount =0;
		int random1;
		int random2;
		int queenPos = -1;
		int index =0;
		
		for(int i = 0; i < 13; i++)
		{
			for(int x = 0; x < 4; x++)
			{
				Deck[cardCount]= new Card(rank[i], suit[x]);
				cardCount++; 
			}
				
		}
		
		Card Deck2 = new Card();
		for(int i = 0; i < 100; i++)
		{
			random1 = (int)(Math.random() * 52);
			random2 = (int)(Math.random() * 52);
			Deck2.swap(Deck[random1]);
			Deck[random1].swap(Deck[random2]);
			Deck[random2].swap(Deck2);
			
		}
		
		System.out.println("Here is a shuffled deck");
		for (int i = 0; i < Deck.length; i++){
			System.out.print(Deck[i].toString() + "   ");
			if ((i+1) % 5 == 0)
				System.out.println();
			}
		
	
		do
		{
			if(queen.cardEquals(Deck[index]))
				queenPos = index;
			else  
				index++;
			
		}while((index!=queenPos));
		
		System.out.println();
		System.out.println();
		System.out.println("The queen of clubs is at index: " + queenPos );
		System.out.println("The queen of clubs is at postion: " + (queenPos + 1) + " in the deck");
		//index does not show real position of queen of clubs since index starts at 0
			
	}
	
	
}

		