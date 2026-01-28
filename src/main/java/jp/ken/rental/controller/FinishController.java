package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.ken.rental.application.service.UserInsertService;
import jp.ken.rental.form.UserForm;

@Controller
@RequestMapping("/finish")
@SessionAttributes({"UserForm"})
public class FinishController {
	
	private UserInsertService userInsertService;
	
	public FinishController(UserInsertService userInsertService) {
		this.userInsertService = userInsertService; 
	}
	
	@GetMapping
	public String toLogout(SessionStatus status) {
		status.setComplete();
		return "redirect:/login";
	}
	@PostMapping
	public String toFinish(@ModelAttribute("UserForm") UserForm uForm) throws Exception {
		userInsertService.registUser(uForm);
		return "home";
	}

}