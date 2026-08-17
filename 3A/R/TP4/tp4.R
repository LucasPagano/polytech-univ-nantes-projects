dTel = read.table("TP4/telephones.data", header = TRUE)
attach(dTel)

#Tables de contigence
table(Fabricant, Prix)

#Mosaicplot
mosaicplot(table(dTel[7:8]), shade=TRUE)

#Attractions et répulsions
assocplot(table(dTel[7:8]))

#Contingence : on peut comparer s'il y a le même nombre de levels

#Chisq2
source('TP4/contingence.R')
source('TP4/khiDeux.R')
khiDeux(Design,Ecran)

#cramer
source('TP4/cramer.R')
cramer(Design,Ecran)

#correlation entre rangs
cor(c(Design),c(Prix))
#Sinon
as.numeric(Design)

#Chisq.test
#Significatif :
#p-valeur < 0.05
#Intense : X-squared grand

D