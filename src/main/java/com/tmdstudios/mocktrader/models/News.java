package com.tmdstudios.mocktrader.models;

public class News {
	
	private String source;
	private String news;
	private Double effect;
	
	public News(String source, String news, Double effect) {
		this.source = source;
		this.news = news;
		this.effect = effect;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public Double getEffect() {
		return effect;
	}
	public void setEffect(Double effect) {
		this.effect = effect;
	}
	
}
