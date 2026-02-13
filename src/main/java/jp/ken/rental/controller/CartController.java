package jp.ken.rental.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.rental.application.service.CartService;
import jp.ken.rental.application.service.UserSearchService;
import jp.ken.rental.form.CartForm;
import jp.ken.rental.form.UserForm;

@Controller
public class CartController {

    private final CartService cartService;
    private final UserSearchService userSearchService;

    public CartController(CartService cartService, UserSearchService userSearchService) {
        this.cartService = cartService;
        this.userSearchService = userSearchService;
    }

    private UserForm getCurrentUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            String email = userDetails.getUsername();
            UserForm form = new UserForm();
            form.setEmail(email);
            return userSearchService.getUserByEmail(form);
        }
        return null;
    }

    @GetMapping("/cart")
    public String toCart(Model model) throws Exception {
        UserForm user = getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }

        List<CartForm> cartList = cartService.getCart(Integer.parseInt(user.getUserId()));
        model.addAttribute("cartList", cartList);

        // 追加済み商品IDのリストを作成してViewに渡す
        List<String> addedProductIds = cartList.stream()
                .map(CartForm::getProductId)
                .map(String::valueOf)
                .collect(Collectors.toList());
        model.addAttribute("addedProductIds", addedProductIds);

        return "cart";
    }

    @PostMapping("/cart")
    public String update(@RequestParam String productId, Model model) throws Exception {
        UserForm user = getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }

        int num = cartService.deleteCart(Integer.parseInt(user.getUserId()), Integer.parseInt(productId));
        if (num == 0) {
            model.addAttribute("error", "削除に失敗しました");
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/add")
    public String add(@RequestParam String productId,
                      @RequestParam String searchName,
                      RedirectAttributes ra) throws Exception {
        UserForm user = getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }

        int num = cartService.addCart(Integer.parseInt(user.getUserId()), Integer.parseInt(productId));
        if (num == 0) {
            ra.addFlashAttribute("errorMessage", "追加に失敗しました");
        } else {
            ra.addFlashAttribute("successMessage", "商品を追加しました");
        }

        ra.addAttribute("productName", searchName);
        return "redirect:/home";
    }
}
