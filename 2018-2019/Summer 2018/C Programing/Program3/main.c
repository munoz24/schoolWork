#include <stdlib.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <dirent.h>
#include <sys/wait.h>
#include <errno.h>

#include "dirFun.h"
#include "toArgv.h"

int main(int argc, char **argv)
{

	char *action = argv[1];


	if (!(!strcmp(action, "-e") || !strcmp(action, "-c"))){
		printf("Incorrect Action command. Please use '-c' to create or '-e' to expand\n");
	}

	if (!strcmp(action, "-c")){
		char *source = argv[2];
		char *target = argv[3];

		int targetFile = open(target, O_CREAT | O_WRONLY, 0777);
		if(targetFile == -1){
                        printf("Failed to create and open file: %s\n", target);
                        return 1;
                }

		DIR* pwd = opendir(source);
		if(pwd == NULL){
			printf("Not Found: %s\n", source);
			return 1;
		}

		struct dirent *p = readdir(pwd);
		int n;

		while(p != NULL){
			char *tempArr[1024];
			char *realStr[100];

			int size = 1024;
			char pathname[size];
			char pathname2[size];

			strcpy(pathname, source);
			strcat(pathname, "/");
			strcat(pathname, p->d_name);


			int correctSize = strlen(source) + strlen(p->d_name) +2;

			strcpy(pathname2, pathname);

			struct stat info;
			stat(pathname, &info);

			n = isFile(pathname);

			if(n == 1){
				int retval = toArgv(pathname, tempArr, realStr);
				//realStr[0] holds file name
				printf("archiving %s\n", realStr[0]);
				unsigned int targetSize = correctSize;
				unsigned int tDataSize = info.st_size;

				write(targetFile, &targetSize, 4);
				write(targetFile, &tDataSize, 4);
				write(targetFile, pathname2, targetSize);

				char buf[tDataSize];

				int readFile = open(pathname2, O_RDONLY);
				if(readFile == -1){
        		                printf("Failed to open and read the file: %s\n", realStr[0]);
                       			 return 1;
                		}

				int bytes_read = read(readFile, buf, tDataSize);

				while(bytes_read > 0){
					write(targetFile, buf, bytes_read);
					bytes_read = read(readFile, buf, tDataSize);
				}
			close(readFile);
                        }

			if (n == 0 && strcmp(p->d_name, ".") != 0 && strcmp(p -> d_name, "..") != 0){
         			list_dir(pathname, target);
   			 }
			p = readdir(pwd);
		}
		close(targetFile);
		printf("Done - %s has been created\n", target);
	}

	if (!strcmp(action, "-e")){

		char *source = argv[2];
        	char *target = argv[3];
		char *tempArr[1024];
		char *readStr[100];

		int new_dir = mkdir(source, 0777);
                if(new_dir == -1){
                        printf("Directory could not be made: %s\n", source);
			return 1;
                }

		DIR* pwd = opendir(source);
		if(pwd == NULL){
                        printf("Not Found: %s\n", source);
                        return 1;
                }
		printf("expanding to  %s\n", source);


		int archiveFile = open(target, O_RDONLY);
		if(archiveFile == -1){
			printf("Failed to open and read the file: %s\n", target);
			return 1;
		}



		unsigned int targetSize;
                unsigned int tDataSize;
		int check = 1;
		while(check > 0){

        		if(archiveFile != -1){
				printf("targetSize 1 = %u\n", targetSize);

                		int check = read(archiveFile, &targetSize, 4);

				printf("targetSize 2 = %u\n", targetSize);


				if(check <= 0){
					check = 0;
				}

                		read(archiveFile, &tDataSize, 4);
// right here
				printf("targetSize 3 = %u\n", targetSize);


                		char fullTarget[targetSize];

                		read(archiveFile, &fullTarget, targetSize);


                		char data[tDataSize];

                		int bytes = read(archiveFile, data, tDataSize);

				printf("loop\n");
				printf("%s\n", fullTarget);
				int retval = dirCheck(fullTarget, tempArr, readStr); // Segmentation Fault
				printf("after dirCheck\n");
				int new_file = open(readStr[0], O_CREAT | O_WRONLY | O_RDONLY );
				if(new_file == -1){
                	        	printf("Failed to create and open the file: %s\n",new_file);
	                        	return 1;
        	        	}
				write(new_file, data, tDataSize);
				close(new_file);
			}
        	}
		close(archiveFile);

	printf("Done - %s has been expanded\n", target);
        }



	return 0;

}


