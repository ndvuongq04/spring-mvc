package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    // DI
    public ProductService(ProductRepository productRepository,
            CartDetailRepository cartDetailRepository,
            CartRepository cartRepository,
            UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Product handleSaveProduct(Product vuong) {
        return this.productRepository.save(vuong);
    }

    public List<Product> getAllProduct() {
        return this.productRepository.findAll();
    }

    public void handleDeleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public Product getProductById(long id) {
        return this.productRepository.getById(id);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {

        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // ktra user co cart hay chua
            Optional<Cart> opCart = this.cartRepository.findCartByUser(user);
            Cart cart = new Cart();
            if (opCart.isEmpty()) {

                // create cart
                Cart newCart = new Cart();
                newCart.setSum(0);
                newCart.setUser(user);

                // save cart
                cart = this.cartRepository.save(newCart);
            } else {
                cart = opCart.get();

            }
            // cart luon co gia tri

            // save cart_detail

            Optional<Product> prdOptional = this.productRepository.findById(productId);
            if (prdOptional.isPresent()) {
                Product product = prdOptional.get(); // lay doi tuong product tu optional

                /*
                 * ktra xem product có card_id đã tồn tại trong database chưa
                 * -> nếu rồi -> người dùng đã thêm sp này vào rồi -> chỉ cần tăng quantity lên
                 * nữa là OK
                 * -> nếu chưa -> thực hiện tạo mới cart_detail thôi
                 */
                CartDetail oldCartDetail = this.cartDetailRepository.findCartDetailByCartAndProduct(cart, product);
                if (oldCartDetail == null) {

                    // chưa có
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(product);
                    cartDetail.setQuantity(1);
                    cartDetail.setPrice(product.getPrice());

                    // cập nhật cart (sum)
                    long sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);

                    // lưu cartDetail
                    this.cartDetailRepository.save(cartDetail);

                } else {

                    // có rồi
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldCartDetail);
                }

            }

        }

    }

    public Optional<Cart> getCartByUser(User user) {
        return this.cartRepository.findCartByUser(user);
    }
}
