#include "Complexe.h"

int main() {
	Complexe c;
	std::cout << c.getRe() << std::endl;

	Complexe d(2.2, 3);
	std::cout << "AVANT MULTIPLICATION" << std::endl;
	std::cout << "La partie reelle est :" << d.getRe() << std::endl;
	std::cout << "La partie imaginaire est :" << d.getIm() << std::endl;

	int mult = 5;
	d.homotethie(mult);

	std::cout << "APRES MULTIPLICATION PAR " << mult << std::endl;
	std::cout << "La partie reelle est :" << d.getRe() << std::endl;
	std::cout << "La partie imaginaire est :" << d.getIm() << std::endl;

	Complexe g(4, 3);
	const Complexe &e = g;
	std::cout << std::endl << "Partie reelle : " << e.getRe() << std::endl;
	std::cout << "Partie imaginaire : " << e.getIm() << std::endl;
	std::cout << "Module : " << g.getMod() << std::endl;
	std::cout << "Argument : " << g.getArg() << std::endl;

	Complexe f(5, 3);
	Complexe egal(5, 3);
	if (f == g) {
		std::cout << "Ils sont egaux" << std::endl;
	}
}