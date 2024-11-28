package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Role;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.service.UploadFileService;
import vn.hoidanit.laptopshop.service.UserService;

@Controller
public class UserController {

    // DI : dependency Injection
    // final : giúp biến userService đảm bảo tính bất biến , biến này chỉ được gán
    // giá trị 1 lần trong hàm tạo
    private final UserService userService;
    private final ServletContext servletContext;
    private final UploadFileService uploadFileService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
            ServletContext servletContext,
            UploadFileService uploadFileService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.servletContext = servletContext;
        this.uploadFileService = uploadFileService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        model.addAttribute("eric", test);
        model.addAttribute("hoiDanIt", "from controller with model");
        // lấy ra tất cả user trong database
        List<User> temp = this.userService.getAllUsers();
        return "hello";
    }

    // URL -> trang tạo mới 1 user
    @RequestMapping("/admin/user/create") // mac dinh laf method = get
    public String getUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    // URL nhận data của form create
    @PostMapping("/admin/user/create")
    public String createUserPage(Model model,
            @ModelAttribute("newUser") @Valid User hoidanit, // để lấy các thuộc tính dc validate
            BindingResult newUserBindingResult, // để hứng lỗi
            // Lấy file
            @RequestParam("hoidanitFile") MultipartFile file) {

        // in ra lỗi lên terminal
        List<FieldError> errors = newUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        // validate
        if (newUserBindingResult.hasErrors()) {
            // nó vẫn giữ lại thông báo lỗi lên trang hiện tại
            return "/admin/user/create";
        }

        // tên file avatar
        String avatar = this.uploadFileService.handleSaveUploadFile(file, "avatar");
        // hashing password
        String hashPassword = this.passwordEncoder.encode(hoidanit.getPassword());
        // Lấy object Role thông qua role name
        Role roleTemp = this.userService.getRoleByName(hoidanit.getRole().getName());
        // set data
        hoidanit.setAvatar(avatar);
        hoidanit.setPassword(hashPassword);
        hoidanit.setRole(roleTemp);

        // save hoidanit
        User temp = this.userService.handleSaveUser(hoidanit);

        return "redirect:/admin/user";
    }

    // URL -> show table user
    @RequestMapping("/admin/user")
    public String getTableUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        // Lưu data và spring rồi chuyển đến view
        model.addAttribute("users1", users);
        return "admin/user/show";
    }

    // URL -> view user
    @RequestMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable long id) {
        /*
         * Lấy tham số động
         * 
         * @PathVariable long id -> mục đích là để cho URL động , thay đổi theo id của
         * user
         *
         */
        User user = this.userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    // URL -> update user
    @RequestMapping("/admin/user/update/{id}") // mac dinh laf method = get
    public String getUpdatePage(Model model, @PathVariable long id) {
        System.out.println("run here getUpdatePage ");
        User currentUser = this.userService.getUserById(id);
        model.addAttribute("updateUser", currentUser);

        return "admin/user/update";
    }

    // URL -> nhận data update user
    @PostMapping(value = "/admin/user/update")
    public String updateUserPage(Model model,
            @ModelAttribute("updateUser") @Valid User userFormData,
            BindingResult updateUserBindingResult) {

        // hiển thị lỗi lên terminal
        List<FieldError> errors = updateUserBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        // chuyển trang khi có lỗi
        if (updateUserBindingResult.hasErrors()) {
            // đẩy lại data đẫ có trước đó -> lên view
            // vì là chúng ta validate fullName -> sẽ không lưu trữ tên cũ nữa -> để nó
            // trống -> để còn hiển thị lỗi chứ
            User userFromDatabase = this.userService.getUserById(userFormData.getId());
            userFormData.setEmail(userFromDatabase.getEmail());
            userFormData.setAddress(userFromDatabase.getAddress());
            userFormData.setPhone(userFromDatabase.getPhone());
            userFormData.setRole(userFromDatabase.getRole());
            model.addAttribute("updateUser", userFormData);
            return "admin/user/update";
        }
        // userFormData : chỉ có các thông tin : fullName , address ,phone , id , còn
        // lại là null
        User currentUser = this.userService.getUserById(userFormData.getId());
        if (currentUser != null) {
            // set lại các giá trị cần update cho currentUser(!password,!email)
            currentUser.setFullName(userFormData.getFullName());
            currentUser.setAddress(userFormData.getAddress());
            currentUser.setPhone(userFormData.getPhone());
            // Save role
            // Lấy đối tượng role by role name
            Role roleTemp = this.userService.getRoleByName(userFormData.getRole().getName());
            currentUser.setRole(roleTemp);
        }
        User temp = this.userService.handleSaveUser(currentUser);
        System.out.println(temp);

        return "redirect:/admin/user";
    }

    // URL -> delte user
    @GetMapping("/admin/user/delete/{id}")
    public String getDeletePage(Model model, @PathVariable long id) {
        System.out.println("run here getDeletePage ");
        model.addAttribute("id", id);
        model.addAttribute("userFormData", new User());
        return "admin/user/delete";
    }

    // URL -> nhận data delete user
    @PostMapping(value = "/admin/user/delete")
    public String postDeleteAUserPage(Model model, @ModelAttribute("userFormData") User userFormData) {
        // userFormData : chỉ có thông tin : id
        this.userService.deleteUserById(userFormData.getId());
        return "redirect:/admin/user";
    }

}
