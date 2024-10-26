package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

    // DI : dependency injection
    private final UserRepository userRepository; // Đảm bảo tính bất biến  ( final )

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
}
