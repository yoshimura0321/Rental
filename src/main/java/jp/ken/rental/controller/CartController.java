package jp.ken.rental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.rental.application.service.CartService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.form.CartForm;
import jp.ken.rental.form.UserForm;

@Controller
@SessionAttributes("cartList")
public class CartController {
	
	CartService cartService;
	UserSearchService userSearchService;
	
	public CartController(CartService cartService,UserSearchService userSearchService) {
		this.cartService=cartService;
		this.userSearchService=userSearchService;
	}
	
	@ModelAttribute("cartList")
	public List<CartForm> setupCartList(){
		return new ArrayList<CartForm>();
	}
	
	@GetMapping("/cart")
	public String toCart(Model model) throws Exception{
		String username=null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
   	 	if(authentication != null) {
   	 		Object principal = authentication.getPrincipal();
   	 		if(principal instanceof UserDetails) {
   	 			username =((UserDetails)authentication.getPrincipal()).getUsername();
   	 		
   	 			UserForm form =new UserForm();
   	 			form.setEmail(username);
   	 			String a =userSearchService.getUserByEmail(form).getUserId();
   	 			List<CartForm> list = cartService.getCart(Integer.parseInt(a));
   	 			model.addAttribute("cartList",list);
   	 		}
   	 		
   	 	}
		return "cart";
	}
	
	@PostMapping("/cart")
	public String update() {
		return "cart";
	}
	
}
