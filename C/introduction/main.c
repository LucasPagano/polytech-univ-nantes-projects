#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <stdbool.h>

int readd()
{
    char c;
    int result;
    bool erreur;
    do{
        erreur = false;
        result = 0;
        do{
        c = getchar();
        if (c >= '0' && c <= '9'){
            result *= 10;
            result += c - '0';
        } else if (c != '\n'){
            erreur = true;
        }

        }while(c != '\n');
        if (erreur) printf("Ne rentrer que des chiffres\n");
    }while(erreur);
    return result;
}


int main()
{
    printf("%i", read());
    return 0;
}


