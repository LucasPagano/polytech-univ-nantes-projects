cramer <- function (X,Y){
  khi = khiDeux(X,Y)
  h = min(length(levels(X)),length(levels(Y)))
  return (sqrt(khi/(length(Y)*(h-1))))
}