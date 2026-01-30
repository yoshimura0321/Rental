package jp.ken.rental.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.form.ProductForm;
@Controller
public class HomeController {
	private ProductSearchService productSearchService;
	
	public HomeController(ProductSearchService productSearchService) {
		
		this.productSearchService = productSearchService;
	}

	@GetMapping("/home")
	public String toProductSearch(Model model) {
		
		ProductForm productForm = new ProductForm();
		model.addAttribute("productForm", productForm);
		
		return "home";
	}
	
	
	
	@PostMapping("/home")
	public String searchProducts(@ModelAttribute ProductForm productForm,
			BindingResult result, Model model)throws Exception{
		
		model.addAttribute("headline" , "HOME");
		
		if(result.hasErrors()) {
			return "home";
		}
		
		List<ProductForm> formList = productSearchService.getProductList(productForm);
		
		model.addAttribute("productList", formList);
		
		return "home";
	}
}
