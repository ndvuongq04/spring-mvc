package vn.hoidanit.laptopshop.controller.admin;

import java.util.List;
import java.util.Optional;

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
    public String getProduct(Model model) {
        // -> lấy tất cả các product -> chuyển đến view
        List<Product> products = this.productService.getAllProduct();
        model.addAttribute("products", products);
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
            @PathVariable long id) {
        model.addAttribute("userFormData", userFormData);
        model.addAttribute("id", id);
        return "/admin/product/delete";
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
    public String getDetailPage(Model model, @PathVariable long id) {
        Product productGoDetail = this.productService.getProductById(id);
        model.addAttribute("productGoDetail", productGoDetail);
        model.addAttribute("id", id);
        return "/admin/product/detail";
    }

    // update
    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
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
