#include <string.h>
#include <sys/types.h>
#include <dirent.h>
#include <sys/stat.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdio.h>
//isFile
#include <dirent.h>
#include <errno.h>
#include "toArgv.h"

int isFile(char *path){

        DIR *directory = opendir(path);

        if(directory != NULL){
                closedir(directory);
                return 0;
        }

        if(errno == ENOTDIR){
                return 1;
        }

        return -1;

}



int dirCheck(char *source, char *dest[], char *readDest[]){

 	int i = 0;
        char s[1] = "/";
        char *token;
        char tempSource[1024];
        token = strtok(source, s);
        DIR* pwd = opendir(source);
        if(pwd == NULL){
                int new_dir = mkdir(token, 0777);
        }

        while(token != NULL){

                strcpy(tempSource, token);
                strcat(tempSource, "/");
                strcat(tempSource, token);

                pwd = opendir(tempSource);
                if(pwd == NULL){
                        int new_dir = mkdir(tempSource, 0777);
                }


                dest[i] = token;
                ++i;
                token = strtok(NULL, s);
        }

        readDest[0] = dest[i-1];

        return i;
}



void list_dir(char *path, char *target){


	int targetFile = open(target, O_CREAT | O_WRONLY | O_RDONLY, 0777);


        if(targetFile == -1){
                printf("Failed to create and open file: %s\n", target);
        }
        char buf[64];

        DIR* pwd = opendir(path);
        if(pwd == NULL){
                printf("Not Found: %s\n", path);
         }

         struct dirent *p = readdir(pwd);
         int n;

        while(p != NULL){
                char *tempArr[1024];
                char *realStr[100];

                 int size = 1024;
                 char pathname[size];
                 char pathname2[size];

                 strcpy(pathname, path);
                 strcat(pathname, "/");
                 strcat(pathname, p->d_name);


                 int correctSize = strlen(path) + strlen(p->d_name) +2;

                 strcpy(pathname2, pathname);

                 struct stat info;
		 stat(pathname, &info);

                  n = isFile(pathname);

                  if(n == 1){

                  int retval = toArgv(pathname2, tempArr, realStr);
                  //realStr[0] holds file name

		  printf("archiving %s\n", realStr[0]);
                  unsigned int targetSize = correctSize;
                  unsigned int tDataSize = info.st_size;

                  write(targetFile, &targetSize, 4);
                  write(targetFile, &tDataSize, 4);
                  write(targetFile, pathname2, targetSize);



                  int readFile = open(pathname2, O_RDONLY);
                  if(readFile == -1){
                        printf("Failed to open and read the file: %s\n", realStr[0]);
                  }

                  int bytes_read = read(readFile, buf, 64);

                  while(bytes_read > 0){
                          write(targetFile, buf, bytes_read);
                          bytes_read = read(readFile, buf, 64);
                  }

                close(readFile);
		}
		p = readdir(pwd);
	}
}
