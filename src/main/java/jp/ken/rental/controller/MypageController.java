package jp.ken.rental.controller;

import java.util.List;

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
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpServletRequest;
import jp.ken.rental.application.service.CartService;
import jp.ken.rental.application.service.UserDeleteService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.application.service.UserUpdateService;
import jp.ken.rental.common.validator.groups.ValidGroupOrder;
import jp.ken.rental.form.CartEntity;
import jp.ken.rental.form.UserForm;

@Controller
@SessionAttributes({"idForm","userForm"})
public class MypageController {

    private final UserUpdateService userUpdateService;
    private final UserDeleteService userDeleteService;
    private UserSearchService userSearchService;
    private final CartService cartService;
    
    
    public MypageController(UserUpdateService userUpdateService,
                            UserDeleteService userDeleteService,
                            UserSearchService userSearchService,
                            CartService cartService) {
        this.userUpdateService = userUpdateService;
        this.userDeleteService = userDeleteService;
        this.userSearchService = userSearchService;
        this.cartService = cartService; 
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
    @ModelAttribute("userForm")
    public UserForm setupUserForm() {
    	return new UserForm();
    }

    @GetMapping("/mypage")
    public String mypage(@ModelAttribute("idForm") UserForm idForm,@ModelAttribute("userForm")UserForm userForm, Model model)throws Exception {
    	userForm.setUserId(idForm.getUserId());
    	userForm.setUserName(idForm.getUserName());
    	userForm.setEmail(idForm.getEmail());
    	userForm.setTel(idForm.getTel());
    	userForm.setPassword(idForm.getPassword());
    	userForm.setBirth(idForm.getBirth());
    	userForm.setAddress(idForm.getAddress());
    	userForm.setCredit(idForm.getCredit());
    	userForm.setPlanName(idForm.getPlanName());
    	userForm.setMembershipMonth(idForm.getMembershipMonth());
    	userForm.setBirth(userForm.getBirth().replace("-", "/"));
    	model.addAttribute("userForm", userForm);
    	
    	
    	 List<CartEntity> currentRentals = cartService.getCurrentRentals(Integer.parseInt(idForm.getUserId()));
    	 List<CartEntity> rentalHistory = cartService.getRentalHistory(Integer.parseInt(idForm.getUserId()));
    	 
    	 model.addAttribute("currentRentals", currentRentals);
    	 model.addAttribute("rentalHistory", rentalHistory);
    	 
    	 return "mypage";

    }
    
    @GetMapping("/mypage/update")
    public String toupdate() {
    	return "profileChange";
    }
    
    @PostMapping("/mypage/update")
    public String toConfirm(@Validated(ValidGroupOrder.class) @ModelAttribute("userForm") UserForm userForm, BindingResult result,Model model) {
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
		
		if(row < 2) {
			return "profileChange";
		}
        return "profileFinish";
    }

    
    @GetMapping("/mypage/delete")
    public String topre(){
        return "preGoodbye";
    }
    
    @PostMapping("/mypage/goodbye")
    public String delete(@ModelAttribute("idForm") UserForm idForm, Model model,HttpServletRequest request,SessionStatus status)throws Exception {
    	
    	List<CartEntity> list = cartService.getCurrentRentals(Integer.parseInt(idForm.getUserId()) );
    	
    	if(list.size()!=0) {
    		model.addAttribute("error","レンタル中に退会はできません");
    		return "error/error";
    	}
    	int num = userDeleteService.deleteUser(Integer.parseInt(idForm.getUserId()))+userDeleteService.securitydelete(Integer.parseInt(idForm.getUserId()));
    	if(num <2) {
    		model.addAttribute("error","削除に失敗しました");
    		return "error/error";
    	}else {
    		status.setComplete();
    		request.getSession().invalidate();
    		SecurityContextHolder.clearContext();
    	}
    	return "goodbye";
    }
    
}
