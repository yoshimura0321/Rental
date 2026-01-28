package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.rental.application.service.UserInsertService;
import jp.ken.rental.form.UserForm;

@Controller
@RequestMapping("/confirm")
@SessionAttributes({"userForm"})
public class ConfirmController {
	
	private UserInsertService userInsertService;
		
		public ConfirmController(UserInsertService userInsertService) {
			this.userInsertService = userInsertService; 
		}
	
	@GetMapping
	public String toConfirm() {
		return "confirm";
	}
	@PostMapping(params = "back")
	public String toRegist() {
		return "redirect:/regist";
	}
	@PostMapping(params = "forward")
	public String forFinish(@ModelAttribute UserForm userForm) throws Exception{
		int row = userInsertService.registUser(userForm);
		
		if(row == 0) {
			return "userRegist";
		}
		return "forward:/finish";
	}

}
