package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RentalController {

	@GetMapping("test")
	public String test() {
		return "test";
	}
}
