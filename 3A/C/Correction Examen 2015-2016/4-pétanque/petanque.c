#include <stdio.h>
#include <stdarg.h>
#include <math.h>

void petanque(double cochonet,...)
{
	va_list vl;
	va_start(vl,cochonet);
	double (*pFn)(double);

	double val;
	int i=0;
	int ival=0;
	while ((pFn=va_arg(vl,double (*)(double)))!=NULL)
	{
		++i;
		double tmp=pFn(cochonet);
		if ((ival==0) || (fabs(tmp-cochonet)<fabs(val-cochonet)))
		{
			ival=i;
			val=tmp;
		}
		printf("%f ",tmp);
	}
	printf("\nLe plus près est le pointeur n°%d (car %f est le nombre le plus proche de %f)\n",ival,val,cochonet);

	va_end(vl);
}

int main(int argc,char *argv[])
{
	petanque(2.0,cos,sin,sqrt,NULL);
	return(0);
}
