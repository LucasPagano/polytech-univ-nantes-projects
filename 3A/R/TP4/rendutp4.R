dEtud = read.table('TP4/etudiants.data', header = TRUE)
attach(dEtud)

#
pie(table(Hair))


assocplot(table(Hair, Sex))
