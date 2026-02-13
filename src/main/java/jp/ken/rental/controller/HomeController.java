package jp.ken.rental.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.rental.application.service.CartService;
import jp.ken.rental.application.service.ProductSearchService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.form.CartForm;
import jp.ken.rental.form.ProductForm;
import jp.ken.rental.form.UserForm;

@Controller
public class HomeController {

    private final ProductSearchService productSearchService;
    private final CartService cartService;
    private final UserSearchService userSearchService;

    public HomeController(ProductSearchService productSearchService,
                          CartService cartService,
                          UserSearchService userSearchService) {
        this.productSearchService = productSearchService;
        this.cartService = cartService;
        this.userSearchService = userSearchService;
    }
    
    //Spring Securityでユーザーの情報を取得してます。
    private UserForm getCurrentUser() throws Exception {
    	//authenticationにユーザーの情報や権限が入ってます。ちなSpring Securityの機能
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //instanceofでauthentication.getPrincipal()がUserDetails型か調べている。
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
        	//ユーザー名(email)を取得し、returnでそのemailに合致する人を検索してformの情報を返す。
            String email = userDetails.getUsername();
            UserForm form = new UserForm();
            form.setEmail(email);
            return userSearchService.getUserByEmail(form);
        }
        return null;
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String productName, Model model) throws Exception {
        List<ProductForm> productList;
        if (productName == null || productName.trim().isEmpty()) {
            productList = productSearchService.getLatest5Products();
        } else {
            ProductForm form = new ProductForm();
            form.setProductName(productName.trim());
            productList = productSearchService.getProductList(form);
            model.addAttribute("headline", "検索結果");
            model.addAttribute("searchName", productName.trim());
        }
        model.addAttribute("productList", productList);

        UserForm user = getCurrentUser();
        if (user != null) {
            List<CartForm> cartList = cartService.getCart(Integer.parseInt(user.getUserId()));
            List<String> addedProductIds = cartList.stream()
            .map(CartForm::getProductId).map(String::valueOf).collect(Collectors.toList());
            model.addAttribute("addedProductIds", addedProductIds);
        }

        return "home";
    }
}
