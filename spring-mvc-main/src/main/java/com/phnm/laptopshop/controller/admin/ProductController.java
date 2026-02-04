package com.phnm.laptopshop.controller.admin;

import com.phnm.laptopshop.domain.Product;
import com.phnm.laptopshop.repository.ProductRepository;
import com.phnm.laptopshop.service.ProductService;
import com.phnm.laptopshop.service.UploadService;
import jakarta.validation.Valid;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {
    private final UploadService uploadService;

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(UploadService uploadService, ProductService productService, ProductRepository productRepository) {
        this.uploadService = uploadService;
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @GetMapping("/admin/product")
    public String getProductPage(
            Model model,
            @RequestParam("page") Optional<String> pageOptional) {
        int page = 1;

        try {
            if (pageOptional.isPresent()) {
                page = Integer.parseInt(pageOptional.get());
            }
        } catch (Exception e) {}
        Pageable pageable = PageRequest.of(page - 1, 10);

        Page<Product> productsPage = productService.getAllProducts(pageable);
        List<Product> products = productsPage.getContent();
        model.addAttribute("products", products);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsPage.getTotalPages());
        return "admin/product/index";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(@ModelAttribute("newProduct") @Valid Product newProduct,
                                BindingResult newProductBindingResult,
                                @RequestParam("productImg") MultipartFile file) {
        List<FieldError> errors = newProductBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (newProductBindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String fileName = uploadService.handleSaveUploadFile(file, "product_thumbnail");
        newProduct.setImage(fileName);
        productService.saveProduct(newProduct);
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("id", id);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(@ModelAttribute("product") @Valid Product product,
                                BindingResult productBindingResult,
                                @RequestParam("productImg") MultipartFile file) {
        List<FieldError> errors = productBindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>" + error.getField() + " - " + error.getDefaultMessage());
        }

        if (productBindingResult.hasErrors()) {
            return "admin/product/update";
        }

        Product currentProduct = productRepository.findProductById(product.getId());
        if (currentProduct != null) {
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setDetailDesc(product.getDetailDesc());
            currentProduct.setShortDesc(product.getShortDesc());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());

            if (!file.isEmpty()) {
                String fileName = uploadService.handleSaveUploadFile(file, "product_thumbnail");
                currentProduct.setImage(fileName);
            }

            productService.saveProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("product", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteProductById(product.getId());
        return "redirect:/admin/product";
    }
}
