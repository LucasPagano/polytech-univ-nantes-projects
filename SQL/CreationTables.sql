/*********************************************
CREATION DES TABLES
*********************************************/

DROP TABLE PAYS CASCADE CONSTRAINTS;
CREATE TABLE PAYS (IdPays,Nom,Capitale,Zone,Population) AS SELECT code,name,capital,province,population FROM Country;
ALTER TABLE PAYS ADD CONSTRAINT popnonulle CHECK (population>=0);
ALTER TABLE PAYS ADD CONSTRAINT nomunique UNIQUE (Nom);
ALTER TABLE PAYS ADD CONSTRAINT pk_PaysId PRIMARY KEY (IdPays);

DROP TABLE VILLE CASCADE CONSTRAINTS;
CREATE TABLE VILLE (nomVille,Pays,Population) AS SELECT name,country,population FROM CITY;
ALTER TABLE VILLE ADD CONSTRAINT fk_VILLE FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);
/*Pas de clé primaire ici, impossible*/

/*PAYS pointe vers l'id du pays, on l'utilisera donc par la suite comme clé primaire, et on n'en mettra pas s'il ne suffit pas*/
DROP TABLE REGIMEPOLITIQUE CASCADE CONSTRAINTS;
CREATE TABLE REGIMEPOLITIQUE(Pays,DateIndependence,EtaitDependentDe,EstDependentDe,TypeGouvernement) AS SELECT country,independence,wasdependent,dependent,government FROM POLITICS;
ALTER TABLE REGIMEPOLITIQUE ADD CONSTRAINT pk_regpol PRIMARY KEY (Pays);
ALTER TABLE REGIMEPOLITIQUE ADD CONSTRAINT fk_regpol FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);


/*Car c'est trop long, on ne fait pas beaucoup de contraintes*/
DROP TABLE LANGAGEPARLE CASCADE CONSTRAINTS;
CREATE TABLE LANGAGEPARLE (Pays,Langue,PourcentageParle) AS SELECT country,name,percentage FROM LANGUAGE;
ALTER TABLE Langageparle ADD CONSTRAINT fk_langage FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

DROP TABLE POPULATION2 CASCADE CONSTRAINTS;
CREATE TABLE POPULATION2 (Pays,croissancedemographique,mortaliteinfantile) AS SELECT country, population_growth,infant_mortality FROM POPULATION;
ALTER TABLE POPULATION2 ADD CONSTRAINT fk_pop2 FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

DROP TABLE ENGLOBE CASCADE CONSTRAINTS;
CREATE TABLE ENGLOBE (Pays,Continent,Pourcentage) AS SELECT country,continent,percentage FROM ENCOMPASSES;
ALTER TABLE ENGLOBE ADD CONSTRAINT fk_englobe FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

DROP TABLE ORGANISATION CASCADE CONSTRAINTS;
CREATE TABLE ORGANISATION (Abbreviation,nomOrga,ville,pays,dateCreation) AS SELECT abbreviation,name,city,country,established FROM ORGANIZATION;
ALTER TABLE ORGANISATION ADD CONSTRAINT fk_orga FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

DROP TABLE ESTMEMBRE CASCADE CONSTRAINTS;
CREATE TABLE ESTMEMBRE  (Pays,organisation,type)  AS SELECT country,organization,type FROM ISMEMBER;
ALTER TABLE ESTMEMBRE ADD CONSTRAINT fk_membre FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

DROP TABLE RELIGIONPRATIQUEE CASCADE CONSTRAINTS;
CREATE TABLE RELIGIONPRATIQUEE  (Pays,nom,pourcentage)  AS SELECT country,name,percentage FROM RELIGION;
ALTER TABLE RELIGIONPRATIQUEE ADD CONSTRAINT fk_religion FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

DROP TABLE ECONOMIE CASCADE CONSTRAINTS;
CREATE TABLE ECONOMIE (Pays,PIB,agriculture,services,industrie,inflation)  AS SELECT country,gdp,agriculture,service,industry,inflation FROM ECONOMY;
ALTER TABLE ECONOMIE ADD CONSTRAINT fk_eco FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);

/********************************************
MISE A JOUR DES DONNEES
*********************************************/
/*Q1*/
INSERT INTO PAYS VALUES ('Gro','Groland','Groville','Imaginaire',666);
/*Q2*/
DELETE FROM VILLE WHERE Pays IN ( SELECT IdPays FROM PAYS where nom='United Kingdom');
/*Q4*/
ALTER TABLE PAYS DROP COLUMN Zone CASCADE CONSTRAINTS;

