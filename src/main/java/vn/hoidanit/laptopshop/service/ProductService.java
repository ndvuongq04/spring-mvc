package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

    public void handleAddProductToCart(String email, long productId) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // ktra user co cart hay chua
            Cart cart = this.cartRepository.findCartByUser(user);

            if (cart == null) {
                // create cart
                Cart newCart = new Cart();
                newCart.setSum(1);
                newCart.setUser(user);

                // save cart
                cart = this.cartRepository.save(newCart);
            }

            // cart luon co gia tri

            // save cart_detail

            Optional<Product> prdOptional = this.productRepository.findById(productId);
            if (prdOptional.isPresent()) {
                Product product = prdOptional.get(); // lay doi tuong product tu optional

                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(product);
                cartDetail.setQuantity(1);
                cartDetail.setPrice(product.getPrice());

                this.cartDetailRepository.save(cartDetail);

            }

        }

    }
}
