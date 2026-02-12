package jp.ken.rental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.form.ProductForm;


@Controller
@SessionAttributes("addedProductIds")
public class HomeController {

    private final ProductSearchService productSearchService;

    public HomeController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }
    
    @ModelAttribute("addedProductIds")
    public List<String> setupAddedProductIds() {
        return new ArrayList<>();
    }


    
    @GetMapping("/home")
    public String home(@RequestParam(required=false) String productName, Model model) throws Exception {
    	List<ProductForm> productList=null;
    	if(productName==null) {
    		productList = productSearchService.getLatest5Products();
    	}else {
    		String name = productName.trim();
    		if(name == null || name.isEmpty()) {
    			productList = productSearchService.getLatest5Products();
    		}else {
    			// ProductForm にセット
    			ProductForm form = new ProductForm();
    			form.setProductName(name);
    			// Service に渡す
    			productList = productSearchService.getProductList(form);    			
    			model.addAttribute("headline", "検索結果");
    			model.addAttribute("searchName",name);
            
    		}
    	}
    	model.addAttribute("productList", productList);
    	return "home";
    }
    

}
