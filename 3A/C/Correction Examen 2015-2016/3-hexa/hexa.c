#include <stdio.h>
#include <malloc.h>

void print_hexa_octet(unsigned char o)
{
	printf("%02X",o);
}

void print_line(unsigned char *str,int cpt)
{
	for (int i=0;i<cpt;i++)
	{
		if (str[i]>=32) putchar(str[i]);
		else putchar('.');
	}
}

void print_hexa_fichier(FILE *fp,int N)
{
	unsigned char c;
	int cpt=0;
	unsigned char *str=(unsigned char*)malloc((N)*sizeof(unsigned char));
	int end=0;

	while (!end)
	{
		c=fgetc(fp);
		if (feof(fp))
		{
			end=1;
			break;
		}

		print_hexa_octet(c);
		str[cpt]=c;
		printf(" ");
		if ((++cpt)==N)
		{
			print_line(str,cpt);
			cpt=0;
			printf("\n");
		}
	}
	if (cpt)
	{
		for (int i=cpt;i<N;i++) printf("   ");
		print_line(str,cpt);
	}
	free(str);
}

int main(int argc,char *argv[])
{
	if (argc==1) print_hexa_fichier(stdin,8);
	else
	{
		FILE *fp;
		if ((fp=fopen(argv[1],"rb"))!=NULL)
		{
			print_hexa_fichier(fp,8);
			fclose(fp);
		}
	}

	return(0);
}
