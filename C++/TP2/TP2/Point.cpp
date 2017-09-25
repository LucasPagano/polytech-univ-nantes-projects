#include "stdafx.h"

Point::Point() : x(0), y(0), z(0) {
	std::cout << "Constructeur par defaut" << std::endl;

	label = _strdup("");
}

Point::Point(double x, double y, double z, const char *label) {
	std::cout << "Constructeur parametre" << std::endl;

	this->x = x;
	this->y = y;
	this->z = z;
	this->label = _strdup(label);
}

Point::Point(const Point &p) {
	std::cout << "Constructeur par recopie" << std::endl;

	this->x = p.x;
	this->y = p.y;
	this->z = p.z;
	this->label = _strdup(p.label);
}

Point::~Point() {
	std::cout << "destructeur" << std::endl;

	free(this->label);
}

Point& Point::operator=(const Point &p) {
	this->x = p.x;
	this->y = p.y;
	this->z = p.z;
	this->label = _strdup(p.label);
	return *this;

}