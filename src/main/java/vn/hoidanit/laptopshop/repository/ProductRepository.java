package vn.hoidanit.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Product save(Product vuong); // lưu 1 sản phẩm

    Page<Product> findAll(Pageable pageable); // lấy tất cả các product và phân trang

    Page<Product> findAll(Specification<Product> spec, Pageable pageable); // lấy tất cả các product theo điều kiện và
                                                                           // phân trang

    void deleteById(long id);

    Product getById(long id);

}
