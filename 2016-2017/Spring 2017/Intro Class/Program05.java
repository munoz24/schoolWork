import java.util.Scanner;

public class Program05 {
	public static void main(String[] args)
	{
    	Scanner stdIn = new Scanner(System.in);
    	char c;
    	char b1 = ' ';
    	char b2 = ' ';
    	char b3 = ' ';
    	char b4 = ' ';
    	char b5 = ' ';
    	char b6 = ' ';
    	char b7 = ' ';
    	char b8 = ' ';
    	char b9 = ' ';
    	int cnt = 1; 
   
    	System.out.println("Welcome to Tic-Tac-Toe : X goes first");
    	System.out.println();
    	System.out.println( b1 + "|" + b2 + "|" + b3);
    	System.out.println( b4 + "|" + b5 + "|" + b6);
    	System.out.println( b7 + "|" + b8 + "|" + b9);
    	System.out.println();
    	do
    	{
    	System.out.println("Please enter location to move [1-9] : ");
    	String str = stdIn.next();
    	c = str.charAt(0);
    	cnt ++;
    	
    	} while (c <= '1' && c >= '9');
    	
    	if (c == '1')
    	{
    		b1 = 'X';
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c <= '1' && c >= '9');
    	}
    	else if(c == '2')
    	{
    		b2 = 'O';
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c != '1' && c != '9');
    	}
    	else if (c == '3')
    	{
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c != '1' && c != '9');
    	}
    	else if (c =='4')
    	{

    		System.out.println( b1 + "|" + b2 + "|" + b3);
    		System.out.println( b4 + "|" + b5 + "|" + b6);
    		System.out.println( b7 + "|" + b8 + "|" + b9);
    		System.out.println();
    		do
    		{
    			System.out.println("Please enter location to move [1-9] : ");
    			String str = stdIn.next();
    			c = str.charAt(0);
    			cnt += 1;
    	
    		}while (c != '1' && c != '9');
    	}
    	else if (c == '5')
    	{
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c != '1' && c != '9');
    	}
    	else if (c == '6')
    	{
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c != '7' && c != '9');
    	}
    	else if (c == '7')
    	{
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c != '1' && c != '9');
    	}
    	else if (c == '8')
    	{
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c != '1');
    	}
    	else if (c == '9')
    	{
    		b9 = 'X';
    		System.out.println( b1 + "|" + b2 + "|" + b3);
        	System.out.println( b4 + "|" + b5 + "|" + b6);
        	System.out.println( b7 + "|" + b8 + "|" + b9);
        	System.out.println();
        	do
        	{
        	System.out.println("Please enter location to move [1-9] : ");
        	String str = stdIn.next();
        	c = str.charAt(0);
        	cnt += 1;
        	
        	} while (c <= '1' && c >= 9);
    	}
    	
    	if (cnt == '9')
    	{
    		System.out.println("It is a tie");
    	}
		stdIn.close();
	}
}
