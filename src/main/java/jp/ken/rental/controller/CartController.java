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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import jp.ken.rental.application.service.CartService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.form.CartForm;
import jp.ken.rental.form.UserForm;

@Controller
@SessionAttributes({"cartList","idForm"})
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
   	 			String a =userSearchService.getUserByEmail(idForm).getUserId();
   	 			idForm.setUserId(a);
   	 		}
   	 	}
   	 	return idForm;
	}
	
	@GetMapping("/cart")
	public String toCart(@ModelAttribute("idForm") UserForm idForm, HttpSession session, Model model) throws Exception{
		/*String username=null;
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
   	 		
   	 	}*/
		String id =idForm.getUserId();
		List<CartForm> list = cartService.getCart(Integer.parseInt(id));
		model.addAttribute("cartList",list);
		return "cart";
	}
	
	@PostMapping("/cart")
	public String update(@ModelAttribute("idForm") UserForm idForm,@RequestParam String productId, Model model) throws Exception{
		String id =idForm.getUserId();
		int num = cartService.deleteCart(Integer.parseInt(id), Integer.parseInt(productId));
		if(num == 0) {
			model.addAttribute("error","削除に失敗しました");
		}
		return "redirect:/cart";
	}
	
}
