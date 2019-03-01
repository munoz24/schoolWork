import java.util.Scanner;

public class Program06 {
	public static void  main (String[] args)
	{
		Scanner stdIn = new Scanner(System.in);
		//Variables
		int cnt = 0;
		int wins = 0;
		int loses = 0;
		char c;
		char t = 0;
		char v = 0;
		char b = 0;
		int d1;
		int d2;
		int d3;
		int d4;
		
		//Game rules
		System.out.println('\t' + "Welcome to Computer Dice");
		System.out.println("------------------------------------------");
		System.out.println("You will first roll your dice");
		System.out.println();
		System.out.println("You then are allowed to re-roll upto");
		System.out.println("two of your dice");
		System.out.println();
		System.out.println("Finally the outcome of your roll is");
		System.out.println("be determined");
		System.out.println();
		System.out.println("any Quad and you receive 108 Wins");
		System.out.println("any Triple and you receive 6 Wins");
		System.out.println("any Two-Pair and your receive 4 Wins");
		System.out.println("anything else and your receive 1 Lose");
		System.out.println();
		System.out.println();
		
		System.out.println("Would you like to play [y, n]: ");
		String str = stdIn.nextLine();
		c = str.charAt(0);
		
		//Game
		while (c == 'y')
		{
			//Dice
			++cnt;
			
			System.out.println("Player");
			System.out.println("---------");
			d1 = (int)(Math.random() * 6) + 1;
			d2 = (int)(Math.random() * 6) + 1;
			d3 = (int)(Math.random() * 6) + 1;
			d4 = (int)(Math.random() * 6) + 1;
			System.out.println(d1 + " " + d2 + " " + d3 + " " + d4);
			
			//Re-Roll
			do 
			{
				System.out.println("Please enter the number of dice to re-roll [0, 2] : ");
				String str2 = stdIn.nextLine();
				c = str2.charAt(0);
				
				//Two Dice
				if (c == '2')
				{
					do
					{
						System.out.println("Please enter the number of the die to re-roll [1, 4] : ");
						String str3 = stdIn.nextLine();
						v = str3.charAt(0);
					} while (v <='1' && v >= '4');
					do 
					{
						System.out.println("Please enter the number of the die to re-roll [1, 4] : ");
						String str4 = stdIn.nextLine();
						b = str4.charAt(0);
					} while (b <='1' && b >= '4');
					if (t == '1' || b == '1' || v == '1')
					{
						d1 = (int)(Math.random() * 6) + 1;
					}
					else if (t == '2' || b == '2' || v == '2')
					{
						d2 = (int)(Math.random() * 6) + 1;
					}
					else if (t == '3' || b == '3' || v == '3')
					{
						d3 = (int)(Math.random() * 6) + 1;
					}
					else if (t == '4' || b == '4' || v == '4')
					{
						d4 = (int)(Math.random() * 6) + 1;
					}
				}
				
				//One Die
				else if (c == '1')
				{
					do 
					{
						System.out.println("Please enter the number of the die to re-roll [1, 4] : ");
						String str3 = stdIn.nextLine();
						t = str3.charAt(0);
					} while (t <= '1' && t >= '4');
				}
				else if (c == '0')
				{
					break;
				}
				
				//Choosing between what die to roll
				if (t == '1' || b == '1' || v == '1')
				{
					d1 = (int)(Math.random() * 6) + 1;
				}
				else if (t == '2' || b == '2' || v == '2')
				{
					d2 = (int)(Math.random() * 6) + 1;
				}
				else if (t == '3' || b == '3' || v == '3')
				{
					d3 = (int)(Math.random() * 6) + 1;
				}
				else if (t == '4' || b == '4' || v == '4')
				{
					d4 = (int)(Math.random() * 6) + 1;
				}
				
			} while(c !='1' && c != '2');
			
			//Printing out new dice
			System.out.println("Player");
			System.out.println("---------");
			System.out.println(d1 + " " + d2 + " " + d3 + " " + d4);
			
			//Check wins or loses
			if ( d1 == d2 && d2 == d3 && d3 == d4)
			{
				System.out.println("Quad - Congrats, you win!");
				wins += 108;
			}
			else if (d1 == d2 && d2 == d3 ||
					d1 == d3 && d3 == d4 ||
					d2 == d3 && d3 == d4 ||
					d1 == d2 && d2 == d4)
			{
				System.out.println("Triple - Congrats, you win!");
				wins += 6;
				
			}
			else if (d1 == d2 || d1 == d3 || d1 == d4 || 
					d2 == d3 || d2 == d3 || d2 == d4 ||
					d3 == d4)
			{
				System.out.println("Two-Pair - Congrats, you win!");
				wins += 4;
				
			}
			else if ( !(d1 == d2 && d2 == d3 && d3 == d4))
			{
				System.out.println("Junker - Sorry, you lose!");
				++loses;
			}
			
			System.out.println("Do you wish to play again [y, n] : ");
			String str3 = stdIn.nextLine();
			c = str3.charAt(0);
			if (c == 'n')
			{
				break;
			}
			
		}
		
		//Display Result
		if (c == 'n')
		System.out.println("Computer Dice Results");
		System.out.println("------------------------");
		System.out.println("You played " + cnt + " rounds");
		System.out.println();
		System.out.println("Wins : " + wins);
		System.out.println("Loses : " + loses);
		stdIn.close();
	}
}
