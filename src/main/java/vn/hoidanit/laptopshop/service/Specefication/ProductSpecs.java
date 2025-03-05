package vn.hoidanit.laptopshop.service.Specefication;

import java.util.List;

import javax.management.Query;

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

    // yêu cầu 1
    public static Specification<Product> minPrice(double min) {
        // greater than or equal
        return (root, query, criteriaBuilder) -> criteriaBuilder.ge(root.get(Product_.PRICE), min);
    }

    // yêu cầu 2
    public static Specification<Product> maxPrice(double max) {
        // less than or equal
        return (root, query, criteriaBuilder) -> criteriaBuilder.le(root.get(Product_.PRICE), max);
    }

    // yêu cầu 3
    public static Specification<Product> factoryEqual(String factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(Product_.FACTORY), factory);
    }

    // yêu cầu 4
    public static Specification<Product> factoryManyEqual(List<String> factory) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(Product_.FACTORY)).value(factory);
    }

    // yêu cầu 5
    public static Specification<Product> matchPrice(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.le(root.get(Product_.PRICE), max),
                criteriaBuilder.ge(root.get(Product_.PRICE), min));
    }

    // yêu cầu 6
    public static Specification<Product> priceManyBetween(double min, double max) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(Product_.PRICE), min, max);
    }
}
