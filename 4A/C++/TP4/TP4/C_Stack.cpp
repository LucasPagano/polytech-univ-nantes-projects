#include "C_Stack.h"


C_Stack::C_Stack(int totalSize) {
	this->totalSize = totalSize;
	this->currentIndex = 0;
	this->tab = new int[totalSize];
}

C_Stack::C_Stack(const C_Stack &stack) {
	totalSize = stack.totalSize;
	currentIndex = stack.currentIndex;
	tab = (int*) malloc(this->totalSize);

	memcpy(this->tab, stack.tab, currentIndex * sizeof(int));
}

C_Stack::C_Stack(C_Stack &&stack)
{
	this->currentIndex = stack.currentIndex;
	this->totalSize = stack.totalSize;
	this->tab = stack.tab;
	stack.tab = nullptr;
}

C_Stack::~C_Stack()
{
	delete[] tab;
}

C_Stack& C_Stack::operator=(const C_Stack &stack)
{
	this->totalSize = stack.totalSize;
	this->currentIndex = stack.currentIndex;
	delete[] this->tab;

	this->tab = new int[this->totalSize];
	memcpy(this->tab, stack.tab, currentIndex * sizeof(int));

	return (*this);
}

C_Stack & C_Stack::operator=(C_Stack &&stack)
{
	this->totalSize = stack.totalSize;
	this->currentIndex = stack.currentIndex;
	delete[] this->tab;
	this->tab = stack.tab;
	stack.tab = nullptr;

	return (*this);
}
