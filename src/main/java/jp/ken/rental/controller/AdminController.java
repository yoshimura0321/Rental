package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.ken.rental.application.service.ProductInsertService;
import jp.ken.rental.form.ProductForm;

@Controller
@RequestMapping("/adminProduct")
public class AdminController {
	
	@ModelAttribute("productForm")
	public ProductForm setupProductForm() {
		return new ProductForm();
	}
	
	private ProductInsertService productInsertService;
	
	public AdminController(ProductInsertService productInsertService) {
		
		this.productInsertService = productInsertService;
	}
	
	@GetMapping
	public String itemInsert(ProductForm productform) {
		return "adminProduct";
	}
	@PostMapping(params = "back")
	public String itemConfirm() {
		return "admin";
	}
	@PostMapping(params = "forward")
	public String forComplete(@ModelAttribute ProductForm productform) throws Exception{
		
		int row = productInsertService.registItem(productform);
		
		if(row == 0) {
			return "adminProduct";
		}
		return "admin";
	}
	

}
