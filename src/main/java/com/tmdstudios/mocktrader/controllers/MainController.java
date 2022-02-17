package com.tmdstudios.mocktrader.controllers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmdstudios.mocktrader.models.News;
import com.tmdstudios.mocktrader.services.NewsDataService;

@Controller
public class MainController {
	
	@SuppressWarnings("unchecked")
	@GetMapping("/")
	public String index(HttpSession session) throws IOException, InterruptedException {
				
		List<News> allNews;
		
		Integer day = 0;
		Double money = 10000.0;
		Double btc = 0.0;
		Double btcPrice = 50000.0;
		ArrayList<String> actions = new ArrayList<>();
		
		if (session.getAttribute("day") == null) {
			session.setAttribute("day", 0);
			session.setAttribute("money", 10000.0);
			session.setAttribute("btc", 0.0);
			session.setAttribute("btcPrice", 50000.0);
			session.setAttribute("actions", actions);
			NewsDataService newsDataService = new NewsDataService();
			allNews = newsDataService.fetchNews();
			session.setAttribute("news", allNews);
			addAction(session, "Welcome trader!");
		}else {
			day = (Integer) session.getAttribute("day");
			money = (Double) session.getAttribute("money");
			btc = (Double) session.getAttribute("btc");
			btcPrice = (Double) session.getAttribute("btcPrice");
			actions = (ArrayList<String>) session.getAttribute("actions");
			session.setAttribute("currentNews", getNews(session));
		}
		
		return "index.jsp";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/activity/")
	public String activity(HttpSession session) {
		
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		
		return "activity.jsp";
	}
	
	@PostMapping("/buy")
	public String buy(@RequestParam("amount") int amount, HttpSession session) {
		
		Integer day = getDay(session);
		News news = getNews(session);
		Double btcPrice = getBtcPrice(session);
		getMoney(session, amount, true);
		getBtc(session, amount, btcPrice, true);
		
		DecimalFormat usdF = new DecimalFormat("#.##");
		DecimalFormat btcF = new DecimalFormat("#.##########");
		addAction(session, "Day " + day + " - " + "Bought " + btcF.format(amount/btcPrice) + " BTC at $" + usdF.format(btcPrice));
		updateBtcPrice(session, news.getEffect());
			
		return "redirect:/";
	}
	
	@PostMapping("/skip")
	public String skip(HttpSession session) {
		
		Integer day = getDay(session);
		News currentNews = getNews(session);
		updateBtcPrice(session, currentNews.getEffect());
		addAction(session, "Day " + day + " - " + "Skipped");
		
		return "redirect:/";
	}
	
	@PostMapping("/sell")
	public String sell(@RequestParam("amount") int amount, HttpSession session) {
		
		Integer day = getDay(session);
		News news = getNews(session);
		Double btcPrice = getBtcPrice(session);
		getMoney(session, amount, false);
		getBtc(session, amount, btcPrice, false);
		
		DecimalFormat usdF = new DecimalFormat("0.00");
		DecimalFormat btcF = new DecimalFormat("0.0000000000");
		addAction(session, "Day " + day + " - " + "Sold " + btcF.format(amount/btcPrice) + " BTC at $" + usdF.format(btcPrice));
		updateBtcPrice(session, news.getEffect());
			
		return "redirect:/";
	}
	
	private News getNews(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<News> news = (List<News>) session.getAttribute("news");
		News currentNews = news.get(new Random().nextInt(news.size()));
		session.setAttribute("currentNews", news);
		return currentNews;
	}
	
	private Integer getDay(HttpSession session) {
		Integer day = (Integer) session.getAttribute("day");
		day++;
		session.setAttribute("day", day);
		return day;
	}
	
	private Double getBtcPrice(HttpSession session) {
		return (Double) session.getAttribute("btcPrice");
	}
	
	private Double updateBtcPrice(HttpSession session, Double effect) {
		Double btcPrice = (Double) session.getAttribute("btcPrice");
		// Add price volatility (linked to effect)
		if(effect > 0) {
			btcPrice += btcPrice * (new Random().nextDouble(0.15) - 0.05);
		}else {
			btcPrice += btcPrice * (new Random().nextDouble(0.20) - 0.125);
		}
		// Add news effect
		btcPrice += btcPrice * effect;
		session.setAttribute("btcPrice", btcPrice);
		return btcPrice;
	}
	
	private Double getMoney(HttpSession session, Integer amount, boolean buy) {
		Double money = (Double) session.getAttribute("money");
		if(buy) {
			money-=amount;
		}else {
			money+=amount;
		}
		session.setAttribute("money", money);
		return money;
	}
	
	private Double getBtc(HttpSession session, Integer amount, Double btcPrice, boolean buy) {
		Double btc = (Double) session.getAttribute("btc");
		if(buy) {
			btc+=amount/btcPrice;
		}else {
			btc-=amount/btcPrice;
		}
		session.setAttribute("btc", btc);
		return btc;
	}
	
	private void addAction(HttpSession session, String action) {
		@SuppressWarnings("unchecked")
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		actions.add(0, action);		
		session.setAttribute("actions", actions);
	}
}
