package com.tmdstudios.mocktrader.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
	
	@SuppressWarnings("unchecked")
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		
		Integer day = 1;
		Integer money = 1000;
		Double btc = 0.0;
		ArrayList<String> actions = new ArrayList<>();
		
		if (session.getAttribute("day") == null) {
			session.setAttribute("day", 0);
			session.setAttribute("money", 0);
			session.setAttribute("btc", 0.0);
			session.setAttribute("actions", actions);
		}else {
			day = (Integer) session.getAttribute("day");
			money = (Integer) session.getAttribute("money");
			btc = (Double) session.getAttribute("btc");
			actions = (ArrayList<String>) session.getAttribute("actions");
		}
		
		model.addAttribute("day", day);
		model.addAttribute("money", money);
		model.addAttribute("btc", btc);
		model.addAttribute("actions", actions);
		
		return "index.jsp";
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/activity/")
	public String activity(HttpSession session, Model model) {
		
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		model.addAttribute("actions", actions);
		
		return "activity.jsp";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/buy")
	public String buy(@RequestParam("amount") int amount, HttpSession session) {
			
		Integer day = (Integer) session.getAttribute("day");
		Integer money = (Integer) session.getAttribute("money");
		Double btc = (Double) session.getAttribute("btc");
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		
		day++;
		money-=30;
		btc+=amount;
		
		actions.add(0, "Day " + day + " - " + "Bought " + amount + " at " + "price per coin");
		
		session.setAttribute("day", day);
		session.setAttribute("money", money);
		session.setAttribute("btc", btc);
		session.setAttribute("actions", actions);
			
		return "redirect:/";
	}
	
	@PostMapping("/skip")
	public String skip(HttpSession session) {
		
		session.setAttribute("day", 3);
		
		return "redirect:/";
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/sell")
	public String sell(@RequestParam("amount") int amount, HttpSession session) {
			
		Integer day = (Integer) session.getAttribute("day");
		Integer money = (Integer) session.getAttribute("money");
		Double btc = (Double) session.getAttribute("btc");
		ArrayList<String> actions = (ArrayList<String>) session.getAttribute("actions");
		
		day++;
		money+=50;
		btc-=amount;
		
		actions.add(0, "Day " + day + " - " + "Sold " + amount + " at " + "price per coin");
		
		session.setAttribute("day", day);
		session.setAttribute("money", money);
		session.setAttribute("btc", btc);
		session.setAttribute("actions", actions);
			
		return "redirect:/";
	}
}
