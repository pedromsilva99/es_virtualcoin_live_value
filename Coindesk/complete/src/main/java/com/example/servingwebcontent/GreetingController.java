package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	@GetMapping("/bitcoin")
	public String bitcoin(@RequestParam(name="name", required=false, defaultValue="idk") String name, Model model) {

		ReadJSON js = new ReadJSON();


		model.addAttribute("name", js.getPrice());
		return "bitcoin";
	}

	@GetMapping("/history")
	public String history(@RequestParam(name="name", required=false, defaultValue="idk") String name, Model model) {

		ReadJSON js = new ReadJSON();

		try{
			model.addAttribute("name", js.readFromJSON());
		}
		catch(Exception e){
			System.out.print("ERRO");
		}
		return "history";
	}
}
