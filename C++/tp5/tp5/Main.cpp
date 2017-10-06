#include <iostream>
#include "Chaine.h"
#include "Template.h"
#include "Matrix.h"

int main() {
	int i = 5; int j = 6;
	std::cout << "i=" << i << " ; j=" << j << std::endl;
	swapT<int>(i, j);
	std::cout << "i=" << i << " ; j=" << j << std::endl;

	double f = 5.55, g = 6.66;
	std::cout << "f=" << f << " ; g=" << g << std::endl;
	swapT<double>(f, g);
	std::cout << "f=" << f << " ; g=" << g << std::endl;

	Chaine s = "chaine 1", t = "chaine 2";
	std::cout << "s=" << s << " ; t=" << t << std::endl;
	swapT(s, t);
	std::cout << "s=" << s << " ; t=" << t << std::endl;

	//// PARTIE 2

	CMatrix m;
	std::cout << m;



	return 0;
}