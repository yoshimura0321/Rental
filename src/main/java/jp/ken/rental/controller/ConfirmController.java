package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/confirm")
@SessionAttributes({"UserForm"})
public class ConfirmController {
	
	@GetMapping
	public String toConfirm() {
		return "confirm";
	}
	@PostMapping(params = "back")
	public String toRegist() {
		return "redirect:/regist";
	}
	@PostMapping(params = "forward")
	public String forFinish() {
		return "forward:/finish";
	}

}
