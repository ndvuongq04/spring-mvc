package vn.hoidanit.laptopshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;
import java.util.List;

// crud : create , read , update , delete 
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User eric); // luu nguoi dung

    Page<User> findAll(Pageable pageable); // lấy tất cả user trong database và phân trang

    List<User> findAllByEmail(String email); // lấy tất cả user trong database có email là ...

    User findOneById(long id); // lấy ra detail information user

    void deleteById(long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
