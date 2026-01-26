package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalController {

	@GetMapping("/home")
	public String test() {
		return "home";
	}
}
