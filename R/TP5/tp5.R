data = read.table('TP5/fishprices.data', header = TRUE)
attach(data)

plot(Price.1970, Price.1980)
cor(Price.1970, Price.1980)

#lm(A ~ B) : A = F(B)
#ANNÉES 80 en fonction des années 70
lm(formula = gPrice.1980 ~ gPrice.1970)
summary(lm(gPrice.1980 ~ gPrice.1970))

#Sortie : intercepts : b
#gPrice.1970 : a

residuals = lm$residuals
coeffs = lm$coefficients
ychapeau=lm$fitted.values

#
hist(residuals)

#
plot(gPrice.1970, gPrice.1980)
abline(coeffs)

plot(lm)

#Verif les résidus
plot(gPrice.1970, gPrice.1980, xlim=c(0,5), ylim=c(-2,6))
abline(lm)
par(new=T)
plot(gPrice.1970, residuals, xlim=c(0,5), ylim=c(-2,6), col="red")
abline(0,0)


###############DALPES############
dAlpes = read.table('TP5/alpes.data', header = T)
attach(dAlpes)

lmAlpes = lm(formula = Temperature ~ Pression)

######Multiple#####
dLife = read.table('TP5/lifesavings.data', header = T)
attach(dLife)


lifeModel=lm(formula = pourcentageEpargne ~ ., data = dLife)
coeffs=lifeModel$coefficients
fitted=lifeModel$fitted.values
residuals=lifeModel$residuals