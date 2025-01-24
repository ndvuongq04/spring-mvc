package vn.hoidanit.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Order;
import vn.hoidanit.laptopshop.domain.OrderDetail;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.CartDetailRepository;
import vn.hoidanit.laptopshop.repository.CartRepository;
import vn.hoidanit.laptopshop.repository.OrderDetailRepository;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    // DI
    public ProductService(ProductRepository productRepository,
            CartDetailRepository cartDetailRepository,
            CartRepository cartRepository,
            UserService userService,
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public Product handleSaveProduct(Product vuong) {
        return this.productRepository.save(vuong);
    }

    public Page<Product> getAllProduct(Pageable page) {
        return this.productRepository.findAll(page);
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

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {

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
                    cartDetail.setQuantity(quantity);
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
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + quantity);
                    this.cartDetailRepository.save(oldCartDetail);
                }

            }

        }

    }

    public Cart fetchCartByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public Optional<Cart> getCartByUser(User user) {
        return this.cartRepository.findCartByUser(user);
    }

    public void handleDeleteCartDetail(HttpSession session, long cartDetailId) {
        Optional<CartDetail> opCartDetail = this.cartDetailRepository.findById(cartDetailId);
        if (!opCartDetail.isEmpty()) {
            CartDetail cartDetail = opCartDetail.get();
            // xóa cartDetail
            this.cartDetailRepository.deleteById(cartDetail.getId());

            // lay cart
            Cart cart = cartDetail.getCart();

            if (cart.getSum() > 1) {
                long sum = cart.getSum() - 1;
                cart.setSum(sum);
                this.cartRepository.save(cart);
                session.setAttribute("sum", sum);
            } else {
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0);
            }

        }
    }

    public void handleUpdateCartDetailBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> opCartDetail = this.cartDetailRepository.findById(cartDetail.getId());

            if (opCartDetail.isPresent()) { // ktra opCartDetail có giá trị hay không
                CartDetail currentCartDetail = opCartDetail.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(
            Order order, User user,
            HttpSession session) {

        // 01 : create order
        order.setUser(user);

        // 02 : create orderDetail
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            long sum = 0;

            for (CartDetail cd : cartDetails) {
                sum += cd.getPrice() * cd.getQuantity();
            }
            order.setTotalPrice(sum); // set TotalPrice
            order.setStatus("PENDING");
            order = this.orderRepository.save(order); // order

            for (CartDetail cd : cartDetails) {
                OrderDetail od = new OrderDetail();
                od.setOrder(order);
                od.setPrice(cd.getPrice());
                od.setProduct(cd.getProduct());
                od.setQuantity(cd.getQuantity());

                this.orderDetailRepository.save(od); // save order detail
            }

            // 03 : delete cart and cartDetail
            for (CartDetail cd : cartDetails) {
                this.cartDetailRepository.deleteById(cd.getId());
            }

            this.cartRepository.deleteById(cart.getId());

            // 04 : update sum cart
            session.setAttribute("sum", 0);

        }

    }
}
