#pragma once
#include <iostream>
class CMatrix
{


public:
	CMatrix();
	virtual ~CMatrix();
	int& operator()(int i, int j);
	int operator()(int i, int j) const;
	
	friend std::ostream& operator<<(std::ostream&, const CMatrix&);

private:
	int array[3][3];

};

std::ostream & operator<<(std::ostream&, const CMatrix&);


