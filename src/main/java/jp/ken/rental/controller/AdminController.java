package jp.ken.rental.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.rental.application.service.CartService;
import jp.ken.rental.application.service.ProductDeleteService;
import jp.ken.rental.application.service.ProductInsertService;
import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.application.service.ProductUpdateService;
import jp.ken.rental.application.service.UserDeleteService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.application.service.UserUpdateService;
import jp.ken.rental.form.CartForm;
import jp.ken.rental.form.ProductForm;
import jp.ken.rental.form.UserForm;

@Controller
public class AdminController {
    
    @ModelAttribute("productForm")
    public ProductForm setupProductForm() {
        return new ProductForm();
    }
    
    private final ProductInsertService productInsertService;
    private final ProductSearchService productSearchService;
    private final ProductDeleteService productDeleteService;
    private final ProductUpdateService productUpdateService;
    private UserSearchService userSearchService;
    private UserDeleteService userDeleteService;
    private UserUpdateService userUpdateService;
    private CartService cartService;
    
    public AdminController(ProductInsertService productInsertService,
    		ProductSearchService productSearchService,ProductDeleteService productDeleteService,
    		ProductUpdateService productUpdateService,UserSearchService userSearchService,
    		UserDeleteService userDeleteService,UserUpdateService userUpdateService,CartService cartService) {
    	
        this.productInsertService = productInsertService;
        this.productSearchService = productSearchService;
        this.productDeleteService = productDeleteService;
        this.productUpdateService = productUpdateService;
        this.userSearchService = userSearchService;
        this.userDeleteService=userDeleteService;
        this.userUpdateService=userUpdateService;
        this.cartService=cartService;
    }
    
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
    
    @GetMapping("/admin/product/new")
    public String itemInsert(ProductForm productform) {
        return "adminproduct";
    }

    @PostMapping(value = "/admin/product/new", params = "back")
    public String itemConfirm() {
        return "admin";
    }

    @PostMapping(value = "/admin/product/new", params = "forward")
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
    @GetMapping("/admin/product/list")
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
    	return "adminProductList";
    }
    
    @PostMapping("/admin/product/delete")
    public String deleteProduct(@RequestParam("productId") String productId,
                                RedirectAttributes redirectAttributes) throws Exception {

        int num = productDeleteService.deleteProduct(Integer.parseInt(productId));

        if (num == 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "削除に失敗しました");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "商品を削除しました");
        }

        return "redirect:/admin/product/list";
    }
    @GetMapping("/admin/product/update")
    public String toUpdate(@RequestParam("productId") int productId, Model model) throws Exception {

        ProductForm product = productSearchService.getProductById(productId);

        if (product == null) {
            model.addAttribute("errorMessage", "商品が見つかりませんでした");
            return "adminProductlist";
        }

        model.addAttribute("productForm", product);

        return "adminProductUpdate"; 
    }

    
    @PostMapping("/admin/product/update")
    public String updateConfirm(
            @ModelAttribute ProductForm productForm,
            RedirectAttributes redirectAttributes) throws Exception {

        int row = productUpdateService.updateitem(productForm);

        if (row < 1) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新に失敗しました");
            return "redirect:/admin/Productlist";
        }

        redirectAttributes.addFlashAttribute("successMessage", "商品を更新しました");
        return "redirect:/admin/product/list";
    }

    @GetMapping("/admin/user/list")
    public String toadaminuser(@RequestParam(required = false)String email,Model model)throws Exception {
    	List<UserForm> formlist = new ArrayList<UserForm>();
    	if(email ==null || email.isBlank()) {
    		formlist = userSearchService.getAllUser();
    	}else {
    		UserForm form = new UserForm();
    		form.setEmail(email);
    		form = userSearchService.getUserByEmail(form);
    		formlist.add(form);
    	}
    	
    	model.addAttribute("userList",formlist);
    	
    	return "adminUserlist";
    }
    
    @PostMapping("/admin/user/delete")
    public String admindelete(@RequestParam String userId,RedirectAttributes ra)throws Exception {
    	int num = userDeleteService.deleteUser(Integer.parseInt(userId));
    	if(num == 0) {
			ra.addFlashAttribute("message", "削除に失敗しました");
		}else {
			ra.addFlashAttribute("message", "削除成功しました");
		}
    	
    	return "redirect:/admin/user/list";
    }
    
    @GetMapping("/admin/user/update")
    public String touserupdate(@RequestParam String email, Model model,RedirectAttributes ra)throws Exception {
    	UserForm form = new UserForm();
		form.setEmail(email);
    	form = userSearchService.getUserByEmail(form);
    	
    	model.addAttribute("userForm",form);
    	
    	return "adminUserUpdate";
    }
    
    @PostMapping("/admin/user/update")
    public String userupdate(@ModelAttribute UserForm userForm,RedirectAttributes ra)throws Exception{
    	int num = userUpdateService.updateUser(userForm);
    	if(num == 0) {
			ra.addFlashAttribute("message", "更新に失敗しました");
		}else {
			ra.addFlashAttribute("message", "更新成功しました");
		}
    	
    	return "redirect:/admin/user/list";
    }
    
    @GetMapping("/admin/rental")
    public String toRental(Model model)throws Exception {
    	List<ProductForm> list = productSearchService.getAdminProduct();
    	model.addAttribute("productlist",list);
    	return "adminCartList";
    }
    
    @GetMapping("/admin/rental/service")
    public String torentalservice(@RequestParam("productId") String productId,@RequestParam("productName") String productName, Model model)throws Exception{
    	List<CartForm> list = cartService.adminrental(Integer.parseInt(productId));
    	model.addAttribute("cartlist",list);
    	model.addAttribute("productName",productName);
    	System.out.println(list);
    	
    	return "adminRental";
    }
    
    @PostMapping("/admin/rental/service")
    public String rental(@RequestParam("userId") String userId,@RequestParam("productId") String productId, RedirectAttributes ra)throws Exception{
    	
    	if(productSearchService.checkrental(Integer.parseInt(productId))) {
    		int num = cartService.rental(Integer.parseInt(userId),Integer.parseInt(productId))+userUpdateService.rentaluser(Integer.parseInt(userId));
    		if(num<2) {
    			ra.addFlashAttribute("message","レンタル処理失敗しました");
    		}else {
    			ra.addFlashAttribute("message","レンタル処理成功しました");
    		}
    	}else {
    		ra.addFlashAttribute("message","在庫が足りません");
    	}
    	return "redirect:/admin/rental";
    }
}
