import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

interface Raiseable
{

	public void create(String fileName);
	
	public void display(String fileName);
	
	public boolean addTo(String inFileName, String outFileName, int id,  double salary,
			int yearsOfService);
	
	public boolean removeFrom(String inFileName, String outFileName, int id,  double salary,
			int yearsOfService);
	
	public int raise(String inFileName, String outFileName, int yearsOfService,
			double salaryIncPercent);
	
	public void mergeFiles(String inFileName1, String inFileName2, String outFileName);

}
