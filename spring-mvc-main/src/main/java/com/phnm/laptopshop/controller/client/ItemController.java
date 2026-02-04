package com.phnm.laptopshop.controller.client;

import com.phnm.laptopshop.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getDetailProductPage(Model model, @PathVariable("id") long id) {
        model.addAttribute("product", productService.getProductById(id));
        return "client/product/detail";
    }

    @PostMapping("/add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        productService.addProductToCart(email, id, session, 1);
        return "redirect:/";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteProductFromCart(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        productService.deleteCartDetail(id, session);
        return "redirect:/cart";
    }

    @PostMapping("/add-product-to-cart-from-detail")
    public String addProductToCartFromDetail(
            HttpServletRequest request,
            @RequestParam("id") long id,
            @RequestParam("quantity") long quantity) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        productService.addProductToCart(email, id, session, quantity);
        return "redirect:/product/" + id;
    }
}
