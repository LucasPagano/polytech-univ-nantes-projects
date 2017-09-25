#pragma once
#include "B.h"
class A {
private:
	double x;
	double y;
public:
	A(); // Constructeur par défaut
	A(const A &a); // Constructeur par recopie
	A(const A &&a); // Constructeur par mouvement
	~A();
	A& operator=(const A &a); // Opérateur d'affectation
	A& operator=(const A &&a); // Opérateur de déplacement

	A(const B &b);
	A& A::operator=(const B &b);

};