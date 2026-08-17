/*********************************************
CREATION DES TABLES

Remarques générales : On a créé les tables à partir des tables disponibles sur https://www.dbis.informatik.uni-goettingen.de/Mondial/#SQL
On a gardé pour le TP entier ces tables.

Remarque section : Car cela était trop long, nous n'avons ajouté que peu de contraintes sur nos tables.
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
CREATE TABLE REGIMEPOLITIQUE(Pays,DateIndependence,EtaitDependentDe,EstDependentDe,TypeGouvernement) AS SELECT 
country,independence,wasdependent,dependent,government FROM POLITICS;
ALTER TABLE REGIMEPOLITIQUE ADD CONSTRAINT pk_regpol PRIMARY KEY (Pays);
ALTER TABLE REGIMEPOLITIQUE ADD CONSTRAINT fk_regpol FOREIGN KEY (Pays) REFERENCES PAYS(IdPays);



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

Remarques section : 

  Pour éviter des répétitions inutiles, nous n'avons fait en double seulement
les requêtes pour lesquelles nous avons jugé cela utile.
  
  Dans une question, un seul retour spécifié pour deux requêtes signifie qu'elles renvoient la même chose.
  
  Toutes les jointures en INNER JOIN peuvent être réalisées en double à la manière présentée Q°3, nous nous
en passerons donc pour la suite.
  
  Toutes les divisions peuvent être réalisées en double à la manière présentée Q°9, de même, on 
s'en passera pour la suite.
*********************************************/

/*Q1*/
SELECT DISTINCT TYPEGOUVERNEMENT FROM REGIMEPOLITIQUE;
/*Renvoie 81 lignes:
federal parliamentary democracy under a constitutional monarchy
part of the Danish realm
theocratic republic...*/

/*Q2*/
SELECT COUNT(DISTINCT TYPEGOUVERNEMENT) AS NBREGIMES FROM REGIMEPOLITIQUE WHERE PAYS IN (SELECT PAYS FROM ENGLOBE WHERE CONTINENT = 'Africa');
/*Renvoie le nombre de régimes : 22*/

/*Q3*/
/*Façon 1*/
SELECT ENGLOBE.CONTINENT, SUM(POPULATION) AS POPULATION FROM 
PAYS INNER JOIN ENGLOBE on ENGLOBE.PAYS=PAYS.IDPAYS
GROUP BY ENGLOBE.CONTINENT;


/*Façon 2:*/
SELECT ENGLOBE.CONTINENT, SUM(POPULATION) AS POPULATION FROM PAYS,ENGLOBE
WHERE ENGLOBE.PAYS=PAYS.IDPAYS
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
/*Façon 1 : présente des défauts mais fonctionne ici*/
SELECT PAYS FROM RELIGIONPRATIQUEE
GROUP BY PAYS
HAVING COUNT(*) = (SELECT COUNT(DISTINCT NOM) FROM RELIGIONPRATIQUEE);


/*Façon 2 : plus élégante et fonctionne dans tous les cas*/
CREATE TABLE DIVISEUR(RELIGIONS) AS SELECT DISTINCT RELIGIONPRATIQUEE.NOM FROM RELIGIONPRATIQUEE;

SELECT PAYS FROM RELIGIONPRATIQUEE R1
    WHERE NOT EXISTS(SELECT DISTINCT RELIGIONS FROM DIVISEUR
        WHERE NOT EXISTS(SELECT DISTINCT PAYS FROM RELIGIONPRATIQUEE R2
            WHERE R1.PAYS = R2.PAYS
            AND (R2.NOM = DIVISEUR.RELIGIONS)));       
/*Ne renvoie rien, ce qui se vérifie dans les tables.*/

/*Q10*/
/*Interprétation : plus présent = présent dans le plus grand nombre de pays.*/
SELECT * FROM (SELECT TYPEGOUVERNEMENT,COUNT(*) AS NOMBREPAYS FROM REGIMEPOLITIQUE
GROUP BY TYPEGOUVERNEMENT
ORDER BY NOMBREPAYS DESC)
WHERE ROWNUM <= 3;
/* Renvoie ceci :
republic	4422
parliamentary democracy	462
constitutional monarchy	156
*/

