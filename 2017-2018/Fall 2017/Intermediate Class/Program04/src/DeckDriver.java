public class DeckDriver {

public static void main(String[] args) {
	Deck deck = new Deck();
	deck.shuffleDeck();

	System.out.println("deck has been shuffled");
	for (int i = 0; i < 52; i++)
	{
		System.out.print(deck.dealCard() + "   ");
		if ((i+1) % 5 == 0)
			System.out.println();
		}
	}
}


