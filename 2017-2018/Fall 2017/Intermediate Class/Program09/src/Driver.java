public class Driver {
	
	public static void main(String[] args)
	{
		Employee emp1 = new Employee();  
		
		emp1.create("new.txt");
		emp1.create("test.txt");
		
		emp1.addTo("test.txt", "test2.txt", 1, 1400, 2);
		emp1.addTo("test2.txt", "test3.txt", 2, 1000, 8);
		emp1.addTo("test3.txt", "test4.txt", 3, 1500, 6);
		emp1.addTo("test4.txt", "test5.txt", 4, 2000, 4);
		
		
		emp1.addTo("new.txt", "new2.txt", 1, 1500, 2);
		emp1.addTo("new2.txt", "new3.txt", 2, 1400, 3);
		emp1.addTo("new3.txt", "new4.txt", 6, 1300, 6);
		
		System.out.println(emp1.removeFrom("test5.txt", "test6.txt", 3, 1500, 6));
		System.out.println(emp1.removeFrom("test6.txt", "test7.txt", 5, 1000, 2));
		
		emp1.raise("test7.txt", "test8.txt", 5, 5);
		
		emp1.mergeFiles("new4.txt", "test8.txt", "merge.txt");
		
		
		emp1.display("merge.txt");
		
		
	    
	    
		
		
		
	}
}
