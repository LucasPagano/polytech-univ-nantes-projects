variance = function(v){
	m = moyenne(v)
	r = 0
	for(i in v){
		r = r+ (i-m)*(i-m)
	}
	r = r/length(v)
	return(r)
}