package com.example.servingwebcontent;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Component
public class GreetingController {

	@GetMapping("/bitcoin")
	public String bitcoin(@RequestParam(name="name", required=false, defaultValue="idk") String name, Model model) {
		//System.out.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
		ReadJSON js = new ReadJSON();
		String [] str = js.getBtcInfo();

		model.addAttribute("name", str[0]);
		model.addAttribute("date", str[1]);
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

	@GetMapping("/history_by_price")
	public String history_by_price(@RequestParam(name="name", required=false, defaultValue="idk") String name, Model model) {

		ReadJSON js = new ReadJSON();

		try{
			model.addAttribute("name", js.orderByPrice());
		}
		catch(Exception e){
			System.out.print("ERRO");
		}
		return "history";
	}

	@Scheduled(fixedRate = 60000)
	public void reportCurrentTime() {
		ReadJSON js = new ReadJSON();
		try{
			System.out.println(js.getBtcInfo()[0]);
		}
		catch(Exception e){
			System.out.print("ERRO");
		}
	}
}
