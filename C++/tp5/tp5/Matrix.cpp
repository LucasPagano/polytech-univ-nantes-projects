#include "Matrix.h"
#include <stdexcept>
#include <iostream>

CMatrix::CMatrix()
{
	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < 3; j++) {
			array[i][j] = 0;
		}
	}
}


CMatrix::~CMatrix()
{
}

int& CMatrix::operator()(int i, int j)
{
	if (i < 3 && j < 3) {
		return(array[i][j]);
	}
	else {
		throw std::out_of_range("Out of range");
	}
}

int CMatrix::operator()(int i, int j) const {
	if (i < 3 && j < 3) {
		return(array[i][j]);
	}
	else {
		throw std::out_of_range("Out of range");
	}
}

std::ostream & operator<<(std::ostream &o, const CMatrix &m)
{
	
	for (int i = 0; i < 3; i++) {
		std::cout << "[";
		for (int j = 0; j < 3; j++) {
			std::cout << m(i,j) << ", ";
		}
		std::cout << "]" << std::endl;
	}

	return o;
}
