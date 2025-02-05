package vn.hoidanit.laptopshop.service.Specefication;

import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.domain.Product_;

public class ProductSpecs {
    // tạo ( mô tả logic) câu truy vấn dưới database -> trả về logic của câu truy
    // vấn ; không thực thi câu lệnh >< findAll trong
    // ProductRepository sẽ thực thi câu lệnh này
    public static Specification<Product> nameLike(String name) {
        // ghi đè function toPredicate của interface Specification
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(Product_.NAME), "%" + name + "%");
    }
}
