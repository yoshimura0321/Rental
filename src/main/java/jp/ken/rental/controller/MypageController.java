package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jp.ken.rental.application.service.UserDeleteService;
import jp.ken.rental.application.service.UserUpdateService;
import jp.ken.rental.form.UserEntity;

@Controller
public class MypageController {
	
	private UserUpdateService userUpdateService;
	private UserDeleteService userDeleteService;

	public MypageController(UserUpdateService userUpdateService, 
							UserDeleteService userDeleteService) {
		this.userUpdateService = userUpdateService;
		this.userDeleteService = userDeleteService;	
	}
	
	//ログインしてないとマイページに行けないようにしています。
	//多分loginControllerのPostでセッション管理する必要がある？
	public String mypage(HttpSession session, Model model) {
		
		UserEntity loginUser = (UserEntity)
		session.getAttribute("loginUser");
			if (loginUser == null) {
				return "redirect:/login";
			}
		model.addAttribute("user", loginUser);
		return "mypage"; 
	}
	@GetMapping("/mypage")
	public String updateForm(HttpSession session, Model model) {
		UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
		if (loginUser == null){
			return "redirect:/login";
		}
		model.addAttribute("userForm", loginUser);
		
		return "mypage";
		}
	
	@PostMapping("/profileConfirm") 
	public String updateConfirm(UserEntity form, Model model) {
		
		model.addAttribute("userForm", form);
		
		return "mypage/profileConfirm";
	}
	
	@PostMapping("/profileFinish")
	public String updateComplete(UserEntity form, HttpSession session) throws Exception {
		
		userUpdateService.updateUser(form);
		session.setAttribute("loginUser", form);
		
		return "mypage/profileFinish";
	}
}
