/* This file contains all the addS functions
   Any file interactions will be handled at quit to create the gradebook
*/
#include "add.h"
#include <string.h>
#include <unistd.h>


void addS(int Fd, char *name, char *students, int countSt)
{
	if(name != NULL)
	{
		// use strtok to go through students and strcmp to check if name already exist
		// if token==0 print error


		// adds space plus student
		// space is used in strtok
		strcat(students, " ");
		strcat(students, name);
		++countSt;
	}

	char error[16] = "No name entered";
	if(name == NULL)
	{
		write(Fd, error, strlen(error));
	}

}

void addA(int Fd, int assignments[1000][100], int countSt, int addAs)
{

	if(countSt <= 0)
        {
                char error[29] = "Please add one student first";
                write(Fd, error, strlen(error));

        }

	else
	{
		// Since entire 2d array is initialized with zero, mark all zeros with -1
		for(int q = 0; q < countSt; q++)
		{
			for(int w = 0; w < addAs; w++)
			{
				if(assignments[q][w] == 0)
				{
					assignments[q][w] = -1;
				}

			}
		}
	}
}


void addE(int Fd, int exams[1000][100], int countSt, int addEx)
{

	if(countSt <= 0)
	{
		char error[29] = "Please add one student first";
		write(Fd, error, strlen(error));

	}

	else
	{
		// Since entire 2d array is initialized with zero, mark all zeros with -1
        	for(int q = 0; q < countSt; q++)
        	{
                	for(int w = 0; w < addEx; w++)
                	{
                        	if(exams[q][w] == 0)
                        	{
                                	exams[q][w] = -1;
                        	}
                	}
        	}
	}
}
