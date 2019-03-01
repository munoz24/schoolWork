#include <stdio.h>
#include "itoa.h"


void itoa(int num, char *str)
{
	int i, r, len = 0, n;

	n = num;
	while(n != 0)
	{
		len++;
		n /= 10;
	}

	for(i = 0; i <len; i++)
	{
		r = num %10;
		num = num / 10;
		str[len - (i +1)] = r + '0';
	}
	str[len] = '\0';
}