/*Q11*/
SELECT NOM FROM PAYS,POPULATION2
          WHERE PAYS.IDPAYS=POPULATION2.PAYS 
          AND POPULATION2.CROISSANCEDEMOGRAPHIQUE = (SELECT MAX(CROISSANCEDEMOGRAPHIQUE) FROM POPULATION2);
/*Renvoie un pays : Lebanon, ce qui se vérifie dans les tables : pas d'égalité.*/


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
/*Renvoie 0*/

/*Q14*/
SELECT NOMORGA FROM (SELECT NOMORGA FROM ORGANISATION
    WHERE NOT EXISTS (SELECT * FROM ENGLOBE,ORGANISATION,ESTMEMBRE
        WHERE ENGLOBE.PAYS=ESTMEMBRE.PAYS AND ORGANISATION.ABBREVIATION=ESTMEMBRE.ORGANISATION 
        AND ENGLOBE.CONTINENT='America'));
/*Renvoie une liste vide*/

/*Q15*/
SELECT NOM,POPULATION FROM PAYS WHERE POPULATION > '60000000'
    ORDER BY POPULATION DESC;
/*Renvoie 22 lignes:
China
India
United States...*/

/*Q16*/
/*Interprétation, comme pas de km² dans les tables : plus petit = plus dépeuplé*/
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
SELECT DISTINCT NOM FROM PAYS,ENGLOBE E1,ENGLOBE E2 WHERE 
    E1.PAYS=PAYS.IDPAYS AND E1.PAYS=E2.PAYS AND E1.CONTINENT != E2.CONTINENT;
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
/*Remarque question : on arrondit le nombre de pratiquants pour avoir un entier./*

/*Solution 1 : On créé la table de chaque continent et on les lie ensuite
Pas optimisé car on doit ajouter un UNION par continent.*/
SELECT * FROM (SELECT CONTINENT,NOM, NBPRATIQUANTS FROM(
    SELECT CONTINENT,NOM, SUM(NBPRATIQUANTS) AS NBPRATIQUANTS FROM( 
        SELECT R20.CONTINENT,R20.NOM,R20.PAYS,ROUND(PAYS.POPULATION*POURCENTAGE/100) AS NBPRATIQUANTS FROM PAYS, ( 
            SELECT E.CONTINENT,RP.NOM,RP.PAYS,RP.POURCENTAGE FROM RELIGIONPRATIQUEE RP,ENGLOBE E
            WHERE RP.PAYS = E.PAYS AND E.CONTINENT = 'Africa') R20
        WHERE PAYS.IDPAYS = R20.PAYS)
    GROUP BY NOM,CONTINENT
    ORDER BY NBPRATIQUANTS DESC)
WHERE ROWNUM <= 5
  UNION
SELECT CONTINENT,NOM, NBPRATIQUANTS FROM(
    SELECT CONTINENT,NOM, SUM(NBPRATIQUANTS) AS NBPRATIQUANTS FROM( 
        SELECT R20.CONTINENT,R20.NOM,R20.PAYS,ROUND(PAYS.POPULATION*POURCENTAGE/100) AS NBPRATIQUANTS FROM PAYS, ( 
            SELECT E.CONTINENT,RP.NOM,RP.PAYS,RP.POURCENTAGE FROM RELIGIONPRATIQUEE RP,ENGLOBE E
            WHERE RP.PAYS = E.PAYS AND E.CONTINENT = 'America') R20
        WHERE PAYS.IDPAYS = R20.PAYS)
    GROUP BY NOM,CONTINENT
    ORDER BY NBPRATIQUANTS DESC)
WHERE ROWNUM <= 5
  UNION
SELECT CONTINENT,NOM, NBPRATIQUANTS FROM(
    SELECT CONTINENT,NOM, SUM(NBPRATIQUANTS) AS NBPRATIQUANTS FROM( 
        SELECT R20.CONTINENT,R20.NOM,R20.PAYS,ROUND(PAYS.POPULATION*POURCENTAGE/100) AS NBPRATIQUANTS FROM PAYS, ( 
            SELECT E.CONTINENT,RP.NOM,RP.PAYS,RP.POURCENTAGE FROM RELIGIONPRATIQUEE RP,ENGLOBE E
            WHERE RP.PAYS = E.PAYS AND E.CONTINENT = 'Asia') R20
        WHERE PAYS.IDPAYS = R20.PAYS)
    GROUP BY NOM,CONTINENT
    ORDER BY NBPRATIQUANTS DESC)
WHERE ROWNUM <= 5
  UNION
SELECT CONTINENT,NOM, NBPRATIQUANTS FROM(
    SELECT CONTINENT,NOM, SUM(NBPRATIQUANTS) AS NBPRATIQUANTS FROM( 
        SELECT R20.CONTINENT,R20.NOM,R20.PAYS,ROUND(PAYS.POPULATION*POURCENTAGE/100) AS NBPRATIQUANTS FROM PAYS, ( 
            SELECT E.CONTINENT,RP.NOM,RP.PAYS,RP.POURCENTAGE FROM RELIGIONPRATIQUEE RP,ENGLOBE E
            WHERE RP.PAYS = E.PAYS AND E.CONTINENT = 'Australia/Oceania') R20
        WHERE PAYS.IDPAYS = R20.PAYS)
    GROUP BY NOM,CONTINENT
    ORDER BY NBPRATIQUANTS DESC)
WHERE ROWNUM <= 5
  UNION
SELECT CONTINENT,NOM, NBPRATIQUANTS FROM(
    SELECT CONTINENT,NOM, SUM(NBPRATIQUANTS) AS NBPRATIQUANTS FROM( 
        SELECT R20.CONTINENT,R20.NOM,R20.PAYS,ROUND(PAYS.POPULATION*POURCENTAGE/100) AS NBPRATIQUANTS FROM PAYS, ( 
            SELECT E.CONTINENT,RP.NOM,RP.PAYS,RP.POURCENTAGE FROM RELIGIONPRATIQUEE RP,ENGLOBE E
            WHERE RP.PAYS = E.PAYS AND E.CONTINENT = 'Europe') R20
        WHERE PAYS.IDPAYS = R20.PAYS)
    GROUP BY NOM,CONTINENT
    ORDER BY NBPRATIQUANTS DESC)
WHERE ROWNUM <= 5)
ORDER BY CONTINENT, NBPRATIQUANTS DESC;

