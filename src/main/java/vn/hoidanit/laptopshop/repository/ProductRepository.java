package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product vuong); // lưu 1 sản phẩm

    List<Product> findAll(); // lấy tất cả các product

    void deleteById(long id);

    Product getById(long id);

}
