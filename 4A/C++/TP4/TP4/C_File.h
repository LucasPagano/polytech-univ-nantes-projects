#pragma once

#include "C_Stack.h"
class C_File : public C_Stack
{
public:
	C_File(int totalSize);
	~C_File();
private:
	int topIndex;
};

