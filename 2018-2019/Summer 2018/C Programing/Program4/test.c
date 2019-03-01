#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define NO_OF_STUDENTS 100
#define NO_GRADES 100


int main(){

	printf("first\n");
	char addS[5] = "addS";
	char addA[5] = "addA";
	char grade[6] = "grade";
	char full[100] = "addS Andres";
	char full2[100] = "Ulises";
	char *one;
	char *two;
	char student[NO_OF_STUDENTS];
	int grades[NO_OF_STUDENTS][NO_GRADES];
	int i, j;

        char *token;
        token = strtok(full, " ");
	one = (char *) malloc(sizeof(token));

        strcat(one, token);

	printf("one = %s\n", one);

        token = strtok(NULL, " ");

	two = (char *)malloc(sizeof(token));

        strcat(two, token);
	printf("two = %s\n", two);

	int n = strcmp(one, addS);
	if(n == 0)
	{
		printf("strcmp addS\n");
		strcpy(student, " ");
		strcat(student, two);
	}
	n = strcmp(one, grade);
	if(n == 0)
        {
                printf("strcmp grade\n");
        }


	strcat(student, " ");
	strcat(student, full2);
	printf("STUDENTS = %s\n",student);
	char backupS[NO_OF_STUDENTS];
	strcpy(backupS, student);

	token = strtok(student, " ");
	int countS = 0;
	while(token != NULL)
	{
		countS++;
		printf("students %s\n", token);
		token = strtok(NULL, " ");
	}



		int countSS = 0;
			for(i = 0; i< countS; i++)
                                {
				countSS++;
                                for (j = 0; j <NO_GRADES; j++)
                                {
					grades[i][j] = -1;
				}
                        }

	grades[0][0] = 20;
	grades[0][1] = 30;
	grades[1][0] = 40;
	grades[1][1] = 50;

			char *backupT;
			backupT = strtok(backupS, " ");

			while(backupT != NULL)
			{
				for(i = 0; i< countSS; i++)
				{

				printf("student: %s\n", backupT);

                        	for (j = 0; j <NO_GRADES; j++)
				{

					if(grades[i][j] == -1)
					{
                                                break;
                                        }
                                        else
                                        {
                                                printf("grades[%d][%d] = %d\n", i, j, grades[i][j]);
                                        }
                        	}
				backupT = strtok(NULL, " ");
        	        	}

				backupT = NULL;
			}



	printf("end\n");

	return 0;
}

