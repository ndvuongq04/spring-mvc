package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.hoidanit.laptopshop.domain.Cart;
import vn.hoidanit.laptopshop.domain.CartDetail;
import vn.hoidanit.laptopshop.domain.Product;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    // ktra người dùng đã thêm sp này vào hay chưa
    CartDetail findCartDetailByCartAndProduct(Cart cart, Product product);

    // lấy ra tất cả sản phẩm của người dùng có id_cart này
    List<CartDetail> findCartDetailByCart(Cart cart);
}
