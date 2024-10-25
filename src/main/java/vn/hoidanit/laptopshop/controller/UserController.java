package vn.hoidanit.laptopshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    // DI : dependency Injection
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("eric", test);
        model.addAttribute("hoiDanIt", "from controller with model");
        return "hello";
    }

    @RequestMapping("/admin/user") // mac dinh laf method = get
    public String getUserPage(Model model) {
        System.out.println("run not here");
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create1", method = RequestMethod.POST)
    public String createUserPage(Model model, @ModelAttribute("newUser") User hoidanit) {
        /*
         * @ModelAttribute("newUser") User hoidanit : tạo 1 đối tượng hoidanit có kiểu
         * User vào model của springMVC có tên là newUser
         * cho phép ta sử dụng nó ở trong view với tên newUser
         */
        System.out.println("run here" + hoidanit);
        return "hello";
    }

}
