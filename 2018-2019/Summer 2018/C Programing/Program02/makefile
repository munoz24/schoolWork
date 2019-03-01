program.o: main.o caesar.o
	gcc main.o caesar.o -o program

main.o: main.c
	gcc -c main.c

caesar.o: caesar.c caesar.h
	gcc -c caesar.c

clean:
	rm *.o
