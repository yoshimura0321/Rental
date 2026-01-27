package jp.ken.rental.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@SessionAttributes("UserForm")
public class RentalController {


	@GetMapping("/home")
	public String home() {
		return "home";
	}

}
