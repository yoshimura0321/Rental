package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("cart")
public class CartController {
	@GetMapping("/cart")
	public String toCart() {
		return "cart";
	}
	
	@PostMapping("/cart")
	public String update() {
		return "cart";
	}
	
}