/*Solution 2 : On sélectionne tous les continents, on les ordonne par nbrpratiquants et on les numérote de manière interne grâce à ROW_NUMBER
pour ensuite ne sélectionner que les 5 premiers.*/

SELECT  CONTINENT,NOM,NBPRATIQUANTS FROM(
    SELECT CONTINENT,NOM,NBPRATIQUANTS,ROW_NUMBER() OVER(PARTITION BY CONTINENT ORDER BY CONTINENT,NBPRATIQUANTS DESC) AS ROWNUMBER FROM(
        SELECT CONTINENT,NOM, SUM(NBPRATIQUANTS) AS NBPRATIQUANTS FROM( 
            SELECT R20.CONTINENT,R20.NOM,R20.PAYS,ROUND(PAYS.POPULATION*POURCENTAGE/100) AS NBPRATIQUANTS FROM PAYS, ( 
                SELECT E.CONTINENT,RP.NOM,RP.PAYS,RP.POURCENTAGE FROM RELIGIONPRATIQUEE RP,ENGLOBE E
                WHERE RP.PAYS = E.PAYS) R20
            WHERE PAYS.IDPAYS = R20.PAYS)
        GROUP BY CONTINENT,NOM
        ORDER BY CONTINENT,NBPRATIQUANTS DESC))
