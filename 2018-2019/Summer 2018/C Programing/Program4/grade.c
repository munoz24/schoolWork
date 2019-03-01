#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include "grade.h"
#include "itoa.h"

void grade(int Fd, char *name, char *students, int assignments[1000][100], int exams[1000][100], int addAs, int addEx)
{
	char backup[1000];
	char backup2[1000];
	char *token;
	int compare;
	char nameE[13] = "Invalid Name";

	strcpy(backup, students);
	strcpy(backup2, students);

	token = strtok(backup, " ");
	int count = 0;

	while(token != NULL)
	{
		compare = strcmp(token, name);
		if(compare == 0)
		{
			break;
		}
		count++;
		token = strtok(NULL, " ");
	}

	if(token == NULL)
	{
		write(Fd, nameE, strlen(nameE));
	}


	char nameP[2] = ":";
	strcat(name, nameP);
	char *newToken;
	newToken = strtok(backup2, " ");


	while(newToken != NULL)
	{

		char buf[256];
		int n;
		int v = 0;
		char *num;
		write(Fd, name, strlen(name));
		for(int j = 0; j < addAs; j++)
		{
			char assignP[] = "Assignment ";
                        char enter[8] = "Enter: ";

			if(assignments[count][j] == 0)
			{
				break;
			}
			else
			{
				while(v == 0)
				{
					if(assignments[count][j] == -1)
					{
						strcat(assignP, "0");
						write(Fd, assignP, strlen(assignP));
						write(Fd, enter, strlen(enter));
						read(Fd, buf, strlen(buf));

						n = atoi(buf);

						if(n > 0 || n < 100)
						{
							assignments[count][j] = n;
							v = 1;
						}
					}
					else
					{
						int n = assignments[count][j];
						itoa(n, num);
						strcat(assignP, num);
						strcat(assignP, nameP);
                                                write(Fd, assignP, strlen(assignP));
                                                write(Fd, enter, strlen(enter));
                                                read(Fd, buf, strlen(buf));

                                                n = atoi(buf);

                                                if(n > 0 && n < 100)
                                                {
                                                        assignments[count][j] = n;
                                                        v = 1;
                                                }
					}
				}
			}
		}
		v = 0;
		for(int j = 0; j < addEx; j++)
                {
                        char examP[] = "Exam ";
                        char enter[8] = "Enter: ";
                        if(exams[count][j] == 0)
                        {
                                break;
                        }
                        else
                        {
                                while(v == 0)
                                {
                                        if(exams[count][j] == -1)
                                        {
                                                strcat(examP, "0");
						strcat(examP, nameP);
                                                write(Fd, examP, strlen(examP));
                                                write(Fd, enter, strlen(enter));
                                                read(Fd, buf, strlen(buf));

                                                n = atoi(buf);

                                                if(n > 0 || n < 100)
                                                {
                                                        exams[count][j] = n;
                                                        v = 1;
                                                }
					}
                                        else
                                        {
						int n = exams[count][j];
                                               	itoa(n, num);
                                               	strcat(examP, num);
						strcat(examP, nameP);

                                                write(Fd, examP, strlen(examP));
                                                write(Fd, enter, strlen(enter));
                                                read(Fd, buf, strlen(buf));

                                                n = atoi(buf);

                                                if(n > 0 && n < 100)
                                                {
                                                        exams[count][j] = n;
                                                        v = 1;
                                                }
                                        }
                                }
                        }
                }

	}

}

void gradeA(int Fd, char *number, char *students, int assignments[1000][100], int addAs, int countSt)
{

	int realNum = atoi(number);
	//computer initialize with zero
	realNum = realNum - 1;

	char backup[1000];
        char *token;
	char buf[256];
	char nameP[2] = ":";
	char name[256];
	char *num;
	int v = 0;
	if(realNum < 0 || realNum > addAs)
	{
        	char errorP[26] = "Invalid Assignment Number";
		write(Fd, errorP, strlen(errorP));
	}
	else
	{
        	strcpy(backup, students);
        	token = strtok(backup, " ");

		while(token != NULL)
		{
			for(int i = 0; i < countSt; i++)
			{
				strcat(name, token);
				strcat(name, nameP);
				write(Fd, name, strlen(name));
				int n = assignments[i][realNum];
				itoa(n, num);
				char assignP[] = "Assignment ";
                        	char enter[8] = "Enter: ";
				strcat(assignP, num);
				strcat(assignP, nameP);

				if(assignments[i][realNum] == 0)
        	                {
                	                break;
                        	}
	                        else
        	                {
                	                while(v == 0)
                        	        {
                                	        if(assignments[i][realNum] == -1)
                                        	{
                                                	strcat(assignP, "0");
                                                	write(Fd, assignP, strlen(assignP));
                                                	write(Fd, enter, strlen(enter));
                                                	read(Fd, buf, strlen(buf));

	                                                n = atoi(buf);

        	                                        if(n > 0 || n < 100)
                	                                {
                        	                                assignments[i][realNum] = n;
                                	                        v = 1;
                                        	        }
						}
                                        	else
                                        	{
                                                	int n = assignments[i][realNum];
							itoa(n, num);
                                                	strcat(assignP, num);

                                               		write(Fd, assignP, strlen(assignP));
                                               		write(Fd, enter, strlen(enter));
                                                	read(Fd, buf, strlen(buf));

                                                	n = atoi(buf);

                                                	if(n > 0 && n < 100)
                                                	{
                                                        	assignments[i][realNum] = n;
                                                        	v = 1;
                                                	}
                                        	}
                                	}
                        	}

			}


		}


	}

}


void gradeE(int Fd, char *number, char *students, int exams[1000][100], int addEx, int countSt)
{

	int realNum = atoi(number);
        //computer initialize with zero
        realNum = realNum - 1;

        char backup[1000];
        char *token;
        char buf[256];
        char nameP[2] = ":";
        char name[256];
        char *num;
        int v = 0;

        if(realNum < 0 || realNum > addEx)
        {
                char errorP[26] = "Invalid Exam Number";
                write(Fd, errorP, strlen(errorP));
        }
        else
        {
                strcpy(backup, students);
                token = strtok(backup, " ");

                while(token != NULL)
                {
                        for(int i = 0; i < countSt; i++)
                        {
                                strcat(name, token);
                                strcat(name, nameP);
                                write(Fd, name, strlen(name));

                                int n = exams[i][realNum];
				itoa(n, num);
                                char examP[] = "Exam ";
                                char enter[8] = "Enter: ";
                                strcat(examP, num);
                                strcat(examP, nameP);

                                if(exams[i][realNum] == 0)
                                {
                                        break;
                                }
                                else
                                {
                                        while(v == 0)
                                        {
                                                if(exams[i][realNum] == -1)
                                                {
                                                        strcat(examP, "0");
                                                        write(Fd, examP, strlen(examP));
                                                        write(Fd, enter, strlen(enter));
                                                        read(Fd, buf, strlen(buf));

                                                        n = atoi(buf);

                                                        if(n > 0 || n < 100)
                                                        {
                                                                exams[i][realNum] = n;
                                                                v = 1;
                                                        }
						}
                                                else
                                                {
							int n = exams[i][realNum];
                                                        itoa(n, num);
                                                        strcat(examP, num);

                                                        write(Fd, examP, strlen(examP));
                                                        write(Fd, enter, strlen(enter));
                                                        read(Fd, buf, strlen(buf));

                                                        n = atoi(buf);


							if(n > 0 && n < 100)
                                                        {
                                                                exams[i][realNum] = n;
                                                                v = 1;
                                                        }
                                                }
                                        }
                                }

                        }

                }

        }

}
