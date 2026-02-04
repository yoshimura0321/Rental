package jp.ken.rental.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jp.ken.rental.application.service.UserDeleteService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.application.service.UserUpdateService;
import jp.ken.rental.common.validator.groups.ValidGroupOrder;
import jp.ken.rental.form.UserForm;

@Controller
@SessionAttributes({"cartList","idForm","userForm"})
public class MypageController {

    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;
    private UserSearchService userSearchService;

    public MypageController(UserUpdateService userUpdateService,
                            UserDeleteService userDeleteService,UserSearchService userSearchService) {
        this.userUpdateService = userUpdateService;
        this.userDeleteService = userDeleteService;
        this.userSearchService=userSearchService;
    }
    
    @ModelAttribute("idForm")
	public UserForm setupIdForm()throws Exception {
		String username=null;
		UserForm idForm = new UserForm();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   	 	if(authentication != null) {
   	 		Object principal = authentication.getPrincipal();
   	 		if(principal instanceof UserDetails) {
   	 			username =((UserDetails)authentication.getPrincipal()).getUsername();
   	 		
   	 			
   	 			idForm.setEmail(username);
   	 			idForm =userSearchService.getUserByEmail(idForm);
   	 			
   	 		}
   	 	}
   	 	return idForm;
	}

    @GetMapping("/mypage")
    public String mypage(@ModelAttribute("idForm") UserForm idForm, Model model) {

        model.addAttribute("userForm", idForm);
        return "mypage";
    }
    
    @GetMapping("/mypage/update")
    public String toupdate(@ModelAttribute("idForm") UserForm idForm, Model model) {
    	idForm.setBirth(idForm.getBirth().replace("-", "/"));
    	model.addAttribute("userForm", idForm);
    	return "profileChange";
    }
    
    @PostMapping("/mypage/update")
    public String toConfirm(@Validated(ValidGroupOrder.class) @ModelAttribute UserForm userForm, BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "profileChange";
		}else {
		return "profileConfirm";
		}
    }
    
    @GetMapping("/mypage/confirm")
    public String toConfirm() {
		return "profileconfirm";
	}
    
    @PostMapping("/mypage/confirm")
    public String updateConfirm(@ModelAttribute UserForm userForm)throws Exception {

    	int row = userUpdateService.updateUser(userForm)+userUpdateService.securityupdate(userForm);
		
		if(row < 3) {
			return "profileChange";
		}
        return "profileFinish";
    }

    
    @GetMapping("/mypage/delete")
    public String topre(){
        return "preGoodbye";
    }
    
    @PostMapping("/mypage/goodbye")
    public String delete(@ModelAttribute("idForm") UserForm idForm, Model model,HttpServletRequest request)throws Exception {
    	int num = userDeleteService.deleteUser(Integer.parseInt(idForm.getUserId()))+userDeleteService.securitydelete(Integer.parseInt(idForm.getUserId()));
    	if(num <3) {
    		model.addAttribute("error","削除に失敗しました");
    		return "error/error";
    	}else {
    		request.getSession().invalidate();
    		SecurityContextHolder.clearContext();
    	}
    	return "goodbye";
    }
    
}
