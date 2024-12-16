package vn.hoidanit.laptopshop.controller.client;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {
    // DI
    private final ProductService productService;
    private final UserService userService;

    public ItemController(ProductService productService,
            UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/product/{id}")
    public String getProductPage(Model model, @PathVariable long id) {
        Product prOfId = this.productService.fetchProductById(id).get(); // .get() để lấy ra đối tượng product
        model.addAttribute("prOfId", prOfId);
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        // lưu sản phẩm
        this.productService.handleAddProductToCart(email, productId, session);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // id user
        long id = (long) session.getAttribute("id");
        User user = new User();
        user.setId(id);

        // lấy id_cart thông qua user
        // cart chưa có sản phẩm nào -> chuyển đến trang tbao lỗi
        if (this.productService.getCartByUser(user).isEmpty()) {
            return "redirect:/error-cart";
        }
        Cart cart = this.productService.getCartByUser(user).get();
        // lấy cartDetail thông qua cart
        List<CartDetail> cartDetails = cart.getCartDetail();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getQuantity() + cd.getPrice();
        }

        // truyền data qua view
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        return "client/cart/show";
    }

    @GetMapping("/error-cart")
    public String getPageErrorCart(Model model) {
        return "client/cart/errorCartNull";
    }

}
