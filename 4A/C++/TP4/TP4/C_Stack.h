#pragma once
#include <malloc.h>
#include <string.h>

class C_Stack
{
protected:
	int totalSize;
	int currentIndex;
	int *tab;
public:
	C_Stack(int totalSize=20);
	C_Stack(const C_Stack &stack);
	C_Stack(C_Stack&&);
	C_Stack& operator=(const C_Stack&);
	C_Stack& operator=(C_Stack&&);
	virtual ~C_Stack();
	inline int getSize() { return(currentIndex); }

	virtual C_Stack& operator<(int);
	virtual C_Stack& operator>(int&);
};

