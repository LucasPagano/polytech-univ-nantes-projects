enum Etats
{
    SEtatDebut,
    SEtat1,
    SEtat2,
    SEtat3,
    SEtatErreur,
    SEtatReussite
};
int parseur();
char recup(FILE* pFile, char* str);
char recup_entame(FILE* pFile, char* str, char c);
void afficherStr(char* str);
