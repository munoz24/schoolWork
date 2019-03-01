import java.util.Scanner;

public class Program08 {
	public static void  main (String[] args)
	{
		Scanner stdIn = new Scanner(System.in);
		char c;
		int tied = 0;
		int won = 0;
		int lost = 0;
		int d1;
		int d2;
		int d3;
		int d4;
		int d5;
		int d6;
		
		welcome();
		
		System.out.println("Would you like to play [y, n]: ");
		String str = stdIn.nextLine();
		c = str.charAt(0);
		if (c == 'n')
		{
			report(won,lost, tied);
		}
		
		//Dice
		while(c == 'y')
		{
	
			System.out.println("Player");
			System.out.println("---------");
			d1 = (int)(Math.random() * 12) + 1;
			d2 = (int)(Math.random() * 12) + 1;
			d3 = (int)(Math.random() * 12) + 1;
			System.out.println(d1 + " " + d2 + " " + d3);
		
			System.out.println("Opponent");
			System.out.println("---------");
			d4 = (int)(Math.random() * 12) + 1;
			d5 = (int)(Math.random() * 12) + 1;
			d6 = (int)(Math.random() * 12) + 1;
			System.out.println(d4 + " " + d5 + " " + d6);
			
			if (determineRound(d1, d2, d3, d4, d5, d6) == 1)
			{
				System.out.println("Congrats, you won! ");
				++won;	
			}
			else if (determineRound(d1, d2, d3, d4, d5, d6) == -1)
			{
				System.out.println("Sorry, you lost ");
				++lost;
			}
			else
			{
				System.out.println("It's a tie ");
				++tied;
			}
		
			System.out.println("Do you wish to play again [y, n] : ");
			str = stdIn.nextLine();
			c = str.charAt(0);
			if (c == 'n')
				{
					report(won,tied, lost);
					break;
				}
		}
		stdIn.close();
	}
	//displays a brief accounting of the rules and format of the game
	public static void welcome()
	{
		System.out.println('\t' + "Welcome to Computer Dice");
		System.out.println("------------------------------------------");
		System.out.println("You will be playing dice against the computer");
		System.out.println();
		System.out.println("you can only win with a Triple or a Pair");
		System.out.println("any Triple beats any Pair");
		System.out.println("any Triple beats any Nothing");
		System.out.println("in case of two Triples - high Triple wins");
		System.out.println("any Pair beats any Nothing");
		System.out.println("in case of two Pairs - high pair wins");
		System.out.println("in case of two identical Pairs - high card wins");
		System.out.println("in the case of two Nothings - its a tie");
		System.out.println("------------------------------------------");
	}
	//returns true only when triple
	public static boolean isTriple(int d1, int d2, int d3)
	{
		if (d1 == d2 && d2 == d3)
		{
		return true;
		}
		else
		{
		return false;
		}
	}
	//returns true only when pair
	public static boolean isPair(int d1, int d2, int d3)
	{
		if (d1 == d2 || d1 == d3 || d2 == d3)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	//determine win, tie, lose
	public static int determineRound(int p1, int p2, int p3, int o1, int o2, int o3)
	{
		int round = -2;
		
		if (isPair(p1, p2, p3) == true && isPair(o1, o2, o3) == false)	
		{
			round = 1;
		}
		else if (isPair(o1, o2, o3) == true && isPair(p1, p2, p3) == false)
		{
			round = -1;
		}
		else
		{
			if (isTriple(p1, p2, p3) == true && isTriple(o1, o2, o3) == false)	
			{
				round = 1;
			}
			else if (isTriple(o1, o2, o3) == true && isTriple(p1, p2, p3) == false)
			{
				round = -1;
			}
			else if (isTriple(p1, p2, p3) == true && isTriple(o1, o2, o3) == true)
			{
				if ( p1 > o1)
				{
					round = 1;
				}
				else
				{
					round = -1;
				}
			}
			else if (isPair(o1, o2, o3) == true && isPair(p1, p2, p3) == true)
			{
				if (p1 > o1)
				{
					round = 1;
				}
				else
				{
					round = -1;
				}
			}
			else
			{
				round = 0;
			}
		}
		return round;
	}
	//displays a report of the game's outcomes to the screen
	public static void report(int wins, int ties, int loses)
	{
		System.out.println("Computer Dice Results");
		System.out.println("------------------------");
		System.out.println("You played " + (wins + loses + ties) + " rounds");
		System.out.println();
		System.out.println("Rounds wons : " + wins);
		System.out.println("Rounds tied : " + ties);
		System.out.println("Rounds lost : " + loses);
		System.out.println("------------------------");
	}
}