/*********************************************
CONSULTATION DE LA BASE
*********************************************/
/*Q1*/
SELECT DISTINCT TYPEGOUVERNEMENT FROM REGIMEPOLITIQUE;
/*Renvoie 81 lignes:
federal parliamentary democracy under a constitutional monarchy
part of the Danish realm
theocratic republic...*/
/*Q2*/
SELECT COUNT(DISTINCT TYPEGOUVERNEMENT) AS NBREGIMES FROM REGIMEPOLITIQUE WHERE PAYS IN (SELECT PAYS FROM ENGLOBE WHERE CONTINENT = 'Africa');
/*RENVOIE LE NOMBRE DE REGIMES : 22*/
/*Q3*/
SELECT ENGLOBE.CONTINENT, SUM(POPULATION) FROM 
PAYS INNER JOIN ENGLOBE on ENGLOBE.PAYS=PAYS.IDPAYS
GROUP BY ENGLOBE.CONTINENT;
/*Renvoie ceci:
America	955818870
Australia/Oceania	289808388
Europe	832211066
Africa	1050181119
Asia	4415908810
*/

/*Q4*/
SELECT ENGLOBE.CONTINENT, REGIMEPOLITIQUE.TYPEGOUVERNEMENT, LANGAGEPARLE.LANGUE FROM
ENGLOBE INNER JOIN REGIMEPOLITIQUE ON ENGLOBE.PAYS=REGIMEPOLITIQUE.PAYS
INNER JOIN LANGAGEPARLE ON LANGAGEPARLE.PAYS=REGIMEPOLITIQUE.PAYS;
/*Renvoie 286 lignes:
Europe parliamentary democracy	Greek
Europe parliamentary democracy	Albanian
Europe parliamentary republic	Greek......*/



/*Q5*/
/*Utiliser estmembre*/
SELECT NOMORGA FROM ORGANISATION WHERE ORGANISATION.ABBREVIATION IN
(SELECT DISTINCT EM.ORGANISATION FROM 
ENGLOBE E, ESTMEMBRE EM WHERE
E.PAYS = EM.PAYS AND E.CONTINENT = 'Europe');
/*Renvoie 129 lignes:
Comuinidade dos Paises de Lingua Portuguesa
Conference of Interaction and Confidence-Building Measures in Asia
Convention of the Southeast European Law Enforcement Center...*/

/*Q6*/
SELECT NOM FROM
PAYS, REGIMEPOLITIQUE WHERE
DATEINDEPENDENCE < TO_DATE('01/01/2000')
AND DATEINDEPENDENCE >= TO_DATE('01/01/1900') 
AND PAYS.IDPAYS=REGIMEPOLITIQUE.PAYS;
/*Renvoie 147 lignes:
Austria
Afghanistan
Antigua and Barbuda...*/

/*Q7*/
SELECT NOMORGA FROM ORGANISATION WHERE
DATECREATION >= TO_DATE('01/01/1900')
AND DATECREATION < TO_DATE('01/01/1950');
/*Renvoie 27 lignes:
Bank for International Settlements
Commonwealth
Council of Europe....*/

/*Q8*/
SELECT DISTINCT V1.NOMVILLE FROM 
VILLE V1, VILLE V2 WHERE
V1.NOMVILLE = V2.NOMVILLE AND V1.PAYS != V2.PAYS;
/*Renvoie 32 lignes:
Colombo
Georgetown
Kingston....*/

/*Q9*/
SELECT PAYS FROM RELIGIONPRATIQUEE
GROUP BY PAYS
HAVING COUNT(*) = (SELECT COUNT(DISTINCT NOM) FROM RELIGIONPRATIQUEE);
/*Ne renvoie rien : logique, trop spécifique*/

/*Q10*/
SELECT * FROM (SELECT TYPEGOUVERNEMENT,COUNT(*) AS NUMBER1 FROM REGIMEPOLITIQUE
GROUP BY TYPEGOUVERNEMENT
ORDER BY NUMBER1 DESC)
WHERE ROWNUM <= 3;
/*
republic	4422
parliamentary democracy	462
constitutional monarchy	156
*/

