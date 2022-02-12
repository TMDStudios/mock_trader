package com.tmdstudios.mocktrader.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		
		Integer money = 0;
		Double btc = 0.0;
		
		if (session.getAttribute("money") == null) {
			session.setAttribute("money", 0);
			session.setAttribute("btc", 0);
		}else {
			money = (Integer) session.getAttribute("money");
			btc = (Double) session.getAttribute("btc");
		}
		
		model.addAttribute("money", money);
		model.addAttribute("btc", btc);
		
		return "index.jsp";
	}
}