WHERE ROWNUMBER <= 5;
/*
Africa	Muslim	458443264
Africa	Christian	144903504
Africa	Roman Catholic	76315235
Africa	Protestant	70108719
Africa	Christian Orthodox	36679629
America	Roman Catholic	564035053
America	Protestant	230240257
America	Jewish	6285092
America	Mormon	5464603
America	Muslim	2820840
Asia	Muslim	1203448423
Asia	Hindu	1026805811
Asia	Buddhist	302025948
Asia	Christian	122030374
Asia	Roman Catholic	91271730
Australia/Oceania	Muslim	219811418
Australia/Oceania	Protestant	20726866
Australia/Oceania	Roman Catholic	16312080
Australia/Oceania	Anglican	5490750
Australia/Oceania	Hindu	5437374
Europe	Roman Catholic	279658674
Europe	Christian Orthodox	130505797
Europe	Muslim	124775332
Europe	Protestant	80514294
Europe	Anglican	28985192
*/

/*Q21*/
--,ENGLOBE WHERE RELIGIONPRATIQUE.PAYS = ENGLOBE.PAYS AND ENGLOBE.CONTINENT='Europe'
/*Q22*/

/*Q23*/



/*Q 24*/
/*Façon 1 : On considère plus parlé = nombre de personnes le plus élevé*/
SELECT NOM,ROUND(NBPARLANT) FROM
(SELECT PAYS.NOM, R1.POURCENTAGEPARLE*PAYS.POPULATION/100 AS NBPARLANT FROM PAYS, 
  (SELECT PAYS, LANGUE, POURCENTAGEPARLE FROM LANGAGEPARLE
        WHERE LANGUE = 'French' AND POURCENTAGEPARLE != 100
        ORDER BY POURCENTAGEPARLE DESC) R1
WHERE PAYS.IDPAYS = R1.PAYS
ORDER BY NBPARLANT DESC)
WHERE ROWNUM = 1;
/* Renvoie le Canada avec 7594193 
Remarque : on arrondit à un nombre de personnes entier*/
 
/*Façon 2: On compte seulement le nombre de pourcents*/
SELECT PAYS,POURCENTAGEPARLE FROM
    (SELECT PAYS, LANGUE, POURCENTAGEPARLE FROM LANGAGEPARLE
        WHERE LANGUE = 'French' AND POURCENTAGEPARLE != 100
        ORDER BY POURCENTAGEPARLE DESC)
WHERE ROWNUM <= 1;
/*Renvoie la Guadeloupe avec 99%*/

/*Q25*/
SELECT CAPITALE FROM PAYS MINUS SELECT VILLE FROM ORGANISATION;
/*Renvoie ceci : 
Abuja
Accra
Adamstown
Al Kuwayt
Al ManÄ?mah
Algiers
Alofi
*/

/*Q26*/
/*Interprétation : plus pauvre <=> plus faible PIB*/
SELECT PAYS FROM
    (SELECT PAYS FROM
        (SELECT * FROM ECONOMIE
    ORDER BY PIB)
WHERE ROWNUM<=3 );
/*Renvoie ces trois pays :
TOK
NIUE
HELX
*/

/*Q27*/
/*Interprétation : continent le plus riche <=> somme des PIB de ses pays la plus élevée*/
/*Façon 1 : Plus optimisé : moins de sous-requêtes.*/
SELECT * FROM 
    (SELECT CONTINENT,SUM(PIB) AS PIB FROM(
        SELECT CONTINENT,ECONOMIE.PAYS,PIB FROM ENGLOBE,ECONOMIE
        WHERE ENGLOBE.PAYS = ECONOMIE.PAYS)
    GROUP BY CONTINENT
    ORDER BY PIB DESC)
WHERE ROWNUM =1;

/*Façon 2:*/
SELECT *
  FROM
    (SELECT CONTINENT,sum(PIB)
      FROM 
        (SELECT DISTINCT ECO.PIB, co.CONTINENT 
          FROM ECONOMIE ECO, 
            (SELECT e.pays, e.CONTINENT FROM englobe e) f,
            ENGLOBE co  
          WHERE f.CONTINENT = co.CONTINENT 
            AND ECO.PAYS = f.PAYS) 
      GROUP BY CONTINENT 
      ORDER BY sum(PIB) desc)
    WHERE rownum = 1; 
 /*Renvoie :
 Asia	27310349 
*/

