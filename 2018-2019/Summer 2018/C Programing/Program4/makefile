grades: server.o getCommand.o qDisplay.o grade.o add.o itoa.o
	gcc server.o getCommand.o qDisplay.o grade.o add.o itoa.o -o grades

server.o: server.c
	gcc -c server.c

getCommand.o: getCommand.c getCommand.h
	gcc -c getCommand.c

qDisplay.o: qDisplay.c qDisplay.h
	gcc -c qDisplay.c

grade.o: grade.c grade.o
	gcc -c grade.c

add.o: add.c add.h
	gcc -c add.c

itoa.o: itoa.c itoa.h
	gcc -c itoa.c

clean:
	rm *.o
