khiDeux <- function(X,Y){
  obs = table(X,Y)
  theo = contingence(X,Y)
  result = matrix = matrix(nrow = length(levels(X)), ncol =
                             length(levels(Y)))
  
  
  for(i in 1:length(levels(X))){
    for (j in 1:length(levels(Y))){
        result[i,j] = (obs[i,j]-theo[i,j])^2/theo[i,j]
      }
    }
  return (sum(result))
}

