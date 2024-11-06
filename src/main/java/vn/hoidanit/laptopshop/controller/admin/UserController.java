package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.laptopshop.domain.User;
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
        return "hello";
    }

    // URL -> trang tạo mới 1 user
    @RequestMapping("/admin/user/create") // mac dinh laf method = get
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "/admin/user/create";
    }
    // URL nhận data của form creat
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
        /*
         * Sau khi tạo 1 user -> chuyển về trang /admin/user/table-user
         * -> vì chỉ chuyển đến trang -> chưa lấy được dữ liệu để chuyển đên đến table-user 
         * -> thay vì chuyển đến /admin/user/table-user thì chúng ra chuyển đến /admin/user để nó lấy 
         * dữ liệu và chuyển đến table-user và hiển thị lên 
         * -> cần dùng thêm câu lệnh redirect 
        */
        return "redirect:/admin/user";
    }

    // URL -> show table user
    @RequestMapping("/admin/user")
    public String getTableUserPage(Model model) {
        List<User> users = this.userService.getAllUsers() ;
        // Lưu data và spring rồi chuyển đến view
        model.addAttribute("users1", users) ;
        return "/admin/user/show";
    }

    // URL -> view user
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model , @PathVariable long id) {
    /*
     * Lấy tham số động
     * @PathVariable long id -> mục đích là để cho URL động , thay đổi theo id của user
     *
    */
        User user = this.userService.getUserById(id) ;
        model.addAttribute("user", user) ;
        return "/admin/user/detail";
    }

    // URL -> update user
    @RequestMapping("/admin/user/update/{id}") // mac dinh laf method = get
    public String getUpdatePage(Model model ,@PathVariable long id ) {
        System.out.println("run here getUpdatePage ");
        User currentUser  = this .userService.getUserById(id) ;
        model.addAttribute("updateUser", currentUser);
        
        return "/admin/user/update";
    }
    // URL -> nhận data update user
    @PostMapping(value = "/admin/user/update")
    public String updateUserPage(Model model, @ModelAttribute("updateUser") User userFormData) {
        // userFormData : chỉ có các thông tin : fullName , address ,phone , id , còn lại là null
        User currentUser  = this .userService.getUserById(userFormData.getId()) ;
        if(currentUser != null){
            // set lại các giá trị cần update cho currentUser(!password,!email)
            currentUser.setFullName(userFormData.getFullName()) ;
            currentUser.setAddress(userFormData.getAddress()) ;
            currentUser.setPhone(userFormData.getPhone()) ;
        }
        User temp = this.userService.handleSaveUser(currentUser);
        System.out.println(temp) ;
        
        return "redirect:/admin/user";
    }

    // URL -> update user
    @GetMapping("/admin/user/delete/{id}")
    public String getDeletePage(Model model ,@PathVariable long id ) {
        System.out.println("run here getDeletePage ");
        model.addAttribute("id", id) ;
        model.addAttribute("userFormData", new User()) ;
        return "/admin/user/delete";
    }
    // URL -> nhận data delete user
    @PostMapping(value = "/admin/user/delete")
    public String postDeleteAUserPage(Model model, @ModelAttribute("userFormData") User userFormData) {
        // userFormData : chỉ có thông tin : id
       this.userService.deleteUserById(userFormData.getId());
       return "redirect:/admin/user";
    }
    

}
