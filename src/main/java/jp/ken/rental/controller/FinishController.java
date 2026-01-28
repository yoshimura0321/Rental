package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/userFinish")
@SessionAttributes({"userForm"})
public class FinishController {
	
	
	
	@GetMapping
	public String toLogout(SessionStatus status) {
		status.setComplete();
		return "redirect:/login";
	}
	@PostMapping
	public String toFinish() throws Exception {
		
		return "home";
	}

}