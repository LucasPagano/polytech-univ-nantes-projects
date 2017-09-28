#include "C_Pile.h"

C_Pile::C_Pile(int totalSize) :
	C_Stack(totalSize)
{
}

C_Pile::~C_Pile()
{
}

C_Stack & C_Stack::operator<(int val)
{
	if (currentIndex == totalSize) {
		throw(std::exception("Pile pleine"));
	}
	tab[currentIndex++] = val;
}

C_Stack & C_Stack::operator>(int &val)
{
	if (currentIndex == 0) {
		throw(std::exception("Pile vide"));
	}
	val = tab[--currentIndex];
	return (*this);
}


