package jp.ken.rental.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class LoginController {



    @GetMapping("/login")
    public String LoginPage(@RequestParam Optional<String> error,Model model) {
    	
    	if(error.isPresent()) {
    		model.addAttribute("error","ユーザー名、またはパスワードが異なっています。");
    	}
        
        return "login";   
    }


}
