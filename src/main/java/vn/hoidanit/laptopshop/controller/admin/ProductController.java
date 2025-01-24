package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.hoidanit.laptopshop.domain.Product;
import vn.hoidanit.laptopshop.service.ProductService;
import vn.hoidanit.laptopshop.service.UploadFileService;

@Controller
public class ProductController {
    // DI
    private final UploadFileService uploadFileService;
    private final ProductService productService;

    public ProductController(UploadFileService uploadFileService,
            ProductService productService) {
        this.productService = productService;
        this.uploadFileService = uploadFileService;
    }

    @GetMapping("/admin/product")
    public String getProduct(Model model,
            @RequestParam("page") Optional<String> pageOptional // lấy tham số query string : page - lưu vào page có nó
                                                                // kdl là int
    ) {
        int page = 1;

        // bỏ vào try catch vì nếu người dùng nhập string -> ép kiểu dữ liệu thất bại ->
        // nó chỉ ném ra exception thôi không dùng chương trình lại
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
            // page = 1
        } catch (Exception e) {
            // page = 1
            // TODO: handle exception
        }

        // chuyển page type int sang type pageable
        Pageable pageable = PageRequest.of(page - 1, 2); // size = 3 ; page : chạy từ 0 -> n-1 page
        Page<Product> products = this.productService.getAllProduct(pageable);
        List<Product> litsProducts = products.getContent(); // lấy nội dung của products chuyển vào listProducts và kdl
                                                            // trả về của getContent là List
        model.addAttribute("products", litsProducts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());

        return "admin/product/show";
    }

    @GetMapping("/admin/product/create")
    public String getProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProductPage(Model model,
            @ModelAttribute("newProduct") @Valid Product newProduct,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // Hứng lỗi
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>" + error.getField() + " - " + error.getDefaultMessage());
        }
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }
        // validate

        // lưu ảnh -> product và hứng tên ảnh
        String image = this.uploadFileService.handleSaveUploadFile(file, "product");
        newProduct.setImage(image);
        // lưu product xuống database
        Product temp = this.productService.handleSaveProduct(newProduct);
        return "redirect:/admin/product";
    }

    // delete
    @GetMapping("/admin/product/delete/{id}")
    public String getDeletePage(Model model,
            @ModelAttribute("userFormData") Product userFormData,
            @PathVariable long id,
            @RequestParam("page") Optional<String> pageOptional) {

        // khi page != số -> chuyển về trang 1 >< chuyển về trang vừa trước đó mà người
        // dùng đã truy cập

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        model.addAttribute("userFormData", userFormData);
        model.addAttribute("id", id);
        model.addAttribute("page", page);

        return "admin/product/delete";
    }

    @PostMapping(value = "/admin/product/delete")
    public String deleteAProduct(Model model,
            @ModelAttribute("userFormData") Product userFormData) {
        // userFormData : chỉ chứa id của product
        this.productService.handleDeleteProduct(userFormData.getId());
        return "redirect:/admin/product"; // để nó cập nhập lại table product
    }

    // view
    @GetMapping("/admin/product/{id}")
    public String getDetailPage(Model model,
            @PathVariable long id,
            @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        Product productGoDetail = this.productService.getProductById(id);
        model.addAttribute("productGoDetail", productGoDetail);
        model.addAttribute("id", id);
        model.addAttribute("page", page);

        return "admin/product/detail";
    }

    // update
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model,
            @PathVariable long id,
            @RequestParam("page") Optional<String> pageOptional) {

        int page = 1;
        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
        model.addAttribute("page", page);

        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            // update new image
            if (!file.isEmpty()) {
                String img = this.uploadFileService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());

            this.productService.handleSaveProduct(currentProduct);
        }

        return "redirect:/admin/product";
    }
}
