#pragma once

#include "C_Stack.h"
#include <exception>
class C_Pile : public C_Stack
{
public:
	C_Pile(int totalSize=20);
	~C_Pile();

};

