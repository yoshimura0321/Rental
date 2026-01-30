package jp.ken.rental.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.SessionAttributes;
//
//import jp.ken.rental.application.service.UserSearchService;
//import jp.ken.rental.form.UserForm;

@Controller
//@SessionAttributes("UserForm")
public class LoginController {

//    private UserSearchService userSearchService;
//
//    public LoginController(UserSearchService userSearchService) {
//        this.userSearchService = userSearchService;
//    }

    @GetMapping("/login")
    public String LoginPage(@RequestParam Optional<String> error,Model model) {
    	
    	if(error.isPresent()) {
    		model.addAttribute("error","ユーザー名、またはパスワードが異なっています。");
    	}
        //model.addAttribute("UserForm", new UserForm());
        return "login";   
    }

//    @PostMapping("/login")
//    public String login(
//            @ModelAttribute("UserForm") UserForm form,
//            Model model) throws Exception {
//
//
//        UserForm user = userSearchService.getUserByEmail(form);
//
//        if (user == null) {
//            model.addAttribute("message", "メールアドレスまたはパスワードが違います");
//            return "login";
//        }
//
//        if (!user.getPassword().equals(form.getPassword())) {
//            model.addAttribute("message", "メールアドレスまたはパスワードが違います");
//            return "login";
//        }
//        
//        model.addAttribute("loginUser", user);
//        return "home";
//    }
}
