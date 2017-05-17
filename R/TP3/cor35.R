cor35 <- function(dataFrame){
  print("Les couples sont :")
  for (i in 1:ncol(dataFrame)){
    for (j in i:ncol(dataFrame)){
#      if (abs(mean(dataFrame[,i]*dataFrame[,j]) - mean(dataFrame[,i])*mean(dataFrame[,j]))/(sd(dataFrame[,i])*sd(dataFrame[,j]))))>0.35){
      cor = abs(cor(dataFrame[,i], dataFrame[,j]))
      if ((cor >0.35) && i != j){
        print(paste(colnames(dataFrame[i]), colnames(dataFrame[j]), "avec un correlation de", round(cor, digits=5), sep=" "))
      }       
    }
  }
}