arch: main.o toArgv.o dirFun.o
	gcc main.o toArgv.o dirFun.o -o arch

main.o: main.c
	gcc -c main.c

toArgv.o: toArgv.c toArgv.h
	gcc -c toArgv.c

dirFun.o: dirFun.c dirFun.h
	gcc -c dirFun.c

clean:
	rm *.o
