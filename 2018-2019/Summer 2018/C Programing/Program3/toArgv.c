#include <string.h>

int toArgv(char *source, char *dest[], char *readDest[]){

	int i = 0;
        char s[1] = "/";
        char *token;
        token = strtok(source, s);
        while(token != NULL){
                dest[i] = token;
                ++i;
                token = strtok(NULL, s);
        }
        readDest[0] = dest[i-1];

        return i;
}
