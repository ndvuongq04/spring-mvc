package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

    // DI : dependency injection
    private final UserRepository userRepository; // Đảm bảo tính bất biến ( final )

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "hello form Service";
    }

    // Lưu 1 user
    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }

    // lấy ra các user có email là ...
    public List<User> getAllByEmail(String email) {
        return this.userRepository.findAllByEmail(email);
    }

    // lấy ra các user có database
    public List<User> getAllUsers() {
        return this.userRepository.findAll() ;
    }

    // lấy ra thông tin chi tiết của người dùng
    public User getUserById(long id){
        return this.userRepository.findOneById(id) ;
    }

}
