#pragma once
class Point {
private:
	double x, y, z;
	char *label;
public:
	Point();
	Point(double x, double y, double z, const char *label);
	Point(const Point&);
	Point& operator=(const Point &p);
	~Point();
};