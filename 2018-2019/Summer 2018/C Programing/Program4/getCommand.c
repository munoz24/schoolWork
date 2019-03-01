#include <string.h>
#include <unistd.h>
#include <stdlib.h>
#include "getCommand.h"

#include "add.h"
#include "grade.h"
#include "qDisplay.h"

void getCommand(char *full, char *one, char *two)
{

        char *token;
        //first word into *one
        token = strtok(full, " ");
        one = (char *) malloc(sizeof(token));
        strcat(one, token);

        //second word into *two
        token = strtok(NULL, " ");
        two = (char *)malloc(sizeof(token));
        strcat(two, token);



}

void callFunction(int Fd, char *one, char *two, char *students, int assignments[1000][100], int exams[1000][100],
									int countSt, int addAs, int addEx)
{

        char addST[5] = "addS";
        char addAT[5] = "addA";
        char addET[5] = "addE";
        char gradeT[6] = "grade";
        char gradeAT[7] = "gradeA";
        char gradeET[7] = "gradeE";
        char displayT[8] = "display";
        char quitT[5] = "quit";
	char error[20] = "Not a valid command";

        int n = strcmp(one, addST);
        if(n == 0)
        {
                addS(Fd, two, students, countSt);
        }

        n = strcmp(one, addAT);
        if(n == 0)
        {
		addAs++;
                addA(Fd, assignments, countSt, addAs);
        }

        n = strcmp(one, addET);
        if(n == 0)
        {
		addEx++;
                addE(Fd, exams, countSt, addEx);
        }
	n = strcmp(one, gradeT);
        if(n == 0)
        {
                grade(Fd, two, students, assignments, exams, addAs, addEx);
        }

        n = strcmp(one, gradeAT);
        if(n == 0)
        {
                gradeA(Fd, two, students, assignments, addAs, countSt);
        }

        n = strcmp(one, gradeET);
        if(n == 0)
        {
                gradeE(Fd, two, students, exams, addEx, countSt);
        }
	n = strcmp(one, displayT);
        if(n == 0)
        {
                display(Fd, students, assignments, exams, countSt, addAs, addEx);
        }

        n = strcmp(one, quitT);
        if(n == 0)
        {
                quit(Fd, students, assignments, exams, countSt, addAs, addEx);
        }

	else
	{
		write(Fd, error, strlen(error));
	}

}
