dVotes = read.table("TP3/votes.data", header=TRUE)
attach(dVotes)

#1 pour lignes, 2 pour colonnes
electeurs = apply(dVotes, 1, FUN=sum)

# Test de normalité, ne le passe pas
shapiro.test(electeurs)

# vote par candidat
vParCand = sort(apply(dVotes, 2, sum), decreasing=TRUE)

# marrice corrélation
cor(dVotes)
# bové plus proche de besancenot que voynet
# bayrou plus proche de royal que sarkozy
# de villier plus proche de le pen que sarkozy

#Corrélation sarko royale, ne pas oublier : il ne faut pas utiliser sd car il faut sd biaisé
corRoyalSarko = (mean(Royal.PS*Sarkozy.UMP) - mean(Royal.PS)*mean(Sarkozy.UMP))/sqrt((mean(Royal.PS^2)-mean(Royal.PS)^2)*(mean(Sarkozy.UMP^2)-mean(Sarkozy.UMP)^2))

#<0.1 : faible
#0.3 : moyen
#0.5 : fort

#Corrélation par paire
pairs(dVotes)
pairs(apply(dVotes,2,jitter),pch=".")

#test : significatif ?
# significatif : p-value > 0.05
cor.test(Besancenot.LCR, Laguiller.LO)



#p-valeur = 2*min(P(T<t|H0),P(T>t|H0))
2*min(pt(0.7, df=15, lower.tail = TRUE), pt(0.7, df=15, lower.tail = FALSE))

#Pour la borne :
qt(0.975, length(Bayrou.UDF)-2)
#Pour la valeur à tester :
T = corBayrouSarko/sqrt(1-corBayrouSarko^2)*sqrt(length(Sarkozy.UMP)-2)
  
#PArtie 2
x=rnorm(n=60)
y=-3.14*x + 7.04
z=-exp(x)
t = x*x

x2 = rnorm(60,0,0.3)
y2=y+x2
t2=t+x2
z2=z+x2