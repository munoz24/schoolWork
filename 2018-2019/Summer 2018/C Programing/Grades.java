import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.Writer;

public class Grades {

	public static void main(String [] args){
		//input scanner
		Scanner input = new Scanner(System.in);
		//setting up arrays
		ArrayList<String> students = new ArrayList<>();
		ArrayList<ArrayList<Integer>> assignments = new ArrayList<>();
		ArrayList<ArrayList<Integer>> exams = new ArrayList<>();

		//string for input
		String command = "";
		//main loop
		while(true) {
			String secondCommand = null;
			System.out.println("Command > ");
			command = input.nextLine();
			//Splitting input into different elements
			String strArray [] = command.split(" ");
			//placing elements into string variables 
			String firstCommand = strArray[0];
			if(strArray.length > 1) {
				secondCommand = strArray[1];
			}
			//Switch loop
			switch(firstCommand) {
			//addS
			case "q" : {
				updateFile(students, assignments, exams);
				System.exit(0);
			}
			case "addS":{
				String name = secondCommand;

				if(name == null){
					System.out.println("No name entered");
					break;
				}
				if(students.contains(name)) {
					System.out.println("Student of name "+ name +" already exist");
				}
				//Name does not Exist
				else {
					students.add(name);
					ArrayList<Integer> createA = new ArrayList<>();
					ArrayList<Integer> createE = new ArrayList<>();
					if(assignments.size() > 0) {
						int n = assignments.get(0).size();
						for(int i = 0; i < n; i++) {
							createA.add(new Integer(0)); 
						}
					}
					if(exams.size() > 0) {
						int n = exams.get(0).size();
						for(int i = 0; i < n; i++) {
							createE.add(new Integer(0));
						}
					}
					assignments.add(createA);
					exams.add(createE);
				}
				break;
			}
			//addA and addE should be the same besides assignments.get(i) and exams.get(i) 
			case "addA" :{
				//no students 
				if(students.size() <= 0) {
					System.out.println("No students in gradebook");
					//skip if there are students
					break;
				}

				for(int i =0;i<students.size();i++){
					assignments.get(i).add(new Integer(0));
				}
				break;
			}

			case "addE" :{
				//no students 
				if(students.size() <= 0) {
					System.out.println("No students in gradebook");
					//skip if there are students
					break;
				}

				for(int i =0;i<students.size();i++){
					exams.get(i).add(new Integer(0));
				}

				break;
			}

			case "grade" : {
				//no students 
				if(students.size() < 0) {
					System.out.println("No students in gradebook");
					//skip if there are students
					break;
				}


				//putting name of student in string from array
				String name = secondCommand;
				int grade;
				//getting index of student 
				int x = students.lastIndexOf(name);
				int y = 1;
				//skip if student name does not exist
				if(!(students.contains(name))) {
					System.out.println("Student of name "+ name +" does not exist");

				}
				else {
					for(int i = 0; i < assignments.get(0).size(); i++){
						//Displays what assignment its on
						System.out.println("Assignment " + y + ":");
						++y;
						do{
							try {
								//Displays student and assignment grade
								System.out.println(students.get(x)+": " + assignments.get(x).get(i));
								System.out.print("Enter new Grade (0 to skip): ");
								grade = Integer.parseInt(input.nextLine());
								//valid input
								if(grade >= 0 && grade <= 100){
									break;
								}
								//valid input
							}catch(Exception e) {
								continue;
							}
						}while(true);
						//-1 does not change grade
						if (grade == 0){
							break;
						}
						// other inputs changes grade
						if(grade >1 && grade <= 100){
							assignments.get(x).set(i, new Integer(grade));
						}
					}
					// Same format but with exams
					y = 1;
					for(int i = 0; i < exams.get(0).size(); i++){
						System.out.println("Exam " + y + ":");
						++y;
						do {
							try {


								System.out.println(students.get(x) +": " + exams.get(x).get(i));
								System.out.print("Enter new Grade (0 to skip): ");
								grade = Integer.parseInt(input.nextLine());
								if(grade >= 0 && grade <= 100){
									break;
								}

							}catch(Exception e) {
								continue;
							}
						} while(true);
						if (grade == 0){
							break;
						}
						if(grade >1 && grade <= 100){
							exams.get(x).set(i, new Integer(grade));
						}
					}
				}
				break;
			}

			case "gradeA":{
				if(students.size() <= 0) {
					System.out.println("No students in gradebook");
					//skip if there are students
					break;
				}
				String name = secondCommand;
				//converting string input to integer
				if(name == null){
					System.out.println("No name entered");
					break;
				}
				
				int numA = Integer.parseInt(secondCommand);				

				numA = numA - 1;

				int grade;
				for(int i = 0; i < assignments.get(0).size(); i++) {

					//Displays what assignment its on
					System.out.println("Assignment " + (numA + 1) + ":");
					for(int x = 0; x < students.size(); x++) {
						while(true) {
							try {
								//Displays student and assignment grade
								System.out.println(students.get(x) +": " + assignments.get(x).get(i));
								System.out.print("Enter new Grade (0 to skip): ");
								grade = Integer.parseInt(input.nextLine());
								//valid input
								if(grade >= 0 && grade <= 100){
									break;
								}
							}catch(Exception e) {
								continue;
							}
						}
						//0 does not change grade
						if (grade == 0){
							continue;
						}
						if (grade > 0 && grade <= 100){
							assignments.get(x).set(i, new Integer(grade));
						}
					}
					i = i +1;
				}
				break;
			}
			case "gradeE":
			{
				if(students.size() <= 0) {
					System.out.println("No students in gradebook");
					//skip if there are students
					break;
				}
				String name = secondCommand;
				//converting string input to integer
				if(name == null){
					System.out.println("No name entered");
					break;
				}
				//converting string input to integer
				int numE = Integer.parseInt(secondCommand);				

				numE = numE - 1;

				int grade;
				for(int i =0;i<exams.get(0).size();i++){
					System.out.println("Exams "+(numE + 1)+": ");
					for(int x =0;x<students.size();x++){
						while(true){
							try{
								System.out.println(students.get(x)+": "+exams.get(x).get(i));
								System.out.print("Enter new Grade (0 to skip): ");
								grade =Integer.parseInt(input.nextLine());
								if(grade >= 0 && grade <= 100){
									break;
								}

							}catch(Exception e){
								continue;
							}
						}
						if (grade == 0){
							continue;
						}
						if(grade > 0 && grade <= 100){
							exams.get(x).set(i,new Integer(grade));
						}
					}
					i = i +1;
				}
				break;
			}
			case "display":{
				display(students,assignments,exams);
				break;
			}
			// Invalid input
			default : {
				System.out.println("Invalid Command");
			}
			}
		}
	}

