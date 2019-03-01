#include "caesar.h"
#include <stdio.h>

char caesar(char s, int n){
    
    int swap;
    
    if( s >= 'a' && s <= 'z')
    {
        swap = s - 'a';
        swap = (swap + n + 26)%26;
        s = 'a' + swap;
    }
    else if( s >= 'A' && s <= 'Z')
    {
        swap = s - 'A';
        swap = (swap + n + 26)%26;
        s = 'A' + swap;
    }
    
    return s;
}

