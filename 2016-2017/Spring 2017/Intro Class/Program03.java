/*
 * Andres Munoz
 * munoz24@uwm.edu
 * Computer Science 250 Section 805
 * 
 * Purpose: 
 * 
*/
import java.util.Scanner;
 
public class Program03 {
    public static void main(String[] args) 
    {
        System.out.println("Retailer");
    	
    	// Initiate a new Scanner
        Scanner stdIn = new Scanner(System.in);
 
        // First Candy
        System.out.print("Plese enter the name of first Candy: ");
        String name1 = stdIn.nextLine();
 
        // Price/Units of First Candy
        System.out.print("Please enter the price (per unit) of " + name1 + " $" );
        double cost1 = stdIn.nextDouble();
        System.out.println();
        
        // Second Candy
        System.out.print("Please enter the name of second Candy: ");
        stdIn.nextLine();
        String name2 = stdIn.nextLine();
       
        // Price/Units of Second Candy
        System.out.print("Please enter the price (per unit) of " + name2 + " $" );
        double cost2 = stdIn.nextDouble();
        
        //Consumer input
        System.out.println();
        System.out.println("Consumer");
        System.out.print("Enter number of units of " + name1 + " to purchase: ");
        int unit1 = stdIn.nextInt();
        System.out.print("Enter number of units of " + name2 + " to purchase: ");
        int unit2 = stdIn.nextInt();
        
        //Variables for price equations
        //d= discount
		//g=general (no tax applied)
		double discount = 0.15;
		double tax = 0.0575;
		double total_cost1;
		double total_cost2;
		double total_of_tax;
		double grand_total;
		
		//Total cost, Tax total, Grand total equations
		//d= discount
		//g=general (no tax applied)
		if (unit1 > 8)
		{
			total_cost1 = 8 * cost1 + (unit1 - 8) * cost1 * (1-discount); 
		}
		else
		{
			total_cost1 = unit1 * cost1;	
		}
		if (unit2 > 8)
		{
			total_cost2 = 8 * cost2 + (unit2 - 8) * cost2 * (1-discount);
		}
		else
		{
			total_cost2 = unit2 * cost2;
		}
		double total_cost = total_cost1 + total_cost2;
		total_of_tax = total_cost * tax;
		grand_total = total_cost + total_of_tax;

        //Order Summary
        System.out.println();
        System.out.println("Order Summary");
        System.out.println();
        System.out.println("Candy Units Price/Unit cost");
        System.out.println();
        if (unit1 > 8)
        	{
        	System.out.println(name1 + " " + 8 + " $" + cost1 + " $" + (8 * cost1));
        	System.out.println(name1 + " " + (unit1 - 8) + " $" + (cost1 * (1 - discount) + " $" + (total_cost1 - (8 * cost1))));
        	}
        else
        {
        	 System.out.println(name1 + " " + unit1 + " $" + cost1 + " $" + total_cost1);
        }
		System.out.println();
		if (unit2 > 8)
		{
		System.out.println(name2 + " " + 8 + " $" + cost2 + " $" + (8 * cost2));
		System.out.println(name1 + " " + (unit2 - 8) + " $" + (cost2 * (1 - discount) + " $" + (total_cost2 - (8 * cost2))));
		}
		else
		{
			System.out.println(name2 + " " + unit2 + " $" + cost2 + " $" + total_cost2);	
		}
		System.out.println();
		System.out.println("Total order cost " + total_cost);
		System.out.println("Total order tax $" + total_of_tax);
		System.out.println(); 
		System.out.println("Order Grand Total $" + grand_total);
     
        //CLosing Scanners
        stdIn.close(); 
    }
}