/*Q11*/
SELECT NOM FROM PAYS,POPULATION2
          WHERE PAYS.IDPAYS=POPULATION2.PAYS AND POPULATION2.CROISSANCEDEMOGRAPHIQUE = (SELECT MAX(CROISSANCEDEMOGRAPHIQUE) FROM POPULATION2);
/*Renvoie un pays : Lebanon, ce qu'on peut supposer logique à l'exception de croissances démographiques égales au max*/


/*Q12*/

SELECT NOMORGA FROM ORGANISATION,ENGLOBE,ESTMEMBRE
WHERE ENGLOBE.PAYS = ESTMEMBRE.PAYS AND ORGANISATION.ABBREVIATION = ESTMEMBRE.ORGANISATION AND ENGLOBE.CONTINENT = 'Europe'
GROUP BY NOMORGA
HAVING COUNT(NOMORGA) = (SELECT COUNT(DISTINCT PAYS) FROM ENGLOBE WHERE CONTINENT='Europe');
/*Renvoie une liste vide*/

/*Q13*/
SELECT COUNT(*) FROM (SELECT NOMORGA FROM ORGANISATION,ENGLOBE,ESTMEMBRE
WHERE ENGLOBE.PAYS = ESTMEMBRE.PAYS AND ORGANISATION.ABBREVIATION = ESTMEMBRE.ORGANISATION AND ENGLOBE.CONTINENT = 'Europe'
GROUP BY NOMORGA
HAVING COUNT(NOMORGA) = (SELECT COUNT(DISTINCT PAYS) FROM ENGLOBE WHERE CONTINENT='Europe'));

/*Renvoie 0 : logique, se référer à Q°13*/
/*Q14*/
SELECT NOMORGA FROM (SELECT NOMORGA FROM ORGANISATION
              WHERE NOT EXISTS (SELECT * FROM ENGLOBE,ORGANISATION,ESTMEMBRE
                                        WHERE ENGLOBE.PAYS=ESTMEMBRE.PAYS AND ORGANISATION.ABBREVIATION=ESTMEMBRE.ORGANISATION AND ENGLOBE.CONTINENT='America'));
/*Renvoie une liste vide*/

/*Q15*/
SELECT NOM,POPULATION FROM PAYS WHERE POPULATION > '60000000'
ORDER BY POPULATION DESC;
/*Renvoie 22 lignes:
China
India
United States...*/

/*Q16*/
/*On fait avec la population -> plus petit = plus dépeuplé*/
SELECT NOM FROM (SELECT NOM FROM PAYS ORDER BY POPULATION)
          WHERE ROWNUM <=10;
/*Renvoie 10 lignes, des pays les plus petits au plus grand :
Pitcairn
Cocos Islands
Groland...
*/


/*Q17*/
SELECT NOM FROM PAYS,ENGLOBE
          WHERE PAYS.IDPAYS=ENGLOBE.PAYS AND ENGLOBE.CONTINENT='Europe';
/*Renvoie 54 lignes:
Austria
Albania
Andorra...*/

/*Q18*/
SELECT DISTINCT NOM FROM PAYS,ENGLOBE E1,ENGLOBE E2 WHERE E1.PAYS=PAYS.IDPAYS AND E1.PAYS=E2.PAYS AND E1.CONTINENT != E2.CONTINENT;
/*Renvoie 5 lignes:
Kazakhstan
Egypt
Indonesia
Russia
Turkey
*/
  
/*Q19*/
/*On ne peut pas faire : on ne possède pas les informations sur les km²*/

/*Q20*/
/*Table de l'Europe*/

SELECT DISTINCT NOM FROM (SELECT RELIGIONPRATIQUEE.NOM,RELIGIONPRATIQUEE.POURCENTAGE FROM 
ENGLOBE,RELIGIONPRATIQUEE WHERE ENGLOBE.CONTINENT='Europe' AND ENGLOBE.PAYS=RELIGIONPRATIQUEE.PAYS
order by POURCENTAGE DESC)
WHERE ROWNUM<=5;

/*Q21*/
--,ENGLOBE WHERE RELIGIONPRATIQUE.PAYS = ENGLOBE.PAYS AND ENGLOBE.CONTINENT='Europe'
/*Q22*/

/*Q23*/

/*Q24*/

/*Q25*/

/*Q26*/

/*Q27*/

/*Q28*/

/*Q29*/

/*Q30*/
