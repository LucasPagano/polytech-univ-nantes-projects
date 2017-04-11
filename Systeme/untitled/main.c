#include <stdio.h>
#include <time.h>
#include <stdlib.h>

// Retourne un unsigned int entre min et max, min<=max
unsigned int randomBounds(unsigned int min, unsigned int max){
	return min + rand()%(max-min+1);
}

int main() {
	srand(time(NULL));
	int dicesFrom = 7;
	int dicesTo = 2;

	int fromWin = 0;
	int toWin = 0;
	int total = 10000000;
	for (int i=0; i<total; i++) {
		int thisDiceValueFrom = 0;
		int dicesValueFrom = 0;

		int thisDiceValueTo = 0;
		int dicesValueTo = 0;

		for (int i = 0; i < dicesFrom; i++) {
			thisDiceValueFrom = randomBounds(1, 6); // AJOUT
//      drawScore(0, thisDiceValueFrom, i); // AJOUT
			dicesValueFrom += thisDiceValueFrom; // AJOUT
		}


		for (int i = 0; i < dicesTo; i++) {
			thisDiceValueTo = randomBounds(1, 6); // AJOUT
//      drawScore(1, thisDiceValueTo, i); // AJOUT
			dicesValueTo += thisDiceValueTo; // AJOUT

		}
		if (dicesValueFrom>dicesValueTo){
			fromWin++;
		}else{
			toWin++;
		}
	}

	printf("From win : %i, Percentage : %f\n", fromWin, (float)fromWin/total);



	return 0;
}