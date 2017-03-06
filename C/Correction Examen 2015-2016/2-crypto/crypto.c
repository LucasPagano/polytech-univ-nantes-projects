#include <stdio.h>

void pBinary(unsigned char x)
{
    for (int i=0;i<8;i++)
    {
        printf("%c",((x>>(7-i))&1)==1 ? '1' : '0');
    }
}

void split(unsigned char X,unsigned char *left,unsigned char *right,unsigned char N)
{
	*left=X>>(8-N);
	*right=(X<<N)&0xff;
}

void crypterF(char *src,char*dest,unsigned char N)
{
	FILE *fpI,*fpO;

	if ((fpI=fopen(src,"r"))!=NULL)
	{
		if ((fpO=fopen(dest,"w"))!=NULL)
		{
			unsigned char c,mem,prev;
			char end=0,first=1;
			unsigned char l,r;

			do
			{
				c=fgetc(fpI);
				if (!(end=feof(fpI)))
				{
					split(c,&l,&r,N);
					if (first)
					{
						first=0;
						mem=l;
					}
					else fputc(l+prev,fpO);
					prev=r;
				}
				else
				{
					if (!first) fputc(mem+prev,fpO);
				}
			} while(!end);
			fclose(fpO);
		}
		fclose(fpI);
	}
}

void crypterS(char *src,char*dest,unsigned char N)
{
	int i;
	unsigned char mem;

	for (i=0;src[i];++i)
	{
		unsigned char l,r;

		split(src[i],&l,&r,N);
		if (i==0) mem=l;
		dest[i]=r;
		if (i) dest[i-1]+=l;
	}
	if (i) dest[i-1]+=mem;
	dest[i]='\0';
}

int main()
{
	crypterF("crypto.c","crypto.crypt",3);

	char src[]={0b0110010,0b11011110,0b00010010,0b01110101,'\0'};
	char dest[5];

	crypterS(src,dest,3);
	for (int i=0;src[i];++i)
	{
		pBinary(src[i]);
		printf(" ");
	}
	printf("\n");
	for (int i=0;dest[i];++i)
	{
		pBinary(dest[i]);
		printf(" ");
	}
	printf("\n");

	return(0);
}
