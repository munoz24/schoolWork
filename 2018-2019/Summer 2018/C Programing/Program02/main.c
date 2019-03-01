#include <stdio.h>
#include "caesar.h"
#include <stdlib.h>

int main(int argc, char *argv[]){

if(argc != 4){
printf("There must be an input file, output file and offset\n");
return 0;
}

int key = atoi(argv[3]);
if(key < -25 && key > 25){
        printf("Offset is out of bounds");
        return 0;
        }

FILE *input = fopen(argv[1] , "r");
if(!input){
	printf("Can not read input file");
	return 0;
	}


FILE *output = fopen(argv[2] , "w");
if(!output){
        printf("Can not create output file");
        return 0;
        }



char replace;
while (replace != EOF)
{
replace = getc(input);
fprintf(output, "%c", caesar(replace , key));
}

fclose(input);
fclose(output);

return 0;
}
