package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"UserForm"})
public class RegistController {

	/*
	@ModelAttribute("UserForm")
	public UserForm setupUserForm() {
		return new UserForm();
	}
	*/
	@GetMapping("/regist")
	public String toRegist() {
		return "regist";
	}
	/*
	@PostMapping("/regist")
	public String toConfirm(@Validated @ModelAttribute UserForm uForm, BindingResult result) {
		if(result.hasErrors()) {
			return "regist";
		}else {
		return "confirm";
	}
	*/
}
