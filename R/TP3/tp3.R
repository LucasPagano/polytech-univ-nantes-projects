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

#Corrélation sarko royale
corRoyalSarko = (mean(Royal.PS*Sarkozy.UMP) - mean(Royal.PS)*mean(Sarkozy.UMP))/(sd(Royal.PS)*sd(Sarkozy.UMP))

