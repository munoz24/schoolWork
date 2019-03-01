/* 
 * Andres Munoz
 * Section 805
 * munoz24@uwm.edu
 * Program04
*/
import java.util.Scanner;

public class Program04
	{
	public static void main(String[] args)
	{
		int cnt = 0;
		int index = 0;
		boolean valid = true;
		Scanner stdIn = new Scanner(System.in);
		System.out.print("Please enter a valid double literal : ");
		String str = stdIn.nextLine();
		System.out.println();

		if (str.charAt(index)== '+' || str.charAt(index)== '-')
		{
			index = 1;
		}
		while(index < str.length())
		{
			if(str.charAt(index) == '.')
			{
				cnt++;
			}
			if(str.charAt(index) >= '0' && str.charAt(index) <= '9')
			{
				valid = true;
			}
			else
			{
				valid = false;
			}
			index++;
		}
		if (cnt > 2 || cnt < 1)
		{
			valid = false;
		}
		else
		{
			valid = true;
		}
		if (valid)
		{
			System.out.println(str + " is a valid double literal");
		}
		else
		{
			System.out.println(str + " is not a valid double literal");
		}
		stdIn.close();
	}
}