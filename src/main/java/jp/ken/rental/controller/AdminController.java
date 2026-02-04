package jp.ken.rental.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.rental.application.service.ProductDeleteService;
import jp.ken.rental.application.service.ProductInsertService;
import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.form.ProductForm;

@Controller
public class AdminController {
    
    @ModelAttribute("productForm")
    public ProductForm setupProductForm() {
        return new ProductForm();
    }
    
    private final ProductInsertService productInsertService;
    private final ProductSearchService productSearchService;
    private final ProductDeleteService productDeleteService;
    
    public AdminController(ProductInsertService productInsertService,
    		ProductSearchService productSearchService,ProductDeleteService productDeleteService) {
        this.productInsertService = productInsertService;
        this.productSearchService = productSearchService;
        this.productDeleteService = productDeleteService;
    }
    
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
    
    @GetMapping("/admin/Product")
    public String itemInsert(ProductForm productform) {
        return "admin/Product";
    }

    @PostMapping(value = "/admin/Product", params = "back")
    public String itemConfirm() {
        return "admin";
    }

    @PostMapping(value = "/admin/Product", params = "forward")
    public String forComplete(
            @ModelAttribute ProductForm productform,
            RedirectAttributes redirectAttributes) throws Exception {

        int row = productInsertService.registItem(productform);

        if (row == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "登録に失敗しました。もう一度お試しください。");
            return "redirect:/admin/Product";
        }

        redirectAttributes.addFlashAttribute("successMessage", "商品を登録しました！");
        return "redirect:/admin";
    }
    @GetMapping("/admin/Productlist")
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
    	return "admin/Productlist";
    }
    
    @PostMapping("/adminProduct/delete")
    public String deleteProduct(@RequestParam("productId") String productId,
                                RedirectAttributes redirectAttributes) throws Exception {

        int num = productDeleteService.deleteProduct(Integer.parseInt(productId));

        if (num == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "商品を削除しました");
        }

        return "redirect:/admin/Productlist";
    }

}
