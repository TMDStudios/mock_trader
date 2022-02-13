package com.tmdstudios.mocktrader.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		Integer money = 10000;
		Double btc = 0.0;
		ArrayList<String> actions = new ArrayList<>();
		
		if (session.getAttribute("day") == null) {
			session.setAttribute("day", 0);
			session.setAttribute("money", 10000);
			session.setAttribute("btc", 0.0);
			session.setAttribute("actions", actions);
			NewsDataService newsDataService = new NewsDataService();
			allNews = newsDataService.fetchNews();
			session.setAttribute("news", allNews);
		}else {
			day = (Integer) session.getAttribute("day");
			money = (Integer) session.getAttribute("money");
			btc = (Double) session.getAttribute("btc");
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
	
	@SuppressWarnings("unchecked")
	@PostMapping("/buy")
	public String buy(@RequestParam("amount") int amount, HttpSession session) {
			
		Integer money = (Integer) session.getAttribute("money");
		Double btc = (Double) session.getAttribute("btc");
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		
		money-=30;
		btc+=amount;
		
		actions.add(0, "Day " + getDay(session) + " - " + "Bought " + amount + " at " + "price per coin");
		
		session.setAttribute("money", money);
		session.setAttribute("btc", btc);
		session.setAttribute("actions", actions);
		session.setAttribute("currentNews", getNews(session));
			
		return "redirect:/";
	}
	
	@PostMapping("/skip")
	public String skip(HttpSession session) {
		
		getDay(session);
		session.setAttribute("currentNews", getNews(session));
		
		return "redirect:/";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/sell")
	public String sell(@RequestParam("amount") int amount, HttpSession session) {
			
		Integer money = (Integer) session.getAttribute("money");
		Double btc = (Double) session.getAttribute("btc");
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		
		money+=50;
		btc-=amount;
		
		actions.add(0, "Day " + getDay(session) + " - " + "Sold " + amount + " at " + "price per coin");
		
		session.setAttribute("money", money);
		session.setAttribute("btc", btc);
		session.setAttribute("actions", actions);
		session.setAttribute("currentNews", getNews(session));
			
		return "redirect:/";
	}
	
	private News getNews(HttpSession session) {
		@SuppressWarnings("unchecked")
		List<News> news = (List<News>) session.getAttribute("news");
		News currentNews = news.get(new Random().nextInt(news.size()));
		return currentNews;
	}
	
	private Integer getDay(HttpSession session) {
		Integer day = (Integer) session.getAttribute("day");
		day++;
		session.setAttribute("day", day);
		return day;
	}
}
