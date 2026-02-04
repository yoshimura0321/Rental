package jp.ken.rental.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.rental.application.service.ProductInsertService;
import jp.ken.rental.form.ProductForm;

@Controller
public class AdminController {
    
    @ModelAttribute("productForm")
    public ProductForm setupProductForm() {
        return new ProductForm();
    }
    
    private final ProductInsertService productInsertService;
    
    public AdminController(ProductInsertService productInsertService) {
        this.productInsertService = productInsertService;
    }
    
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
    
    @GetMapping("/adminProduct")
    public String itemInsert(ProductForm productform) {
        return "adminProduct";
    }

    @PostMapping(value = "/adminProduct", params = "back")
    public String itemConfirm() {
        return "admin";
    }

    @PostMapping(value = "/adminProduct", params = "forward")
    public String forComplete(
            @ModelAttribute ProductForm productform,
            RedirectAttributes redirectAttributes) throws Exception {

        int row = productInsertService.registItem(productform);

        if (row == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "登録に失敗しました。もう一度お試しください。");
            return "redirect:/adminProduct";
        }

        redirectAttributes.addFlashAttribute("successMessage", "商品を登録しました！");
        return "redirect:/admin";
    }
    @GetMapping("/adminProductlist")
    public String adminproductlistPage() {
        return "adminProductlist";
    }
}
