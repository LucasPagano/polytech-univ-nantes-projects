#include "Complexe.h"

Complexe::Complexe() : 
	re(0.0), 
	im(0.0) 
{
}

Complexe::Complexe(double reel, double imaginaire) : 
	re(reel), 
	im(imaginaire) 
{
}


Complexe::~Complexe() {
}

double Complexe::getRe() const {
	return this->re;
}

double Complexe::getIm() const {
	return this->im;
}

void Complexe::homotethie(int mult) {
	if (mult < 0) {
		std::cout << "La valeur doit être positive !" << std::endl;
	}
	else {
		re *= mult;
		im *= mult;
	}
}

double Complexe::getMod() {
	return sqrt(re*re + im*im);
}

double Complexe::getArg() {
	if (abs(re) < epsilon){ // Le réel est égal à 0
		if (abs(im) < epsilon) {
			std::cout << "L'argument n'est pas défini" << std::endl;
			return(0);
		}
		else if (im > 0) {
			return pi / 2;
		}
		else if (im < 0) {
			return -pi / 2;
		}
	}
	else if (abs(im) < epsilon) {
		if (re > 0) {
			return 0;
		}
		else if (re < 0) {
			return pi;
		}
	}
	else {
		return 2 * atan(im / (re + this->getMod()));
	}
}

Complexe& Complexe::operator=(const Complexe &c) {
	re = c.re;
	im = c.im;
	return (*this);
}


bool operator==(const Complexe &c1, const Complexe &c2) {
	return ((abs(c1.getRe() - c2.getRe()) < epsilon) && (abs(c1.getIm() - c2.getIm()) < epsilon));
}