/*Q28*/
/*Façon 1*/
SELECT ECONOMIE.PAYS FROM ECONOMIE WHERE ECONOMIE.PIB > 1000 
  UNION
SELECT POPULATION2.PAYS FROM POPULATION2 WHERE POPULATION2.CROISSANCEDEMOGRAPHIQUE > 0;

/*Façon 2*/
SELECT ECONOMIE.PAYS FROM ECONOMIE,POPULATION2
WHERE ECONOMIE.PAYS = POPULATION2.PAYS
AND (ECONOMIE.PIB > 1000 OR POPULATION2.CROISSANCEDEMOGRAPHIQUE > 0);
/*Renvoie 227 lignes::
A
AFG
AG
AL
AND...
*/

/*Q29*/
/*Façon 1*/
SELECT ECONOMIE.PAYS FROM ECONOMIE WHERE ECONOMIE.PIB < 1000 
  INTERSECT
SELECT POPULATION2.PAYS FROM POPULATION2 WHERE POPULATION2.CROISSANCEDEMOGRAPHIQUE > 0;

/*Façon 2*/
SELECT ECONOMIE.PAYS FROM ECONOMIE,POPULATION2
WHERE ECONOMIE.PAYS = POPULATION2.PAYS
AND (ECONOMIE.PIB < 1000 AND POPULATION2.CROISSANCEDEMOGRAPHIQUE > 0);
/*Renvoie 25 lignes:
AXA
WD
WG
MNTS
NLSM
KN...
*/

/*Q30*/
SELECT PAYS.NOM AS NOMPAYS,LANGAGEPARLE.LANGUE FROM LANGAGEPARLE, PAYS
WHERE LANGAGEPARLE.PAYS = PAYS.IDPAYS AND PAYS.POPULATION > 1000000
AND LANGAGEPARLE.POURCENTAGEPARLE < 5;
/*Renvoie 63 lignes:
Albania	Greek
Macedonia	Serbian
Macedonia	Roma
Macedonia	Turkish...
*/

/********************************************
QUESTIONS SUPPLEMENTAIRES : CONSULTATION BIS DE LA BASE

Remarques section : On reprend à partir d'ici les tables mondiales disponibles sur 
https://www.dbis.informatik.uni-goettingen.de/Mondial/#SQL
*********************************************/
/*Q1*/
/*Remarque : il faut faire attention, les informations sont dans les deux colonnes.*/
/*Façon 1:*/
SELECT DISTINCT COUNTRY.NAME FROM BORDERS, COUNTRY
    WHERE (BORDERS.COUNTRY2 = COUNTRY.CODE 
    AND COUNTRY1 = 'F')
    OR BORDERS.COUNTRY1 = COUNTRY.CODE
    AND COUNTRY2 = 'F';

/*Façon 2:*/
SELECT DISTINCT COUNTRY.NAME FROM BORDERS, COUNTRY
    WHERE (BORDERS.COUNTRY2 = COUNTRY.CODE 
    AND COUNTRY1 = 'F')
 UNION
SELECT DISTINCT COUNTRY.NAME FROM BORDERS, COUNTRY
    WHERE (BORDERS.COUNTRY1 = COUNTRY.CODE 
    AND COUNTRY2 = 'F');
/*Renvoie ceci : 
Spain
Germany
Luxembourg
Andorra
Belgium
Switzerland
Italy
Monaco
*/

/*Q2*/
/*Attention, le Danube est ici noté en allemand : Donau.*/
SELECT DISTINCT COUNTRY.NAME FROM GEO_RIVER,COUNTRY
    WHERE COUNTRY.CODE = GEO_RIVER.COUNTRY
    AND GEO_RIVER.RIVER='Donau';
/*Renvoie ceci :
Serbia
Slovakia
Croatia
Moldova
Austria
Germany
Hungary
Romania
Bulgaria
Ukraine
*/


