//Andres Munoz
import java.util.Scanner;

public class Assignment07 {
	public static void main(String[] args)
	{
		Scanner stdIn = new Scanner(System.in);
		welcome();
		int spinNum = -1; 
		int chipsNow = 100; 
		System.out.println("You have " + chipsNow + " chips");
		System.out.println();
		int myBet = 0;
		int slotNum = 0;
		String slotColor = "";
		int getMenuChoice;
		String gameColor = determineColor(spinNum);

		do 
		{
			getMenuChoice = getMenuChoice(stdIn);

			if(getMenuChoice == 1)
			{
				slotNum = getNumber(stdIn);
				myBet = getBet(stdIn, chipsNow);
			}
			else if (getMenuChoice == 2)
			{
				slotColor = getColor(stdIn); 
				myBet = getBet(stdIn, chipsNow);
			}
			else if (getMenuChoice == 3)
			{
				report(chipsNow);
				break;
			}

			spinNum = (int)(Math.random()*36);
			System.out.println("Spinning the wheel ...");
			System.out.println("Spin number: " + spinNum);
			System.out.println("Spin color: " + determineColor(spinNum));
			System.out.println();
			gameColor = determineColor(spinNum);

			if (spinNum == slotNum || slotColor.equals(gameColor)) //Cannot make slotColor and gameColor equal each other for a win
			{                                              //Only registers lose even if they match logically
				System.out.println("Congrats, you won!");
				if (spinNum == slotNum)
				{
					chipsNow += 35 * myBet;
				}
				else if (slotColor.equals(gameColor))
				{
					chipsNow += myBet;
				}
				System.out.println("You have "+ chipsNow + " chips");
			}
			else if (spinNum != slotNum || !slotColor.equals(gameColor))
			{
				System.out.println("Sorry, you lose");
				if (spinNum != slotNum)
				{
					chipsNow -= myBet;
				}
				else if (!slotColor.equals(gameColor))
				{
					chipsNow -= myBet;
				}
				System.out.println("You have "+ chipsNow + " chips");
				if (chipsNow == 0)
				{
					System.out.println();
					System.out.println("Come again when you have more chips!");
					break;
				}
			}
		}while (getMenuChoice != 3);

	}
	public static void welcome() {
		System.out.println("############################");
		System.out.println("#   WELCOME TO ROULETTE    #");
		System.out.println("############################");
		System.out.println("# NUMBER BETS PAYOUT: 35:1 #");
		System.out.println("# COLOR BETS PAYOUT:   1:1 #");
		System.out.println("############################");
		System.out.println();
	}
	public static int getMenuChoice(Scanner stdIn) {
		int a;
		System.out.println();
		System.out.println("1. Pick a number to bet on");
		System.out.println("2. Pick a color to bet on");
		System.out.println("3. Cash out");
		System.out.println();
		do {
			System.out.println("Enter a choice [1-3] : ");
			a = stdIn.nextInt();
		} while (a < 1 || a > 3);
		return a;
	}
	public static int getNumber(Scanner stdIn) {
		int a;
		do {
			System.out.println("Enter the number to bet on [0-36] : ");
			a = stdIn.nextInt();
		} while (a < 0 || a > 36);
		return a;
	}
	public static String getColor(Scanner stdIn) {
		String str = "";
		do {
			System.out.println("Enter the color to bet on [Red or Black] : ");
			str = stdIn.next();
		} while (!str.equalsIgnoreCase("red") && !str.equalsIgnoreCase("black"));
		return str;
	}
	public static int getBet(Scanner stdIn, int chipsNow) {
		int bet = 0; 
		do
		{
			System.out.println("Enter a bet [1, " + chipsNow + "] : ");
			bet = stdIn.nextInt();
		} while (bet < 1 || bet > chipsNow);
		return bet;
	}
	public static String determineColor(int spinNum) {
		String str = "";
		int spin = (int)(Math.random()*36);
		if(spin == 0)
		{
			str = "green"; 
		}
		else if(spin %2 == 0)
		{
			str = "red";
		}
		else if(spin %2 ==1)
		{
			str = "black";
		}
		return str;
	}
	public static void report(int chipsNow) {
		if (chipsNow > 100)
		{
			chipsNow = chipsNow -100;
			System.out.println("Thanks for playing, you won a total of " + chipsNow + " chips");
		}
		else if (chipsNow < 100)
		{
			chipsNow = 100 - chipsNow;
			System.out.println("Thanks for playing, you lost " + chipsNow + " chips");
		}
		else if (chipsNow == 100)
		{
			chipsNow = 0;
			System.out.println("Thanks for playing, you evened out your chips");
		}
		return;
	}
}