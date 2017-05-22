contingence <- function(X,Y){
  matrix = matrix(nrow = length(levels(X)), ncol =
           length(levels(Y)))
  
  obs = table(X,Y)
  total = length(X)
  
  sumX = table(X)
  sumY = table(Y)
  
  for(i in 1:length(levels(X))){
    ligne = sumX[i]
    for (j in 1:length(levels(Y))){
      matrix[i,j] = ligne*sumY[j]/total
    }
  }
  
  return (matrix)
}