/*Q3*/ 
SELECT DISTINCT COUNTRY.NAME FROM CITY C1, CITY C2, COUNTRY
    WHERE COUNTRY.CODE = C1.COUNTRY 
    AND C1.COUNTRY = C2.COUNTRY
    AND C1.POPULATION > 1000000
    AND C2.POPULATION < 50000;
/*Renvoie ceci:
United Kingdom
Austria
United States
Canada
Russia
Ukraine
Mexico
*/

/*Q4*/
/*Interprétation : d'Europe = passant par un pays européen*/
SELECT DISTINCT RIVER FROM GEO_RIVER,ENCOMPASSES
    WHERE GEO_RIVER.COUNTRY = ENCOMPASSES.COUNTRY
    AND ENCOMPASSES.CONTINENT = 'Europe';
/*Renvoie 150 lignes :
Thames
Shannon
Kymijoki
Elbe
Moldau
Limmat
Loire...
*/

/*Q5*/
/*On numérote les couples mer,pays et on sélectionne les mers de ceux ayant un numéro supérieur à 5*/
SELECT DISTINCT SEA FROM(
    SELECT SEA,COUNTRY, ROW_NUMBER() OVER(PARTITION BY SEA ORDER BY SEA,COUNTRY DESC) AS ROWNUMBER FROM(
        SELECT DISTINCT G1.SEA,G1.COUNTRY FROM GEO_SEA G1, GEO_SEA G2
            WHERE G1.COUNTRY != G2.COUNTRY 
            AND G1.SEA = G2.SEA))
    WHERE ROWNUMBER > 5;
/*Renvoie ceci:
Atlantic Ocean
Indian Ocean
Mediterranean Sea
Red Sea
Caribbean Sea
Baltic Sea
South China Sea
Black Sea
North Sea
Pacific Ocean
Persian Gulf
*/

/*Q6*/
/*On prend tous les pays et on enlève ceux ayant une ville de plus de 500000 habitants*/
SELECT NAME FROM COUNTRY,
(SELECT CODE FROM COUNTRY 
 MINUS
SELECT COUNTRY FROM CITY 
    WHERE CITY.POPULATION > 500000) Q6
    WHERE Q6.CODE = COUNTRY.CODE;
/*Renvoie 115 lignes:
Albania
Montenegro
Kosovo
Andorra
Liechtenstein
Slovakia
Slovenia...
*/
    
/*Q7*/
/*On prend tous les pays et on enlève ceux qui possèdent une bordure avec une mer*/
SELECT NAME FROM COUNTRY,
(SELECT CODE FROM COUNTRY
 MINUS
SELECT COUNTRY FROM GEO_SEA)Q7
WHERE COUNTRY.CODE = Q7.CODE;
/*Renvoie 41 lignes:
Macedonia
Serbia
Kosovo
Andorra
Austria
Czech Republic
Hungary...
*/

/*Q8*/
/*On regarde si la différence entre la population du pays et celle dans les villes répertoriées appartenant au pays
est inférieure à 75% de la population du pays.*/

SELECT COUNTRY.NAME FROM COUNTRY,
    (SELECT CODE, SUM(POPULATION) AS POPREFERENCEE FROM(
        SELECT CODE, CITY.NAME,CITY.POPULATION FROM COUNTRY,CITY
           WHERE COUNTRY.CODE = CITY.COUNTRY)
        GROUP BY CODE) POPREF
    WHERE COUNTRY.CODE = POPREF.CODE AND COUNTRY.POPULATION - POPREF.POPREFERENCEE < 75*COUNTRY.POPULATION/100;
/*Renvoie 110 lignes:
Serbia
Spain
Czech Republic
Brunei
Lebanon
Japan
Maldives...
*/

/*Q9*/
/*Interprétation : plus proche signifie distance la plus faible entre deux points dans le plan cartésien.*/
SELECT NAME FROM(
  SELECT NAME,SQRT(POWER((CITY.LATITUDE -48),2)+POWER((CITY.LONGITUDE-2),2)) AS DISTANCE FROM CITY
  ORDER BY DISTANCE)
WHERE ROWNUM=1;
/*Renvoie Orléans*/

/*Q10*/

