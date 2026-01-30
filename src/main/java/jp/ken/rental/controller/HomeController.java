package jp.ken.rental.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.form.ProductForm;


@Controller
public class HomeController {

    private final ProductSearchService productSearchService;

    public HomeController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/home")
    public String home(Model model) throws Exception {

        List<ProductForm> productList = productSearchService.getLatest5Products();
        model.addAttribute("productList", productList);
        model.addAttribute("productForm", new ProductForm()); 

        return "home";
    }

    @PostMapping("/home")
    public String search(@ModelAttribute ProductForm form, Model model) throws Exception {

        List<ProductForm> productList = productSearchService.getProductList(form);
        model.addAttribute("productList", productList);
        model.addAttribute("productForm", form);

        return "home";
    }
}
