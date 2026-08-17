#ifndef _Chaine_h
	#define _Chaine_h
#include <iostream>

class Chaine
{
public:
	Chaine();
	Chaine(const Chaine &chaine);
	Chaine(Chaine &&chaine);
	Chaine(char *str);
	~Chaine();
	Chaine& operator=(const Chaine&);
	Chaine& operator=(Chaine&&);

	inline const char* GetString() const { return(String); }
	inline unsigned int GetSize() const { return(Size); }
	void AddString(const char *str);
	void ReplaceString(const char *str);

private:
	char *String;
	unsigned int Size;
};

std::ostream& operator<<(std::ostream& o, Chaine &c);
#endif
