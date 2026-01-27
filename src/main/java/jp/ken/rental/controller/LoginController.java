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
@SessionAttributes("UserForm")
public class LoginController {
	
	@ModelAttribute("UserForm")
	public UserForm setupUserForm() {
		return new UserForm();
	}
	@GetMapping
	public String toLogin() {
		return "login";
	}
	
	@PostMapping
	public String toRegist(@Validated @ModelAttribute UserForm userForm,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "login";
		}else {
			return "home";
		}
		
	}

}
