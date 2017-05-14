somme <- function(v){
if(is.vector(v) && is.numeric(v)){
  ret <- 0
  for(i in v){
   ret <- ret + i
  }
  return(ret)
 } else {
  return("Erreur de parametre")
 }
}