import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Scanner;

public class Employee implements Raiseable{

	public void create(String fileName)
	{

		try
		{
			PrintWriter outFile = new PrintWriter(fileName);
			outFile.close();
		}
		catch (IOException e)
		{
			System.out.println("File Error : " + e.getMessage());
		}
		// Creates a new (empty) file that can later be used to add entries of the above
		// format
	}
	public void display(String fileName)
	{
		try {
			Scanner file = new Scanner(new File(fileName));
			while(file.hasNextLine())
			{
				String var = file.nextLine();
				System.out.println(var);
			}
			file.close();
		}
		catch(IOException e)
		{
			System.out.println("File Error : " + e.getMessage());
		}
		// Formats and displays the contents of the specified file to the screen

	}
	public boolean addTo(String inFileName, String outFileName, int id,  double salary,
			int yearsOfService)
	{
		boolean x = true;
		boolean write = false;
		boolean check = false;
		String [] arr;
		try(Scanner fileIn = new Scanner(Paths.get(inFileName));
				PrintWriter fileOut = new PrintWriter(outFileName))
		{
			if(fileIn.hasNextLine() == false)
			{
				fileOut.println(id + ":" + salary+ ":" + yearsOfService);
				check = true;
			}

				while(fileIn.hasNextLine()) {
					String in =  fileIn.nextLine();
					arr = in.split(":");
					int readId = Integer.parseInt(arr[0].trim());

					if (readId == id)
					{
						x = false;
					}
					else {
						if (readId < id ) {
							fileOut.println(in);
						}
						else
						{
							if (write == true)
							{
								fileOut.println(in);
							}
							else
							{
								fileOut.println(id + ":" + salary+ ":" + yearsOfService);
								check = true;
								fileOut.println(in);
								write = true;
							}
						}
					}
				}
				if(check == false)
				{
					fileOut.println(id + ":" + salary+ ":" + yearsOfService);
				}
		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return x;
	}
	// Creates a new file that is a copy of the given input file - with the new
	// entry added such that the new file is sorted by the entries’ ids
	// Return true if no match was found and falthe entry was added; se if a duplicate id

	public boolean removeFrom(String inFileName, String outFileName, int id,  double salary,
			int yearsOfService)
	{
		boolean x = false;
		String [] arr;
		String in;

		try(Scanner fileIn = new Scanner(Paths.get(inFileName));
				PrintWriter fileOut = new PrintWriter(outFileName))
		{
			while(fileIn.hasNextLine()) {
				in =  fileIn.nextLine();
				arr = in.split(":");
				int readId = Integer.parseInt(arr[0].trim());
				double readS = Double.parseDouble(arr[1].trim());
				int readY = Integer.parseInt(arr[2].trim());

				if (readId == id && readS == salary && readY == yearsOfService)
				{
					x = true;
				}
				else {
					fileOut.println(in);
				}
			}

		}
		catch(IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return x;
		// Creates a new file that is a copy of the given input file - with the specified
		// entry removed
		// Returns true if a matching entry was found / removed; false if no such entry exists
	}
	public int raise(String inFileName, String outFileName, int yearsOfService,
			double salaryIncPercent)
	{
		String [] arr;
		String in;
		int cnt = 0;
		

		try(Scanner fileIn = new Scanner(Paths.get(inFileName));
				PrintWriter fileOut = new PrintWriter(outFileName);)
		{
			while(fileIn.hasNextLine()) {

				in =  fileIn.nextLine();
				arr = in.split(":");
				double readS = Double.parseDouble(arr[1].trim());
				int readY = Integer.parseInt(arr[2].trim());

				if(readY >= yearsOfService)
				{
					readS = readS * salaryIncPercent;
					fileOut.println(arr[0]+ ":" + readS + ":" + arr[2]);
					++cnt;
				}
				else
				{
					fileOut.println(in);
				}
			}

		}
		catch(IOException e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		return cnt;
	}
	// Creates a new file that is a copy of the given input file - where all entries whose
	// years of service are greater than or equal to yearsOfService have had their salaries
	// increased by salaryIncPercent
	// Return the number of entries that the raise was applied to

	
	
	//merge not working. everything else is fine. 
	public void mergeFiles(String inFileName1, String inFileName2, String outFileName)
	{
		String [] arr;
		String [] arr2;
		int readCnt = 0;
		int readCnt2 = 0;
		int Cnt = 0;
		int Cnt2 = 0;
		String in;
		String in2;
		
		try(Scanner fileIn = new Scanner(Paths.get(inFileName1));)
		{
			while(fileIn.hasNextLine())
			{
				++readCnt;
			}
			fileIn.close();
		}
		catch (IOException e)
		{
			System.out.println("File Error : " + e.getMessage());
		}
		try(Scanner fileIn = new Scanner(Paths.get(inFileName2));)
		{
			while(fileIn.hasNextLine())
			{
				++readCnt;
			}
			fileIn.close();
		}
		catch (IOException e)
		{
			System.out.println("File Error : " + e.getMessage());
		}
		
		

		try(Scanner fileIn = new Scanner(Paths.get(inFileName1));
				Scanner fileIn2 = new Scanner(Paths.get(inFileName2));
				PrintWriter fileOut = new PrintWriter(outFileName);)
		{
			in =  fileIn.nextLine();
			arr = in.split(":");
			in2 =  fileIn2.nextLine();
			arr2 = in2.split(":");
			int readId = Integer.parseInt(arr[0].trim());
			double readS = Double.parseDouble(arr[1].trim());
			
			int readId2 = Integer.parseInt(arr2[0].trim());
			double readS2 = Double.parseDouble(arr2[1].trim());
			
			if(readCnt >= readCnt2)
			{
				while(fileIn.hasNextLine())
				{
					if(readId == readId2)
					{
						if (readS >= readS2)
						{
							fileOut.println(in);	
							++Cnt;
						}
						else
						{
							fileOut.println(in2);
							++Cnt2;
						}
					}
					else if(readId > readId2)
					{
						fileOut.println(in);	
						++Cnt;
						fileOut.println(in2);
						++Cnt2;
					}
					else 
					{
						fileOut.println(in2);
						++Cnt2;
						fileOut.println(in);
						++Cnt;
					}
				}
			}
			
			else
			{
				while(fileIn2.hasNextLine())
				{
					if(readId2 == readId)
					{
						if (readS2 >= readS)
						{
							fileOut.println(in2);	
							++Cnt2;
						}
						else
						{
							fileOut.println(in);
							++Cnt;
						}
					}
					else if(readId2 > readId)
					{
						fileOut.println(in2);	
						++Cnt2;
						fileOut.println(in);
						++Cnt;
					}
					else 
					{
						fileOut.println(in);
						++Cnt;
						fileOut.println(in2);
						++Cnt2;
					}
				}
			}
			
			
			while(Cnt != readCnt && Cnt2 != readCnt2)
			{
				if(Cnt != readCnt)
				{
					fileOut.println(in);
					++Cnt;
				}
				else if(Cnt2 != readCnt2)
				{
					fileOut.println(in2);
					++Cnt2;
				}
				
			}
		}
		catch(IOException e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		// Creates a new file that is a sorted merge of the two given (sorted) input files
		// In case of duplicate entries, only the one with the highest salary is kept in
		// outFileName
	}


}
