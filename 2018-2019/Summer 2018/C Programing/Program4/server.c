#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <netdb.h>
#include <netinet/in.h>
#include <string.h>
#include <stdbool.h>
#include <ctype.h>
#include "getCommand.h"


int main( int argc, char *argv[] )
{

  int realPort;
  realPort = atoi(argv[1]);

   if(realPort < 11000 || realPort > 19999)
   {
	printf("Bad Port Number but be between 11000 and 19999");
	return 0;
   }

   int listenSocketFd, connectSocketFd, portNumber;
   unsigned int lengthOfClientAddress;
   const int bufferSize = 256;
   char buffer[bufferSize];
   struct sockaddr_in serverAddress, clientAddress;

   /* create a socket in the open file table */
   listenSocketFd = socket(AF_INET, SOCK_STREAM, 0);

   if (listenSocketFd < 0)
   {
      perror("ERROR opening listener socket");
      exit(1);
   }

   /* Initialize socket structure */
   bzero((char *) &serverAddress, sizeof(serverAddress));
   portNumber = 12117; //you should use 1 + <last 4 digits of your student ID>

   serverAddress.sin_family = AF_INET;
   serverAddress.sin_addr.s_addr = INADDR_ANY;
   serverAddress.sin_port = htons(portNumber);

   /* Bind the socket to the given port number using bind() call.*/
   if (bind(listenSocketFd, (struct sockaddr*) &serverAddress, sizeof(serverAddress)) < 0) 
   {
      perror("ERROR on binding to socket");
      exit(1);
   }

   /*  Start listening for the clients, here the process will
    *  go to sleep mode and will wait for the incoming connection
    */

   listen(listenSocketFd,3);
   lengthOfClientAddress = sizeof(clientAddress);
   int sessionCount = 0;


   while(true)
   {

     /* Accept actual connection from the client */
     connectSocketFd = accept(listenSocketFd, (struct sockaddr *)&clientAddress, &lengthOfClientAddress);
     //if there is no incoming request for the given port number, the process enters the waiting state

     if (connectSocketFd < 0)
     {
        perror("ERROR on accept");
        exit(1);
     }

     /* If connection is established then start communicating */
     if (fork())
     {
        //in parent, close the connectSocket, print a message then call accept again
        close(connectSocketFd);
        printf("just accepted session %d\n", sessionCount);

     }
     else
     {
        //in child, close the listener socket and  start communicating with client
        close(listenSocketFd);
        char initialPrompt[] = "Command > ";
        write(connectSocketFd, initialPrompt, strlen(initialPrompt));
	int addAs = 0;
	int addEx = 0;
	int countSt = 0;
	char students[1000];
         int assignments[1000][100];
         int exams[1000][100];
         char *command;
        char *command1;
        char *command2;


        while (true)  //the child never leaves this loop, and so will never call accept again
        {

           bzero(buffer,bufferSize);


           int n = read(connectSocketFd, buffer, bufferSize-1);
           if (n <= 0)
           {
              perror("ERROR reading from socket (possibly closed by client)");
              exit(1);
           }

           //check to see if the message is only whitespace
           //putty appears to send blank lines sometimes, this check
           //lets the server ignore blank lines
           int flag = true;
           for (int i=0; i<strlen(buffer);i++)
           {
              flag = flag && isspace(buffer[i]);
           }



	   command = (char *)malloc(sizeof(buffer));


	   strcat(command, buffer);

	if(!flag)
	{
	  //getting command
           	getCommand(command, command1, command2);
		callFunction(connectSocketFd, command1, command2, students, assignments, exams, countSt, addAs, addEx);


		n = write(connectSocketFd, initialPrompt, strlen(initialPrompt));

              if (n <= 0)
              {
                 perror("ERROR writing to socket (possibly closed by client)");
                 exit(1);
              }
           }
        }
     }
   }
   return 0;
}
