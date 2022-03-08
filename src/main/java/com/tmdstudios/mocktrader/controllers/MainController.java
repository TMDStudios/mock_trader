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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmdstudios.mocktrader.models.News;
import com.tmdstudios.mocktrader.services.NewsDataService;

@Controller
public class MainController {
	
	private List<News> allNews;
	
	private Integer day = 0;
	private Integer lastDay = 0;
	private Double money = 10000.0;
	private Double btc = 0.0;
	private Double total = 10000.0;
	private Double btcPrice = 50000.0;
	private Double lastBtcPrice = 50000.0;
	private Double trend = 0.0;
	private ArrayList<String> actions = new ArrayList<>();
	
	private String[] banners = {
			"https://tmdstudios.files.wordpress.com/2021/02/plclogolight.png?h=120",
			"https://tmdstudios.files.wordpress.com/2021/11/clbanner-1.png?h=120",
			"https://tmdstudios.files.wordpress.com/2022/03/tmdlogowide.png?h=120",
			"https://tmdstudios.files.wordpress.com/2022/03/nfts.png?h=120",
			"https://tmdstudios.files.wordpress.com/2021/04/galagames.png?h=120",
			"https://tmdstudios.files.wordpress.com/2019/02/bitcoinbanner.png?h=120"
			};
	
	private String[] links = {
			"https://play.google.com/store/apps/details?id=com.tmdstudios.python",
			"https://play.google.com/store/apps/details?id=com.tmdstudios.cryptoledgerkotlin",
			"https://tmdstudios.wordpress.com",
			"https://tmdstudios.wordpress.com/nfts/",
			"https://tmdstudios.wordpress.com/2021/04/06/gala-games/",
			"https://freebitco.in/?r=15749838"
			};
	
	@SuppressWarnings("unchecked")
	@GetMapping("/")
	public String index(HttpSession session) throws IOException, InterruptedException {
				
		if (session.getAttribute("day") == null) {
			resetGame(session);
		}else {
			day = (Integer) session.getAttribute("day");
			lastDay = (Integer) session.getAttribute("lastDay");
			money = (Double) session.getAttribute("money");
			btc = (Double) session.getAttribute("btc");
			total = (Double) session.getAttribute("btc") * getBtcPrice(session) + (Double) session.getAttribute("money");
			btcPrice = (Double) session.getAttribute("btcPrice");
			lastBtcPrice = (Double) session.getAttribute("lastBtcPrice");
			trend = (Double) session.getAttribute("trend");
			actions = (ArrayList<String>) session.getAttribute("actions");
			if(lastDay!=day) {
				session.setAttribute("currentNews", getNews(session));
			}
			session.setAttribute("lastDay", day);
			session.setAttribute("total", total);
		}
		
		return "index.jsp";
	}
	
	@GetMapping("/activity/")
	public String activity() {	
		return "activity.jsp";
	}
	
	@PostMapping("/buy")
	public String buy(@RequestParam(value = "amount", defaultValue = "0") int amount, HttpSession session) {
		
		if(amount <= (Double) session.getAttribute("money")) {
			Integer day = getDay(session);
			News news = getNews(session);
			Double btcPrice = getBtcPrice(session);
			getMoney(session, amount, true);
			getBtc(session, amount, btcPrice, true);
			
			DecimalFormat usdF = new DecimalFormat("#.00");
			DecimalFormat btcF = new DecimalFormat("0.00000000");
			addAction(session, "Day " + day + " - " + "Bought " + btcF.format(amount/btcPrice) + " BTC at $" + usdF.format(btcPrice));
			updateBtcPrice(session, news.getEffect());
		}else {
			addAction(session, "Insufficient funds!");
		}
			
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
	public String sell(@RequestParam(value = "amount", defaultValue = "0") int amount, HttpSession session) {
		
		if(amount <= (Double) session.getAttribute("btc") * getBtcPrice(session)) {
			Integer day = getDay(session);
			News news = getNews(session);
			Double btcPrice = getBtcPrice(session);
			getMoney(session, amount, false);
			getBtc(session, amount, btcPrice, false);
			
			DecimalFormat usdF = new DecimalFormat("0.00");
			DecimalFormat btcF = new DecimalFormat("0.00000000");
			addAction(session, "Day " + day + " - " + "Sold " + btcF.format(amount/btcPrice) + " BTC at $" + usdF.format(btcPrice));
			updateBtcPrice(session, news.getEffect());
		}else {
			addAction(session, "Insufficient funds!");
		}
			
		return "redirect:/";
	}
	
	@RequestMapping("/play_again/")
	public String playAgain(HttpSession session) throws IOException, InterruptedException {	
		actions.clear();
		allNews.clear();
		resetGame(session);
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
		if(day%5==0) {
			int indexVal = new Random().nextInt(banners.length);
			session.setAttribute("banner", banners[indexVal]);
			session.setAttribute("link", links[indexVal]);
		}
		return day;
	}
	
	private Double getBtcPrice(HttpSession session) {
		return (Double) session.getAttribute("btcPrice");
	}
	
	private Double updateBtcPrice(HttpSession session, Double effect) {
		Double btcPrice = (Double) session.getAttribute("btcPrice");
		Double lastBtcPrice = (Double) session.getAttribute("lastBtcPrice");
		session.setAttribute("lastBtcPrice", btcPrice);
		lastBtcPrice = btcPrice;
		
		// Add price volatility (linked to effect)
		Random r = new Random();
		if(effect > 0) {
			double priceChange = 0.025 + (0.05 - 0.025) * r.nextDouble();
			btcPrice += btcPrice * priceChange;
		}else {
			double priceChange = 0.033 + (0.05 - 0.033) * r.nextDouble();
			btcPrice += btcPrice * priceChange;
		}
		
		// Add news effect
		btcPrice += btcPrice * effect;
		session.setAttribute("btcPrice", btcPrice);
		session.setAttribute("trend", (1 - lastBtcPrice/btcPrice) * 100);
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
	
	private void resetGame(HttpSession session) throws IOException, InterruptedException {
		session.setAttribute("day", 0);
		session.setAttribute("lastDay", 0);
		session.setAttribute("money", 10000.0);
		session.setAttribute("btc", 0.0);
		session.setAttribute("total", 10000.0);
		session.setAttribute("btcPrice", 50000.0);
		session.setAttribute("lastBtcPrice", 50000.0);
		session.setAttribute("trend", 0.0);
		session.setAttribute("actions", actions);
		session.setAttribute("banner", banners[0]);
		session.setAttribute("link", links[0]);
		NewsDataService newsDataService = new NewsDataService();
		allNews = newsDataService.fetchNews();
		session.setAttribute("news", allNews);
		addAction(session, "Will you be the next (mock) Bitcoin Billionaire?");
		addAction(session, "You can also click 'SKIP' to go to the next day.\n" + 
				"The BTC price will fluctuate based on the news of the day. Buy low and sell high!\n"
				);
		addAction(session, 
				"The goal of this game is to accumulate as much wealth as possible in 100 days.\n" +  
				"Each day, you can buy or sell BTC by entering the dollar amount and clicking on the corresponding button.\n" 
				);
		addAction(session, "Welcome trader!");
	}

}
