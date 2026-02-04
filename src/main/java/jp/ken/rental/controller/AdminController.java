package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ken.rental.application.service.ProductInsertService;
import jp.ken.rental.form.ProductForm;

@Controller
@RequestMapping("/itemInsert")
public class AdminController {
	
	private ProductInsertService productInsertService;
	
	public AdminController(ProductInsertService productInsertService) {
		
		this.productInsertService = productInsertService;
	}
	
	@GetMapping
	public String itemInsert() {
		return "itemInsert";
	}
	@PostMapping(params = "back")
	public String itemConfirm() {
		return "itemConfirm";
	}
	@PostMapping(params = "forward")
	public String forComplete(@ModelAttribute ProductForm pform) throws Exception{
		
		int row = productInsertService.registItem(pform);
		
		if(row == 0) {
			return "itemInsert";
		}
		return "adminpage";
	}
	

}
