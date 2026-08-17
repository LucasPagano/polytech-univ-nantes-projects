// TP2.cpp : Définit le point d'entrée pour l'application console.
//

#include "stdafx.h"
#include "Point.h"


Point CreerPoint(double x, double y, double z, const char *label)
{
	std::cout << "debut de creerPoint ?" << std::endl;
	Point p(x, y, z, label);
	//Point &t = p;
	return(p);
}

Point CreerPoint(Point p)
{
	Point p1(p);
	return(p1);
}

int main()
{
	Point p1;
	Point p2(1.2, 0, 0.3, "p2");
	Point p3 = Point(2, 3.1, 2, "p3"); 
	 

	p1 = CreerPoint(2.4, 8, 3.5, "p4");
	p1 = CreerPoint(p2);
	std::cout << "fin du main" << std::endl;
	return 0;
}