	public static void updateFile(ArrayList<String>student, ArrayList<ArrayList<Integer>>assignment, ArrayList<ArrayList<Integer>>exam){
		ArrayList<Integer> GradeA  = new ArrayList<>();
		ArrayList<Integer> GradeE  = new ArrayList<>();
		try {
			//creating text file
			PrintWriter file = new PrintWriter(new File("grades.txt"));
			
			
			String A = "A";
			String E = "E";
			int y;
			int number = 0;
			file.print("\t\t");
			for(int i = 0;i < student.size(); i++){
				for(y = 0; y < assignment.get(i).size() ; ++y){
					++number;
					file.print(A + number + "\t");
				}
				number = 0;

				for(y = 0; y < exam.get(i).size() ; ++y ){
					++number;
					file.print(E + number + "\t");
				}
				break;
			}
			file.print("T");
			file.println();
			
			for(int i = 0; i < student.size(); i++) {
				GradeA.add(new Integer(0));
				GradeE.add(new Integer(0));
				
				file.print(student.get(i) + "\t\t");
				for(int x = 0; x < assignment.get(i).size(); x++) {
					file.print(assignment.get(i).get(x)+"\t");
					GradeA.set(i,new Integer((int)GradeA.get(i) + (int)assignment.get(i).get(x)));
				}
				for(int x = 0; x < exam.get(i).size(); x++) {
					file.print(exam.get(i).get(x)+"\t");
					GradeE.set(i,new Integer((int)GradeE.get(i) + (int)exam.get(i).get(x)));
				}
				file.print((((int)GradeA.get(i)+(int)GradeE.get(i))/(assignment.get(i).size()+exam.get(i).size()))+"\n");
				file.print("\n");
			}
			file.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void display(ArrayList<String>student, ArrayList<ArrayList<Integer>>assignment, ArrayList<ArrayList<Integer>>exam) {
		ArrayList<Integer> GradeA  = new ArrayList<>();
		ArrayList<Integer> GradeE  = new ArrayList<>();
		//Printing Assignment and Exam titles
		String A = "A";
		String E = "E";
		int y;
		int number = 0;
		System.out.print("\t");
		for(int i = 0;i < student.size(); i++){
			for(y = 0; y < assignment.get(i).size() ; ++y){
				++number;
				System.out.print(A + number + "\t");
			}
			number = 0;

			for(y = 0; y < exam.get(i).size() ; ++y ){
				++number;
				System.out.print(E + number + "\t");
			}
			break;
		}
		System.out.print("T");

		System.out.println();

		for(int i = 0;i < student.size(); i++){
			GradeA.add(new Integer(0));
			GradeE.add(new Integer(0));


			System.out.print(student.get(i) + "\t");
			for(int x =0; x < assignment.get(i).size(); x++){
				System.out.print(assignment.get(i).get(x)+"\t");
				GradeA.set(i,new Integer((int)GradeA.get(i) + (int)assignment.get(i).get(x)));
			}
			for(int x = 0; x < exam.get(i).size(); x++) {
				System.out.print(exam.get(i).get(x)+"\t");
				GradeE.set(i,new Integer((int)GradeE.get(i) + (int)exam.get(i).get(x)));
			}
			System.out.print((((int)GradeA.get(i)+(int)GradeE.get(i))/(assignment.get(i).size()+exam.get(i).size()))+"\n");
		}

	}
}
