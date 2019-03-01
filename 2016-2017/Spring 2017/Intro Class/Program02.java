/*
 * Andres Munoz
 * munoz24@uwm.edu
 * Computer Science 250 Section 805
 * 
 * Purpose: This program can calculate the surface area of a rectangular prism 
 * given the height, width, and depth. It will also calculate a single rectangular 
 * piece of wrapping paper that will cover the entire surface area of the box 
 * 
*/
public class Program02 
	{
	public static void main(String[] args)
	{
		// All variables for rectangular box
		double h = 10.0; // height of the box
		double w = 8.0; // width of the box
		double d = 6.0; // depth of the box
		double a; // surface area of box
		String i = " in"; // units of measurements for width, height, depth
		String s = " in squared"; // units of measurements for surface area
		
		// Known formula of surface area of a rectangle 
		a = 2* ((w * d) + (h * d) + (h * w)); 
		
		// Display all variables and surface area 
		System.out.print("Given a box with a height of ");
		System.out.println(h + i);
		System.out.print("a width of ");
		System.out.print(w + i);
		System.out.print(" and a depth of ");
		System.out.println(d + i);
		System.out.println();
		System.out.print("The total surface area of the box is : ");
		System.out.println(a + s + ".");
		System.out.println();
		
		// All variables for wrapping paper 
		double x; // Length of wrapping paper
		double y; // Width of wrapping paper
		
		// Formula to find wrapping paper length given height, width and depth of a rectangular box
		x = (2 * d) + ( 2 * w); 
		
		// Formula to find wrapping paper width given height, width and depth of a rectangular box
		y = (2 * d) + h;
		
		// Display wrapping paper length and width
		System.out.print("The length of the piece of wrapping paper is ");
		System.out.print(x + i);
		System.out.println(".");
		System.out.print("The width of the piece of wrapping paper is ");
		System.out.print(y + i);
		System.out.print(".");	
	}

}