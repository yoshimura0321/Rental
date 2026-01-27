package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.rental.form.UserForm;

@Controller
@SessionAttributes({"UserForm"})
public class RegistController {

	
	@ModelAttribute("UserForm")
	public UserForm setupUserForm() {
		return new UserForm();
	}
	@GetMapping("/regist")
	public String toRegist() {
		return "regist";
	}
	
	@PostMapping("/regist")
	public String toConfirm(@Validated @ModelAttribute UserForm uForm, BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "regist";
		}else {
		return "confirm";
	}
}
	}
	

