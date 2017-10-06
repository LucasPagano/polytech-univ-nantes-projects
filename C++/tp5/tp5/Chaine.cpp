#include "Chaine.h"
#include <string.h>
#include <malloc.h>

//#define CHAINE_USE_NEW

Chaine::Chaine() :
	String(nullptr),
	Size(0)
{
	AddString("");
}

Chaine::Chaine(const Chaine &chaine) :
String(nullptr),
Size(0)
{
	AddString(chaine.String);
}

Chaine::Chaine(Chaine &&chaine) :
String(chaine.String),
Size(chaine.Size)
{
	chaine.String=nullptr;
	chaine.Size=0;
}

Chaine::Chaine(char *str) :
	String(nullptr),
	Size(0)
{
	AddString(str);
}

Chaine::~Chaine()
{
#ifdef CHAINE_USE_NEW
	delete[] String;
#else
	free(String);
#endif
}

void Chaine::AddString(const char *str)
{
	unsigned int sizeStr;

	sizeStr=(unsigned int)strlen(str);

#ifdef CHAINE_USE_NEW
	char *strTemp;

	strTemp=new char[Size+sizeStr+1];
	if (String) strcpy(strTemp,String);
	delete[] String;
	String=strTemp;
#else
	String=(char*)realloc(String,(Size+sizeStr+1)*sizeof(char));
#endif

	strcpy(&String[Size],str);
	Size+=sizeStr;
}

Chaine& Chaine::operator=(const Chaine &c) {
	Size = c.GetSize();
	String = strcpy(String, c.GetString());
	return *this;
}

Chaine & Chaine::operator=(Chaine &&c)
{
	Size = c.GetSize();
#ifdef CHAINE_USE_NEW
	delete[] String;
#else
	free(String);
#endif

	String = c.String;
	c.String = nullptr;

	return *this;
}


void Chaine::ReplaceString(const char *str)
{
	Size=(unsigned int)strlen(str);

#ifdef CHAINE_USE_NEW
	char *strTemp;

	strTemp=new char[Size+1];
	delete[] String;
	String=strTemp;
#else
	String=(char*)realloc(String,(Size+1)*sizeof(char));
#endif

	strcpy(String,str);
}

std::ostream& operator<<(std::ostream& o, Chaine &c) {
	std::cout << c.GetString();
	return o;
}
