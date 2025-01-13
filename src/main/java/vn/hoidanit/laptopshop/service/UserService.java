package vn.hoidanit.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.domain.dto.RegisterDTO;
import vn.hoidanit.laptopshop.repository.OrderRepository;
import vn.hoidanit.laptopshop.repository.ProductRepository;
import vn.hoidanit.laptopshop.repository.RoleRepository;
import vn.hoidanit.laptopshop.repository.UserRepository;

@Service
public class UserService {

    // DI : dependency injection
    private final UserRepository userRepository; // Đảm bảo tính bất biến ( final )
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.roleRepository = roleRepository;
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
        return this.userRepository.findAll();
    }

    // lấy ra thông tin chi tiết của người dùng
    public User getUserById(long id) {
        return this.userRepository.findOneById(id);
    }

    // xóa 1 user
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
        ;
    }

    // lấy ra 1 đối tượng role
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDTOtoUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setFullName(registerDTO.getFirstName() + " " + registerDTO.getLastName());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(registerDTO.getPassword());
        return user;

    }

    public boolean checkEmailExist(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public long handleCountUsers() {
        return this.userRepository.count();
    }

    public long handleCountProducts() {
        return this.productRepository.count();
    }

    public long handleCountOrders() {
        return this.orderRepository.count();
    }
}
