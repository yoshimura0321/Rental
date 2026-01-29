package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.rental.common.validator.groups.ValidGroupOrder;
import jp.ken.rental.form.UserForm;

@Controller
@SessionAttributes({"userForm"})
public class RegistController {

	
	@ModelAttribute("userForm")
	public UserForm setupUserForm() {
		return new UserForm();
	}
	@GetMapping("/regist")
	public String toRegist() {
		return "userRegist";
	}
	
	@PostMapping("/regist")
	public String toConfirm(@Validated(ValidGroupOrder.class) @ModelAttribute UserForm userForm, BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "userRegist";
		}else {
		return "userConfirm";
	}
}
	}
	