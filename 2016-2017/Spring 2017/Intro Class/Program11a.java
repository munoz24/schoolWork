import java.util.Scanner;

public class Program11a {
	public static void main(String[] args)
	{
		Scanner stdIn = new Scanner(System.in);
		
		
		final int maxSize = 128;
	    String[] titles = new String[maxSize];
	    int[] lengths = new int[maxSize];
	    int numDVDs = 0;
	    String menu;
	    
		menu = menu(stdIn);
		
		System.out.println();
	    while (!menu.equalsIgnoreCase("q"))
	    {
	      if (menu.equalsIgnoreCase("a"))
	        numDVDs = addDVD(titles, lengths, numDVDs, stdIn);
	      else if (menu.equalsIgnoreCase("t"))
	        searchByTitle(titles, lengths, numDVDs, stdIn);    
	      else if (menu.equalsIgnoreCase("l"))
	        searchByLength(titles, lengths, numDVDs, stdIn);
	     
	      System.out.println('\n');
	      menu = menu(stdIn);
	      System.out.println();
	    } 
		
		
		stdIn.close();
	}
	public static String menu(Scanner stdIn)
	{
		System.out.println("***************************");
		System.out.println("A  Add a DVD              *");
		System.out.println("T  Search by Title        *");
		System.out.println("L  Search by Length       *");
		System.out.println("Q  Quit                   *");
		
		String str;
		do
        {
			System.out.println("***************************");
			System.out.println("Please enter an option : ");
			str = stdIn.next();
        } while (!(str.equalsIgnoreCase("a") || str.equalsIgnoreCase("t") || str.equalsIgnoreCase("l")
                || str.equalsIgnoreCase("q")));
		System.out.println("***************************");
		
		return str;
	}
	public static int addDVD(String[] titles, int[] lengths, int numDVDs, Scanner stdIn)
	{
		String title;
	    int length;

	    System.out.print("Please enter DVD title : ");
	    title = stdIn.next();
	    System.out.print("Please enter DVD length : ");
	    length = stdIn.nextInt();
	   
	    titles[numDVDs] = title;
	    lengths[numDVDs] = length;
	   
	    return numDVDs + 1;
	}
	public static void searchByTitle(String titles[], int lengths[], int numDVDs, Scanner stdIn)
	{
		String title;

	    System.out.print("Please enter DVD title (post * allowed) : ");
	    title = stdIn.next();
	   
	    System.out.println();
	    System.out.println("Title\t\tLength");
	    System.out.println("-----------------------------");
	    if (title.charAt(title.length()-1) == '*')
	    {
	      String som = title.substring(0, title.length()-1);
	      for (int d = 0; d < numDVDs; ++d)
	      {
	        if (titles[d].length()>= som.length())
	        {
	          String tmp = titles[d].substring(0, som.length());
	          if (tmp.equals(som))
	            System.out.println(titles[d] + "\t" + lengths[d] + "min");
	        }
	      }
	    }
	    else
	      for (int d = 0; d < numDVDs; ++d)
	        if (titles[d].equals(title))
	          System.out.println(titles[d] + "\t" + lengths[d] + "min");
	    System.out.println();
	}
	public static void searchByLength(String[] titles, int[] lengths, int numDVDs, Scanner stdIn)
	{
		String sLength;
	    char lengthMod;
	    int length;

	    System.out.print("Please enter DVD length (pre < = > manditory) : ");
	    sLength = stdIn.next();
	    lengthMod = sLength.charAt(0);
	   
	    System.out.println();
	    System.out.println("Title\t\tLength");
	    System.out.println("---------------------------------------");
	    if (lengthMod == '<')
	    {
	      length = Integer.parseInt(sLength.substring(1, sLength.length()));
	      for (int d = 0; d < numDVDs; ++d)
	        if (lengths[d] < length)

	System.out.println(titles[d] + "\t" + lengths[d] + " min");
	    }
	    else if (lengthMod == '=')
	    {
	      length = Integer.parseInt(sLength.substring(1, sLength.length()));
	      for (int d = 0; d < numDVDs; ++d)
	        if (lengths[d] == length)
	          System.out.println(titles[d] + "\t" + lengths[d] + " min");
	    }
	    else if (lengthMod == '>')
	    {
	      length = Integer.parseInt(sLength.substring(1, sLength.length()));
	      for (int d = 0; d < numDVDs; ++d)
	        if (lengths[d] > length)
	          System.out.println(titles[d] + "\t" + lengths[d] + " min");
	    }
	    else
	      System.out.println("Format Error!");

	    System.out.println();
	}

}
