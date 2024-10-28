package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    // DI : dependency Injection
    // final : giúp biến userService đảm bảo tính bất biến , biến này chỉ được gán
    // giá trị 1 lần trong hàm tạo
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("eric", test);
        model.addAttribute("hoiDanIt", "from controller with model");
        // lấy ra tất cả user trong database
        List<User> temp = this.userService.getAllUsers() ;
        System.out.println( temp);
        return "hello";
    }

    // URL -> trang tạo mới 1 user
    @RequestMapping("/admin/user/create") // mac dinh laf method = get
    public String getUserPage(Model model) {
        System.out.println("run not here");
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    // URL -> show table user
    @RequestMapping("/admin/user") // mac dinh laf method = get
    public String getTableUserPage(Model model) {
        System.out.println("run here getTableUserPage ");
        // model.addAttribute("newUser", new User());
        return "/admin/user/table-user";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        /*
         * @ModelAttribute("newUser") User hoidanit : tạo 1 đối tượng hoidanit có kiểu
         * User vào model của springMVC có tên là newUser
         * cho phép ta sử dụng nó ở trong view với tên newUser
         */
        // System.out.println("run here " + hoidanit);
        User temp = this.userService.handleSaveUser(hoidanit);
        System.out.println(temp) ;
        return "hello";
    }

}
