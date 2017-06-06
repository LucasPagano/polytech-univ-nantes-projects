package main;

class Choice{
	public String text;
	private Integer maxClient;
	public Integer idChoice;

	public Choice(String text, Integer maxClient){
		this.text = text;
		this.maxClient = maxClient;
	}
	
	public String getText(){
		return text;
	}

	public Integer getMaxClient(){
		return maxClient;
	}
}