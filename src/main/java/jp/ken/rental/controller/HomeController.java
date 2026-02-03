package jp.ken.rental.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.form.ProductForm;


@Controller
public class HomeController {

    private final ProductSearchService productSearchService;

    public HomeController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }
    
    @GetMapping("/home")
    public String home(@RequestParam Optional<String> productName, Model model) throws Exception {
    	
    	if(productName == null || productName.toString().isBlank()) {
    		List<ProductForm> productList = productSearchService.getLatest5Products();
            model.addAttribute("productList", productList);

            return "home";
    	}else {
    		// ProductForm にセット
            ProductForm form = new ProductForm();
            form.setProductName(productName.toString());

            // Service に渡す
            List<ProductForm> productList = productSearchService.getProductList(form);

            model.addAttribute("productList", productList);
            model.addAttribute("headline", "検索結果");

            return "home";
    	}
    }
/*
    // ★ GET：最新5件を表示
    @GetMapping("/home")
    public String home(Model model) throws Exception {

        List<ProductForm> productList = productSearchService.getLatest5Products();
        model.addAttribute("productList", productList);

        return "home";
    }

    // ★ POST：名前検索
    @PostMapping("/home")
    public String search(@RequestParam String productName, Model model) throws Exception {

        // ProductForm にセット
        ProductForm form = new ProductForm();
        form.setProductName(productName);

        // Service に渡す
        List<ProductForm> productList = productSearchService.getProductList(form);

        model.addAttribute("productList", productList);
        model.addAttribute("headline", "検索結果");

        return "home";
    }*/
}
