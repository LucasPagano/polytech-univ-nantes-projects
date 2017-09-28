#pragma once
#include <iostream>
#include <cmath>

const double pi = 3.1415;
const double epsilon = 0.0001;


class Complexe
{
private:
	double re, im;
public:
	Complexe();
	Complexe::Complexe(double reel, double imaginaire);
	~Complexe();
	double getRe() const;
	double getIm() const;

	void homotethie(int mult);
	double getMod();
	double getArg();

	Complexe& operator=(const Complexe&);
};

bool operator==(const Complexe &c1, const Complexe &c2);
