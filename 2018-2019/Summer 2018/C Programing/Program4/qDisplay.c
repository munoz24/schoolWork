#include "qDisplay.h"
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <stdio.h>
#include "itoa.h"

void quit(int Fd, char *students, int assignments[1000][100], int exams[1000][100], int countSt, int addAs, int addEx)
{
	int gradeA = 0;
	int gradeE = 0;
	int finalG = 0;
	int aE = 0;
	char header[256];
	char A[2] = "A";
	char E[2] = "E";
	char T[6] = "T\r\n";
	char tab[] = "	";
	char *temp;
	int val;
	char studentInfo[1000];
	strcat(header, tab);
	strcat(header, tab);
	int c = open("GradeBook.txt", O_CREAT | O_WRONLY, 0777);

	for(int g = 0; g < addAs; g++)
	{
		itoa(g, temp);
		strcat(header, A);
		strcat(header, temp);
		strcat(header, tab);
	}
	for(int g = 0; g < addEx; g++)
        {
                itoa(g, temp);
		strcat(header, E);
                strcat(header, temp);
                strcat(header, tab);
        }

	strcat(header, T);
	write(c, header, strlen(header));


	char backup[1000];
	strcpy(backup, students);

	char *token;
	token = strtok(backup, " ");

	while(token != NULL)
	{
		for(int i = 0; i < countSt; i++)
		{
			strcat(studentInfo, token);
			strcat(studentInfo, tab);
			for(int j = 0; j < addAs; j++)
			{
				int num = assignments[i][j];
				char *n;
				itoa(num, n);
				gradeA += assignments[i][j];
				strcat(studentInfo, n);
				strcat(studentInfo, tab);
			}
			for(int j = 0; j < addEx; j++)
                        {
				int num = exams[i][j];
                                char *n;
                                itoa(num, n);
                                gradeE += exams[i][j];
                                strcat(studentInfo, n);
                                strcat(studentInfo, tab);
                        }
			aE = addAs + addEx;
			finalG = gradeA + gradeE;
			finalG = finalG / aE;

			char *m;
			itoa(finalG, m);
			strcat(studentInfo, m);
			strcat(studentInfo, "/r/n");
			write(c, studentInfo, strlen(studentInfo));
			token = strtok(NULL, " ");
		}
		token = NULL;
	}
	exit(0);
}


void display(int Fd, char *students, int assignments[1000][100], int exams[1000][100], int countSt, int addAs, int addEx)
{
	int gradeA = 0;
        int gradeE = 0;
        int finalG = 0;
        int aE = 0;
        char header[256];
        char A[2] = "A";
        char E[2] = "E";
        char T[6] = "T\r\n";
        char tab[] = "  ";
        char *temp;
        char studentInfo[1000];
        strcat(header, tab);
        strcat(header, tab);

        for(int g = 0; g < addAs; g++)
        {
                itoa(g, temp);
                strcat(header, A);
                strcat(header, temp);
                strcat(header, tab);
        }
        for(int g = 0; g < addEx; g++)
        {
                itoa(g, temp);
                strcat(header, E);
                strcat(header, temp);
                strcat(header, tab);
        }

        strcat(header, T);
        write(Fd, header, strlen(header));


        char backup[1000];
        strcpy(backup, students);

        char *token;
        token = strtok(backup, " ");

	while(token != NULL)
        {
                for(int i = 0; i < countSt; i++)
                {
                        strcat(studentInfo, token);
                        strcat(studentInfo, tab);
                        for(int j = 0; j < addAs; j++)
                        {

				int num = assignments[i][j];
                                char *n;
                                itoa(num, n);

                                gradeA += assignments[i][j];
                                strcat(studentInfo, n);
                                strcat(studentInfo, tab);
                        }
                        for(int j = 0; j < addEx; j++)
                        {
				int num = exams[i][j];
                                char *n;
                                itoa(num, n);
                                gradeE += exams[i][j];
                                strcat(studentInfo, n);
                                strcat(studentInfo, tab);
                        }
                        aE = addAs + addEx;
                        finalG = gradeA + gradeE;
                        finalG = finalG / aE;
			char *m;
                        itoa(finalG, m);
                        strcat(studentInfo, m);
                        strcat(studentInfo, "/r/n");
                        write(Fd, studentInfo, strlen(studentInfo));
                        token = strtok(NULL, " ");
                }
                token = NULL;
        }


}



